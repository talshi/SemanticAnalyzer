import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class ParserMain 
{
	public static void main(String[] args) 
	{
		Parser p = new Parser(args);
		AST ast = p.yyLL1Parse();
		String output = "";
		if(ast == null) {
			// print 'Syntax mismatch' to output file
			output += "Syntax mismatch";
		}
		else {
			ScopeManager sm = new ScopeManager(args[1]);
			output = sm.toString();
//			System.out.println(output);
		}
		// print to file
		String fileName = args[1].split("\\.")[0];
		File file = new File(fileName + ".sem");
		Writer writer = null;
		try {
			if(!file.createNewFile()) {
				System.err.println("File already exist!");
			}
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			writer.write(output);
			writer.close();
		} catch (FileNotFoundException e1) {
			System.err.println("Error creating writer object!");
			System.exit(0);
		} catch (IOException e) {
			System.err.println("Error creating output file!");
			System.exit(0);
		} 
	}

}
