public class VarRefDAO {

	private String varName;
	private int lineNum;
	private int blockNum;
	private String decLineNum;

	public VarRefDAO(String varName, int lineNum, int blockNum, String decBlock) {
		this.varName = varName;
		this.lineNum = lineNum;
		this.blockNum = blockNum;
		if(decBlock != null)
			this.decLineNum = decBlock;
	}
	
	public String toString() {
		return varName + ":" + "\n" +
				"ref line: " + lineNum + "\n" +
				"ref block: " + blockNum + "\n" +
				"dec line: " + decLineNum;
	}

}