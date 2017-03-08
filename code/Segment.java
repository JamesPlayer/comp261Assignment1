import java.util.List;

/**
 * A road segment between two nodes
 * @author james
 *
 */
public class Segment {
	
	private int roadId;
	
	private int length;
	
	private int nodeId1;
	
	private int nodeId2;
	
	private List<Location> coords;

	/**
	 * @return the roadId
	 */
	public int getRoadId() {
		return roadId;
	}

	/**
	 * @param roadId the roadId to set
	 */
	public void setRoadId(int roadId) {
		this.roadId = roadId;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @return the nodeId1
	 */
	public int getNodeId1() {
		return nodeId1;
	}

	/**
	 * @param nodeId1 the nodeId1 to set
	 */
	public void setNodeId1(int nodeId1) {
		this.nodeId1 = nodeId1;
	}

	/**
	 * @return the nodeId2
	 */
	public int getNodeId2() {
		return nodeId2;
	}

	/**
	 * @param nodeId2 the nodeId2 to set
	 */
	public void setNodeId2(int nodeId2) {
		this.nodeId2 = nodeId2;
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
	 * @param roadId
	 * @param length
	 * @param nodeId1
	 * @param nodeId2
	 * @param coords
	 */
	public Segment(int roadId, int length, int nodeId1, int nodeId2, List<Location> coords) {
		this.roadId = roadId;
		this.length = length;
		this.nodeId1 = nodeId1;
		this.nodeId2 = nodeId2;
		this.coords = coords;
	}

}
