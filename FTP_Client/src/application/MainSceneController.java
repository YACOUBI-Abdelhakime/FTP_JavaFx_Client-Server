package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.TextArea;

import javafx.scene.layout.BorderPane;

import javafx.scene.layout.VBox;


public class MainSceneController {
	@FXML
	private TextField hostV;
	@FXML
	private TextField portV;
	@FXML
	private TextField usernameV;
	@FXML
	private TextField passwordV;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnSignup;
	@FXML
	private Button btnLogout;
	@FXML
	private BorderPane errors;
	@FXML
	private Label errorsV;
	@FXML
	private TextArea response;
	@FXML
	private VBox containerCmds;
	@FXML
	private Button btnClear;
	@FXML
	private TextField cmdV;
	@FXML
	private Button btnCd;
	@FXML
	private Button btnMkdir;
	@FXML
	private Button btnRmdir;
	@FXML
	private Button btnGet;
	@FXML
	private Button btnStor;
	@FXML
	private Button btnLs;
	@FXML
	private Button btnPwd;

	// Event Listener on Button[#btnLogin].onAction
	@FXML
	public void btnLoginClicked(ActionEvent event) {
		
		if(hostV.getText().trim().equals("") || portV.getText().trim().equals("") || usernameV.getText().trim().equals("") || passwordV.getText().trim().equals("")) {
			errorsV.setVisible(true);
			errorsV.setText("Please fill all fields");
			return;
		}
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
					containerCmds.setDisable(false);
					btnLogout.setDisable(false);
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
	// Event Listener on Button[#btnSignup].onAction
	@FXML
	public void errorsVClicked(ActionEvent event) {
		errorsV.setVisible(false);
	}
	
	// Event Listener on Button[#btnSignup].onAction
	@FXML
	public void btnSignupClicked(ActionEvent event) {
		if(hostV.getText().trim().equals("") || portV.getText().trim().equals("") || usernameV.getText().trim().equals("") || passwordV.getText().trim().equals("")) {
			errorsV.setVisible(true);
			errorsV.setText("Please fill all fields");
			return;
		}
		
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
				containerCmds.setDisable(false);
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
		containerCmds.setDisable(true);
		try {
			User.server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		User.connected = false;
		//response.setText("");
		User.writeResponse("--deleteAll--", response);
	}
// Event Listener on Button[#btnClear].onAction
@FXML
public void btnClearClicked(ActionEvent event) {
	response.setText("");
	User.res = "";
	errorsV.setVisible(false);
}
// Event Listener on Button[#btnCd].onAction
@FXML
public void btnCdClicked(ActionEvent event) {
	if(cmdV.getText().trim().equals("")) {
		errorsV.setVisible(true);
		errorsV.setText("Please write the file name");
		return;
	}
	sendCmds("cd",true);
}
// Event Listener on Button[#btnMkdir].onAction
@FXML
public void btnMkdirClicked(ActionEvent event) {
	if(cmdV.getText().trim().equals("")) {
		errorsV.setVisible(true);
		errorsV.setText("Please write the file name");
		return;
	}
	sendCmds("mkdir",true);
}
// Event Listener on Button[#btnRmdir].onAction
@FXML
public void btnRmdirClicked(ActionEvent event) {
	if(cmdV.getText().trim().equals("")) {
		errorsV.setVisible(true);
		errorsV.setText("Please write the file name");
		return;
	}
	sendCmds("rmdir",true);
}
// Event Listener on Button[#btnGet].onAction
@FXML
public void btnGetClicked(ActionEvent event) {
	if(cmdV.getText().trim().equals("")) {
		errorsV.setVisible(true);
		errorsV.setText("Please write the file name");
		return;
	}
	User.sendCmd("get "+cmdV.getText().trim());
	try {
		User.getCmd(cmdV.getText().trim(), response, errorsV);
	} catch (IOException e) {
		errorsV.setVisible(true);
		errorsV.setText("Problem in the server try later.");
	}
	cmdV.setText("");
}
// Event Listener on Button[#btnStor].onAction
@FXML
public void btnStorClicked(ActionEvent event) {
	if(cmdV.getText().trim().equals("")) {
		errorsV.setVisible(true);
		errorsV.setText("Please write the file name");
		return;
	}
	File tempFile = new File(".\\FILES\\"+cmdV.getText().trim()); 
	if(tempFile.exists()) {
		User.sendCmd("stor "+cmdV.getText().trim());
		try {
			User.storCmd(cmdV.getText().trim(), response, errorsV);
		} catch (IOException e) {
			errorsV.setVisible(true);
			errorsV.setText("Problem in the server try later.");
		}
	}else {
		errorsV.setVisible(true);
		errorsV.setText("File not found");
	}
	cmdV.setText("");
}
// Event Listener on Button[#btnLs].onAction
@FXML
public void btnLsClicked(ActionEvent event) {
	sendCmds("ls",false);
}
// Event Listener on Button[#btnPwd].onAction
@FXML
public void btnPwdClicked(ActionEvent event) {
	sendCmds("pwd",false);
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
	containerCmds.setDisable(true);
	errorsV.setText("Server closed");
	errorsV.setVisible(true);
	User.writeResponse("--deleteAll--", response);
}
public void sendCmds(String cmdStr,boolean useCmdV) {
	errorsV.setVisible(false);
	if(useCmdV) {
		User.sendCmd(cmdStr+" "+cmdV.getText().trim());
		cmdV.setText("");
	}else {
		User.sendCmd(cmdStr);
	}
	
	if(User.readFromServer(true, response,errorsV) == 404) {
		serverClosed();
	}
}
}
