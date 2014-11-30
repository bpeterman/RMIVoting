import java.rmi.Remote;
import java.util.List;

public interface VotingInterface extends Remote {
	public boolean validateUser(String username, String password) throws java.rmi.RemoteException;
	public boolean castVote(String username, String password, int num) throws java.rmi.RemoteException;
	public boolean isAdmin(String username, String password) throws java.rmi.RemoteException;
	public boolean addChoice(String choice) throws java.rmi.RemoteException;
	public List<String> getChoices() throws java.rmi.RemoteException;
	public String getResults() throws java.rmi.RemoteException;
	public boolean removeVote(String username, String password) throws java.rmi.RemoteException;
	public void registerForCallback(CallbackClientInterface callbackClientObject) throws java.rmi.RemoteException;
	public void unregisterForCallback(CallbackClientInterface callbackClientObject) throws java.rmi.RemoteException;
	public void endVoting() throws java.rmi.RemoteException;
}
