import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AucklandMap extends GUI {
	
	private RoadGraph roadGraph;
	
	private Location origin;
	
	private double scale;

	/**
	 * Constructor
	 */
	public AucklandMap() {
		scale = 200.0;
		roadGraph = new RoadGraph();
	}

	@Override
	protected void redraw(Graphics g) {
		for (Node node : roadGraph.getNodes().values()) {
			node.draw(g, origin, scale);
		}

	}

	@Override
	protected void onClick(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onSearch() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMove(Move m) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons) {
		try {
			LoadGraphResult loadGraphResult = roadGraph.load(nodes, roads, segments);
			getTextOutputArea().append(loadGraphResult.result + "\n");
			
			Location topLeft = Location.newFromLatLon(loadGraphResult.northernMostLat, loadGraphResult.westernMostLon);
			Location bottomRight = Location.newFromLatLon(loadGraphResult.southernMostLat, loadGraphResult.easternMostLon);
			double geoLength = topLeft.y - bottomRight.y;
			
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
