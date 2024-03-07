package com.sunrisehms.controller;

import com.sunrisehms.dto.CustomerDto;
import com.sunrisehms.enums.CustomerStatus;
import com.sunrisehms.enums.ServiceType;
import com.sunrisehms.service.ServiceFactory;
import com.sunrisehms.service.custom.CustomerService;
import com.sunrisehms.util.ViewUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CustomerCreateFormController implements Initializable {

    CustomerService customerService = (CustomerService) ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCreate;

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

    @FXML
    void createCustomer(ActionEvent event) {

        try {
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String middleName = txtMiddleName.getText().equals("") ? null : txtMiddleName.getText();
            String nic = txtNIC.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText().equals("") ? null : txtEmail.getText();

            CustomerDto customerDto = new CustomerDto();
            customerDto.setFirstName(firstName);
            customerDto.setMiddleName(middleName);
            customerDto.setLastName(lastName);
            customerDto.setNic(nic);
            customerDto.setPhone(phone);
            customerDto.setEmail(email);
            customerDto.setStatus(CustomerStatus.ACTIVE.getId());

            Long id = customerService.save(customerDto);
            System.out.println(id);
            goBack(event);
        } catch (Exception ex) {
            Logger.getLogger(CustomerCreateFormController.class.getName()).log(Level.SEVERE, null, ex);
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
