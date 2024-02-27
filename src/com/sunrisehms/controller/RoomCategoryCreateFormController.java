package com.sunrisehms.controller;

import com.sunrisehms.dto.RoomCategoryDto;
import com.sunrisehms.enums.ServiceType;
import com.sunrisehms.service.ServiceFactory;
import com.sunrisehms.service.custom.RoomCategoryService;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomCategoryCreateFormController implements Initializable {

    RoomCategoryService roomCategoryService = (RoomCategoryService) ServiceFactory.getInstance().getService(ServiceType.ROOM_CATEGORY);

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCreate;

    @FXML
    private CheckBox chbAC;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNoOfBeds;

    @FXML
    private TextField txtPrice;

    @FXML
    void createCategory(ActionEvent event) {
        try {
            RoomCategoryDto roomCategoryDto = new RoomCategoryDto(
                    null,
                    txtName.getText(),
                    Integer.parseInt(txtNoOfBeds.getText()),
                    BigDecimal.valueOf(Double.parseDouble(txtPrice.getText())),
                    chbAC.isSelected()
            );

            Integer id = roomCategoryService.save(roomCategoryDto);
            System.out.println(id);
            goBack(event);

        } catch (Exception ex) {
            Logger.getLogger(RoomCategoryCreateFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        try {

            txtName.clear();
            txtNoOfBeds.clear();
            txtPrice.clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sunrisehms/view/RoomCategoryMangementView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
