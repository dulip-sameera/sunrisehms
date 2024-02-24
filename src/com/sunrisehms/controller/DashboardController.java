package com.sunrisehms.controller;

import com.sunrisehms.dto.UserDto;
import com.sunrisehms.enums.ServiceType;
import com.sunrisehms.enums.Task;
import com.sunrisehms.exception.FialedToSaveTheLogException;
import com.sunrisehms.service.ServiceFactory;
import com.sunrisehms.service.custom.LoginService;
import com.sunrisehms.util.UserSession;
import java.io.IOException;
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
import javafx.stage.Stage;
import javax.security.auth.login.FailedLoginException;

public class DashboardController implements Initializable {
    
    LoginService loginService = (LoginService)ServiceFactory.getInstance().getService(ServiceType.LOGIN);

    @FXML
    private Button btnCustomerManagement;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnReservationManagement;

    @FXML
    private Button btnRoomCategoryManagement;

    @FXML
    private Button btnRoomManagement;

    @FXML
    private Button btnUserManagement;

    @FXML
    private Label lblUserDetails;

    @FXML
    void logout(ActionEvent event) {
        try {
            loginService.logout();
            UserSession.getInstance().removeSession();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sunrisehms/view/LoginView.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (FialedToSaveTheLogException fsl) {
            System.out.println(fsl.getMessage());
            lblUserDetails.setText("Log out failed! Try Agai!");
        }catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void viewCustomerManagement(ActionEvent event) {
        System.out.println("customer");
    }

    @FXML
    void viewReservationManagement(ActionEvent event) {
        System.out.println("reservation");
    }

    @FXML
    void viewRoomCategoryMangement(ActionEvent event) {
        System.out.println("room category");
    }

    @FXML
    void viewRoomMangement(ActionEvent event) {
        System.out.println("room");
    }

    @FXML
    void viewUserManagement(ActionEvent event) {
        System.out.println("user");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserDto currentUser = UserSession.getInstance().getUser();
        lblUserDetails.setText("User: " + currentUser.getUserName()+"("+currentUser.getJobTitle().toUpperCase()+")");
        
        btnUserManagement.setDisable(!havePermission(currentUser, Task.CREATE_USER, Task.READ_USER, Task.UPDATE_USER, Task.DELETE_USER));
        btnRoomCategoryManagement.setDisable(!havePermission(currentUser, Task.CREATE_ROOM_CATEGORY,Task.READ_ROOM_CATEGORY, Task.UPDATE_ROOM_CATEGORY, Task.DELETE_ROOM_CATEGORY));
        btnRoomManagement.setDisable(!havePermission(currentUser, Task.CREATE_ROOM, Task.READ_ROOM, Task.UPDATE_ROOM, Task.DELETE_ROOM));
        btnCustomerManagement.setDisable(!havePermission(currentUser, Task.CREATE_CUSTOMER, Task.READ_CUSTOMER, Task.UPDATE_CUSTOMER, Task.DELETE_CUSTOMER));
        btnReservationManagement.setDisable(!havePermission(currentUser, Task.CREATE_RESERVATION, Task.READ_RESERVATION, Task.UPDATE_RESERVATION, Task.DELETE_RESERVATION, Task.CANCEL_RESERVATION));
        
    }
    
    
    private boolean havePermission(UserDto user, Task... tasks) {
        for (Integer privilege : user.getPrivileges()) {
            for (Task task : tasks) {
                if (privilege.equals(task.getId())) return true;
            }
        }
        
        return false;
    }

}
