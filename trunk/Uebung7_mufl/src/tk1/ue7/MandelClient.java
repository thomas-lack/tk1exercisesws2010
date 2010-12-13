package tk1.ue7;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.UUID;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import com.ibm.tspaces.Callback;
import com.ibm.tspaces.Field;
import com.ibm.tspaces.SuperTuple;
import com.ibm.tspaces.Tuple;
import com.ibm.tspaces.TupleSpace;
import com.ibm.tspaces.TupleSpaceException;

public class MandelClient extends JFrame implements Callback{
	
	private static final long serialVersionUID = -4488721619640920574L;
	
	TupleSpace server;
	UUID clientId;
	MandelCanvas canvas;
	MandelRenderRequest request;

	public MandelClient(String host, int port) throws TupleSpaceException {
		System.out.println("Try to connect to " + host + ":" + port);
		Tuple serverState = TupleSpace.status(host, port);
		
		if(null == serverState || 
				serverState.getField(0).getValue().equals("NotRunning")){
			throw new TupleSpaceException(
					"No server is running @ " + host + ":" + port);
		}
		
		clientId = UUID.randomUUID();
		server = new TupleSpace(IConstants.MANDEL_CHANNEL, host, port);
		
		Tuple responseTemplate = new Tuple(
				new Field(clientId.toString()), 
				new Field(MandelRenderResponse.class));
		
		server.eventRegister(TupleSpace.WRITE, responseTemplate, this);
		initGUI();
	}
	
	private void initGUI(){
		canvas = new MandelCanvas();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		JMenuBar menubar = new JMenuBar();
	    JMenu mandelMenu = new JMenu("Mandel");
	    JMenuItem refresh = new JMenuItem("Refresh");
	    refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				recalcMandel();
			}
		});
	    
	    JMenuItem exit = new JMenuItem("Exit");
	    exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
	    
	    mandelMenu.add(refresh);
	    mandelMenu.add(new JSeparator());
	    mandelMenu.add(exit);
	    menubar.add(mandelMenu);
		
		setTitle("Mandelbrot");
		setSize(500, 500);
		setJMenuBar(menubar);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add("Center", canvas);
	}
	
	public void recalcMandel(){
		
	}
	
	@Override
	public boolean call(String eventName, String tsName, int seqNum, 
			SuperTuple tuple, boolean isException) {
		
		return false;
	}

	/**
	 * @param args
	 */
	
	public void send_request(long id, double xStart, double yStart, 
			double xEnd, double yEnd, int mandelInit, int imgWidth, 
			int imgHeight) throws TupleSpaceException
	{
		request = new MandelRenderRequest(id,xStart,yStart,xEnd,yEnd,mandelInit,imgWidth,imgHeight);
		server.write(clientId.toString(),request);
	}
	
	
	
	public static void main(String[] args) {
		ComandlineTool cmdTool = new ComandlineTool(args);
		MandelClient client;
		
		try {
			client = new MandelClient(
					cmdTool.getHost(),
					cmdTool.getPort());
			client.setVisible(true);
		} catch (TupleSpaceException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(
					null, 
					e.getMessage(),
					"",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

}
