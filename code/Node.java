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
	
	private Location location;
	
	private Set<Segment> outSegs;
	
	private Set<Segment> inSegs;

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
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
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
	 * @param id
	 * @param location
	 * @param outSegs
	 * @param inSegs
	 */
	public Node(int id, Location location, Set<Segment> outSegs, Set<Segment> inSegs) {
		this.id = id;
		this.location = location;
		this.outSegs = outSegs;
		this.inSegs = inSegs;
	}

	public void draw(Graphics g, Location origin, double scale) {
		g.setColor(SQUARE_COLOR);
		Point point = location.asPoint(origin, scale);
		g.fillRect(point.x, point.y, SQUARE_SIZE, SQUARE_SIZE);
	}
	
	public void highlight(Graphics g, Location origin, double scale) {
		g.setColor(HIGHLIGHT_COLOR);
		Point point = location.asPoint(origin, scale);
		g.fillRect(point.x-(HIGHLIGHT_SIZE/2), point.y-(HIGHLIGHT_SIZE/2), HIGHLIGHT_SIZE, HIGHLIGHT_SIZE);
	}
}
