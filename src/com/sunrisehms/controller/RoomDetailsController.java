package com.sunrisehms.controller;

import com.sunrisehms.dto.RoomCategoryDto;
import com.sunrisehms.dto.RoomDto;
import com.sunrisehms.enums.RoomStatus;
import com.sunrisehms.enums.ServiceType;
import com.sunrisehms.service.ServiceFactory;
import com.sunrisehms.service.custom.RoomCategoryService;
import com.sunrisehms.service.custom.RoomService;
import com.sunrisehms.util.RoomSingleton;
import com.sunrisehms.util.RoomTableRow;
import com.sunrisehms.util.ViewUtil;
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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RoomDetailsController implements Initializable {

    RoomCategoryService roomCategoryService = (RoomCategoryService) ServiceFactory.getInstance().getService(ServiceType.ROOM_CATEGORY);
    RoomService roomService = (RoomService) ServiceFactory.getInstance().getService(ServiceType.ROOM);

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TextField txtFloor;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtRoomNo;

    private RoomTableRow data;

    @FXML
    void deleteRoom(ActionEvent event) {
        try {
            roomService.delete(data.getId());
            goBack(event);
        } catch (Exception ex) {
            Logger.getLogger(RoomCategoryDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        try {

            txtFloor.clear();
            txtRoomNo.clear();
            txtPrice.clear();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ViewUtil.loadView(getClass(), "RoomManagementView.fxml", stage);
            
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    @FXML
    void updateRoom(ActionEvent event) {
        try {
            RoomDto updatedRoom = new RoomDto();
            updatedRoom.setId(data.getId());
            updatedRoom.setRoomNo(Integer.valueOf(txtRoomNo.getText()));
            updatedRoom.setFloor(Integer.valueOf(txtFloor.getText()));
            updatedRoom.setRoomCategory(getCategoryId());
            updatedRoom.setRoomStatus(getStatusId());
            updatedRoom.setPrice(BigDecimal.valueOf(Double.parseDouble(txtPrice.getText())));

            roomService.update(updatedRoom);
            goBack(event);
        } catch (Exception ex) {
            Logger.getLogger(RoomDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCategoryCombo();
        setStatusCombo();
        data = RoomSingleton.getInstance().getRoom();
        txtFloor.setText(data.getFloor().toString());
        txtPrice.setText(data.getPrice().toString());
        txtRoomNo.setText(data.getRoomNo().toString());
        cmbStatus.getSelectionModel().select(data.getStatus());
        cmbCategory.getSelectionModel().select(data.getCategory());
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

    private String getRoomStatusName(Integer roomStatusId) {
        RoomStatus[] roomStatuses = RoomStatus.class.getEnumConstants();
        for (RoomStatus roomStatuse : roomStatuses) {
            if (roomStatuse.getId().equals(roomStatusId)) {
                return roomStatuse.getName();
            }
        }
        return null;
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
