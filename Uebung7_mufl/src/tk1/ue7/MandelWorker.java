package tk1.ue7;

public class MandelWorker {
	
	public MandelWorker(String host, int port) {
		// TODO Auto-generated constructor stub
	}
	
	private void execute() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		ComandlineTool cmdTool = new ComandlineTool(args);
		
		MandelWorker worker = new MandelWorker(
				cmdTool.getHost(), 
				cmdTool.getPort());
		
		worker.execute();
	}
}
