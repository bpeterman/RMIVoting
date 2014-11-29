import java.rmi.Remote;

public interface VotingInterface extends Remote {
	public boolean validateUser(String username, String password) throws java.rmi.RemoteException;
}
