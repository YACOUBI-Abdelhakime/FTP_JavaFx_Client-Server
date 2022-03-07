import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Service extends Thread{
    private Socket socket = null;
    public UserInfo userInfo;
    
    Service(Socket s){
    	this.socket = s;
    	userInfo = new UserInfo();
    }

	public void run() {		
		
		BufferedReader br = null;
		PrintStream ps = null;
		try {
			
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ps = new PrintStream(socket.getOutputStream());	
			
			//final PrintStream ps = new PrintStream(socket.getOutputStream());				
			//envoie shutdown au client avant de sortir samarch dans terminal mais pas dans eclips
			/*Runtime.getRuntime().addShutdownHook(new Thread(){
	            public void run(){
	                ps.println("2 Shutdown");
	            }
	        });*/
			
			ps.println("1 Bienvenue ! ");
			ps.println("1 Serveur FTP Personnel.");
			ps.println("1 Authentification : -> user <username> puis -> pass <password>");
			ps.println("0 Sign up : -> signup <username> <password>");
			
			String commande = "";
			
			
			while(!(commande=br.readLine()).equals("bye")){
				System.out.println((userInfo.username==null?"": userInfo.username)+">> "+commande);
				CommandExecutor.executeCommande(ps, commande, userInfo);
			}
			if(commande.equals("bye")) {
				(new CommandeLOGOUT(ps, "logout")).execute(userInfo);
				System.out.println((userInfo.username==null?"": userInfo.username)+">> Client quiter");
			}
			
		} catch (Exception e) {
			(new CommandeLOGOUT(ps, "logout")).execute(userInfo);
			System.out.println((userInfo.username==null?"": userInfo.username)+">> Client quiter");
			//e.printStackTrace();
		}finally{
			try {
				if(socket != null) {						
					socket.close();						
				}
				if(ps != null) {						
					ps.close();						
				}
				if(br != null) {						
					br.close();						
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}//fin main
}
