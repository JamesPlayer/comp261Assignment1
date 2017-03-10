public class LoadNodesResult {

	public final double northernMostLat;
	public final double westernMostLon;
	public final double southernMostLat;
	public final double easternMostLon;
	public final int count;
	
	/**
	 * @param northernMostLat
	 * @param westernMostLon
	 * @param southernMostLat
	 * @param easternMostLon
	 * @param count
	 */
	public LoadNodesResult(double northernMostLat, double westernMostLon, double southernMostLat, double easternMostLon,
			int count) {
		this.northernMostLat = northernMostLat;
		this.westernMostLon = westernMostLon;
		this.southernMostLat = southernMostLat;
		this.easternMostLon = easternMostLon;
		this.count = count;
	}
	
	
}
