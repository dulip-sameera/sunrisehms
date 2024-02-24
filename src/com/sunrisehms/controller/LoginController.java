
package com.sunrisehms.controller;

import com.sunrisehms.dto.UserDto;
import com.sunrisehms.dto.UserLoginDto;
import com.sunrisehms.enums.ServiceType;
import com.sunrisehms.exception.FialedToSaveTheLogException;
import com.sunrisehms.exception.WrongPassword;
import com.sunrisehms.service.ServiceFactory;
import com.sunrisehms.service.custom.LoginService;
import com.sunrisehms.util.UserSession;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable{
    
    LoginService loginService = (LoginService)ServiceFactory.getInstance().getService(ServiceType.LOGIN);

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;
    
    @FXML
    private Label lblMessage;
    
     @FXML
    public void login(ActionEvent event) {
        String name = txtUserName.getText();
        String password = txtPassword.getText();
        UserLoginDto dto = new UserLoginDto(name, password);
        try {
            UserDto user = loginService.login(dto);
            if (user == null) {
                lblMessage.setText("Wrong user name!");
                lblMessage.setVisible(true);
                return;
            }
            lblMessage.setVisible(false);
            txtPassword.setText("");
            txtUserName.setText("");
            UserSession.getInstance().setUser(user);
            
            // open dashboard view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sunrisehms/view/DashboardView.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            
        } catch (WrongPassword wp) {
            lblMessage.setText("Incorrect password!");
            lblMessage.setVisible(true);
        } catch (FialedToSaveTheLogException fsle) {
            lblMessage.setText("Failed to Log, Try Again!");
            lblMessage.setVisible(true);
        }catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblMessage.setVisible(false);
    }

}

