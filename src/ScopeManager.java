import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
		addBlock();

		// loop over input
		while(true) {
			TokenInfo ti = s.yylex();
			if(ti != null && ti.getTokenType() == ETokenType.EOF) {
				break;
			}
			if(ti != null) {
				exec(ti);
			}
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
			if(isDeclaration) {
				if(!addVariableDeclaration(ti)) {
					System.err.println("ERROR: addVariableDeclaration failed.");
				}
			}
			else { 
				if(!addVariableReference(ti))
					System.err.println("ERROR: addVariableReference failed.");
			}
			break;
		case LC:
			addBlock();
			break;
		case RC:
			releaseBlock();
			break;
//		case MAIN:
//			break;
//		case ASSIGN:
//			break;
		default:
			break;
		}
	}

	public boolean addVariableReference(TokenInfo ti) {
		if(ti == null) {
			return false;
		}
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
		return true;
	}

//	public int getBlockDecelerationNumber(TokenInfo ti) {
//		// TODO
//		return 0;
//	}

//	private boolean isExistInCurrentBlock(TokenInfo ti) {
//		Block b = st.peek();
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

	private boolean addVariableDeclaration(TokenInfo ti) { 
		if(ti != null && !st.isEmpty()) {
			st.peek().addVariable(ti);
			return true;
		}
		else {
			System.err.println("Token is null or Stack is empty!");
			return false;
		}
	}

	private void releaseBlock() {
//		releasedBlocks.add(st.pop());
		st.pop();
	}

	private void logDec() {
		this.isDeclaration = true;
	}

	private void unLogDec() { 
		this.isDeclaration = false;
	}

	public boolean isDeclaration() {
		return this.isDeclaration;
	}

	public void addBlock() {
		Block b = new Block();
		st.push(b);
		releasedBlocks.add(b);
	}

	public String toString() {
		String str = "";

		if(releasedBlocks.size() > 0) {
			str += "redeclarations\n\n";
			for(Block b: releasedBlocks) {
				Map<String, VarBundle> m = b.getMap();
				Set<Entry<String, VarBundle>> entrySet = m.entrySet();
				if(entrySet.size() > 0 && b.isRedclareExist()) {
					str += "B" + b.getSerialNumber() + ":";
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
					str += "\n\n";
				}
			}
		}
//		else {
//			str += "\nno declarations";
//		}

		str += "references";
		if(varRefList.size() > 0) {
			for(int i = 0; i < varRefList.size(); i++) {
				str += "\n\n" + varRefList.get(i).toString();
			}
		}
		else {
			str += "no references";
		}
		return str;
	}

}
