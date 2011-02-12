package agent;

import org.mundo.agent.Agent;
import org.mundo.annotation.mcSerialize;
import org.mundo.rt.Mundo;

import api.ILoadBalancer;
import api.IMandelCalculator;
import app.MandelCanvas;

@mcSerialize
public class MandelAgent extends Agent implements IMandelCalculator {

	int[] MandelData;		// the result of the Mandelbrot calculation
	double x_start;
	double x_end;
	double y_start;
	double y_end;
	int maxiter = 1000 ;	// maximum iterations = 1000 
	int width  = 250;		// constant for image size 500x500 and divide factor 4 
	int height = 250;		// constant for image size 500x500 and divide factor 4
	
	MandelCanvas canvas;

	/**
	 * Start a Mandelbrot calculation and move the
	 * Agent to the Load Balancer
	 * @param x_start
	 * @param y_start
	 * @param x_end
	 * @param y_end
	 */
	public void run(double x_start, double y_start, double x_end, double y_end)
	{
		this.x_start = x_start;
		this.y_start = y_start;
		this.x_end = x_end;
		this.y_end = y_end;
		
		MandelData = new int[width*height];
		
		moveTo("LoadBalancer", "atLoadBalancer");
	}
	
	/**
	 * Generate a random Server load
	 * (maybe just randomly assign a server)
	 * And move the Agent to the server
	 */
	public void atLoadBalancer(){
		ILoadBalancer loadBalancer = (ILoadBalancer)Mundo.getServiceByType(ILoadBalancer.class);
		String server = loadBalancer.getBestServer();
		moveTo(server, "atServer");
	}
	
	public void atServer(){
		// der Agent soll alles berechnen, nicht der Server (so wie ich das verstanden habe)
		doCalculation();
		moveTo("Master","atMaster");
	}
	
	/**
	 * Integrate the int[] MandelData the Agent brings back from
	 * the server into the gui / result array
	 */
	public void atMaster(){
		int x = (int) x_start;
		int y = (int) y_start;
		canvas.addSubimage(MandelData, x, y, width, height);
	}

	/**
	 * calculate the Mandelbrot Data for the given subimage 
	 */
	@Override
	public void doCalculation() 
	{
		MandelData = new Mandelbrot(width ,height, x_start, y_start, x_end, y_end, maxiter).calculate();		
	}
	
	
	/**
	 * GET AND SET METHODS THAT LINK THE AGENT TO THE
	 * MANDEL CANVAS OF THE GUI 
	 * 		-> 	SINCE CONSTRUCTOR PARAMETERS AREN'T
	 * 			ALLOWED IN MUNDOCORE
	 */
	
	public void setCanvas(MandelCanvas canvas)
	{
		this.canvas = canvas;
	}
	
	public MandelCanvas getCanvas()
	{
		return canvas;
	}

}
