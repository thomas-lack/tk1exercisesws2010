package tk1.ue13.agent;

import org.mundo.annotation.mcSerialize;

@mcSerialize
public class MandelConfig {
	public int posX, posY;
	public int width, height;
	public double xstart, xend;
	public double ystart, yend;
	public int maxiter;
	
	public MandelConfig() {
		maxiter = 1000;
	}

	public MandelConfig(int posX, int posY, int width, int height,
			double xstart, double ystart, double xend, double yend, int maxiter) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.xstart = xstart;
		this.xend = xend;
		this.ystart = ystart;
		this.yend = yend;
		this.maxiter = maxiter;
	}
}
