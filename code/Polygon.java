import java.awt.Color;
import java.util.List;

public class Polygon {
	
	private Integer type;
	
	private List<Location> coords;

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the coords
	 */
	public List<Location> getCoords() {
		return coords;
	}

	/**
	 * @param coords the coords to set
	 */
	public void setCoords(List<Location> coords) {
		this.coords = coords;
	}

	/**
	 * @param color
	 * @param coords
	 */
	public Polygon(Integer type, List<Location> coords) {
		this.type = type;
		this.coords = coords;
	}
	
	

}
