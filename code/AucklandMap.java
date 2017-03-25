import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.plaf.basic.ComboPopup;

public class AucklandMap extends GUI {
	
	private static final double ZOOM_FACTOR = 0.2;
	
	private static final int MOVE_FACTOR = 20; // In pixels
	
	private RoadGraph roadGraph;
	
	private Location origin;
	
	private double scale;
		
	private Node highlightedNode;
	
	private Set<Segment> highlightedSegments;
	

	/**
	 * Constructor
	 */
	public AucklandMap() {
		roadGraph = new RoadGraph();
		highlightedNode = null;
		highlightedSegments = new HashSet<Segment>();
	}

	@Override
	protected void redraw(Graphics g) {
		
		// Draw polygons
		for (Polygon polygon : roadGraph.getPolygons()) {
			polygon.draw(g, origin, scale);
		}
		
		// Draw segments
		for (Segment segment : roadGraph.getSegments()) {
			segment.draw(g, origin, scale);
		}
		
		// Draw intersections (nodes)
		for (Node node : roadGraph.getNodes().values()) {
			node.draw(g, origin, scale);
		}
		
		// Draw highlighted intersection
		if (highlightedNode != null) {
			highlightedNode.highlight(g, origin, scale);
		}
		
		// Draw highlighted segments
		for (Segment segment : highlightedSegments) {
			segment.highlight(g, origin, scale);
		}
	}

	@Override
	protected void onClick(MouseEvent e) {
		
		// Turn the mouse click coords into a location
		Location clickLocation = Location.newFromPoint(new Point(e.getX(), e.getY()), origin, scale);
		
		// Get the closest node
		Node closestNode = null;
		Double closestDistance = null;
		
		for (Node node : roadGraph.getNodes().values()) {
			double nodeDistance = clickLocation.distance(node.getLocation());
			if (closestDistance == null || nodeDistance < closestDistance) {
				closestNode = node;
				closestDistance = nodeDistance;
			}
		}
		
		if (closestNode == null) return;
		
		// Show some info about the intersection
		getTextOutputArea().append(String.format("Intersection id: %d\n", closestNode.getId()));
		
		// Highlight it on the page
		highlightedNode = closestNode;
		
		// Find the roads at the intersection and display the info
		Set<Segment> segments = new HashSet<Segment>(closestNode.getInSegs());
		segments.addAll(closestNode.getOutSegs());
		Set<String> roads = new HashSet<String>();
		
		for (Segment segment : segments) {
			roads.add(segment.getRoad().getName());
		}
		
		getTextOutputArea().append(String.format("Roads from this intersection: %s\n", roads.toString()));
	}

	@Override
	protected void onSearch() {
		String prefix = getSearchBox().getText();
		RoadTrieGetAllResult searchResults = roadGraph.getRoadsWithPrefix(prefix);
		updateSearchResults(searchResults);
	}
	
	protected void onComboSelection(JComboBox comboBox, ActionEvent e) {
		Road road = (Road) comboBox.getSelectedItem();
		if (road == null) return;
		
		// Remove any existing highlighted segments
		highlightedSegments.clear();
		highlightedSegments.addAll(road.getSegments());
		getTextOutputArea().append(String.format("Highlighted %s\n", road.getName()));
	}
	
	@Override
	protected void onComboKeyPressed(JComboBox comboBox, JTextField editor, KeyEvent e) {
		String prefix = editor.getText();
		RoadTrieGetAllResult searchResults = roadGraph.getRoadsWithPrefix(prefix);
		updateSearchResults(searchResults);
		List<Road> foundRoads = searchResults.roads;
		
		// Update combo box options without it setting a new default value
		removeComboBoxActionListener();
		comboBox.removeAllItems();	
		for (Road road : foundRoads) {
			comboBox.addItem(road);
		}
		editor.setText(prefix);
		addComboBoxActionListener();
		comboBox.showPopup();
	}
	
