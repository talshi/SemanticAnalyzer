
public class VarRefDAO {

	private String varName;
	private int lineNum;
	private int blockNum;
	private String decLineNum;
	
	public VarRefDAO(String str1, String str2, int num, String str3) {
		
	}
	
	public String toString() {
		return varName + ":" + "\n" +
				"ref line: " + lineNum +
				"ref block: " + blockNum +
				"dec line: " + decLineNum;
	}
	
}
