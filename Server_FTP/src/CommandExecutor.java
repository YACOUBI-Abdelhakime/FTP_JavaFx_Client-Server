import java.io.PrintStream;

public class CommandExecutor {
	
	//public static boolean userOk = false ;
	//public static boolean pwOk = false ;
	
	public static void executeCommande(PrintStream ps, String commande, UserInfo userInfo) {
		boolean cmdNotExist = true;
		if(userInfo.userOk && userInfo.pwOk) {
			// Changer de repertoire. Un (..) permet de revenir au repertoire superieur
			if(commande.split(" ")[0].equals("cd")) { (new CommandeCD(ps, commande)).execute(userInfo);cmdNotExist=false; };
	
			// Telecharger un fichier
			if(commande.split(" ")[0].equals("get")) { (new CommandeGET(ps, commande)).execute(userInfo);cmdNotExist=false; };
			
			// Afficher la liste des fichiers et des dossiers du repertoire courant
			if(commande.split(" ")[0].equals("ls")) {(new CommandeLS(ps, commande)).execute(userInfo);cmdNotExist=false; };
		
			// Afficher le repertoire courant
			if(commande.split(" ")[0].equals("pwd")) { (new CommandePWD(ps, commande)).execute(userInfo);cmdNotExist=false; };
			
			// Envoyer (uploader) un fichier
			if(commande.split(" ")[0].equals("stor")) { (new CommandeSTOR(ps, commande)).execute(userInfo);cmdNotExist=false; };
			
			// Envoyer (uploader) un fichier
			if(commande.split(" ")[0].equals("mkdir")) {(new CommandeMKDIR(ps, commande)).execute(userInfo);cmdNotExist=false; };
						
			// Envoyer (uploader) un fichier
			if(commande.split(" ")[0].equals("rmdir")) { (new CommandeRMDIR(ps, commande)).execute(userInfo);cmdNotExist=false; };
			
			// se deconnecter 
			if(commande.split(" ")[0].equals("logout")) { (new CommandeLOGOUT(ps, commande)).execute(userInfo);cmdNotExist=false; };
		
		}else {
			if(commande.split(" ")[0].equals("pass") || commande.split(" ")[0].equals("user") || commande.split(" ")[0].equals("signup")) {
				// Le mot de passe pour l'authentification
				if(commande.split(" ")[0].equals("pass")) { (new CommandePASS(ps, commande)).execute(userInfo);cmdNotExist=false; };
	
				// Le login pour l'authentification
				if(commande.split(" ")[0].equals("user")) { (new CommandeUSER(ps, commande)).execute(userInfo);cmdNotExist=false; };
				
				// Create new user
				if(commande.split(" ")[0].equals("signup")) { (new CommandeSIGNUP(ps, commande)).execute(userInfo);cmdNotExist=false; };
			}
			else { ps.println("2 Vous n'êtes pas connecté !"); cmdNotExist=false; };
		}
		
		if(cmdNotExist) {
			ps.println("2 Commande inconnue !");
		}
	}

}
