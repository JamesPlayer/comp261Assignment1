public class AStarFringeNode {

	public Node origin;
	
	public Node dest;
	
	public double costFromStart;
	
	public double estToGoal;
	
	/**
	 * @param origin
	 * @param dest
	 * @param costFromStart
	 * @param estToGoal
	 */
	public AStarFringeNode(Node origin, Node dest, double costFromStart, double estToGoal) {
		this.origin = origin;
		this.dest = dest;
		this.costFromStart = costFromStart;
		this.estToGoal = estToGoal;
	}
	
	
	
}