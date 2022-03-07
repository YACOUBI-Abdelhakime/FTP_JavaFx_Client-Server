import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class CommandeUSER extends Commande {
	
	public CommandeUSER(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(UserInfo userInfo) {
		File[] users = userInfo.curPath.listFiles();
		Scanner scan;
		boolean ok = false;
		
		if(commandeArgs.length != 1) {
			userInfo.userOk = false;
			ps.println("2 Erreur d'Usage de la commande : >> user <username>");
			return ;
		}
		
		for(File userF : users) {
			String user = userF.getName();
			
			if(commandeArgs[0].toLowerCase().equals(user.toLowerCase())) {
				userInfo.userOk = true;
				ps.println("0 Commande user OK");
				String tmp = userInfo.curPath.toString()+"\\"+user;
				userInfo.curPath= new File(tmp);
				userInfo.rootPath = userInfo.curPath;
				userInfo.username = user;
				//System.out.println("CUR PATH :: "+Main.curPath.toString());
				try {
					String passFile = userInfo.curPath.toString()+"\\pass.txt";
					File pass = new File(passFile);
					scan = new Scanner(pass);
					userInfo.password = scan.nextLine();
					//System.out.println("PASSWORD :: "+Main.password);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				ok = true;
				break;
			}
			
		}
		
		if(!ok) {
			userInfo.userOk = false;
			ps.println("2 Le user " + commandeArgs[0] + " n'existe pas");	
		}
		
	}

}
