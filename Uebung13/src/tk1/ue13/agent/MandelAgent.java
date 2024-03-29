package tk1.ue13.agent;

import org.mundo.agent.Agent;
import org.mundo.annotation.mcSerialize;
import org.mundo.rt.Mundo;
import org.mundo.rt.RMCException;
import org.mundo.service.Node;

import tk1.ue13.api.ILoadBalancer;
import tk1.ue13.api.IMandelAgent;
import tk1.ue13.api.IMandelApp;


@mcSerialize
public class MandelAgent extends Agent implements IMandelAgent {
	public int[] mandelData;		// the result of the Mandelbrot calculation
	public MandelConfig config;

	/**
	 * Start a Mandelbrot calculation and move the
	 * Agent to the Load Balancer
	 * @param x_start
	 * @param y_start
	 * @param x_end
	 * @param y_end
	 */
	public void run(MandelConfig config){
		try {			
			System.out.println("*** " + getServiceId() + " starting at "+Node.thisNode().getName());
			this.config = config;
			moveTo("LoadBalancer", "atLoadBalancer");
		} catch (Exception e) {
			System.out.println(getServiceId() + " failed to move");
		}
	}
	
	/**
	 * Generate a random Server load
	 * (maybe just randomly assign a server)
	 * And move the Agent to the server
	 */
	public void atLoadBalancer(){
		try {
			System.out.println("*** " + getServiceId() + " now at "+Node.thisNode().getName());
			ILoadBalancer loadBalancer = (ILoadBalancer)Mundo.getServiceByType(ILoadBalancer.class);
			String server = loadBalancer.getBestServer();		
			moveTo(server, "atServer");			
		} catch (Exception e) {
			System.out.println(getServiceId() + " failed to move");
		}
	}
	
	public void atServer(){
		try {
			System.out.println("*** " + getServiceId() + " now at "+Node.thisNode().getName());
			doCalculation();
			moveTo("master","atMaster");		
		} catch (Exception e) {
			System.out.println(getServiceId() + " failed to move");
		}
	}
	
	/**
	 * Integrate the MandelData the Agent brings back from
	 * the server into the GUI
	 */
	public void atMaster(){
		System.out.println("*** " + getServiceId() + " back at "+Node.thisNode().getName());
		IMandelApp app = (IMandelApp)Mundo.getServiceByType(IMandelApp.class);
		app.setMandelImage(config, mandelData);
	}

	/**
	 * calculate the Mandelbrot Data for the given sub image 
	 */
	public void doCalculation() {
		mandelData = new MandelLogic(
				config.width ,config.height, 
				config.xstart, config.ystart, 
				config.xend, config.yend, 
				config.maxiter).calculate();		
	}
}
