import java.net.ServerSocket;
import java.net.Socket;



public class MainServeur {
	public static void main(String []args) {
		ServerSocket serveurFTP=null;
		Socket socket=null;
		
		try {
			serveurFTP=new ServerSocket(3030);
			int nbClients = 0;
			while(true) {
				System.out.println("Le Serveur FTP attend un client ...");
				socket=serveurFTP.accept();
				nbClients++;
				System.out.println("Client <"+nbClients+"> connecter");
				Service sr = new Service(socket);
				sr.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
