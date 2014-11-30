import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class VotingImpl extends UnicastRemoteObject implements VotingInterface {

	public boolean isVotingOpen; // Should be pretty self-explanitory
	public List<User> users = new ArrayList<User>(); // all users in the system
	public List<User> votes = new ArrayList<User>(); // all users who have a
														// vote
	public User admin = new User("admin", "adminpass"); // creates the admin
														// user
	public Options options = new Options(); // contains a list that holds all
											// available options
	private Vector clientList; // holds a list of all clients subscribed

	public VotingImpl() throws RemoteException, MalformedURLException {
		super();
		addUsers();
		isVotingOpen = true;
		clientList = new Vector();
	}

	// Prevents all users from placing any more votes
	// Calls the method that notifies the users
	public void endVoting() {
		isVotingOpen = false;
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

	// adds a option to the list of choices
	public boolean addChoice(String choice) {
		if (options.choices.contains(choice))
			return false;
		options.addToChoices(choice);
		return true;
	}

	// Gets all choices available
	public List<String> getChoices() {
		if (options.choices.size() == 0)
			return null;
		return options.choices;
	}

	// This method adds all the users to the users list.
	public void addUsers() {
		users.add(new User("blake", "blakepass"));
		users.add(new User("tom", "tompass"));
		users.add(new User("mike", "mikepass"));
		users.add(admin);
	}

	// This method checks to see if the username and password is one of the
	// admin.
	// Returns true if it is admin false if otherwise.
	public boolean isAdmin(String username, String password) {
		if (username.equals("admin") && password.equals("adminpass"))
			return true;
		return false;
	}

	// This checks to make sure that the user is in the system.
	public boolean validateUser(String username, String password) {
		if (users.contains(new User(username, password)))
			return true;
		return false;
	}

	// Actually casts the vote for the user.
	public boolean castVote(String username, String password, int num) {
		if (!isVotingOpen)
			return false;
		User checkUser = new User(username, password);
		if (votes.contains(checkUser)) {
			for (int i = 0; i < votes.size(); i++) {
				if (votes.get(i).getUsername().equals(username)) {
					votes.remove(i);
				}
			}
		}
		String choice = options.choices.get(num);
		checkUser.setUserVote(new Vote(choice));
		votes.add(checkUser);
		return true;
	}

	// If the user has voted already then it removes the user from the votes
	// list.
	// If their vote doesn't exist then it returns false.
	public boolean removeVote(String username, String password) {
		if (!isVotingOpen)
			return false;
		User checkUser = new User(username, password);
		if (votes.contains(checkUser)) {
			for (int i = 0; i < votes.size(); i++) {
				if (votes.get(i).getUsername().equals(username)) {
					votes.remove(i);
					return true;
				}
			}
		}
		return false;
	}

	// Gets the results of the election. Will work for the running results as
	// well
	public String getResults() {
		int index = 0;
		int[] counts = new int[options.choices.size()];
		for (int i = 0; i < votes.size(); i++) {
			for (int j = 0; j < options.choices.size(); j++) {
				if (options.choices.get(j).equals(
						votes.get(i).getUserVote().getChoice())) {
					index = j;
					break;
				}
			}
			counts[index]++;
		}
		String results = "Results:\n";
		for (int i = 0; i < options.choices.size(); i++) {
			results += options.choices.get(i);
			results += " : ";
			results += counts[i] + " votes";
			results += "\n";
		}

		return results;
	}

	// registers that the user wants to be notified when the election ends.
	public void registerForCallback(CallbackClientInterface callbackClientObject)
			throws RemoteException {
		if (!(clientList.contains(callbackClientObject))) {
			clientList.addElement(callbackClientObject);
		}

	}

	// if the user subscribed to callbacks then it removes them from the list
	public synchronized void unregisterForCallback(
			CallbackClientInterface callbackClientObject)
			throws RemoteException {
		if (clientList.removeElement(callbackClientObject)) {

		}

	}

	// This lets all clients subscribed know that the election is over.
	public synchronized void doCallbacks() throws RemoteException,
			MalformedURLException, NotBoundException {
		for (int i = 0; i < clientList.size(); i++) {
			CallbackClientInterface nextClient = (CallbackClientInterface) clientList
					.elementAt(i);
			nextClient.notifyMe("back");
		}
	}
}
