import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Map.Entry;

public class BlockManager {

	private Stack<Block> st;
	private boolean isDeclaration;
	private List<VarRefDAO> varRefList;
	private List<Block> releasedBlocks;

	public BlockManager() {
		this.st = new Stack<Block>();
		this.isDeclaration = false;
		this.varRefList = new ArrayList<VarRefDAO>();
		this.releasedBlocks = new ArrayList<Block>();

		// add first block to the stack and to the arrayList
		addBlock(new Block());
	}

	public void addBlock(Block b) {
		st.push(b);
		releasedBlocks.add(b);
	}

	public void releaseBlock() {
		st.pop();
	}

	public void exec(TokenInfo ti) {

		switch(ti.getTokenType()) {
		case LC: // {
			addBlock(new Block());
			break;
		case RC: // }
			releaseBlock();
			break;
		case ID: 
			if(this.isDeclaration)
				// this ID was declared in the current block 
				addVariableDec(ti);
			else
				addVariableReference(ti); 
			break;
		case INT:
			this.isDeclaration = true;
			break;
		case SC: // ;
			this.isDeclaration = false;
			break;
		}
	}

	public boolean isDeclaration() {
		return this.isDeclaration;
	}

	public void addVariableReference(TokenInfo ti) {

		String varName = ti.getAttribute();
		int varLine = ti.getLine();
		int blockNum = st.peek().getSerialNumber();
		String decBlock = null;
		Stack<Block> tempBlocks = (Stack<Block>)st.clone();

		// find in which block this tokenInfo is declared
		while(!tempBlocks.isEmpty())
		{
			Block b = tempBlocks.pop();
			Map<String, VarBundle> map = b.getMap();
			String att = ti.getAttribute();

			if(map.get(att) != null)
			{
				decBlock = "" + (map.get(att)).getLineNumList().get(0);
				break;
			}
		}

		this.varRefList.add(new VarRefDAO(varName, varLine, blockNum, decBlock)); 
	}

	public void addVariableDec(TokenInfo ti)
	{
		Block head = st.peek();
		head.addVariable(ti);
	}

	// print to file
	public void print(String inputFile)
	{
		String str = "redeclarations\n\n";
		for(Block b : releasedBlocks)
		{
			boolean writeBlock = true;
			Map<String, VarBundle> m = b.getMap();
			Set<Entry<String, VarBundle>> entrySet = m.entrySet();
			
			for (Entry<String, VarBundle> entry : entrySet)
			{
				VarBundle vb = entry.getValue();
				if(vb.getLineNumList().size() > 1)
				{
					if(writeBlock)
					{
						str += "B" + b.getSerialNumber() + ":\n";
						writeBlock = false;
					}
					str += vb.getVarName() + ": ";
					for(int i=0; i<vb.getLineNumList().size(); i++)
					{
						str += vb.getLineNumList().get(i) + ", ";
					}
					str += "\n";
				}
			}
			if(!writeBlock)
				str += "\n\n";
		}
		
		str += "\n\nreferences\n\n";
		
		for(VarRefDAO var : varRefList)
		{
			str += var.toString() + "\n\n";
		}
		
		String outputFile = inputFile.substring(0, inputFile.indexOf("."));
		outputFile += ".sem";
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
			bw.write(str);
			bw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	
//	public int getBlockDecelerationNumber(TokenInfo ti) {
//		// TODO
//		return 0;
//	}
//
//	private boolean isExistInCurrentBlock(TokenInfo ti) {
//		Block b = st.peek();
//		b.isVariableExists(ti);
//		return false;
//	}

	//	private boolean isExist() {
	//		// TODO
	//		return false;
	//	}
}