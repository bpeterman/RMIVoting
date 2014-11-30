import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class VotingImpl extends UnicastRemoteObject implements VotingInterface{

	public boolean isVotingOpen;
	public List<User> users = new ArrayList<User>();
	public List<User> votes = new ArrayList<User>();
	public User admin = new User("admin", "adminpass");
	public Options options = new Options();
	private Vector clientList;
	
	
	public VotingImpl() throws RemoteException, MalformedURLException {
		super();
		addUsers();
		isVotingOpen=true;
		clientList = new Vector();
	}
	
	public void endVoting(){
		isVotingOpen=false;
		try {
			doCallbacks();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean addChoice(String choice){
		if(options.choices.contains(choice))
			return false;
		options.addToChoices(choice);
		return true;
	}
	
	public List<String> getChoices(){
		if(options.choices.size()==0)
			return null;
		return options.choices;
	}
	
	public void addUsers(){
		users.add(new User("blake", "blakepass"));
		users.add(new User("tom", "tompass"));
		users.add(new User("mike", "mikepass"));
		users.add(admin);
	}
	
	public boolean isAdmin(String username, String password){
		if(username.equals("admin") && password.equals("adminpass"))
			return true;
		return false;
	}
	
	public boolean validateUser(String username, String password){
		if (users.contains(new User(username, password)))
			return true;
		return false;
	}
	
	public boolean castVote(String username, String password, int num){
		if(!isVotingOpen)
			return false;
		User checkUser = new User(username, password);
		if(votes.contains(checkUser)){
			for(int i=0; i<votes.size(); i++){
				if(votes.get(i).getUsername().equals(username)){
					votes.remove(i);
				}
			}
		}
		String choice = options.choices.get(num);
		checkUser.setUserVote(new Vote(choice));
		votes.add(checkUser);
		return true;
	}
	
	public boolean removeVote(String username, String password){
		if(!isVotingOpen)
			return false;
		User checkUser = new User(username, password);
		if(votes.contains(checkUser)){
			for(int i=0; i<votes.size(); i++){
				if(votes.get(i).getUsername().equals(username)){
					votes.remove(i);
					return true;
				}
			}
		}
		return false;
	}
	
	public String getResults(){
		int index=0;
		int[] counts = new int[options.choices.size()];
		for (int i=0; i<votes.size(); i++){
			for (int j=0; j<options.choices.size(); j++){
				if(options.choices.get(j).equals(votes.get(i).getUserVote().getChoice())){
					index=j;
					break;
				}
			}
			counts[index]++;
		}
		String results="Results:\n";
		for(int i=0; i<options.choices.size(); i++){
			results += options.choices.get(i);
			results += " : ";
			results += counts[i] + " votes";
			results += "\n";
		}
		
		return results;
	}

	public void registerForCallback(CallbackClientInterface callbackClientObject)
			throws RemoteException {
		if(!(clientList.contains(callbackClientObject))){
			clientList.addElement(callbackClientObject);
		}
		
	}

	public synchronized void unregisterForCallback(
			CallbackClientInterface callbackClientObject)
			throws RemoteException {
		if(clientList.removeElement(callbackClientObject)){
			
		}
		
	}
	
	public synchronized void doCallbacks() throws RemoteException, MalformedURLException, NotBoundException{
		for(int i=0; i<clientList.size(); i++){
			CallbackClientInterface nextClient = (CallbackClientInterface)clientList.elementAt(i);
			nextClient.notifyMe("back");
		}
	}
}
