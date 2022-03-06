package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
		        tmpAllRes += tmpRes;
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
			res = res+str;
			response.setText(res);
		}else {
			res = "";
			response.setText(res);
		}
	}
	
	
	
}
