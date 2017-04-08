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
	        return (int) (x.estToGoal - y.estToGoal);
	    }
	}
	
	public List<Segment> getPath(Node start, Node goal) {
		
		AStarFringeNode fringeNode;
		
		fringe.add(new AStarFringeNode(null, start, 0.0, heuristic(start, goal)));
		
		while (!fringe.isEmpty()) {
			fringeNode = fringe.poll();
			
			// We found it
			if (fringeNode.origin == goal) {
				return path;
			}
			
			for (Segment segment : fringeNode.dest.getOutSegs()) {
				
				Node neighbour = segment.getEndNode();
				
				if (visited.contains(neighbour)) {
					continue;
				}
				
				visited.add(neighbour);
				double costToNeighbour = fringeNode.costFromStart + segment.getLength();
				double estToGoal = costToNeighbour + heuristic(neighbour, goal);
				fringe.add(new AStarFringeNode(fringeNode.dest, neighbour, costToNeighbour, estToGoal));
				path.add(segment);
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
