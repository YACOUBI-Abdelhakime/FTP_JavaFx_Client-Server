import java.io.File;
import java.io.PrintStream;
import java.nio.file.Path;

public class CommandeCD extends Commande {

	public CommandeCD(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(UserInfo userInfo) {	
		if(this.commandeArgs.length != 1) {
			ps.println("2 Erreur d'Usage de la commande : >> cd <path>");
		}else {
			Path tmpPath = userInfo.curPath.toPath().normalize();
		
			tmpPath = tmpPath.resolve(this.commandeArgs[0]).normalize();
			File newFile = tmpPath.toFile();
		    		      
			if (newFile.exists()) {
				Boolean AcceptedPath = tmpPath.toAbsolutePath().startsWith(userInfo.rootPath.getAbsolutePath());
				
				if(AcceptedPath) {
					userInfo.curPath = tmpPath.toFile().getAbsoluteFile();
			        ps.println("0 " + userInfo.AbsToRelativePath(userInfo.curPath)+"\\");
				}else {
					ps.println("2 Inacciptable Path.");
				}
			} else {
		        ps.println("2 Directory not found.");
			}
		}
	}
}
