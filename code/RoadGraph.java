import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Graph containing roads, segments and intersections
 * @author james
 *
 */
public class RoadGraph {
	
	private Map<Integer, Node> nodes;
	
	private Set<Segment> segments;
	
	private Map<Integer, Road> roads;

	/**
	 * @return the nodes
	 */
	public Map<Integer, Node> getNodes() {
		return nodes;
	}

	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(Map<Integer, Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * @return the segments
	 */
	public Set<Segment> getSegments() {
		return segments;
	}

	/**
	 * @param segments the segments to set
	 */
	public void setSegments(Set<Segment> segments) {
		this.segments = segments;
	}

	/**
	 * @return the roads
	 */
	public Map<Integer, Road> getRoads() {
		return roads;
	}

	/**
	 * @param roads the roads to set
	 */
	public void setRoads(Map<Integer, Road> roads) {
		this.roads = roads;
	}

	/**
	 * Constructor
	 */
	public RoadGraph() {
		nodes = new HashMap<Integer, Node>();
		segments = new HashSet<Segment>();
		roads = new HashMap<Integer, Road>();
	}

	public void load(File nodesFile, File roadsFile, File segmentsFile) throws FileNotFoundException, IOException {
		loadNodes(nodesFile);
		loadRoads(roadsFile);
		loadSegments(segmentsFile);
	}

	private void loadSegments(File segmentsFile) throws NumberFormatException, IOException {
		BufferedReader data = new BufferedReader(new FileReader(segmentsFile)); 
		String line;
		int id = 1;
		
		while ((line = data.readLine()) != null) {
			String[] values = line.split("\t");
			
			// Since we don't have a segmentId from data, create one by incrementing an int
			id++;
			
			double length				= Double.parseDouble(values[1]);
			Road road					= roads.get(Integer.parseInt(values[0]));
			Node startNode				= nodes.get(Integer.parseInt(values[2]));
			Node endNode				= nodes.get(Integer.parseInt(values[3]));
			List<Location> coords		= new ArrayList<Location>();
			
			// Add coords
			for (int i=4; i<values.length; i=i+2) {
				Location location = new Location(Double.parseDouble(values[i]), Double.parseDouble(values[i+1]));
				coords.add(location);
			}
			
			// Create segment
			Segment segment = new Segment(id, length, road, startNode, endNode, coords);
			
			// Add outgoing segment to start node
			startNode.addOutSeg(segment);
			
			// Add incoming segment to end node
			endNode.addInSeg(segment);
			
			// Add segment to road
			road.addSegment(segment);
			
			// If road is not one-way then add reverse directions for nodes
			if (!road.isOneWay()) {
				startNode.addInSeg(segment);
				endNode.addOutSeg(segment);
			}
		}
	}
	
	
	private void addSegmentToRoad(Road road, Segment segment) {
		
	}
	
	/**
	 * Load roads data from a file
	 * @param roadsFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void loadRoads(File roadsFile) throws FileNotFoundException, IOException {
		BufferedReader data = new BufferedReader(new FileReader(roadsFile)); 
		
		// Skip first line
		String line = data.readLine();
		
		while ((line = data.readLine()) != null) {
			String[] values 	= line.split("\t");
			int id 				= Integer.parseInt(values[0]);
			String name 		= values[2];
			boolean isOneWay 	= values[4] != "0";
			int speedLimit 		= Integer.parseInt(values[5]);
			
			// Create road
			Road road = new Road(id, name, new ArrayList<Segment>(), isOneWay, speedLimit);
			
			// Add to road map
			roads.put(id, road);
		}	
	}

	/**
	 * Load nodes data from a file
	 * @param nodesFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void loadNodes(File nodesFile) throws FileNotFoundException, IOException {
		BufferedReader data = new BufferedReader(new FileReader(nodesFile)); 
		String line;
		
		while ((line = data.readLine()) != null) {
			String[] values 	= line.split("\t");
			int id 				= Integer.parseInt(values[0]);
			double lat			= Double.parseDouble(values[1]);
			double lon			= Double.parseDouble(values[2]);
			Location location   = new Location(lat, lon);
			
			// Create node
			Node node = new Node(id, location, new ArrayList<Segment>(), new ArrayList<Segment>());
			
			// Add to nodes map
			nodes.put(id, node);
		}
	}
	
	

}
