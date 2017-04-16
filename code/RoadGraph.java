import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
	
	private RoadTrie roadTrie;
	
	private Set<Polygon> polygons;
	
	private Set<Restriction> restrictions;

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
	 * @return the roadTrie
	 */
	public RoadTrie getRoadTrie() {
		return roadTrie;
	}

	/**
	 * @param roadTrie the roadTrie to set
	 */
	public void setRoadTrie(RoadTrie roadTrie) {
		this.roadTrie = roadTrie;
	}

	/**
	 * @return the polygons
	 */
	public Set<Polygon> getPolygons() {
		return polygons;
	}

	/**
	 * @param polygons the polygons to set
	 */
	public void setPolygons(Set<Polygon> polygons) {
		this.polygons = polygons;
	}

	/**
	 * @return the restrictions
	 */
	public Set<Restriction> getRestrictions() {
		return restrictions;
	}

	/**
	 * @param restrictions the restrictions to set
	 */
	public void setRestrictions(Set<Restriction> restrictions) {
		this.restrictions = restrictions;
	}

	/**
	 * Constructor
	 */
	public RoadGraph() {
		nodes = new HashMap<Integer, Node>();
		segments = new HashSet<Segment>();
		roads = new HashMap<Integer, Road>();
		roadTrie = new RoadTrie();
		polygons = new HashSet<Polygon>();
		restrictions = new HashSet<Restriction>();
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
	public LoadGraphResult load(File nodesFile, File roadsFile, File segmentsFile, File polygonsFile, File restrictionsFile) throws FileNotFoundException, IOException {
		int numRoadsAdded = loadRoads(roadsFile);
		LoadNodesResult loadNodesResult = loadNodes(nodesFile);
		int numSegmentsAdded = loadSegments(segmentsFile);
		String result = String.format("Added %d roads, %d intersections and %d road segments", numRoadsAdded, loadNodesResult.count, numSegmentsAdded);
		
		// Add polygon data
		if (polygonsFile != null) {			
			loadPolygons(polygonsFile);
		}
		
		// Add restriction data
		if (restrictionsFile != null) {
			loadRestrictions(restrictionsFile);
		}
		
		return new LoadGraphResult(loadNodesResult.northernMostLat, loadNodesResult.westernMostLon, loadNodesResult.southernMostLat, loadNodesResult.easternMostLon, loadNodesResult.count, numRoadsAdded, numSegmentsAdded, result);
	}
	
	private void loadRestrictions(File restrictionsFile) throws FileNotFoundException, IOException {
		BufferedReader data = new BufferedReader(new FileReader(restrictionsFile));
		int count = 0;
		
		// Skip first line
		String line = data.readLine();
		
		while ((line = data.readLine()) != null) {
			String[] values 	= line.split("\t");
			
			Restriction restriction = new Restriction(
					Integer.parseInt(values[0]), 
					Integer.parseInt(values[1]), 
					Integer.parseInt(values[2]), 
					Integer.parseInt(values[3]), 
					Integer.parseInt(values[4]));
			
			restrictions.add(restriction);
		}
	}

	/**
	 * Add polygon data
	 * @param polygonsFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void loadPolygons(File polygonsFile) throws FileNotFoundException, IOException {
		BufferedReader data = new BufferedReader(new FileReader(polygonsFile));
		String line;
		Integer type = null;
		String coordsString;
		List<String> coordPairs;
		List<Location> coords;
		
		while ((line = data.readLine()) != null) {
			
			// Set type
			if (line.split("=").length == 2 && line.split("=")[0].equals("Type")) {
				type = Integer.decode(line.split("=")[1]);
			}
			
			// Set coords
			if (line.split("=").length == 2 && line.split("=")[0].startsWith("Data")) {
				
				coordsString = line.split("=")[1];
				coordPairs = Arrays.asList(coordsString.substring(1,coordsString.lastIndexOf(')')).split("\\),\\("));
				coords = new ArrayList<Location>();
				
				for (String coordPair : coordPairs) {
					double lat = Double.parseDouble(coordPair.split(",")[0]);
					double lon = Double.parseDouble(coordPair.split(",")[1]);
					Location location = Location.newFromLatLon(lat, lon);
					coords.add(location);
				}
				
				polygons.add(new Polygon(type, coords));
			}
		}
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
			
			// Add start node as a neighbour to end node and add end node as a neighbour to start node
			startNode.addNeighbour(endNode);
			endNode.addNeighbour(startNode);
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
		
		Map<Integer, Integer> speedLimitWeightings = new HashMap<Integer, Integer>();
		speedLimitWeightings.put(0, 5);
		speedLimitWeightings.put(1, 20);
		speedLimitWeightings.put(2, 40);
		speedLimitWeightings.put(3, 60);
		speedLimitWeightings.put(4, 80);
		speedLimitWeightings.put(5, 100);
		speedLimitWeightings.put(6, 110);
		speedLimitWeightings.put(7, 120);
		
		Map<Integer, Integer> roadClassWeightings = new HashMap<Integer, Integer>();
		roadClassWeightings.put(0, 100);
		roadClassWeightings.put(1, 110);
		roadClassWeightings.put(2, 120);
		roadClassWeightings.put(3, 130);
		roadClassWeightings.put(4, 140);
		
		while ((line = data.readLine()) != null) {
			String[] values 	= line.split("\t");
			int id 				= Integer.parseInt(values[0]);
			String name 		= values[2];
			boolean isOneWay 	= !values[4].equals("0");
			int speedLimit 		= Integer.parseInt(values[5]);
			int roadClass       = Integer.parseInt(values[6]);
			
			// Create road
			Road road = new Road(id, name, new HashSet<Segment>(), isOneWay, speedLimitWeightings.get(speedLimit), roadClassWeightings.get(roadClass));
			
			// Add to road map
			roads.put(id, road);
			
			// Add road to roadTrie 
			roadTrie.add(road);
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
			
			// Create node
			Node node = new Node(id, lat, lon, new HashSet<Segment>(), new HashSet<Segment>(), new HashSet<Node>());
			
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
	
	public RoadTrieGetAllResult getRoadsWithPrefix(String prefix) {
		return roadTrie.getAll(prefix);
		
	}
	
	public AStarFringeNode AStarSearch(Node start, Node end, Set<Restriction> restrictions, boolean isTimeBased) {
		AStarSearch aStarSearch = new AStarSearch(restrictions, isTimeBased);
		return aStarSearch.getPath(start, end);
	}
	
	public Set<Node> findArtPoints() {
		ArtPointSearch artPointSearch = new ArtPointSearch(new HashSet<Node>(nodes.values()));
		return artPointSearch.findArtPoints();
	}
}
