import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Block {

	private static int counter = 0;
	private int serialNumber;
	private Map<String, VarBundle> m;
	private boolean redeclare;

	public Block() {
		this.serialNumber = counter;
		counter++;
		this.m = new HashMap<String, VarBundle>();
		redeclare = false;
	}

	public void addVariable(TokenInfo ti) {

		VarBundle vb = m.get(ti.getAttribute());

		if(vb == null) {
			vb = new VarBundle(ti.getAttribute(), ti.getLine());
			m.put(ti.getAttribute(), vb);
		}
		else {
			vb.addLineNumber(ti.getLine());
			redeclare = true;
		}
	}

	public VarBundle getVar(String var) { return m.get(var); }
	public int getSerialNumber() { return this.serialNumber; }
	public Map<String, VarBundle> getMap() { return this.m; }
	public boolean isRedclareExist() { return this.redeclare; }
	
	public String toString() {
		return "Block Serial Number: " + serialNumber + "\n" +
				"Block Map: " + m.toString() + "\n";		
	}
}