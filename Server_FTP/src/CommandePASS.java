import java.io.PrintStream;

public class CommandePASS extends Commande {
	
	public CommandePASS(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(UserInfo userInfo) {
		if(commandeArgs.length != 1) {
			userInfo.pwOk = false;
			ps.println("2 Erreur d'Usage de la commande : >> pass <password>");
			return ;
		}
		if(commandeArgs[0].toLowerCase().equals(userInfo.password)) {
			userInfo.pwOk = true;
			ps.println("1 Commande pass OK");
			ps.println("0 Vous êtes bien connecté sur notre serveur");

		}
		else {
			userInfo.pwOk = false;
			ps.println("2 Le mode de passe est faux");
		}
		
	}

}
