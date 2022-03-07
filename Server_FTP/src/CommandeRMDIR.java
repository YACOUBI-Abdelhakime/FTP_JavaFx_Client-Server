import java.io.File;
import java.io.PrintStream;

public class CommandeRMDIR extends Commande {
	
	public CommandeRMDIR(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(UserInfo userInfo) {
		if(this.commandeArgs.length != 1) {
			ps.println("2 Erreur d'Usage de la commande : >> rmdir <dir>");
		}else {
			
			String dirStr = userInfo.curPath+"\\"+this.commandeArgs[0];
	        File F = new File(dirStr);
	        if(!F.exists()) {
	        	ps.println("2 Directory not found !");
				return;
			}

	        if (F.delete()) {
	        	ps.println("0 Directory Deleted");
	        }else {
	        	ps.println("2 Directory cannot be Deleted !");
	        }
		}
	}

}
