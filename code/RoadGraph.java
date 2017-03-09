import java.util.HashMap;
import java.util.HashSet;
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

}
