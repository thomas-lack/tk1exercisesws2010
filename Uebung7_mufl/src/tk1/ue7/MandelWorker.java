package tk1.ue7;

import java.util.Date;
import java.util.UUID;

import javax.swing.JOptionPane;

import com.ibm.tspaces.Field;
import com.ibm.tspaces.Tuple;
import com.ibm.tspaces.TupleSpace;
import com.ibm.tspaces.TupleSpaceException;

public class MandelWorker 
{
   private TupleSpace server;	
   private MandelRenderResponse response;
	private MandelRenderRequest request;
	private UUID workerID;
	private String masterId;
	private int id;
	private double xStart;
	private double yStart;
	private double xEnd;
	private double yEnd;
	private int mandelInit;
	private int imgWidth;
	private int imgHeight;
	private int[] data;
	private int maxiter = 1000;
	private Tuple responseTemplate;
	
	
	public MandelWorker(String host, int port) throws TupleSpaceException
	{
		workerID = UUID.randomUUID();
	   server = new TupleSpace(IConstants.MANDEL_CHANNEL, host, port);
				
		//template for polling
	   responseTemplate = new Tuple(new Field(String.class), new Field(MandelRenderRequest.class));
	   
		// start polling
	   poll(responseTemplate);
	}
	
	/**
	 *  polling function
	 *  parameter : the template / filter tuple that defines what kind of 
	 *  tuples the worker should poll
	 *  Once a tuple has been processed the Worker writes a response tuple
	 *  to the tspace 
	 */
	public void poll(Tuple filter)throws TupleSpaceException
	{
		int count = 0; // debug variable counts the number of tuples a worker has processed
		long start,stop; // runtime limits
		start = System.currentTimeMillis();
		stop = System.currentTimeMillis();

		//System.out.println(stop-start);
		while((stop - start) < 1000) // runs for 1000 ms
		{
		   //if there is a matching tuple  take it
		   if(server.read(filter) != null)  
			{			
			   // taken tuple
			   Tuple tmp = server.waitToTake(filter); 
   			
			   // sometimes a tuple is taken beforehand from another worker
			   // in this case do nothing
			   if (tmp != null)
			   {
			      try
	            {
	               // get tuples master
	               masterId = (String) tmp.getField(0).getValue();
	               //extract the data from the tuple ( the MandelRenderRequest)
	               request = (MandelRenderRequest)tmp.getField(1).getValue(); 
	            }
	            catch(Exception e)
	            {
	               System.err.println("Worker: Error at getting Tuple Information");
	               System.err.println("Got Tuple? " + tmp);
	               e.printStackTrace();
	            }
	            
	            xStart = request.xStart;
	            yStart = request.yStart;
	            xEnd = request.xEnd;
	            yEnd = request.yEnd;
	            mandelInit = request.mandelInit;
	            imgHeight = request.imgHeight;
	            imgWidth = request.imgWidth;
	            id = request.id;
	                              
	            data = new int[imgHeight * imgWidth];
	            
	            // calculate the image data
	             int x, y, i=0;
	             for (y=0; y<imgHeight; y++)
	             {
	               for (x=0; x<imgWidth; x++)
	               {
	                 data[i++]=iterate(xStart+(xEnd-xStart)*x/(imgWidth-1),
	                                   yStart+(yEnd-yStart)*y/(imgHeight-1))%256;
	               }
	             }
	             // write data to the tspace
	            send_response(id, data , imgWidth, imgHeight, workerID.toString());
	            count++; // for debug only
			   }
			}
			stop = System.currentTimeMillis(); // refresh time for runtime limitation
			System.out.println(stop-start);
		}
		System.out.println("WORKER : No more tuples left. " +count+ " tuples processed.");
	}
		
	private int iterate (double x, double y)
   {
      int iter=0;
      double aold, bold, zsquared,a=0,b=0,asquared=0,bsquared=0;
      aold=0; bold=0;
      asquared=a*a;
      a=x;
      bsquared=b*b;
      b=y;
      zsquared=asquared+bsquared;
      for (iter=0; iter<maxiter; iter++)
      {
          a=asquared-bsquared+x;
          asquared=a*a;
          b=2*aold*b+y;
          if (bold==b && aold==a)
          {
             iter=maxiter-1;
          }
          bsquared=b*b;
          zsquared=asquared+bsquared;
          if (zsquared>4)
          {
             break;
          }
          bold=b; aold=a;
      }
      return iter;
   }
	
	/**
	 *  send a MandelRenderResponse to the tspace
	 * @param id
	 * @param data
	 * @param imgWidth
	 * @param imgHeight
	 * @throws TupleSpaceException
	 *
	 *  Writes a Object of the type MandelResponse to the tspace 
	 */
	public void send_response(int id, int[] data, int imgWidth, 
			int imgHeight, String workerID) throws TupleSpaceException
	{
		response = new MandelRenderResponse(id,data,imgWidth,imgHeight,workerID);
		server.write(masterId,response);
	}

	public static void main(String[] args) 
	{
		ComandlineTool cmdTool = new ComandlineTool(args);
		try
		{
			// Worker's poll gets initiated in the constructor
			new MandelWorker(cmdTool.getHost(),cmdTool.getPort());
		}
		catch(TupleSpaceException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,	e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	 }
}
