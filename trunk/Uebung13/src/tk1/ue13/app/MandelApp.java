package tk1.ue13.app;

import java.awt.Canvas;
import java.awt.image.IndexColorModel;

import org.mundo.agent.Agent;
import org.mundo.agent.DoIMobility;
import org.mundo.rt.Mundo;
import org.mundo.util.DefaultApplication;

import tk1.ue13.agent.MandelAgent;
import tk1.ue13.agent.MandelConfig;
import tk1.ue13.api.DoIMandelAgent;
import tk1.ue13.api.IMandelApp;

public class MandelApp extends DefaultApplication implements IMandelApp {
	MandelGUI gui;
	MandelCanvas canvas;
	IndexColorModel colorModel;
	boolean isRunning = true;
	
	public MandelApp(){
		gui = new MandelGUI(this);
		canvas = gui.getCanvas();
	}
	
	public static void main(String[] args){
//		Mundo.init();
//		MandelApp app = new MandelApp();
//		Mundo.registerService(app);
//		app.execute();
//		Mundo.shutdown();
		start(new MandelApp());
	}
	
	/**
	 * START CALCULATION PROCEDURE HERE
	 */
	public void refresh(){
		MandelCanvas canvas = gui.getCanvas();
		int x_num = 3; // vielleicht über die GUI ändern
		int y_num = 2; // vielleicht über die GUI ändern
		
		int w = canvas.getWidth() / x_num;
		int h = canvas.getHeight() / y_num;
		
		double x0 = -2.0;
		double y0 = -1.25;
		
		double x_step = 3.0 / x_num;
		double y_step = 2.5 / y_num;
		System.out.println("x_step: " + x_step + " y_step: " + y_step);
		
		for(int x = 0; x < x_num; ++x){
			for(int y = 0; y < y_num; ++y){
				double _x = x0 + (x * x_step);
				double _y = y0 + (y * y_step);
				
				System.out.println();
				System.out.println(" x: " + (x * w) + " y: " +(y * h));
				System.out.println(" w: " + w + " h: " + h);
				System.out.println(" x0: " + _x + " y0: " + _y);
				System.out.println(" x1: " + (_x + x_step) + " y1: " + (_y + y_step));
				
				startAgent(new MandelConfig(
						x * w, y * h, 
						w, h, 
						_x, _y, 
						_x + x_step, _y + y_step, 
						1000));
			}
		}
	}
	
	public void startAgent(MandelConfig config){
		try {
			DoIMobility mobility = Agent.newInstance(
					this.getSession(), 
					MandelAgent.class.getName());
			DoIMandelAgent agent = new DoIMandelAgent(mobility);
			agent.run(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setMandelImage(MandelConfig config, int[] data){
		MandelCanvas canvas = gui.getCanvas();
		canvas.addSubimage(
				data, 
				config.posX, config.posY, 
				config.width, config.height);
	}
	
	public void execute(){
		while (isRunning) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}
