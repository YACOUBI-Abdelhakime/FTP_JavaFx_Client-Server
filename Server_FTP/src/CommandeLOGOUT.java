import java.io.PrintStream;
import java.nio.file.Path;

public class CommandeLOGOUT extends Commande{
	public CommandeLOGOUT(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(UserInfo userInfo) {
		if(commandeArgs.length != 0) {
			ps.println("2 Erreur d'Usage de la commande : >> logout /*Without arguments*/");	
			return;
		}
		userInfo.userOk = false;
		userInfo.pwOk = false;
		
		if(userInfo.rootPath!=null) {
			Path tmpPath = userInfo.rootPath.toPath().normalize();
			tmpPath = tmpPath.resolve("..").normalize();
			userInfo.curPath = tmpPath.toFile();
		}		
		
		ps.println("0 logout successfully");
	}

}
