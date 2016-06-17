import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

public class ScopeManager {

	private Stack<Block> st;
	private List<Block> releasedBlocks;
	private boolean isDeclaration;
	private List<VarRefDAO> varRefList;
	Scanner s;

	public ScopeManager(String inputFile) {
		st = new Stack<Block>();
		releasedBlocks = new ArrayList<Block>();
		isDeclaration = false;
		varRefList = new ArrayList<VarRefDAO>();
		s = new Scanner(inputFile);

		// loop over input
		while(true) {
			TokenInfo ti = s.yylex();
			exec(ti);
		}
	}

	private void exec(TokenInfo ti) {
		switch(ti.getTokenType()) {
		case SC:
			this.isDeclaration = false;
			break;
		case INT:
			this.isDeclaration = true;
			break;
		case ID:
			if(isDeclaration)
				addVariableDeclaration(ti);
			else
				addVariableReference(ti);
			break;
		case LC:
			// add new block
			addBlock();
			break;
		case RC:
			break;
		case MAIN:
			break;
		case ASSIGN:
			break;
		default:
			break;
		}
	}



	public void addVariableReference(TokenInfo ti) {
		String name = ti.getAttribute();
		int line = ti.getLine();
		int blockNum = st.peek().getSerialNumber();
		String declarationBlock = null;
		Iterator<Block> blockDecNumIter = st.iterator();

		while(blockDecNumIter.hasNext()) {
			Block b = blockDecNumIter.next();
			Map<String, VarBundle> m = b.getMap();
			String attr = ti.getAttribute();
			VarBundle vb = m.get(attr);
			if(vb != null)
				declarationBlock = "" + vb.getLineNumList().get(0);
		}
		varRefList.add(new VarRefDAO(name, line, blockNum, declarationBlock));
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

	private void addVariableDeclaration(TokenInfo ti) { st.peek().addVariable(ti); }
	private void releaseBlock() { releasedBlocks.add(st.pop()); }
	private void logDec() { this.isDeclaration = true; }
	private void unLogDec() { this.isDeclaration = false; }
	public boolean isDeclaration() { return this.isDeclaration; }
	public void addBlock(Block b) { st.push(b); }
	public void addBlock() {
		Block b = new Block();
		st.push(b);
	}

	public String toString() {
		String str = "";
		releasedBlocks.add(st.pop());
		for(Block b: releasedBlocks) {
			str += "\n\nB" + b.getSerialNumber() + ":";
			Map<String, VarBundle> m = b.getMap();
			Set<Entry<String, VarBundle>> entrySet = m.entrySet();
			if(entrySet.size() > 1) {
				for(Entry<String, VarBundle> entry: entrySet) {
					VarBundle vb = entry.getValue();
					if(vb.getLineNumList().size() > 1) {
						str += "\n" + vb.getVarName() + ": ";
						for(int i = 0; i < vb.getLineNumList().size(); i++) {
							str += vb.getLineNumList().get(i);
							if(i < vb.getLineNumList().size()-1)
								str += ",";
						}
					}
				}
			}
			else {
				str += "\nno declarations";
			}
		}
		
		str += "\nreferences\n";
		if(varRefList.size() > 0) {
			for(int i = 0; i < varRefList.size(); i++) {
				str += "\n" + varRefList.get(i).toString();
			}
		}
		else {
			str += "no references";
		}
		return str;
	}

}
