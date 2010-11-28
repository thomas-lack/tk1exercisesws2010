package jms_client;

import java.awt.Color;
import java.awt.Point;

/**
 * Class to represent a line on the Whiteboard
 * 
 * @author Florian Mueller
 */
public class LineData {
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
