import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ScopeManager {

	private Stack<Block> st;
	private boolean isDeclaration;
	private List<VarRefDAO> varRefList;
	Scanner s;
	
	public ScopeManager(String inputFile) {
		st = new Stack<Block>();
		isDeclaration = false;
		varRefList = new ArrayList<VarRefDAO>();
		s = new Scanner(inputFile);
		// loop over input
		while(true) {
			TokenInfo ti = s.yylex();
			exec(ti);
			// TODO break statement
		}
	}

	public void exec(TokenInfo ti) {
		switch(ti.getTokenType()) {
		case SC:
			// end of line
			break;
		case INT:
			// must be a decleration
			break;
		case ID:
			break;
		case LC:
			// TODO add new block
			break;
		case RC:
			break;
		case MAIN:
			break;
		case ASSIGN:
			break;
		}
	}

	public boolean isDeclaration() {
		return this.isDeclaration;
	}

	public void addVariableReference(TokenInfo ti) {
		//		VarRefDAO vrd = new VarRefDAO(str1, str2, num, str3);
		//		varRefList.add(vrd);
	}

	public void logInt() {
		// TODO
	}

	public void unLogInt() {
		// TODO
	}

	public void addBlock(Block b) {
		st.push(b);
	}

	public int getBlockDecelerationNumber(TokenInfo ti) {
		// TODO
		return 0;
	}

	private boolean isExistInCurrentBlock(TokenInfo ti) {
		Block b = st.peek();
//		b.isVariableExists(ti);
		return false;
	}

	//	private boolean isExist() {
	//		// TODO
	//		return false;
	//	}

	private void releaseBlock() {
		st.pop();
	}

	public void printVarRefList() {
		if(varRefList.isEmpty()) {
			System.out.println("no references");
		}
		else {
			for(VarRefDAO vrd: varRefList) {
				System.out.println(vrd.toString());
			}
		}
	}

}
