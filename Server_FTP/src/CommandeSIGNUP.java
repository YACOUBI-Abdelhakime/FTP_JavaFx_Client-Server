import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class CommandeSIGNUP extends Commande{
	public CommandeSIGNUP(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(UserInfo userInfo) {
		if (commandeArgs.length == 2) {
			try {
				File newDir = new File(userInfo.curPath.toString()+"\\"+commandeArgs[0]);
				newDir.mkdir();
				FileOutputStream fos = new FileOutputStream(newDir.toString()+"\\pass.txt");
				
				fos.write(commandeArgs[1].getBytes());				
				fos.close();
				
				userInfo.userOk = true;
				userInfo.pwOk = true;
				userInfo.curPath = newDir;
				ps.println("1 Accaunt created");
				ps.println("0 Vous êtes bien connecté sur notre serveur");
				
				/*String userStr = "user "+commandeArgs[1];
				String passStr = "pass "+commandeArgs[2];
				(new CommandeUSER(ps, userStr)).execute();
				(new CommandePASS(ps, passStr)).execute();*/
				
			} catch (Exception e) {
				ps.println("2 Erreur try leater please");
				e.printStackTrace();
			}	
		}else {
			ps.println("2 Erreur d'Usage de la commande : >> signup <username> <password>");
		}
	}
}
