import java.net.MalformedURLException;
import java.rmi.NotBoundException;

public interface CallbackClientInterface extends java.rmi.Remote {
	public void notifyMe(String message) throws java.rmi.RemoteException,
			MalformedURLException, NotBoundException;
}
