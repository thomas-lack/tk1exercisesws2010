package client;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

/**
 * Class to represent a line on the Whiteboard
 * 
 * @author Florian Mueller
 */
public class LineData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8870414451669042628L;
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
