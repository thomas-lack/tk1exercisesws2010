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
	UUID masterId;
	
	public MandelWorker(String host, int port) throws TupleSpaceException{
		server = new TupleSpace(IConstants.MANDEL_CHANNEL, host, port);

		Tuple responseTemplate = new Tuple(
				new Field(String.class), 
				new Field(MandelRenderRequest.class));
		

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
