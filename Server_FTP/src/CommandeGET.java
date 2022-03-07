import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandeGET extends Commande {
	
	public CommandeGET(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(UserInfo userInfo) {
		if (commandeArgs.length == 1) {
			int port = 3031;
			File tempFile = new File(".\\FILES\\"+commandeArgs[0]);
			if(!tempFile.exists()) {
				ps.println("NotFound");
				return;
			}
			if(!commandeArgs[0].contains("pass.txt")) {
				ps.println("download");
			}else {
				ps.println("0 ");
				return ;
			}
			try {
				//creer un autre flux de donner pour envoyer le fichier
				ServerSocket serverFtp = new ServerSocket(port);
				
				ps.println("0 port "+port);
				Socket sockFTP = serverFtp.accept();
				OutputStream os = sockFTP.getOutputStream();
				File file = userInfo.curPath.toPath().resolve(commandeArgs[0]).toFile();
				
				FileInputStream fis = new FileInputStream(file);

				byte[] buf = new byte[512];
				while(fis.read(buf) != -1 ) {
					os.write(buf);
				}				
		    
			    serverFtp.close();
			    sockFTP.close();
		    	os.close();
		    	fis.close();
		    	ps.println("0 File downloaded");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			ps.println("2 Erreur d'Usage de la commande : >> get <file name>");
		}
	}
}
