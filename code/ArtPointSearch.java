import java.util.HashSet;
import java.util.Set;

public class ArtPointSearch {
	
	protected Set<Node> nodes;
	
	protected Set<Node> artPoints = new HashSet<Node>();
	
	
	
	
	/**
	 * @param nodes
	 * @param artPoints
	 */
	public ArtPointSearch(Set<Node> nodes) {
		this.nodes = nodes;
		
		// Rest all counts and reachbacks to infinity
		for (Node node : nodes) {
			node.artPointCount = Integer.MAX_VALUE;
			node.artPointReachBack = Integer.MAX_VALUE;
		}
	}

	public Set<Node> findArtPoints() {
		
		Node start = null;
		int numSubTrees = 0;
		
		// Empty check
		if (nodes.isEmpty()) return artPoints;
		
		for (Node node : nodes) {
			start = node; break;
		}
		
		start.artPointCount = 0;
		
		for (Node neighbour : start.getNeighbours()) {
			if (neighbour.artPointCount == Integer.MAX_VALUE) {
				recArtPoints(neighbour, 1, start);
				numSubTrees++;
			}
		}
		
		if (numSubTrees > 1) {
			artPoints.add(start);
		}	
		
		return artPoints;
		
	}
	
	public int recArtPoints(Node node, int count, Node fromNode) {
		node.artPointCount = count;
		int reachBack = count;
		int childReach;
		
		for (Node neighbour : node.getNeighbours()) {
			
			// Ignore fromNode
			if (neighbour == fromNode) continue;
			
			if (neighbour.artPointCount < Integer.MAX_VALUE) {
				reachBack = Math.min(neighbour.artPointCount, reachBack);
			} else {
				childReach = recArtPoints(neighbour, count+1, node);
				if (childReach >= count) {
					artPoints.add(node);
				}
				reachBack = Math.min(childReach, reachBack);
			}
		}
		
		return reachBack;
	}

}
