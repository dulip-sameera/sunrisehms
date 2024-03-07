package com.sunrisehms.controller;

import com.sunrisehms.dto.TaskDto;
import com.sunrisehms.dto.UserDto;
import com.sunrisehms.enums.ServiceType;
import com.sunrisehms.enums.UserStatus;
import com.sunrisehms.service.ServiceFactory;
import com.sunrisehms.service.custom.TaskService;
import com.sunrisehms.service.custom.UserService;
import com.sunrisehms.util.UserSingelton;
import com.sunrisehms.util.UserTableRow;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserDetailsController implements Initializable {

    UserService userService = (UserService) ServiceFactory.getInstance().getService(ServiceType.USER);
    TaskService taskService = (TaskService) ServiceFactory.getInstance().getService(ServiceType.TASK);

    @FXML
    private Button btnAddPrivilege;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRemovePrivilege;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbStatus;

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
    private UserTableRow data;

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
    void deleteUser(ActionEvent event) {
        try {
            userService.delete(data.getId());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ViewUtil.loadView(getClass(), "UserManagementView.fxml", stage);
        } catch (Exception ex) {
            Logger.getLogger(UserDetailsController.class.getName()).log(Level.SEVERE, null, ex);
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

    @FXML
    void udpateUser(ActionEvent event) {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String jobTitle = txtJobTitle.getText();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        UserDto updatedUser = new UserDto();
        updatedUser.setId(data.getId());
        updatedUser.setFirstName(firstName);
        updatedUser.setLastName(lastName);
        updatedUser.setJobTitle(jobTitle);
        updatedUser.setUserName(userName);
        updatedUser.setPassword(password);

        Integer userStatus = cmbStatus.getValue().equals(UserStatus.ACTIVE.getName())
                ? UserStatus.ACTIVE.getId()
                : UserStatus.DELETE.getId();

        updatedUser.setStatus(userStatus);
        updatedUser.setPrivileges(getPrivilegeIds(listPrivileges.getItems()));

        try {
            System.out.println(updatedUser);
            userService.update(updatedUser);
            goBack(event);
        } catch (Exception ex) {
            Logger.getLogger(UserDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            data = UserSingelton.getInstance().getUserData();

            cmbStatus.setItems(FXCollections.observableArrayList(UserStatus.ACTIVE.getName(), UserStatus.DELETE.getName()));
            cmbStatus.getSelectionModel().select(data.getStatus());

            UserDto user = userService.get(data.getId());
            setAllTaskNames(user.getPrivileges());
            txtFirstName.setText(user.getFirstName());
            txtLastName.setText(user.getLastName());
            txtUserName.setText(user.getUserName());
            txtPassword.setText(user.getPassword());
            txtJobTitle.setText(user.getJobTitle());
            listTask.getItems().addAll(taskList);
            listPrivileges.getItems().addAll(getCurrentPrivileNames(user.getPrivileges()));
        } catch (Exception ex) {
            Logger.getLogger(UserDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setAllTaskNames(List<Integer> currentPrivileges) {

        try {
            tasks = taskService.getAll();
            for (TaskDto task : tasks) {
                if (!currentPrivileges.contains(task.getId())) {
                    taskList.add(task.getName());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(UserCreationFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private List<String> getCurrentPrivileNames(List<Integer> currentPrivileges) {
        List<String> currentTaskNames = new ArrayList<>();
        for (TaskDto task : tasks) {
            if (currentPrivileges.contains(task.getId())) {
                currentTaskNames.add(task.getName());
            }
        }

        return currentTaskNames;
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
