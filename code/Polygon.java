import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
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
	
	public void draw(Graphics g, Location origin, double scale) {
		
		// Would set colors here for the different types, if I knew what each type represented!
		// For now, just setting all polygons to sea blue, seems to draw the ocean quite nicely
		switch (type) {
			default:
				g.setColor(Color.decode("0x76E1FF"));
		}
		
		int[] xPoints = new int[coords.size()];
		int[] yPoints = new int[coords.size()];
		int i = 0;
		
		for (Location location : coords) {
			xPoints[i] = location.asPoint(origin, scale).x;
			yPoints[i] = location.asPoint(origin, scale).y;
			i++;
		}
		
		g.fillPolygon(xPoints, yPoints, i);
	}
	
	

}
