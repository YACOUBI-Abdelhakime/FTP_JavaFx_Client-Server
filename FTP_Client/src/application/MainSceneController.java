package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.scene.control.TextArea;

public class MainSceneController {
	@FXML
	private TextField hostV;
	@FXML
	private TextField portV;
	@FXML
	private TextField usernameV;
	@FXML
	private TextField passwordV;
	public Label errorsV;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnSignup;
	@FXML
	private Button btnLogout;
	@FXML
	private TextField cmdV;
	@FXML
	private Button btnSend;
	@FXML
	public TextArea response;

	// Event Listener on Button[#btnLogin].onAction
	@FXML
	public void btnLoginClicked(ActionEvent event) {
		User.connected  = User.connectServer(hostV.getText(),portV.getText());
		
		if(User.connected) {
			User.readFromServer(false,response,errorsV);
			User.sendCmd("user "+usernameV.getText());//System.out.println("user "+usernameV.getText());
			int x = User.readFromServer(false,response,errorsV);
			if(x==0) {
				User.sendCmd("pass "+passwordV.getText()); //System.out.println("pass "+passwordV.getText());
				int y = User.readFromServer(false,response,errorsV);
				if(y == 0) {
					//connected 
					//Disable
					btnLogin.setDisable(true);
					btnSignup.setDisable(true);
					hostV.setDisable(true);
					portV.setDisable(true);
					usernameV.setDisable(true);
					passwordV.setDisable(true);
					//Enable
					btnLogout.setDisable(false);
					btnSend.setDisable(false);
					cmdV.setDisable(false);
					User.writeResponse(">> you are connected\n",response);
					errorsV.setVisible(false);
				}else if(y == 404) {
					serverClosed();
					return;
				}else {
					errorsV.setVisible(true);
					errorsV.setText("Password not correct");
					try {
						User.server.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else if(x == 404) {
				serverClosed();
				return;
			}else {
				errorsV.setVisible(true);
				errorsV.setText("User does not exist");
				try {
					User.server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else {
			errorsV.setVisible(true);
			errorsV.setText("Host or port is not correct");
		}
	}
	
	public void errorsVClicked(ActionEvent event) {
		errorsV.setVisible(false);
	}
	
	// Event Listener on Button[#btnSignup].onAction
	@FXML
	public void btnSignupClicked(ActionEvent event) {
		User.connected  = User.connectServer(hostV.getText(),portV.getText());
		
		if(User.connected) {
			if(404 == User.readFromServer(false,response,errorsV)){
				serverClosed();
				return;
			}
			User.sendCmd("signup "+usernameV.getText()+" "+passwordV.getText());//System.out.println("user "+usernameV.getText());
			int x = User.readFromServer(false,response,errorsV);
			if(x == 0) {
				//connected 
				//Disable
				btnLogin.setDisable(true);
				btnSignup.setDisable(true);
				hostV.setDisable(true);
				portV.setDisable(true);
				usernameV.setDisable(true);
				passwordV.setDisable(true);
				//Enable
				btnLogout.setDisable(false);
				btnSend.setDisable(false);
				cmdV.setDisable(false);
				User.writeResponse(">> Account created\n>> you are connected\n",response);
				errorsV.setVisible(false);
				
			}else if(x == 404) {
				
			}else{
				errorsV.setVisible(true);
				errorsV.setText("Account not created try later !!");
				try {
					User.server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else {
			errorsV.setVisible(true);
			errorsV.setText("Host or port is not correct");
		}
	}
	// Event Listener on Button[#btnLogout].onAction
	@FXML
	public void btnLogoutClicked(ActionEvent event) {
		errorsV.setVisible(false);
		User.sendCmd("logout");
		if(User.readFromServer(false, response,errorsV) == 404) {
			serverClosed();
		}
		
		btnLogin.setDisable(false);
		btnSignup.setDisable(false);
		hostV.setDisable(false);
		portV.setDisable(false);
		usernameV.setDisable(false);
		passwordV.setDisable(false);
		//Enable
		btnLogout.setDisable(true);
		btnSend.setDisable(true);
		cmdV.setDisable(true);
		//response.setText("");
		User.writeResponse("--deleteAll--", response);
	}
	// Event Listener on Button[#btnSend].onAction
	@FXML
	public void btnSendClicked(ActionEvent event) {
		errorsV.setVisible(false);
		User.sendCmd(cmdV.getText());
		cmdV.setText("");
		if(User.readFromServer(true, response,errorsV) == 404) {
			serverClosed();
		}
	}
	public void serverClosed() {
		btnLogin.setDisable(false);
		btnSignup.setDisable(false);
		hostV.setDisable(false);
		portV.setDisable(false);
		usernameV.setDisable(false);
		passwordV.setDisable(false);
		//Enable
		btnLogout.setDisable(true);
		btnSend.setDisable(true);
		cmdV.setDisable(true);
		errorsV.setText("Server closed");
		errorsV.setVisible(true);
		User.writeResponse("--deleteAll--", response);
	}
	
}
