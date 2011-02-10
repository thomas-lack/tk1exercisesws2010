package agent;

import org.mundo.agent.Agent;
import org.mundo.annotation.mcSerialize;
import org.mundo.rt.Mundo;

import api.ILoadBalancer;
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
	public void run(double x, double y)
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
	 * Integrate the int[] date the Agent brings back from
	 * the server into the gui / result array
	 */
	public void atMaster(){
		
		/**
		 * Add data to result array in and update GUI 
		 */
	}

	@Override
	public void doCalculation() {
		// TODO Mandelberechnung
		
	}
}
