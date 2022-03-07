package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class User {
	public static String host;
	public static String userName;
	public static String password;
	public static int port;
	public static Socket server = null;
	public static boolean connected = false;
	public static BufferedReader read;
	public static PrintStream write;
	public static String res = "";//server response
	public static String req = "";//request sended
	
	public static boolean connectServer(String host,String port) {
		try {
			int portI = Integer.valueOf(port);
			server = new Socket(host, portI);
			read = new BufferedReader(new InputStreamReader(server.getInputStream()));
			write = new PrintStream(server.getOutputStream());
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public static void sendCmd(String cmd) {
		req = cmd;
		write.println(req);
	}
	
	public static int readFromServer(boolean displayRes,TextArea response,Label errorsV) {
		int state;
		String tmpRes,tmpAllRes="";
		try {
			do{
				tmpRes = read.readLine();//System.out.println("+>> "+tmpRes);
		        state = Integer.valueOf(tmpRes.substring(0, tmpRes.indexOf(" ")));
		        tmpRes = ">> "+tmpRes.substring(2).trim()+"\n";
		        tmpAllRes += tmpRes.equals(">> \n") ? "" : tmpRes;
		        //tmpAllRes += tmpRes;
			} while (state == 1); 
			if(displayRes) {
				writeResponse(tmpAllRes,response);
			}
			
			if(state == 0){
				return 0;
			}else {
				errorsV.setVisible(true);
				errorsV.setText(tmpRes.substring(3).trim());
				return 1;
			}
		} catch (IOException e) {
			//e.printStackTrace();
			return 404;
		}
	}
	
	public static void writeResponse(String str,TextArea response) {
		if(!str.equals("--deleteAll--")) {
			res = res+str+"------------------------------------------------------\n";
			response.setText(res);
		}else {
			res = "";
			response.setText(res);
		}
	}
	
	public static void getCmd(String filename,TextArea response,Label errorsV) throws IOException{	
		  String serverRes = read.readLine();
		  System.out.println("serverRes >> "+serverRes);
	      if(serverRes.equals("download")) {
		      String s =  read.readLine();
		      int port = Integer.valueOf(s.split(" ")[2]);
		      Socket socket = new Socket(User.host, port);
		      
		      InputStream is = socket.getInputStream();
		      File fileFolder = new File("downloads");
		      fileFolder.mkdir();
		      FileOutputStream fos = new FileOutputStream("downloads\\" + new File(filename).getName());
		      
		      writeResponse(">> Downloading "+filename+" please wait ...\n",response);
		      byte[] buf = new byte[512];
		      while(is.read(buf) != -1 ) {
		    	  fos.write(buf);
		      }
		      fos.close();
		      is.close();
		      socket.close();	
		      s =  read.readLine();
		      writeResponse(">> "+filename+" Downloaded \n",response);
		      
		      	    
			}else if(serverRes.equals("NotFound")){
				errorsV.setVisible(true);
				errorsV.setText("File not found");
			} else {
				errorsV.setVisible(true);
				errorsV.setText("You can\'t download this file");
			}
	}//fin get
	public static void storCmd(String filePath,TextArea response,Label errorsV) throws IOException {
		String cc = read.readLine();
		if(cc.equals("upload")) {
		      String s =  read.readLine();
		      int port = Integer.valueOf(s.split(" ")[2]);
		      Socket socket = new Socket(User.host, port);
		      
		      OutputStream os = socket.getOutputStream();
		      FileInputStream fis = new FileInputStream(".\\FILES\\"+filePath);
		      writeResponse(">> Uploading "+filePath+" please wait ...\n",response);
		      
		      byte[] buf = new byte[512];
		      int nb = 101010;
		      while(( nb = fis.read(buf)) != -1 ) {
		    	  os.write(buf);
		    	  System.out.println("NB+> "+nb);
		      }
		      fis.close();
		      os.close();
		      socket.close();
		      
		      s =  read.readLine();
		      writeResponse(">> "+filePath+" Uploaded \n",response);
		}else {
			errorsV.setVisible(true);
			errorsV.setText("You can\'t Upload this file");
	    }
	}//fin stor
	
	
}
