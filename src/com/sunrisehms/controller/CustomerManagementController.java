package com.sunrisehms.controller;

import com.sunrisehms.dto.CustomerDto;
import com.sunrisehms.enums.CustomerStatus;
import com.sunrisehms.enums.ServiceType;
import com.sunrisehms.exception.FailedToSaveTheLogException;
import com.sunrisehms.service.ServiceFactory;
import com.sunrisehms.service.custom.CustomerService;
import com.sunrisehms.service.custom.LoginService;
import com.sunrisehms.util.CustomerSingleton;
import com.sunrisehms.util.CustomerTableRow;
import com.sunrisehms.util.UserSession;
import com.sunrisehms.util.ViewUtil;
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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CustomerManagementController implements Initializable {

    CustomerService customerService = (CustomerService) ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);
    LoginService loginService = (LoginService) ServiceFactory.getInstance().getService(ServiceType.LOGIN);

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnNewCustomer;

    @FXML
    private Button btnRefreshTable;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn colAction;

    @FXML
    private TableColumn<CustomerTableRow, String> colEmail;

    @FXML
    private TableColumn<CustomerTableRow, String> colFullName;

    @FXML
    private TableColumn<CustomerTableRow, Long> colId;

    @FXML
    private TableColumn<CustomerTableRow, String> colNIC;

    @FXML
    private TableColumn<CustomerTableRow, String> colPhone;

    @FXML
    private TableColumn<CustomerTableRow, String> colStatus;

    @FXML
    private Label lblSearchWarningMsg;

    @FXML
    private Label lblUserDetails;

    @FXML
    private TableView<CustomerTableRow> tblCustomer;

    @FXML
    private TextField txtSerach;

    @FXML
    void goBack(ActionEvent event) {
        try {

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ViewUtil.loadView(getClass(), "DashboardView.fxml", stage);

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

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ViewUtil.loadView(getClass(), "LoginView.fxml", stage);

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
    void openCustomerCreateForm(ActionEvent event) {
        try {

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ViewUtil.loadView(getClass(), "CustomerCreateFormView.fxml", stage);

        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    @FXML
    void refreshTable(ActionEvent event) {
        txtSerach.clear();
        lblSearchWarningMsg.setVisible(false);
        loadTable();
    }

    @FXML
    void searchCustomer(ActionEvent event) {
        try {
            String searchText = txtSerach.getText();
            if (searchText.equals("")) {
                lblSearchWarningMsg.setText("Enter Customer NIC");
                lblSearchWarningMsg.setVisible(true);
            } else {
                lblSearchWarningMsg.setVisible(false);
                CustomerDto customerDto = customerService.getByNIC(txtSerach.getText());
                if (customerDto == null) {
                    lblSearchWarningMsg.setText("Invalid Customer NIC!");
                    lblSearchWarningMsg.setVisible(true);
                } else {
                    lblSearchWarningMsg.setVisible(false);

                    String fullName = customerDto.getFirstName() + " " + customerDto.getMiddleName() + " " + customerDto.getLastName();
                    String email = customerDto.getEmail() == null ? null : customerDto.getEmail();
                    String status = customerDto.getStatus().equals(CustomerStatus.ACTIVE.getId()) ? CustomerStatus.ACTIVE.getName() : CustomerStatus.DELETED.getName();
                    tblCustomer.getItems().clear();
                    tblCustomer.getItems().add(
                            new CustomerTableRow(
                                    customerDto.getId(),
                                    fullName,
                                    customerDto.getNic(),
                                    customerDto.getPhone(),
                                    email,
                                    status
                            )
                    );
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(RoomCategoryMangementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblSearchWarningMsg.setVisible(false);

        // setup table
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        Callback<TableColumn<CustomerTableRow, String>, TableCell<CustomerTableRow, String>> cellFactory = (param) -> {

            final TableCell<CustomerTableRow, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String text, boolean empty) {
                    super.updateItem(text, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnOpen = new Button("Open");

                        btnOpen.setOnAction((event) -> {

                            try {

                                CustomerTableRow c = getTableView().getItems().get(getIndex());
                                CustomerSingleton.getInstance().setCustomer(c);

                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                ViewUtil.loadView(getClass(), "CustomerDetailsView.fxml", stage);

                            } catch (IOException ex) {
                                Logger.getLogger(UserManagementController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });

                        setGraphic(btnOpen);
                        setText(null);

                    }
                }

            };

            return cell;
        };

        colAction.setCellFactory(cellFactory);

        loadTable();
    }

    private void loadTable() {
        try {
            List<CustomerDto> customerDtos = customerService.getAll();
            ObservableList<CustomerTableRow> customerTableRows = FXCollections.observableArrayList();

            for (CustomerDto customerDto : customerDtos) {

                String fullName = customerDto.getFirstName() + " " + customerDto.getMiddleName() + " " + customerDto.getLastName();
                String email = customerDto.getEmail() == null ? null : customerDto.getEmail();
                String status = customerDto.getStatus().equals(CustomerStatus.ACTIVE.getId()) ? CustomerStatus.ACTIVE.getName() : CustomerStatus.DELETED.getName();
                customerTableRows.add(
                        new CustomerTableRow(
                                customerDto.getId(),
                                fullName,
                                customerDto.getNic(),
                                customerDto.getPhone(),
                                email,
                                status
                        )
                );
            }

            setDataToTable(customerTableRows);
        } catch (Exception ex) {
            Logger.getLogger(RoomCategoryMangementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setDataToTable(ObservableList<CustomerTableRow> customerTableRows) {
        tblCustomer.setItems(customerTableRows);
    }

}
