import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Store road names with minimal space and quick retrieval
 * @author james
 *
 */
public class RoadTrie {
	
	private Road value = null;
	
	private boolean marked = false;
	
	private Map<Character, RoadTrie> children;

	/**
	 * @return the value
	 */
	public Road getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Road value) {
		this.value = value;
	}

	/**
	 * @return the marked
	 */
	public boolean isMarked() {
		return marked;
	}

	/**
	 * @param marked the marked to set
	 */
	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	/**
	 * @return the children
	 */
	public Map<Character, RoadTrie> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(Map<Character, RoadTrie> children) {
		this.children = children;
	}
	
	public void addChild(Character c, RoadTrie node) {
		this.children.put(c, node);
	}

	/**
	 * Constructor
	 */
	public RoadTrie() {
		this.value = null;
		this.marked = false;
		this.children = new HashMap<Character, RoadTrie>();
	}
	
	/**
	 * Add a new word to the trie
	 * @param word
	 */
	public void add(Road road) {
		RoadTrie node = this;
		for (char c : road.getName().toCharArray()) {
			if (!node.getChildren().containsKey(c)) {
				node.addChild(c, new RoadTrie());
			}
			node = node.getChildren().get(c);
		}
		node.setMarked(true);
		node.setValue(road);
	}
	
	/**
	 * Get all words starting with passed prefix
	 * @param prefix
	 * @return List<Road> values
	 */
	public RoadTrieGetAllResult getAll(String prefix) {
		RoadTrie node = this;
		List<Road> values = new ArrayList<Road>();
		
		for (Character c : prefix.toCharArray()) {
			node = node.getChildren().get(c);
			
			// Null check
			if (node == null) return new RoadTrieGetAllResult(values, null);
		}
		getAllFromNode(node, values);
		return new RoadTrieGetAllResult(values, node);
	}
	
	/**
	 * Add words of passed node onto the passed list
	 * @param node
	 * @param values
	 */
	private static void getAllFromNode(RoadTrie node, List<Road> values) {
		
		if (node == null) return;
		
		if (node.isMarked()) {
			values.add(node.getValue());
		}
		
		for (Character c : node.getChildren().keySet()) {
			getAllFromNode(node.getChildren().get(c), values);
		}
	}
	
	
	

}
