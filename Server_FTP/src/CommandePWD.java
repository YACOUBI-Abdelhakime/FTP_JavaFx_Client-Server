import java.io.PrintStream;

public class CommandePWD extends Commande {
	
	public CommandePWD(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(UserInfo userInfo) {
		if(commandeArgs.length != 0) {
			ps.println("2 Erreur d'Usage de la commande : >> pwd /*Without arguments*/");	
			return;
		}
		String s = userInfo.AbsToRelativePath(userInfo.curPath);
		ps.println("0 " + s+"\\");
	}

}
