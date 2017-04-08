import java.util.PriorityQueue;
import java.util.Set;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class AStarSearch {

	PriorityQueue<AStarFringeNode> fringe;
	
	Set<Node> visited;
	
	public AStarSearch() {
		fringe = new PriorityQueue<AStarFringeNode>(10, new AStarFringeComparator());
		visited = new HashSet<Node>();
	}

	protected class AStarFringeComparator implements Comparator<AStarFringeNode>
	{
	    @Override
	    public int compare(AStarFringeNode x, AStarFringeNode y)
	    {
	        return x.estToGoal - y.estToGoal;
	    }
	}
	
	public List<Node> getPath(Node start, Node end) {
		return null;
		
	}
	
}
