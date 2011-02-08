package agent;

import org.mundo.agent.Agent;
import org.mundo.annotation.mcSerialize;
import org.mundo.service.Node;

import api.IMandelCalculator;

@mcSerialize
public class MandelAgent extends Agent implements IMandelCalculator {

	int ServerNumber = 3;	// number of servers in the system
	int[] MandelData;		// the result of the Mandelbrot calculation
	double x;				
	double y;

	/**
	 * Start a Mandelbrot calculation and move the
	 * Agent to the Load Balancer
	 * @param x
	 * @param y
	 */
	public void calculate(double x, double y)
	{
		this.x = x;
		this.y = y;
		moveTo("LoadBalancer", "atLoadBalancer");
	}
	
	/**
	 * Generate a random Server load
	 * (maybe just randomly assign a server)
	 * And move the Agent to the server
	 */
	public void atLoadBalancer(){
		for(int server = 0; server < serverNumber; server++)
		int random = Math.random();
		
	}
	
	/**
	 * Calculate the pixels for the Madelbrot picture
	 * and store it in int[] data and move the Agent
	 * back to the Master
	 */
	public void atServer(){

		moveTo("Master","atMaster");
	}
	
	/**
	 * Integrate the int[] date the Agent brings back from
	 * the server into the gui / result array
	 */
	public void atMaster(){
		
		/**
		 * Add data to result array in and update GUI 
		 */
	}
	
}
