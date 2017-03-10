import java.util.List;

/**
 * A road segment between two nodes
 * @author james
 *
 */
public class Segment {
	
	private int id;
	
	private double length;

	private Road road;
	
	private Node startNode;
	
	private Node endNode;
	
	private List<Location> coords;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the length
	 */
	public double getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(double length) {
		this.length = length;
	}

	/**
	 * @return the road
	 */
	public Road getRoad() {
		return road;
	}

	/**
	 * @param road the road to set
	 */
	public void setRoad(Road road) {
		this.road = road;
	}

	/**
	 * @return the startNode
	 */
	public Node getStartNode() {
		return startNode;
	}

	/**
	 * @param startNode the startNode to set
	 */
	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}

	/**
	 * @return the endNode
	 */
	public Node getEndNode() {
		return endNode;
	}

	/**
	 * @param endNode the endNode to set
	 */
	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}

	/**
	 * @return the coords
	 */
	public List<Location> getCoords() {
		return coords;
	}

	/**
	 * @param coords the coords to set
	 */
	public void setCoords(List<Location> coords) {
		this.coords = coords;
	}

	/**
	 * @param id
	 * @param length
	 * @param road
	 * @param startNode
	 * @param endNode
	 * @param coords
	 */
	public Segment(int id, double length, Road road, Node startNode, Node endNode, List<Location> coords) {
		this.id = id;
		this.length = length;
		this.road = road;
		this.startNode = startNode;
		this.endNode = endNode;
		this.coords = coords;
	}

	

}
