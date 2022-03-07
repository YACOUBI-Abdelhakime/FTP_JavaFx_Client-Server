import java.io.File;
import java.io.PrintStream;

public class CommandeLS extends Commande {
	
	public CommandeLS(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(UserInfo userInfo) {
		File[] files = userInfo.curPath.listFiles();
		if(commandeArgs.length != 0) {
			ps.println("2 Erreur d'Usage de la commande : >> ls /*Without arguments*/");	
			return;
		}
		
	    
	    if (files != null && files.length > 0) {
	      
	      for (int i = 0; i < files.length; i++) {
	        if(!files[i].getName().equals("pass.txt")) {
	        	String data = "1 " + files[i].getName();
		        if (files[i].isDirectory()) {
		          data += "\\";
		        }
		        ps.println(data);
	        }
	        
	      }
	      ps.println("0 ");
	      
	    } else {
	      ps.println("0 Empty.");
	    }
	}

}
