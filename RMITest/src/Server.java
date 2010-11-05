import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements IServerInterface{
	private static final long serialVersionUID = -6986677818524597615L;

	protected Server() throws RemoteException {
		super();
	}

	private class ClientThread extends Thread{
		IClientInterface client;
		
		public ClientThread(IClientInterface client) {
			this.client = client;
		}
		
		@Override
		public void run() {
			try {
				for (int i = 0; i < 10; i++) {				
					client.say("Hello");
				}
				
				client.say("bye");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	};
	
	@Override
	public void register(IClientInterface client) throws RemoteException {
		new ClientThread(client).start();
	}
	
	
		
	public static void main(String[] args){
		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			Naming.rebind("test", new Server());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
}
