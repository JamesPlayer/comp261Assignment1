public class LoadGraphResult {
	public final double northernMostLat;
	public final double westernMostLon;
	public final double southernMostLat;
	public final double easternMostLon;
	public final int numNodes;
	public final int numRoads;
	public final int numSegments;
	public final String result;
	
	/**
	 * @param northernMostLat
	 * @param westernMostLon
	 * @param southernMostLat
	 * @param easternMostLon
	 * @param numNodes
	 * @param numRoads
	 * @param numSegments
	 */
	public LoadGraphResult(double northernMostLat, double westernMostLon, double southernMostLat, double easternMostLon,
			int numNodes, int numRoads, int numSegments, String result) {
		this.northernMostLat = northernMostLat;
		this.westernMostLon = westernMostLon;
		this.southernMostLat = southernMostLat;
		this.easternMostLon = easternMostLon;
		this.numNodes = numNodes;
		this.numRoads = numRoads;
		this.numSegments = numSegments;
		this.result = result;
	}
	
	
}
