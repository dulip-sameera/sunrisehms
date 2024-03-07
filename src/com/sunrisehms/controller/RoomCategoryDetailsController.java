package com.sunrisehms.controller;

import com.sunrisehms.dto.RoomCategoryDto;
import com.sunrisehms.enums.ServiceType;
import com.sunrisehms.service.ServiceFactory;
import com.sunrisehms.service.custom.RoomCategoryService;
import com.sunrisehms.util.RoomCategorySinngleton;
import com.sunrisehms.util.RoomCategoryTableRow;
import com.sunrisehms.util.ViewUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RoomCategoryDetailsController implements Initializable {

    RoomCategoryService roomCategoryService = (RoomCategoryService) ServiceFactory.getInstance().getService(ServiceType.ROOM_CATEGORY);

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private CheckBox chbAC;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNoOfBeds;

    @FXML
    private TextField txtPrice;

    private RoomCategoryTableRow data;

    @FXML
    void deleteCategory(ActionEvent event) {
        try {
            roomCategoryService.delete(data.getId());
            goBack(event);
        } catch (Exception ex) {
            Logger.getLogger(RoomCategoryDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        try {

            txtName.clear();
            txtNoOfBeds.clear();
            txtPrice.clear();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ViewUtil.loadView(getClass(), "RoomCategoryMangementView.fxml", stage);

        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    @FXML
    void updateCategory(ActionEvent event) {
        try {
            RoomCategoryDto roomCategoryDto = new RoomCategoryDto(
                    data.getId(),
                    txtName.getText(),
                    Integer.valueOf(txtNoOfBeds.getText()),
                    BigDecimal.valueOf(Double.parseDouble(txtPrice.getText())),
                    chbAC.isSelected()
            );

            roomCategoryService.update(roomCategoryDto);
            goBack(event);

        } catch (Exception ex) {
            Logger.getLogger(RoomCategoryCreateFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = RoomCategorySinngleton.getInstance().getRoomCategory();

        txtName.setText(data.getName());
        txtNoOfBeds.setText(data.getNoOfBeds().toString());
        txtPrice.setText(data.getPrice().toString());
        chbAC.setSelected(data.getAc().equals("AC"));

    }

}
