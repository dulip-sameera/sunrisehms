package com.sunrisehms.controller;

import com.sunrisehms.dto.UserDto;
import com.sunrisehms.enums.ServiceType;
import com.sunrisehms.enums.UserStatus;
import com.sunrisehms.exception.FailedToSaveTheLogException;
import com.sunrisehms.repository.custom.UserRepository;
import com.sunrisehms.service.ServiceFactory;
import com.sunrisehms.service.custom.LoginService;
import com.sunrisehms.service.custom.UserService;
import com.sunrisehms.util.UserSession;
import com.sunrisehms.util.UserSingelton;
import com.sunrisehms.util.UserTableRow;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UserManagementController implements Initializable {

    LoginService loginService = (LoginService) ServiceFactory.getInstance().getService(ServiceType.LOGIN);
    UserService userService = (UserService) ServiceFactory.getInstance().getService(ServiceType.USER);

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnNewUser;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnRefreshTable;

    @FXML
    private TableView<UserTableRow> tblUser;

    @FXML
    private TableColumn<UserTableRow, String> colAction;

    @FXML
    private TableColumn<UserTableRow, String> colFirstName;

    @FXML
    private TableColumn<UserTableRow, Long> colId;

    @FXML
    private TableColumn<UserTableRow, String> colJobTitle;

    @FXML
    private TableColumn<UserTableRow, String> colLastName;

    @FXML
    private TableColumn<UserTableRow, String> colStatus;

    @FXML
    private TableColumn<UserTableRow, String> colUserName;

    @FXML
    private Label lblSearchWarningMsg;

    @FXML
    private Label lblUserDetails;

    @FXML
    private TextField txtSerach;

    private ObservableList<UserTableRow> userList = FXCollections.observableArrayList();

    @FXML
    void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sunrisehms/view/DashboardView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    @FXML
    void logout(ActionEvent event) {
        try {
            loginService.logout();
            UserSession.getInstance().removeSession();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sunrisehms/view/LoginView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (FailedToSaveTheLogException fsl) {
            System.out.println(fsl.getMessage());
            lblUserDetails.setText("Log out failed! Try Agai!");
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void openUserCreateForm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sunrisehms/view/UserCreationFormView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    private void loadDate(List<UserDto> users) {
        userList.clear();
        for (UserDto user : users) {

            String status = user.getStatus().equals(UserStatus.ACTIVE.getId())
                    ? UserStatus.ACTIVE.getName() : UserStatus.DELETE.getName();

            UserTableRow data = new UserTableRow(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    status,
                    user.getUserName(),
                    user.getJobTitle()
            );
            userList.add(data);
        }

        tblUser.setItems(userList);
    }

    private void loadData(UserDto user) {
        String status = user.getStatus().equals(UserStatus.ACTIVE.getId())
                ? UserStatus.ACTIVE.getName() : UserStatus.DELETE.getName();

        UserTableRow data = new UserTableRow(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                status,
                user.getUserName(),
                user.getJobTitle()
        );

        userList.clear();
        userList.add(data);
        tblUser.setItems(userList);
    }

    @FXML
    void searchUser(ActionEvent event) {
        try {
            String searchText = txtSerach.getText();
            if (searchText.equals("")) {
                lblSearchWarningMsg.setText("Enter search id");
                lblSearchWarningMsg.setVisible(true);
            } else {
                lblSearchWarningMsg.setVisible(false);
                Long searchId = Long.valueOf(searchText);
                UserDto user = userService.get(searchId);
                System.out.println(user);
                if (user == null) {
                    lblSearchWarningMsg.setText("Invalid User ID!");
                    lblSearchWarningMsg.setVisible(true);
                } else {
                    lblSearchWarningMsg.setVisible(false);
                    loadData(user);
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(UserManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void refreshTable(ActionEvent event) {
        try {
            lblSearchWarningMsg.setVisible(false);
            txtSerach.setText("");
            List<UserDto> users = userService.getAll();
            loadDate(users);
            System.out.println("Refresh");
        } catch (Exception ex) {
            Logger.getLogger(UserManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            lblSearchWarningMsg.setVisible(false);

            // setup table
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
            colJobTitle.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));

            // add open button
            // create cell factory
            Callback<TableColumn<UserTableRow, String>, TableCell<UserTableRow, String>> callFactory = (param) -> {

                // makeing the table cell containning the button
                final TableCell<UserTableRow, String> cell = new TableCell<>() {

                    @Override
                    protected void updateItem(String text, boolean empty) {
                        super.updateItem(text, empty);

                        // ensure that cell is created only on non-empty rows
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {

                            final Button btnOpen = new Button("Open");

                            btnOpen.setOnAction((event) -> {

                                try {
                                    // extract the clicked user table object
                                    UserTableRow u = getTableView().getItems().get(getIndex());
                                    UserSingelton.getInstance().setUserData(u);

                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sunrisehms/view/UserDetailsView.fxml"));
                                    Parent root = loader.load();

                                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    stage.setScene(new Scene(root));
                                    stage.show();

                                } catch (IOException ex) {
                                    Logger.getLogger(UserManagementController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            });

                            // set created button to cell
                            setGraphic(btnOpen);
                            setText(null);

                        }
                    }

                };

                // return the cell created
                return cell;
            };

            // set the custom factory to action column
            colAction.setCellFactory(callFactory);

            List<UserDto> users = userService.getAll();

            loadDate(users);

        } catch (Exception ex) {
            Logger.getLogger(UserManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
