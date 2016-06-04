import java.util.Stack;

public class ScopeManager {
	
	private Stack<Block> st;
	boolean isDeclaration;
	
	public ScopeManager() {
		st = new Stack<Block>();
		isDeclaration = false;
	}

	private boolean isExistInCurrentBlock() {
		// TODO
		return false;
	}
	
	private boolean isExist() {
		// TODO
		return false;
	}
	
	private void releaseBlock() {
		// TODO (pop from stack)
	}
	
	
	
}
