
public class Vote {

	private String choice;
	public Vote(String choice) {
		this.choice = choice;
	}
	
	public boolean isIt(Vote obj){
		if (obj.choice.equals(choice))
			return true;
		return false;
	}
	
	public String getChoice() {
		return choice;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}

}
