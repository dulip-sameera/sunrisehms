package com.sunrisehms.controller;

import com.sunrisehms.dto.CustomerDto;
import com.sunrisehms.enums.CustomerStatus;
import com.sunrisehms.enums.ServiceType;
import com.sunrisehms.service.ServiceFactory;
import com.sunrisehms.service.custom.CustomerService;
import com.sunrisehms.util.CustomerSingleton;
import com.sunrisehms.util.CustomerTableRow;
import com.sunrisehms.util.ViewUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CustomerDetailsController implements Initializable {

    CustomerService customerService = (CustomerService) ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtMiddleName;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtPhone;

    private CustomerTableRow data;

    @FXML
    void deleteCustomer(ActionEvent event) {
        try {
            customerService.delete(data.getId());
            goBack(event);
        } catch (Exception ex) {
            Logger.getLogger(RoomCategoryDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        try {
            txtEmail.clear();
            txtFirstName.clear();
            txtLastName.clear();
            txtMiddleName.clear();
            txtNIC.clear();
            txtPhone.clear();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ViewUtil.loadView(getClass(), "CustomerManagementView.fxml", stage);

        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    @FXML
    void udpateCustomer(ActionEvent event) {

        try {
            Integer status = cmbStatus.getValue().equals(CustomerStatus.ACTIVE.getName()) ? CustomerStatus.ACTIVE.getId() : CustomerStatus.DELETED.getId();

            CustomerDto customerDto = new CustomerDto(
                    data.getId(),
                    txtFirstName.getText(),
                    txtMiddleName.getText(),
                    txtLastName.getText(),
                    txtNIC.getText(),
                    txtPhone.getText(),
                    txtEmail.getText(),
                    status
            );

            customerService.update(customerDto);
            goBack(event);
        } catch (Exception ex) {
            Logger.getLogger(CustomerDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            data = CustomerSingleton.getInstance().getCustomer();
            cmbStatus.setItems(FXCollections.observableArrayList(CustomerStatus.ACTIVE.getName(), CustomerStatus.DELETED.getName()));

            CustomerDto customerDto = customerService.get(data.getId());

            txtEmail.setText(customerDto.getEmail());
            txtPhone.setText(customerDto.getPhone());
            txtFirstName.setText(customerDto.getFirstName());
            txtMiddleName.setText(customerDto.getMiddleName());
            txtLastName.setText(customerDto.getLastName());
            txtNIC.setText(customerDto.getNic());
            cmbStatus.getSelectionModel().select(data.getStatus());

        } catch (Exception ex) {
            Logger.getLogger(CustomerDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
