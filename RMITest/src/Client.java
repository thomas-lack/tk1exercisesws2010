import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements IClientInterface{
	private static final long serialVersionUID = 6205938039454596825L;
	String msg;

	protected Client() throws RemoteException {
		super();
	}

	@Override
	public void say(String message) throws RemoteException {
		method(message);
	}
	
	public void method(String message){
		System.out.println(message);
	}

	public static void main(String[] args) {
		
		try {
			Client client = new Client();
			IServerInterface server = (IServerInterface) Naming.lookup("//127.0.0.1/test");
			server.register(client);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		System.out.println("client finished");
	}
}
