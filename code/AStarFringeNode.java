public class AStarFringeNode {

	public Segment segment;
	
	public double costFromStart;
	
	public double estToGoal;
	
	/**
	 * @param origin
	 * @param dest
	 * @param costFromStart
	 * @param estToGoal
	 */
	public AStarFringeNode(Segment segment, double costFromStart, double estToGoal) {
		this.segment = segment;
		this.costFromStart = costFromStart;
		this.estToGoal = estToGoal;
	}
	
	
	
}