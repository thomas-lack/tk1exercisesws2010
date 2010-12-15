package tk1.ue7;

import java.util.Date;

import javax.swing.JOptionPane;

import com.ibm.tspaces.Field;
import com.ibm.tspaces.Tuple;
import com.ibm.tspaces.TupleSpace;
import com.ibm.tspaces.TupleSpaceException;

public class MandelWorker 
{
	TupleSpace server;	
	String host;
	int port;
   
	MandelRenderResponse response;
	MandelRenderRequest request;
	String masterId;
	int id;
	double xStart;
	double yStart;
	double xEnd;
	double yEnd;
	int mandelInit;
	int imgWidth;
	int imgHeight;
	int[] data;
	int maxiter = 1000;
	Tuple responseTemplate;
	
	
	public MandelWorker(String host, int port) throws TupleSpaceException
	{
		this.host = host;
		this.port = port;
	   server = new TupleSpace(IConstants.MANDEL_CHANNEL, host, port);

		//responseTemplate = new Tuple(new Field(String.class),new Field(MarshalledObject.class));
	   //template for polling
	   responseTemplate = new Tuple(new Field(String.class), new Field(MandelRenderRequest.class));
	   
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
			if(server.read(filter) != null) //if there is a matching tuple  take it 
			{			
			Tuple tmp = server.take(filter); // taken tuple
			
			masterId = (String) tmp.getField(0).getValue(); // get tuples master
			
			request = (MandelRenderRequest)tmp.getField(1).getValue(); //extract the data from the tuple ( the MandelRenderRequest)
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
			send_response(id, data , imgWidth, imgHeight);
			count++; // for debug only
			}
			//System.out.println("DEBUG : WORKER : No more tuples left. " +count+ " tuples processed.");
			stop = System.currentTimeMillis(); // refresh time for runtime limitation
			System.out.println(stop-start);
		}
	}
		
	private int iterate (double x, double y)
   {
      int iter=0;
      double aold, bold, a2old=Double.MAX_VALUE,
              b2old=Double.MAX_VALUE, zsquared,a=0,b=0,asquared=0,bsquared=0;
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
			int imgHeight) throws TupleSpaceException
	{
		response = new MandelRenderResponse(id,data,imgWidth,imgHeight);
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
