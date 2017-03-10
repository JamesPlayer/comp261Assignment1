import java.util.List;

/**
 * A complete road
 * @author james
 *
 */
public class Road {
	
	private int id;
	
	private String name;
	
	private List<Segment> segments;
	
	private boolean isOneWay;
	
	private int speedLimit;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the segments
	 */
	public List<Segment> getSegments() {
		return segments;
	}

	/**
	 * @param segments the segments to set
	 */
	public void setSegments(List<Segment> segments) {
		this.segments = segments;
	}

	/**
	 * @return the isOneWay
	 */
	public boolean isOneWay() {
		return isOneWay;
	}

	/**
	 * @param isOneWay the isOneWay to set
	 */
	public void setOneWay(boolean isOneWay) {
		this.isOneWay = isOneWay;
	}

	/**
	 * @return the speedLimit
	 */
	public int getSpeedLimit() {
		return speedLimit;
	}

	/**
	 * @param speedLimit the speedLimit to set
	 */
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
	}
	
	/**
	 * Add segment to list of segments for this road
	 * @param segment
	 */
	public void addSegment(Segment segment) {
		segments.add(segment);
	}

	/**
	 * @param id
	 * @param name
	 * @param segments
	 * @param isOneWay
	 * @param speedLimit
	 */
	public Road(int id, String name, List<Segment> segments, boolean isOneWay, int speedLimit) {
		this.id = id;
		this.name = name;
		this.segments = segments;
		this.isOneWay = isOneWay;
		this.speedLimit = speedLimit;
	}

		
}