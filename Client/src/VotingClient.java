import java.rmi.Naming;

public class VotingClient {

	public static void main(String[] args) {
		try {
			String registryURL = "rmi://" + Constants.url + ":"
					+ Constants.RMIPort + "/voting";
			System.out.println(registryURL);
			VotingInterface v = (VotingInterface) Naming.lookup(registryURL);
			System.out.println("Lookup Completed");
			if(v.validateUser("admin", "adminpass"))
				System.out.println("Validated");
			else
				System.out.println("Not Validated");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
