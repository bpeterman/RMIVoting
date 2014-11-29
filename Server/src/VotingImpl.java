import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class VotingImpl extends UnicastRemoteObject implements VotingInterface {

	public List<User> users = new ArrayList<User>();
	public User admin = new User("admin", "adminpass");
	public VotingImpl() throws RemoteException {
		super();
		addUsers();
	}
	
	public void addUsers(){
		users.add(new User("blake", "blakepass"));
		users.add(new User("tom", "tompass"));
		users.add(new User("mike", "mikepass"));
		users.add(admin);
	}

	public String sayHello(String name) throws RemoteException {
		return "Hello, World";
	}
	
	public boolean validateUser(String username, String password){
		if (users.contains(new User(username, password)))
			return true;
		return false;
	}
}
