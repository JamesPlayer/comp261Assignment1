
public class Restriction {

	public int nodeId1;
	
	public int roadId1;
	
	public int nodeId;
	
	public int roadId2;
	
	public int nodeId2;

	/**
	 * @param nodeId1
	 * @param roadId1
	 * @param nodeId
	 * @param roadId2
	 * @param nodeId2
	 */
	public Restriction(int nodeId1, int roadId1, int nodeId, int roadId2, int nodeId2) {
		this.nodeId1 = nodeId1;
		this.roadId1 = roadId1;
		this.nodeId = nodeId;
		this.roadId2 = roadId2;
		this.nodeId2 = nodeId2;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nodeId;
		result = prime * result + nodeId1;
		result = prime * result + nodeId2;
		result = prime * result + roadId1;
		result = prime * result + roadId2;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Restriction other = (Restriction) obj;
		if (nodeId != other.nodeId)
			return false;
		if (nodeId1 != other.nodeId1)
			return false;
		if (nodeId2 != other.nodeId2)
			return false;
		if (roadId1 != other.roadId1)
			return false;
		if (roadId2 != other.roadId2)
			return false;
		return true;
	}
	
	
	
	
}
