import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Set;

/**
 * An intersection between two or more road segments
 * @author james
 *
 */
public class Node {
	
	private static final int SQUARE_SIZE = 1;
	
	private static final int HIGHLIGHT_SIZE = 7;
	
	private static final Color SQUARE_COLOR = Color.BLUE;
	
	private static final Color HIGHLIGHT_COLOR = Color.GREEN;

	private int id;
	
	private double lat;
	
	private double lon;
	
	private Set<Segment> outSegs;
	
	private Set<Segment> inSegs;
	
	private Set<Node> neighbours;

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
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * @return the lon
	 */
	public double getLon() {
		return lon;
	}

	/**
	 * @param lon the lon to set
	 */
	public void setLon(double lon) {
		this.lon = lon;
	}

	/**
	 * @return the outSegs
	 */
	public Set<Segment> getOutSegs() {
		return outSegs;
	}

	/**
	 * @param outSegs the outSegs to set
	 */
	public void setOutSegs(Set<Segment> outSegs) {
		this.outSegs = outSegs;
	}

	/**
	 * @return the inSegs
	 */
	public Set<Segment> getInSegs() {
		return inSegs;
	}

	/**
	 * @param inSegs the inSegs to set
	 */
	public void setInSegs(Set<Segment> inSegs) {
		this.inSegs = inSegs;
	}
	
	/**
	 * Add a segment to list of outgoing segments
	 * @param segment
	 */
	public void addOutSeg(Segment segment) {
		this.outSegs.add(segment);
	}
	
	/**
	 * Add a segment to list of incoming segments
	 * @param segment
	 */
	public void addInSeg(Segment segment) {
		this.inSegs.add(segment);
	}

	/**
	 * @return the neighbours
	 */
	public Set<Node> getNeighbours() {
		return neighbours;
	}

	/**
	 * @param neighbours the neighbours to set
	 */
	public void setNeighbours(Set<Node> neighbours) {
		this.neighbours = neighbours;
	}
	
	public void addNeighbour(Node neighbour) {
		this.neighbours.add(neighbour);
	}

	/**
	 * @param id
	 * @param location
	 * @param outSegs
	 * @param inSegs
	 * @param neighbours
	 */
	public Node(int id, double lat, double lon, Set<Segment> outSegs, Set<Segment> inSegs, Set<Node> neighbours) {
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.outSegs = outSegs;
		this.inSegs = inSegs;
		this.neighbours = neighbours;
	}

	public void draw(Graphics g, Location origin, double scale) {
		g.setColor(SQUARE_COLOR);
		Location location = Location.newFromLatLon(lat, lon);
		Point point = location.asPoint(origin, scale);
		g.fillRect(point.x, point.y, SQUARE_SIZE, SQUARE_SIZE);
	}
	
	public void highlight(Graphics g, Location origin, double scale) {
		g.setColor(HIGHLIGHT_COLOR);
		Location location = Location.newFromLatLon(lat, lon);
		Point point = location.asPoint(origin, scale);
		g.fillRect(point.x-(HIGHLIGHT_SIZE/2), point.y-(HIGHLIGHT_SIZE/2), HIGHLIGHT_SIZE, HIGHLIGHT_SIZE);
	}
}
