package tk1.ue7;

import java.io.Serializable;

/**
 * A structure that holds the data for 
 * the calculation of the Mandelbaum 
 * picture. A object of this type will
 * be send to a Worker ( MandelWorker)
 */
public class MandelRenderRequest implements Serializable 
{
	private static final long serialVersionUID = 8017669381803399074L;
	
	public int id;
	public double xStart;
	public double yStart;
	public double xEnd;
	public double yEnd;
	public int mandelInit;
	public int imgWidth;
	public int imgHeight;
	
	public MandelRenderRequest(int id, double xStart, double yStart, 
			double xEnd, double yEnd, int mandelInit, int imgWidth, 
			int imgHeight) 
	{
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
