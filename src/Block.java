import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Block {

	private static int counter = 0;
	private int serialNumber;
	private Map<String, VarBundle> m;

	public Block() {
		this.serialNumber = counter;
		counter++;
		
		this.m = new HashMap<String, VarBundle>();
	}

	public void addVariable(TokenInfo ti) {

		System.out.println("token info line = " + ti.getLine()); //--
		
		VarBundle vb = m.get(ti.getAttribute());

		if(vb == null) {
			
			vb = new VarBundle(ti.getAttribute(), ti.getLine());
			m.put(ti.getAttribute(), vb); 
		}
		else {
			vb.addLine(ti.getLine());
		}
	}

	public VarBundle getVar(String var) {
		return m.get(var);
	}

	public int getSerialNumber() {
		return this.serialNumber;
	}

	public Map<String, VarBundle> getMap() {
		return this.m;
	}

//	public boolean isVariableExists(TokenInfo t) {
//		//		if(variables.contains(t)) {
//		//			return true;
//		//		}
//		return false;
//	}
}