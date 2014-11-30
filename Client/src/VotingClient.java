import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class VotingClient {

	public static void main(String[] args) {
		try {
			String registryURL = "rmi://" + Constants.url + ":"
					+ Constants.RMIPort + "/voting";
			System.out.println(registryURL);
			VotingInterface v = (VotingInterface) Naming.lookup(registryURL);
			System.out.println("Lookup Completed");
			System.out.println("Username:");
			InputStreamReader is = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(is);
			String username = br.readLine();
			System.out.println("Password");
			String password = br.readLine();
			while(true){
				if(v.validateUser(username, password)){
					System.out.println("Validated");
					break;
				}
				System.out.println("Not Validated");
				System.out.println("Username:");
				username = br.readLine();
				System.out.println("Password");
				password = br.readLine();
			}
			while(true){
				printOptions();
				String choice = br.readLine();
				int decision = Integer.parseInt(choice);
				if(decision==1){
					proposeOption(v, br);
				}
				else if(decision==2){
					viewOptions(v);
				}
				else if(decision==3){
					vote(v, br, username, password);
				}
				else if(decision==4){
					removeVote(v, username, password);
				}
				else if(decision==5){
					viewResults(v);
				}
				else if(decision==6){
					subscribe(v, username, password);
				}
				else if(decision==7){
					viewResults(v);
				}
				else if(decision==8){
					viewResults(v);
				}
				else if(decision==9){
					System.out.println("Bye!");
					System.exit(0);
				}
				else if(decision==10 && username.equals("admin")){
					v.endVoting();
					System.out.println("Voting Ended");
				}		
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void printOptions(){
		System.out.println("What do you want to do? : ");
		System.out.println("1.) Propose a new option");
		System.out.println("2.) View options");
		System.out.println("3.) Vote");
		System.out.println("4.) Remove Vote");
		System.out.println("5.) See results so far");
		System.out.println("6.) Subscribe for ending");
		System.out.println("7.) Stop subscription for ending");
		System.out.println("8.) Do nothing");
		System.out.println("9.) Quit");
	}
	
	public static void proposeOption(VotingInterface v, BufferedReader br) throws IOException{
		System.out.println("Choice:");
		String choice = br.readLine();
		if(!v.addChoice(choice)){
			System.out.println("Choice invalid");
		}
		else
			System.out.println("Choice added.");
	}
	
	public static void viewOptions(VotingInterface v) throws IOException{
		System.out.println("Choices:");
		List<String> choices = v.getChoices();
		if(choices==null){
			System.out.println("No choices yet");
			System.out.println("--------------------------\n");
			return;
		}
		for(int i=0; i<choices.size(); i++){
			System.out.println((i) + ".) " + choices.get(i));
		}
		System.out.println("--------------------------\n");
	}
	
	public static void vote(VotingInterface v, BufferedReader br, String username, String password) throws IOException{
		System.out.println("Choose the number you would like to vote for:");
		String choice = br.readLine();
		int decision = Integer.parseInt(choice);
		if(v.castVote(username, password, decision)){
			System.out.println("Vote Successful");
		} else{
			System.out.println("Vote not successful");
		}
	}
	
	public static void viewResults(VotingInterface v) throws IOException{
		System.out.println(v.getResults());
	}
	
	public static void removeVote(VotingInterface v, String username, String password) throws IOException{
		System.out.println("Choose the number you would like to vote for:");
		if(v.removeVote(username, password)){
			System.out.println("Vote removed Successfully");
		} else{
			System.out.println("Vote not removed successfully");
		}
	}
	
	public static void subscribe(VotingInterface v, String username, String password) throws RemoteException{
		CallbackClientInterface callbackObj = new CallbackClientImpl();
		v.registerForCallback(callbackObj);
		System.out.println("Subscribed");
	}

	
}
