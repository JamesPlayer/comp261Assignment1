import java.util.List;

/**
 * An intersection between two or more road segments
 * @author james
 *
 */
public class Node {

	private int id;
	
	private Location location;
	
	private List<Segment> outSegs;
	
	private List<Segment> inSegs;

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
	public List<Segment> getOutSegs() {
		return outSegs;
	}

	/**
	 * @param outSegs the outSegs to set
	 */
	public void setOutSegs(List<Segment> outSegs) {
		this.outSegs = outSegs;
	}

	/**
	 * @return the inSegs
	 */
	public List<Segment> getInSegs() {
		return inSegs;
	}

	/**
	 * @param inSegs the inSegs to set
	 */
	public void setInSegs(List<Segment> inSegs) {
		this.inSegs = inSegs;
	}

	/**
	 * @param id
	 * @param location
	 * @param outSegs
	 * @param inSegs
	 */
	public Node(int id, Location location, List<Segment> outSegs, List<Segment> inSegs) {
		this.id = id;
		this.location = location;
		this.outSegs = outSegs;
		this.inSegs = inSegs;
	}
}
