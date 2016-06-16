import java.util.Set;

public class Block {
	
	private static int counter = 0;
	private int serialNumber;
//	private Set<TokenInfo> variables;
	private VarBundle variables;

	public Block() {
		serialNumber = counter;
		counter++;
		variables = new VarBundle();
	}
	
	private void addVariable(TokenInfo t) {
//		variables.add(t);
	}
	
	public boolean isVariableExists(TokenInfo t) {
//		if(variables.contains(t)) {
//			return true;
//		}
		return false;
	}
	
	public int getSerialNumber() {
		return this.serialNumber;
	}
	
}
