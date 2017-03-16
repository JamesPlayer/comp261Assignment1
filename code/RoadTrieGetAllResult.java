import java.util.List;

public class RoadTrieGetAllResult {
	
	public final List<Road> roads;
	public final RoadTrie node;
	
	/**
	 * @param roads
	 * @param node
	 */
	public RoadTrieGetAllResult(List<Road> roads, RoadTrie node) {
		this.roads = roads;
		this.node = node;
	}
}
