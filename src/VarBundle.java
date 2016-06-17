import java.util.ArrayList;
import java.util.List;

public class VarBundle {

	private String varName;
	private List<Integer> lineNumList;

	public VarBundle(String varName, int line) {
		lineNumList = new ArrayList<Integer>();
		this.varName = varName;
		lineNumList.add(line);
	}

	public void addLine(int lineNumber) {
		lineNumList.add(lineNumber);
	}

	public String getVarName() {
		return this.varName;
	}
	
	public List<Integer> getLineNumList() {
		return this.lineNumList;
	}

}