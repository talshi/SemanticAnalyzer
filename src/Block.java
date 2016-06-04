import java.util.Set;

public class Block {
	
	private static int counter;
	private int serialNumber;
	private Set<Token> s;

	public Block() {
		
		serialNumber = counter;
		counter++;
	}
	
	private void addVariable(Token t) {
		// TODO
		
	}
	
	private boolean isVariableExists(Token t) {
		// TODO
		return false;
	}
	
}
