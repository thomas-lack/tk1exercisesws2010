package tk1.ue7;

import java.io.Serializable;

public class MandelRenderRequest implements Serializable {
	private static final long serialVersionUID = 8017669381803399074L;
	
	public long id;
	public double xStart;
	public double yStart;
	public double xEnd;
	public double yEnd;
	public int mandelInit;
	public int imgWidth;
	public int imgHeight;
	
	public MandelRenderRequest(long id, double xStart, double yStart, 
			double xEnd, double yEnd, int mandelInit, int imgWidth, 
			int imgHeight) {
		super();
		this.id = id;
		this.xStart = xStart;
		this.yStart = yStart;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
		this.mandelInit = mandelInit;
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
	}
}
