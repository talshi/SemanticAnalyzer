import java.util.List;

public class VarBundle {
	
	private String varName;
	private List<Integer> lineNumList;
	
	public VarBundle() {
		
	}
	
	public VarBundle(String varName, int lineNumber) {
		this.varName = varName;
		this.lineNumList.add(lineNumber); // ??
	}
	
	public void addLine(int lineNumber) {
		// TODO
	}

}
