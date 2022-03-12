import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandeSTOR extends Commande {
	
	public CommandeSTOR(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(UserInfo userInfo) {
		if (commandeArgs.length == 1) {
			int port = 3032;
			if(commandeArgs[0].contains("pass.txt")) {
				ps.println("2 ");
				return ;
			}else {
				ps.println("upload");
			}
			try {
				//creer un autre flux de donner pour recoi le fichier
				ServerSocket serverFtp = new ServerSocket(port);
				
				ps.println("0 port "+port);
				Socket sockFTP = serverFtp.accept();
				InputStream is = sockFTP.getInputStream();
				//File file = Main.curPath.toPath().resolve(commandeArgs[0]).toFile();
				
				FileOutputStream fos = new FileOutputStream(userInfo.curPath.toString()+"\\"+commandeArgs[0]);

				byte[] buf = new byte[512];
				while(is.read(buf) != -1 ) {
					fos.write(buf);
				}
				
		    	fos.close();
			    serverFtp.close();
			    sockFTP.close();
		    	is.close();
		    	ps.println("0 File uploaded");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			ps.println("2 Erreur d'Usage de la commande : >> stor <file path>");
		}
	}

}
