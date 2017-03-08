/**
 * An intersection between two or more road segments
 * @author james
 *
 */
public class Node {

	private int id;
	
	private Location location;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @param id
	 * @param location
	 */
	public Node(int id, Location location) {
		this.id = id;
		this.location = location;
	}
	
}
