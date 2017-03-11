import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AucklandMap extends GUI {
	
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
		
		// Draw segments
		for (Segment segment : roadGraph.getSegments()) {
			segment.draw(g, origin, scale);
		}
		
		// Draw intersections (nodes)
		for (Node node : roadGraph.getNodes().values()) {
			node.draw(g, origin, scale);
		}
		
		if (highlightedNode != null) {
			highlightedNode.highlight(g, origin, scale);
		}
		
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
		double closestDistance = -1.0;
		
		for (Node node : roadGraph.getNodes().values()) {
			double nodeDistance = clickLocation.distance(node.getLocation());
			if (closestDistance == -1.0 || nodeDistance < closestDistance) {
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
		
		// Remove any existing highlighted segments
		highlightedSegments.clear();
		
		String roadName = getSearchBox().getText();
		Set<Road> foundRoads = new HashSet<Road>();
		
		// Find road with this exact name
		for (Road road : roadGraph.getRoads().values()) {
			if (road.getName().equals(roadName)) {
				foundRoads.add(road);
			}
		}
		
		// Handle no road found
		if (foundRoads.isEmpty()) {
			getTextOutputArea().append("No roads matched your search\n");
			return;
		}
		
		// Add all segments from roads that were found
		int count = 0;
		for (Road road : foundRoads) {			
			highlightedSegments.addAll(road.getSegments());
			count++;
		}
		getTextOutputArea().append(String.format("Highlighted %d road(s) matching your search\n", count));
	}

	@Override
	protected void onMove(Move m) {
		
		Point oldPoint = origin.asPoint(origin, scale);
		
		// NORTH, SOUTH, EAST, WEST, ZOOM_IN, ZOOM_OUT
		switch (m) {
			case NORTH:
				origin = Location.newFromPoint(new Point(oldPoint.x, oldPoint.y-20), origin, scale);
				break;
			case SOUTH:
				origin = Location.newFromPoint(new Point(oldPoint.x, oldPoint.y+20), origin, scale);
				break;
			case EAST:
				origin = Location.newFromPoint(new Point(oldPoint.x+20, oldPoint.y), origin, scale);
				break;
			case WEST:
				origin = Location.newFromPoint(new Point(oldPoint.x-20, oldPoint.y), origin, scale);
				break;
			case ZOOM_IN:
				scale = scale*1.2;
				break;
			case ZOOM_OUT:
				scale = scale/1.2;
				break;
			default:
		}
	}

	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons) {
		try {
			
			// Load files data into graph
			LoadGraphResult loadGraphResult = roadGraph.load(nodes, roads, segments);
			getTextOutputArea().append(loadGraphResult.result + "\n");
			
			// Get topLeft and bottomRight points to get initial origin and scale
			Location topLeft = Location.newFromLatLon(loadGraphResult.northernMostLat, loadGraphResult.westernMostLon);
			Location bottomRight = Location.newFromLatLon(loadGraphResult.southernMostLat, loadGraphResult.easternMostLon);
			double geoLength = topLeft.y - bottomRight.y;
			
			// Set initial origin and scale
			origin = Location.newFromLatLon(loadGraphResult.northernMostLat, loadGraphResult.westernMostLon);
			scale = 400 / geoLength;
			
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
