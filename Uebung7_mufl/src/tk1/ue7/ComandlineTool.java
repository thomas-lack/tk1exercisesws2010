package tk1.ue7;

import com.ibm.tspaces.TupleSpace;

public class ComandlineTool {
	
	String host;
	int port;
	
	public ComandlineTool(String[] args) {
		if(0 == args.length){
			host = TupleSpace.DEFAULTHOST;
			port = TupleSpace.DEFAULTPORT;
		}
		else if(1 == args.length){
			host = args[0];
			port = TupleSpace.DEFAULTPORT;
		}
		else if(2 == args.length){
			host = args[0];
			port = Integer.parseInt(args[1]);
		}
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
}
