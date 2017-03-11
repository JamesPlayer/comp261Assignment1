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

	/**
	 * Load in all nodes, roads and segments
	 * @param nodesFile
	 * @param roadsFile
	 * @param segmentsFile
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public LoadGraphResult load(File nodesFile, File roadsFile, File segmentsFile) throws FileNotFoundException, IOException {
		int numRoadsAdded = loadRoads(roadsFile);
		LoadNodesResult loadNodesResult = loadNodes(nodesFile);
		int numSegmentsAdded = loadSegments(segmentsFile);
		String result = String.format("Added %d roads, %d intersections and %d road segments", numRoadsAdded, loadNodesResult.count, numSegmentsAdded);
		return new LoadGraphResult(loadNodesResult.northernMostLat, loadNodesResult.westernMostLon, loadNodesResult.southernMostLat, loadNodesResult.easternMostLon, loadNodesResult.count, numRoadsAdded, numSegmentsAdded, result);
	}

	/**
	 * Load road segments
	 * @param segmentsFile
	 * @return Integer
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	private int loadSegments(File segmentsFile) throws FileNotFoundException, IOException {
		BufferedReader data = new BufferedReader(new FileReader(segmentsFile)); 
		int id = 1;
		int count = 0;
		
		// Skip first line
		String line = data.readLine();
		
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
				Location location = Location.newFromLatLon(Double.parseDouble(values[i]), Double.parseDouble(values[i+1]));
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
			
			// Add segment to segments
			segments.add(segment);
			
			count++;
			
			// If road is not one-way then add reverse directions for nodes
			if (!road.isOneWay()) {
				Segment reverseSegment = new Segment(++id, length, road, endNode, startNode, coords);
				startNode.addInSeg(reverseSegment);
				endNode.addOutSeg(reverseSegment);
				road.addSegment(reverseSegment);
				segments.add(reverseSegment);
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Load roads data from a file
	 * @param roadsFile
	 * @return Integer
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private int loadRoads(File roadsFile) throws FileNotFoundException, IOException {
		BufferedReader data = new BufferedReader(new FileReader(roadsFile));
		int count = 0;
		
		// Skip first line
		String line = data.readLine();
		
		while ((line = data.readLine()) != null) {
			String[] values 	= line.split("\t");
			int id 				= Integer.parseInt(values[0]);
			String name 		= values[2];
			boolean isOneWay 	= values[4] != "0";
			int speedLimit 		= Integer.parseInt(values[5]);
			
			// Create road
			Road road = new Road(id, name, new HashSet<Segment>(), isOneWay, speedLimit);
			
			// Add to road map
			roads.put(id, road);
			count++;
		}
		return count;
	}

	/**
	 * Load nodes data from a file
	 * @param nodesFile
	 * @return Integer
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private LoadNodesResult loadNodes(File nodesFile) throws FileNotFoundException, IOException {
		BufferedReader data = new BufferedReader(new FileReader(nodesFile)); 
		String line;
		
		// Initialize default extreme lats and lons to be middle of auckland
		double northernMostLat = -36.847622;
		double westernMostLon = 174.763444;
		double southernMostLat = -36.847622;
		double easternMostLon = 174.763444;
		int count = 0;
		
		while ((line = data.readLine()) != null) {
			String[] values 	= line.split("\t");
			int id 				= Integer.parseInt(values[0]);
			double lat			= Double.parseDouble(values[1]);
			double lon			= Double.parseDouble(values[2]);
			Location location   = Location.newFromLatLon(lat, lon);
			
			// Create node
			Node node = new Node(id, location, new HashSet<Segment>(), new HashSet<Segment>());
			
			// Add to nodes map
			nodes.put(id, node);
			
			if (lat > northernMostLat) {
				northernMostLat = lat;
			}
			
			if (lon < westernMostLon) {
				westernMostLon = lon;
			}
			
			if (lat < southernMostLat) {
				southernMostLat = lat;
			}
			
			if (lon > easternMostLon) {
				easternMostLon = lon;
			}
			
			count++;
		}
		return new LoadNodesResult(northernMostLat, westernMostLon, southernMostLat, easternMostLon, count);
	}
	
}
