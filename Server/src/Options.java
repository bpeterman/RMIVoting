import java.util.ArrayList;
import java.util.List;


public class Options {
	List<String> choices = new ArrayList<String>();
	public Options() {
		
	}
	
	public boolean addToChoices(String choice){
		if(choices.contains(choice))
			return false;
		choices.add(choice);
		return true;
	}
	
	public void removeFromChoices(String choice){
		if(choices.contains(choice)){
			choices.remove(choice);
		}
	}
	

}
