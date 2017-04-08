import java.util.PriorityQueue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class AStarSearch {

	PriorityQueue<AStarFringeNode> fringe;
	
	List<Segment> path;
	
	Set<Node> visited;
	
	public AStarSearch() {
		fringe = new PriorityQueue<AStarFringeNode>(10, new AStarFringeComparator());
		path = new ArrayList<Segment>();
		visited = new HashSet<Node>();
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
	
	public List<Segment> getPath(Node start, Node goal) {
		
		AStarFringeNode fringeNode;
		
		fringe.add(new AStarFringeNode(new Segment(-1, 0, null, null, start, null), 0.0, heuristic(start, goal)));
		
		while (!fringe.isEmpty()) {
			fringeNode = fringe.poll();
			
			if (visited.contains(fringeNode.segment.getEndNode())) {
				continue;
			}
			
			visited.add(fringeNode.segment.getEndNode());
			
			// Add segment to path
			if (fringeNode.segment.getStartNode() != null) {
				path.add(fringeNode.segment);
			}
			
			// We found it
			if (fringeNode.segment.getEndNode() == goal) {
				return path;
			}
			
			for (Segment segment : fringeNode.segment.getEndNode().getOutSegs()) {
				Node neighbour = segment.getEndNode();
				double costToNeighbour = fringeNode.costFromStart + segment.getLength();
				double estToGoal = costToNeighbour + heuristic(neighbour, goal);
				fringe.add(new AStarFringeNode(segment, costToNeighbour, estToGoal));
			}
		}
		
		return path;
		
	}
	
	public double heuristic(Node start, Node goal) {
		Location startLoc = Location.newFromLatLon(start.getLat(), start.getLon());
		Location goalLoc = Location.newFromLatLon(goal.getLat(), goal.getLon());
		return startLoc.distance(goalLoc);
	}
	
}
