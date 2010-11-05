import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IClientInterface extends Remote{
	public void say(String message) throws RemoteException;
}
