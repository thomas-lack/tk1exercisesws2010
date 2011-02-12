package app;

import org.mundo.rt.Mundo;
import org.mundo.rt.Service;
import org.mundo.agent.Agent;
import org.mundo.agent.DoIMobility;

import agent.MandelAgent;



public class MandelApp {

	MandelAgent Agent001;
	MandelAgent Agent002;
	MandelAgent Agent003;
	MandelAgent Agent004;
	
	MandelGUI gui;
	MandelCanvas canvas;
	
	public MandelApp()
	{
		Agent001 = new MandelAgent();
		Agent002 = new MandelAgent();
		Agent003 = new MandelAgent();
		Agent004 = new MandelAgent();
		
		gui = new MandelGUI(this);
		canvas = gui.getCanvas();
		
		Agent001.setCanvas(canvas);
		Agent002.setCanvas(canvas);
		Agent003.setCanvas(canvas);
		Agent003.setCanvas(canvas);
	}
	
	public static void main(String[] args)
	{
		new MandelApp();
	}
	
	/**
	 * START CALCULATION PROCEDURE HERE
	 */
	public void start()
	{
		/*		
		 * 		*********************
		 *		*         *         *
		 *		*   002   *   004   *
		 *		*		  *         *
		 *		*********************
		 *		*		  *         *        
		 *		*   001   *   003   *
		 *		*		  *         *
		 *		*********************    
		 */
		
		Agent001.run(0.0, 0.0, 250.0, 250.0);
		Agent002.run(0.0, 250.0, 250.0, 500.0);
		Agent003.run(250.0, 0.0, 500.0, 250.0);
		Agent004.run(250.0, 250.0, 500.0, 500.0);
	}
}
