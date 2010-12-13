package tk1.ue7;

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
	
	public MandelWorker(String host, int port) throws TupleSpaceException{
		server = new TupleSpace(IConstants.MANDEL_CHANNEL, host, port);

		Tuple responseTemplate = new Tuple(
				new Field(String.class), 
				new Field(MandelRenderRequest.class));
		

	}
	
	public void poll(Tuple filter)throws TupleSpaceException
	{
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
			
			//TODO : call calculation

			send_response(id, data , imgWidth, imgHeight);
		}
	}
	
	public void send_response(long id, int[] data, int imgWidth, 
			int imgHeight) throws TupleSpaceException
	{
		response = new MandelRenderResponse(id,data,imgWidth,imgHeight);
		server.write(masterId.toString(),response);
	}
	
	
	private void execute() {
		// TODO Auto-generated method stub
		
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
