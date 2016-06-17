
public class ParserMain 
{
	public static void main(String[] args) 
	{
		Parser p = new Parser(args);
		AST ast = p.yyLL1Parse();
	}

}
