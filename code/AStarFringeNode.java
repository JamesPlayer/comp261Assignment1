public class AStarFringeNode {

	public Segment segment;
	
	public AStarFringeNode from;
	
	public double costFromStart;
	
	public double estToGoal;
	
	/**
	 * @param origin
	 * @param dest
	 * @param costFromStart
	 * @param estToGoal
	 */
	public AStarFringeNode(Segment segment, AStarFringeNode from, double costFromStart, double estToGoal) {
		this.segment = segment;
		this.from = from;
		this.costFromStart = costFromStart;
		this.estToGoal = estToGoal;
	}
	
	
	
}