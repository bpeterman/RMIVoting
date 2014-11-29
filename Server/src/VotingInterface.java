import java.rmi.Remote;

public interface VotingInterface extends Remote {
	public String sayHello(String name) throws java.rmi.RemoteException;
	public boolean validateUser(String username, String password) throws java.rmi.RemoteException;
}
