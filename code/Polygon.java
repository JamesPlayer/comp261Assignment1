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
		
		
		/*
		Code (Hex) Code (Decimal) Description 
		0x01 1 City 
		0x02 2 City 
		0x03 3 City 
		0x04 4 Military 
		0x05 5 Car Park (Parking Lot) 
		0x06 6 Parking Garage 
		0x07 7 Airport 
		0x08 8 Shopping Centre 
		0x09 9 Marina 
		0x0a 10 University 
		0x0b 11 Hospital 
		0x0c 12 Industrial 
		0x0d 13 Reservation 
		0x0e 14 Airport Runway 
		0x13 19 Man made area 
		0x14 20 National park 
		0x15 21 National park 
		0x16 22 National park 
		0x17 23 City Park 
		0x18 24 Golf 
		0x19 25 Sport 
		0x1a 26 Cemetery 
		0x1e 30 State Park 
		0x1f 31 State Park 
		0x28 40 Ocean 
		0x3b 59 Blue-Unknown 
		0x32 50 Sea 
		0x3b 59 Blue-Unknown 
		0x3c 60 Lake 
		0x3d 61 Lake 
		0x3e 62 Lake 
		0x3f 63 Lake 
		0x40 64 Lake 
		0x41 65 Lake 
		0x42 66 Lake 
		0x43 67 Lake 
		0x44 68 Lake 
		0x45 69 Blue-Unknown 
		0x46 70 River 
		0x47 71 River 
		0x48 72 River 
		0x49 73 River 
		0x4b 75 Background 
		0x4c 76 Intermittent River/Lake 
		0x4d 77 Glacier 
		0x4e 78 Orchard or plantation 
		0x4f 79 Scrub 
		0x50 80 Woods 
		0x51 81 Wetland 
		0x52 82 Tundra 
		0x53 83 Flats */
		
		
		switch (type) {
		
			// Buildings
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
			case 17:
			case 18:
			case 19:
				g.setColor(Color.decode("0xC7C9C8"));
				break;
			
			// Parks
			case 20:
			case 21:
			case 22:
			case 23:
			case 24:
			case 25:
			case 26:
			case 27:
			case 28:
			case 29:
			case 30:
			case 31:
			case 32:
			case 33:
			case 34:
			case 35:
			case 36:
			case 37:
			case 38:
			case 39:
				g.setColor(Color.decode("0x4EC472"));
				break;
				
			// Water
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
