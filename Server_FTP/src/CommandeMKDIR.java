import java.io.File;
import java.io.PrintStream;

public class CommandeMKDIR extends Commande {
	
	public CommandeMKDIR(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(UserInfo userInfo) {
		if(this.commandeArgs.length != 1) {
			ps.println("2 Erreur d'Usage de la commande :  >> mkdir <dir>");
		}else {
			String dirStr = userInfo.curPath+"\\"+this.commandeArgs[0];
	        File newF = new File(dirStr);

	        if (newF.mkdir()) {
	        	ps.println("0 Directory Created");
	        }else {
	        	ps.println("2 Directory cannot be created !");
	        }
		}
	}

}
