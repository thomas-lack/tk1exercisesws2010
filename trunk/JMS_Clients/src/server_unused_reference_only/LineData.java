package server_unused_reference_only;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

/**
 * Class to represent a line on the Whiteboard
 * 
 * @author Florian Mueller
 */
public class LineData implements Serializable{

	private static final long serialVersionUID = -8041100145236434280L;
	
	public Point start;
	public Point end;
	public Color color;
	
	public LineData(Point start, Point end, Color color) {
		super();
		this.start = start;
		this.end = end;
		this.color = color;
	}
}
