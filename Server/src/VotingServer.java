import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class VotingServer {

	public static void main(String[] args) {

		try {
			System.out.println(Constants.RMIPort);
			startRegistry(Constants.RMIPort);
			VotingImpl exportedObj = new VotingImpl();
			String registryURL = "rmi://localhost:" + Constants.RMIPort
					+ "/voting";
			Naming.rebind(registryURL, exportedObj);
			listRegistry(registryURL);
			System.out.println("Server Ready.");
		} catch (Exception re) {
			re.printStackTrace();
		}

	}

	public static void startRegistry(int RMIPortNum) throws RemoteException {
		try {
			Registry registry = LocateRegistry.createRegistry(RMIPortNum);
			registry.list();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void listRegistry(String registryURL) throws RemoteException,
			MalformedURLException {
		System.out.println("Registry " + registryURL + " contains: ");
		String[] names = Naming.list(registryURL);
		for (int i = 0; i < names.length; i++)
			System.out.println(names[i]);
	}

}
