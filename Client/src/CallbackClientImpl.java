import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CallbackClientImpl extends UnicastRemoteObject implements
		CallbackClientInterface {

	public CallbackClientImpl() throws RemoteException {
		super();
	}

	public void notifyMe(String message) throws MalformedURLException,
			RemoteException, NotBoundException {
		String registryURL = "rmi://" + Constants.url + ":" + Constants.RMIPort
				+ "/voting";
		VotingInterface v = (VotingInterface) Naming.lookup(registryURL);
		System.out.println(v.getResults());
		System.out
				.println("\n\n-----------------------------\nBye, Client is closing!");
		System.exit(0);
	}

}
