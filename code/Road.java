import java.util.Set;

/**
 * A complete road
 * @author james
 *
 */
public class Road {
	
	private int id;
	
	private String name;
	
	private Set<Segment> segments;
	
	private boolean isOneWay;
	
	private int speedLimit;
	
	private int roadClass;

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
	 * @return the roadClass
	 */
	public int getRoadClass() {
		return roadClass;
	}

	/**
	 * @param roadClass the roadClass to set
	 */
	public void setRoadClass(int roadClass) {
		this.roadClass = roadClass;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * @param id
	 * @param name
	 * @param segments
	 * @param isOneWay
	 * @param speedLimit
	 */
	public Road(int id, String name, Set<Segment> segments, boolean isOneWay, int speedLimit, int roadClass) {
		this.id = id;
		this.name = name;
		this.segments = segments;
		this.isOneWay = isOneWay;
		this.speedLimit = speedLimit;
		this.roadClass = roadClass;
	}

		
}