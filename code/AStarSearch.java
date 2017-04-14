import java.util.PriorityQueue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class AStarSearch {

	PriorityQueue<AStarFringeNode> fringe;
		
	Set<Node> visited;
	
	Set<Restriction> restrictions;
	
	public AStarSearch(Set<Restriction> restrictions) {
		fringe = new PriorityQueue<AStarFringeNode>(10, new AStarFringeComparator());
		visited = new HashSet<Node>();
		this.restrictions = restrictions;
	}

	protected class AStarFringeComparator implements Comparator<AStarFringeNode>
	{
	    @Override
	    public int compare(AStarFringeNode x, AStarFringeNode y)
	    {
	    	if (x.estToGoal < y.estToGoal) {
	            return -1;
	        } if (x.estToGoal > y.estToGoal) {
	            return 1;
	        }
	        return 0;
	    }
	}
	
	/**
	 * Return the last fringe node so we can trace it back to the first
	 * @param start
	 * @param goal
	 * @return
	 */
	public AStarFringeNode getPath(Node start, Node goal) {
		
		AStarFringeNode fringeNode = new AStarFringeNode(new Segment(-1, 0, null, null, start, null), null, 0.0, heuristic(start, goal));
		
		fringe.add(fringeNode);
		
		while (!fringe.isEmpty()) {
			fringeNode = fringe.poll();
			
			if (visited.contains(fringeNode.segment.getEndNode())) {
				continue;
			}
			
			visited.add(fringeNode.segment.getEndNode());
			
			// We found it
			if (fringeNode.segment.getEndNode() == goal) {
				return fringeNode;
			}
			
			for (Segment segment : fringeNode.segment.getEndNode().getOutSegs()) {
				Node neighbour = segment.getEndNode();
				
				// Check for restriction
				if (!(fringeNode.segment.getStartNode() == null) && restrictions.contains(new Restriction(
						fringeNode.segment.getStartNode().getId(),
						fringeNode.segment.getRoad().getId(),
						fringeNode.segment.getEndNode().getId(),
						segment.getRoad().getId(),
						neighbour.getId()))) {
					continue;
				}
				
				double costToNeighbour = fringeNode.costFromStart + segment.getLength();
				double estToGoal = costToNeighbour + heuristic(neighbour, goal);
				fringe.add(new AStarFringeNode(segment, fringeNode, costToNeighbour, estToGoal));
			}
		}
		
		return fringeNode;
		
	}
	
	public double heuristic(Node start, Node goal) {
		Location startLoc = Location.newFromLatLon(start.getLat(), start.getLon());
		Location goalLoc = Location.newFromLatLon(goal.getLat(), goal.getLon());
		return startLoc.distance(goalLoc);
	}
	
}
