package tk1.ue7;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Random;
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

public class MandelClient extends JFrame implements Callback
{
	private static final long serialVersionUID = -4488721619640920574L;
	
	public UUID clientId;
	private TupleSpace server;
	private MandelCanvas canvas;
	private MandelRenderRequest request;
	private HashMap<String, Integer> workerColors;
	// factor D, in which the canvas will be divided to render a DxD grid
	private final int divideFactor = 4;     
	//we want to divide the picture in DxD ~equal parts to get D^2 jobs later on
   private int[][] coords = new int[divideFactor*divideFactor][4];
	
   
	public MandelClient(String host, int port) throws TupleSpaceException 
	{
		System.out.println("Client: Try to connect to " + host + ":" + port);
		Tuple serverState = TupleSpace.status(host, port);
		
		if(null == serverState || serverState.getField(0).getValue().equals("NotRunning"))
		{
			throw new TupleSpaceException("Client: No server is running @ " + host + ":" + port);
		}
		
		//initialize internal variables
		clientId = UUID.randomUUID();
		server = new TupleSpace(IConstants.MANDEL_CHANNEL, host, port);
		workerColors = new HashMap<String, Integer>();
		
		// register this object as tuplespace server listener (WRITE)
		Tuple responseTemplate = new Tuple(new Field(clientId.toString()), new Field(MandelRenderResponse.class));
		server.eventRegister(TupleSpace.WRITE, responseTemplate, this, true);
		
		// initialize gui
		initGUI();
	}
	
	private void initGUI()
	{
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
	
	/**
	 * start new mandelbrot calculation process
	 */
	public void recalcMandel()
	{
		//get x,y size of canvas
	   int x = canvas.getWidth();
	   int y = canvas.getHeight();
	   
	   // get _x,_y size of grid tile width and height
	   int _x = x / divideFactor;
      int _y = y / divideFactor;
            
	   for(int i=0; i<divideFactor*divideFactor; i++)
	   {
	      int _i = i % divideFactor;
	      
	      coords[i][0] = _i * _x; //x1
	      coords[i][1] = i / divideFactor * _y; //y1
	      coords[i][2] = (_i == divideFactor - 1) ?  x : coords[i][0] + _x; //x2
	      coords[i][3] = ( i / divideFactor == divideFactor - 1) ? y : coords[i][1] + _y; //y2
	   }
	   
	   // Test output
	   /*
	   System.out.println("breite: " + x + ", höhe: " + y);
	   
	   for (int i=0; i<divideFactor*divideFactor; i++)
	   {
	      System.out.println(i + ": ["+coords[i][0]+","+coords[i][1]+"]-["+coords[i][2]+","+coords[i][3]+"]");
	   }
	   */
	   
	   // send requests for divided parts to tspace
	   double px = (3.0 / x);
	   double py = (2.5 / y);
	   for (int i=0; i<divideFactor*divideFactor; i++)
	   {
	      try
         {
            // mandelbrot starting/end values have to be recalculated for the current tile
	         double startx = px * coords[i][0] -2.1;
            double starty = py * coords[i][1] -1.25;
            double endx = px * coords[i][2] -2.1;
            double endy = py * coords[i][3] -1.25;
	         send_request(i, startx, starty, endx, endy, 1000, coords[i][2]-coords[i][0], coords[i][3]-coords[i][1]);
         } catch (TupleSpaceException e)
         {
            System.err.println("Client: cannot send tuple to tspace t(o_ot)");
            e.printStackTrace();
         }
	   }
	}
	
	@Override
	public boolean call(String eventName, String tsName, int seqNum, SuperTuple tuple, boolean isException) 
	{
	   if (!isException)
	   {
	      MandelRenderResponse t = null;
	      try
	      {
	         t = (MandelRenderResponse) tuple.getField(1).getValue();
	      } catch (TupleSpaceException e)
	      {
	         System.err.println("Client call Error:");
	         e.printStackTrace();
	      }     
	      
	      // if we got data from a tuple, add it to the canvas
	      if (t != null)
	      {
	         int[] colorizedData = colorizeTile(t.data, t.workerID, t.imgWidth);
	         canvas.addSubimage(colorizedData, coords[t.id][0], coords[t.id][1], t.imgWidth, t.imgHeight);
	      }
	      
	      // try to remove the tuple from tspace, after data has been read
	      // we can do this in the call method, because it is started in its own threat (via parameter)
	      try
         {
            server.deleteTupleById(tuple.getTupleID());
         } catch (TupleSpaceException e)
         {
            System.err.println("Client Tuple Remove Error:");
            e.printStackTrace();
         }
	   }
	   
	   return false;
	}
	
	/**
	 * generate a random color for the current worker (if not done before)
	 * and colorize all left pixels of a given subimage/tile in this color
	 * 
	 * @param data
	 * @param workerID
	 * @param width
	 * @return
	 */
	private int[] colorizeTile(int[] data, String workerID, int width)
	{
	   int c = 0;
	   // get color previously generated for the current worker (if available)
	   if (workerColors.containsKey(workerID))
	   {
	      c = workerColors.get(workerID);
	   }
	   // generate new random color that is not used for the current worker and later use
	   else
	   {
	      Random generator = new Random();
	      while (!workerColors.containsKey(workerID))
	      {
	         c = generator.nextInt(220) + 30;
	         if (!workerColors.containsValue(c))
	         {
	            workerColors.put(workerID, c);
	         }
	      }
	   }
	   
	   // draw outermost left pixels in one color to identify the worker, that calculated
      // the current subimage/tile 
      for (int i=0; i< data.length; i++)
      {
         if (i%width == 0)
         {
            data[i] = c;
         }
      }
	   
	   return data;
	}
	
	/**
	 * @param args
	 **/
	public void send_request(int id, double xStart, double yStart, 
			double xEnd, double yEnd, int mandelInit, int imgWidth, 
			int imgHeight) throws TupleSpaceException
	{
		request = new MandelRenderRequest(id,xStart,yStart,xEnd,yEnd,mandelInit,imgWidth,imgHeight);
		server.write(clientId.toString(),request);
	}
		
	
	public static void main(String[] args) 
	{
		ComandlineTool cmdTool = new ComandlineTool(args);
		MandelClient client;
		
		try 
		{
			client = new MandelClient(cmdTool.getHost(),cmdTool.getPort());		
			client.setVisible(true);
		} catch (TupleSpaceException e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e.getMessage(),"",JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
}