	protected void updateSearchResults(RoadTrieGetAllResult searchResults) {
		
		// Remove any existing highlighted segments
		highlightedSegments.clear();
		
		List<Road> foundRoads = searchResults.roads;
		boolean isExactMatch = searchResults.node != null ? searchResults.node.isMarked() : false;
		
		// If it's an exact match then only add the exact road to results
		if (isExactMatch) {
			foundRoads.clear();
			foundRoads.add(searchResults.node.getValue());
		}
		
		// Handle no road found
		if (foundRoads == null || foundRoads.isEmpty()) {
			getTextOutputArea().append("No roads matched your search\n");
			return;
		}
		
		// Add all segments from roads that were found
		getTextOutputArea().append(String.format("Highlighted %d road(s) matching your search:\n", foundRoads.size()));
		for (Road road : foundRoads) {			
			highlightedSegments.addAll(road.getSegments());
			getTextOutputArea().append(String.format("%s\n", road.getName()));
		}
	}

	@Override
	protected void onMove(Move m) {
		
		Point oldPoint = origin.asPoint(origin, scale);
		
		// NORTH, SOUTH, EAST, WEST, ZOOM_IN, ZOOM_OUT
		switch (m) {
			case NORTH:
				origin = Location.newFromPoint(new Point(oldPoint.x, oldPoint.y-MOVE_FACTOR), origin, scale);
				break;
			case SOUTH:
				origin = Location.newFromPoint(new Point(oldPoint.x, oldPoint.y+MOVE_FACTOR), origin, scale);
				break;
			case EAST:
				origin = Location.newFromPoint(new Point(oldPoint.x+MOVE_FACTOR, oldPoint.y), origin, scale);
				break;
			case WEST:
				origin = Location.newFromPoint(new Point(oldPoint.x-MOVE_FACTOR, oldPoint.y), origin, scale);
				break;
			case ZOOM_IN:
				setOriginFromZoom(1 - ZOOM_FACTOR);
				scale = scale/(1 - ZOOM_FACTOR);
				break;
			case ZOOM_OUT:
				setOriginFromZoom(1 + ZOOM_FACTOR);
				scale = scale/(1 + ZOOM_FACTOR);
				break;
			default:
		}
	}
	
	private void setOriginFromZoom(double zoom) {
		int height = (int) getDrawingAreaDimension().getHeight();
		int width = (int) getDrawingAreaDimension().getWidth();
		List<Point> vertices = new ArrayList<Point>();
		List<Location> loc = new ArrayList<Location>();
		double locWidth;
		double locHeight;
		double dx, dy;
		
		vertices.add(new Point(0,0));
		vertices.add(new Point(0,height));
		vertices.add(new Point(width,0));
		vertices.add(new Point(width,height));
		
		for (int i=0; i<4; i++) {
			loc.add(Location.newFromPoint(vertices.get(i), origin, scale));
		}
		
		locWidth = loc.get(2).x - loc.get(0).x;
		locHeight = loc.get(1).y - loc.get(0).y;
		
		dx = (locWidth - locWidth*zoom) / 2;
		dy = (locHeight - locHeight*zoom) / 2;
		
		origin = origin.moveBy(dx, dy);
	}

	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons) {
		try {
			
			// Load files data into graph
			LoadGraphResult loadGraphResult = roadGraph.load(nodes, roads, segments, polygons);
			getTextOutputArea().append(loadGraphResult.result + "\n");
			
			// Get topLeft and bottomRight points to get initial origin and scale
			Location topLeft = Location.newFromLatLon(loadGraphResult.northernMostLat, loadGraphResult.westernMostLon);
			Location bottomRight = Location.newFromLatLon(loadGraphResult.southernMostLat, loadGraphResult.easternMostLon);
			double geoWidth = bottomRight.x - topLeft.x;
			
			// Set initial origin and scale
			origin = Location.newFromLatLon(loadGraphResult.northernMostLat, loadGraphResult.westernMostLon);
			scale = getDrawingAreaDimension().getWidth() / geoWidth;
			
		} catch (FileNotFoundException e) {
			getTextOutputArea().append("Could not find file :(\n");
		} catch (IOException e) {
			getTextOutputArea().append("Could not read file :(\n");
		}

	}

	public static void main(String[] args) {
		new AucklandMap();
	}

}
