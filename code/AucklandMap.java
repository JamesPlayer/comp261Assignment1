import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AucklandMap extends GUI {
	
	private RoadGraph roadGraph;

	/**
	 * Constructor
	 */
	public AucklandMap() {
		roadGraph = new RoadGraph();
	}

	@Override
	protected void redraw(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onClick(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onSearch() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMove(Move m) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons) {
		try {
			roadGraph.load(nodes, roads, segments);
		} catch (FileNotFoundException e) {
			getTextOutputArea().append("Could not find file :(");
		} catch (IOException e) {
			getTextOutputArea().append("Could not read file :(");
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
