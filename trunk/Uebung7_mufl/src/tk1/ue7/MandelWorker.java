package tk1.ue7;

import java.rmi.MarshalledObject;
import java.util.UUID;

import javax.swing.JOptionPane;

import com.ibm.tspaces.Field;
import com.ibm.tspaces.Tuple;
import com.ibm.tspaces.TupleSpace;
import com.ibm.tspaces.TupleSpaceException;

public class MandelWorker {
	
	TupleSpace server;	
	MandelRenderResponse response;
	MandelRenderRequest request;
	UUID masterId;
	long id;
	double xStart;
	double yStart;
	double xEnd;
	double yEnd;
	int mandelInit;
	int imgWidth;
	int imgHeight;
	int[]data;
	int maxiter = 1000;
	Tuple responseTemplate;
	
	public MandelWorker(String host, int port) throws TupleSpaceException{
		server = new TupleSpace(IConstants.MANDEL_CHANNEL, host, port);

		responseTemplate = new Tuple(
				new Field(String.class), 
				new Field(MarshalledObject.class));
		
		poll(responseTemplate);

	}
	
	public void poll(Tuple filter)throws TupleSpaceException
	{
		int count = 0;
		while(server.read(filter) != null)
		{			
			Tuple tmp = server.take(filter);
			masterId = (UUID)tmp.getField(0).getValue();
			request = (MandelRenderRequest)tmp.getField(1).getValue();
			xStart = request.xStart;
			yStart = request.yStart;
			xEnd = request.xEnd;
			yEnd = request.yEnd;
			mandelInit = request.mandelInit;
			imgHeight = request.imgHeight;
			imgWidth = request.imgWidth;
			id = request.id;
			data = new int[imgHeight * imgWidth];
			
		    int x, y, i=0;
		    for (y=0; y<imgHeight; y++)
		    {
		      for (x=0; x<imgWidth; x++)
		      {
		        data[i++]=iterate(xStart+(xEnd-xStart)*x/(imgWidth-1),
		                          yStart+(yEnd-yStart)*y/(imgHeight-1))%256;
		      }
		    }

			send_response(id, data , imgWidth, imgHeight);
			count++;
		}
		System.out.println("DEBUG : WORKER : No more tuples left. " +count+ " tuples processed.");
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
	        break;
	      bold=b; aold=a;
	    }
	    return iter;
	  }
	
	public void send_response(long id, int[] data, int imgWidth, 
			int imgHeight) throws TupleSpaceException
	{
		response = new MandelRenderResponse(id,data,imgWidth,imgHeight);
		server.write(masterId.toString(),response);
	}
	
	
	private void execute() {
	/**
	try
	{
		poll(responseTemplate);
	}
	catch(TupleSpaceException e)
	{
		e.printStackTrace();
		JOptionPane.showMessageDialog(
				null, 
				e.getMessage(),
				"",
				JOptionPane.ERROR_MESSAGE);
		System.exit(1);
		}
	**/		
	}
	
	public static void main(String[] args) {
		ComandlineTool cmdTool = new ComandlineTool(args);
		try
		{
		MandelWorker worker = new MandelWorker(
				cmdTool.getHost(), 
				cmdTool.getPort());
		worker.execute();
		}
		catch(TupleSpaceException e)
		{
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
