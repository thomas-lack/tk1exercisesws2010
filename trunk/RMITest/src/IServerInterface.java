import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerInterface extends Remote {
	public void register(IClientInterface client) throws RemoteException;
	
}
