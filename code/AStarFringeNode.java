public class AStarFringeNode {

	public Node origin;
	
	public Node dest;
	
	public int costFromStart;
	
	public int estToGoal;

	/**
	 * @param origin
	 * @param dest
	 * @param costFromStart
	 * @param estToGoal
	 */
	public AStarFringeNode(Node origin, Node dest, int costFromStart, int estToGoal) {
		this.origin = origin;
		this.dest = dest;
		this.costFromStart = costFromStart;
		this.estToGoal = estToGoal;
	}
	
	
	
}