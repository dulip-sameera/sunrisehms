package com.sunrisehms.controller;

import com.sunrisehms.dto.RoomCategoryDto;
import com.sunrisehms.dto.RoomDto;
import com.sunrisehms.enums.RoomStatus;
import com.sunrisehms.enums.ServiceType;
import com.sunrisehms.service.ServiceFactory;
import com.sunrisehms.service.custom.RoomCategoryService;
import com.sunrisehms.service.custom.RoomService;
import java.io.IOException;
import java.math.BigDecimal;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RoomCreateFormController implements Initializable {

    RoomCategoryService roomCategoryService = (RoomCategoryService) ServiceFactory.getInstance().getService(ServiceType.ROOM_CATEGORY);
    RoomService roomService = (RoomService) ServiceFactory.getInstance().getService(ServiceType.ROOM);

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCreate;

    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private TextField txtPrice;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TextField txtFloor;

    @FXML
    private TextField txtRoomNo;

    @FXML
    void createRoom(ActionEvent event) {
        try {

            RoomDto roomDto = new RoomDto(
                    null,
                    Integer.valueOf(txtRoomNo.getText()),
                    Integer.valueOf(txtFloor.getText()),
                    BigDecimal.valueOf(Double.valueOf(txtPrice.getText())),
                    getStatusId(),
                    getCategoryId()
            );

            long id = roomService.save(roomDto);
            System.out.println(id);
            goBack(event);

        } catch (Exception ex) {
            Logger.getLogger(RoomCategoryCreateFromController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        try {

            txtFloor.clear();
            txtRoomNo.clear();
            txtPrice.clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sunrisehms/view/RoomManagementView.fxml"));
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
        setStatusCombo();
        setCategoryCombo();
    }

    private void setStatusCombo() {
        ObservableList<String> statuses = FXCollections.observableArrayList();
        RoomStatus[] roomStatuses = RoomStatus.class.getEnumConstants();
        for (RoomStatus roomStatuse : roomStatuses) {
            statuses.add(roomStatuse.getName());
        }
        cmbStatus.setItems(statuses);
    }

    private void setCategoryCombo() {
        try {
            ObservableList<String> catagories = FXCollections.observableArrayList();
            List<RoomCategoryDto> roomCategoryDtos = roomCategoryService.getAll();
            for (RoomCategoryDto roomCategoryDto : roomCategoryDtos) {
                catagories.add(roomCategoryDto.getName());
            }
            cmbCategory.setItems(catagories);
        } catch (Exception ex) {
            Logger.getLogger(RoomCreateFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Integer getCategoryId() {
        Integer category = null;
        try {
            List<RoomCategoryDto> roomCategoryDtos = roomCategoryService.getAll();
            for (RoomCategoryDto roomCategoryDto : roomCategoryDtos) {
                if (roomCategoryDto.getName().equals(cmbCategory.getValue())) {
                    category = roomCategoryDto.getId();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(RoomCreateFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return category;
    }

    private Integer getStatusId() {
        RoomStatus[] roomStatuses = RoomStatus.class.getEnumConstants();
        for (RoomStatus roomStatuse : roomStatuses) {
            if (roomStatuse.getName().equals(cmbStatus.getValue())) {
                return roomStatuse.getId();
            }
        }
        return null;
    }

}
