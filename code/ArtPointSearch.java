import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ArtPointSearch {
	
	protected Set<Node> nodes;
	
	protected Set<Node> artPoints = new HashSet<Node>();
	
	protected Stack<IterArtPointNode> iterArtPointNodes = new Stack<IterArtPointNode>();
	
	
	
	
	/**
	 * @param nodes
	 * @param artPoints
	 */
	public ArtPointSearch(Set<Node> nodes) {
		this.nodes = nodes;
		
		// Reset all counts and reachbacks to infinity
		for (Node node : nodes) {
			node.artPointCount = Integer.MAX_VALUE;
			node.artPointReachBack = Integer.MAX_VALUE;
			node.artPointChildren = new ArrayDeque<Node>();
		}
	}

	public Set<Node> findArtPoints() {
		
		Node start = null;
		int numSubTrees = 0;
		
		// Empty check
		if (nodes.isEmpty()) return artPoints;
		
		for (Node node : nodes) {
			
			// Ignore if we've been here before
			if (node.artPointCount < Integer.MAX_VALUE) {
				continue;
			}
			
			start = node;
			numSubTrees = 0;
			
			start.artPointCount = 0;
			
			for (Node neighbour : start.getNeighbours()) {
				if (neighbour.artPointCount == Integer.MAX_VALUE) {
					iterArtPoints(neighbour, 1, start);
					numSubTrees++;
				}
			}
			
			if (numSubTrees > 1) {
				artPoints.add(start);
			}
		}
				
		return artPoints;
		
	}
	
	public void iterArtPoints(Node firstNode, int count, Node root) {
		iterArtPointNodes.push(new IterArtPointNode(firstNode, count, root));
		while (!iterArtPointNodes.isEmpty()) {
			IterArtPointNode iterArtPointNode = iterArtPointNodes.peek();
			
			if (iterArtPointNode.node.artPointCount == Integer.MAX_VALUE) {
				iterArtPointNode.node.artPointCount = count;
				iterArtPointNode.node.artPointReachBack = count;
				iterArtPointNode.node.artPointChildren = new ArrayDeque<Node>();
				
				for (Node neighbour : iterArtPointNode.node.getNeighbours()) {
					if (neighbour != iterArtPointNode.root) {
						iterArtPointNode.node.artPointChildren.add(neighbour);
					}
				}
			} else if (!iterArtPointNode.node.artPointChildren.isEmpty()) {
				Node child = iterArtPointNode.node.artPointChildren.poll();
				if (child.artPointReachBack < Integer.MAX_VALUE) {
					iterArtPointNode.node.artPointReachBack = Math.min(iterArtPointNode.node.artPointReachBack, child.artPointCount);
				} else {
					iterArtPointNodes.push(new IterArtPointNode(child, count++, iterArtPointNode.node));
				}
			} else {
				if (iterArtPointNode.node != firstNode) {
					if (iterArtPointNode.node.artPointReachBack >= iterArtPointNode.root.artPointCount) {
						artPoints.add(iterArtPointNode.root);
					}
					iterArtPointNode.root.artPointReachBack = Math.min(iterArtPointNode.root.artPointReachBack, iterArtPointNode.node.artPointReachBack);
				}
				iterArtPointNodes.pop();
			}
		}
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
