package com.sunrisehms.controller;

import com.sunrisehms.dto.TaskDto;
import com.sunrisehms.dto.UserDto;
import com.sunrisehms.enums.ServiceType;
import com.sunrisehms.enums.UserStatus;
import com.sunrisehms.service.ServiceFactory;
import com.sunrisehms.service.custom.TaskService;
import com.sunrisehms.service.custom.UserService;
import com.sunrisehms.util.ViewUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserCreationFormController implements Initializable{
    
    TaskService taskService = (TaskService)ServiceFactory.getInstance().getService(ServiceType.TASK);
    UserService userService = (UserService)ServiceFactory.getInstance().getService(ServiceType.USER);

    @FXML
    private Button btnAddPrivilege;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnRemovePrivilege;

    @FXML
    private ListView<String> listPrivileges;

    @FXML
    private ListView<String> listTask;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtJobTitle;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;
    
    private ObservableList<String> taskList = FXCollections.observableArrayList();
    private List<TaskDto> tasks;

    @FXML
    void addPrivilege(ActionEvent event) {
        String selectedItem = listTask.getSelectionModel().getSelectedItem();
        listTask.getItems().remove(selectedItem);
        listPrivileges.getItems().add(selectedItem);
    }
    
    @FXML
    void removePrivilege(ActionEvent event) {
        String selectedItem = listPrivileges.getSelectionModel().getSelectedItem();
        listPrivileges.getItems().remove(selectedItem);
        listTask.getItems().add(selectedItem);
    }

    @FXML
    void createUser(ActionEvent event) {
        
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String jobTitle = txtJobTitle.getText();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        
        UserDto newUser = new UserDto();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setJobTitle(jobTitle);
        newUser.setUserName(userName);
        newUser.setPassword(password);
        newUser.setStatus(UserStatus.ACTIVE.getId());
        newUser.setPrivileges(getPrivilegeIds(listPrivileges.getItems()));
        
        try {
            Long id = userService.save(newUser);
            System.out.println(id);
            goBack(event);
        } catch (Exception ex) {
            Logger.getLogger(UserCreationFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    void goBack(ActionEvent event) {
        try {
            
            txtFirstName.setText("");
            txtLastName.setText("");
            txtUserName.setText("");
            txtPassword.setText("");
            txtJobTitle.setText("");
            listPrivileges.getItems().clear();
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ViewUtil.loadView(getClass(), "UserManagementView.fxml", stage);

        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setAllTaskNames();
        listTask.getItems().addAll(taskList);
        listTask.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
    }
    
    private void setAllTaskNames() {
        
        try {
            tasks = taskService.getAll();
            for (TaskDto task : tasks) {
                taskList.add(task.getName());
            }
        } catch (Exception ex) {
            Logger.getLogger(UserCreationFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private List<Integer> getPrivilegeIds(List<String> privileges) {
        List<Integer> ids = new ArrayList<>();
        for (String privilege : privileges) {
            tasks.forEach(task -> {
                if (task.getName().equals(privilege)) {
                    ids.add(task.getId());
                }
            });
        }
        return ids;
    }
            

}
