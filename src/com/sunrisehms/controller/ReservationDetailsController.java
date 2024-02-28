package com.sunrisehms.controller;

import com.sunrisehms.dto.CustomerDto;
import com.sunrisehms.dto.ReservationDto;
import com.sunrisehms.dto.ReservationPackageDto;
import com.sunrisehms.dto.RoomCategoryDto;
import com.sunrisehms.dto.RoomDto;
import com.sunrisehms.enums.ReservationStatus;
import com.sunrisehms.enums.RoomStatus;
import com.sunrisehms.enums.ServiceType;
import com.sunrisehms.service.ServiceFactory;
import com.sunrisehms.service.custom.CustomerService;
import com.sunrisehms.service.custom.ReservationPackageService;
import com.sunrisehms.service.custom.ReservationService;
import com.sunrisehms.service.custom.RoomCategoryService;
import com.sunrisehms.service.custom.RoomService;
import com.sunrisehms.util.ReservationSingleton;
import com.sunrisehms.util.ReservationTableRow;
import com.sunrisehms.util.RoomTableRow;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ReservationDetailsController implements Initializable {

    CustomerService customerService = (CustomerService) ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);
    RoomCategoryService roomCategoryService = (RoomCategoryService) ServiceFactory.getInstance().getService(ServiceType.ROOM_CATEGORY);
    ReservationPackageService reservationPackageService = (ReservationPackageService) ServiceFactory.getInstance().getService(ServiceType.RESERVATION_PACKAGE);
    ReservationService reservationService = (ReservationService) ServiceFactory.getInstance().getService(ServiceType.RESERVATION);
    RoomService roomService = (RoomService) ServiceFactory.getInstance().getService(ServiceType.ROOM);

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCancelReservation;

    @FXML
    private Button btnDeleteReservation;

    @FXML
    private Button btnRemoveRoom;

    @FXML
    private Button btnReserve;

    @FXML
    private Button btnSearch;

    @FXML
    private CheckBox chbCheckIn;

    @FXML
    private CheckBox chbCheckOut;

    @FXML
    private Label lblCheckInTime;

    @FXML
    private Label lblCheckOutTime;

    @FXML
    private ComboBox<String> cmbPackage;

    @FXML
    private ComboBox<String> cmbRoomCategory;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TableColumn colAction;

    @FXML
    private TableColumn<RoomTableRow, Integer> colFloor;

    @FXML
    private TableColumn<RoomTableRow, Long> colId;

    @FXML
    private TableColumn<RoomTableRow, Integer> colRoomNo;

    @FXML
    private DatePicker datePickerFrom;

    @FXML
    private DatePicker datePickerTo;

    @FXML
    private Label lblCustomerContact;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblSearchWarningMsg;

    @FXML
    private ListView<Integer> listSelectedRooms;

    @FXML
    private TableView<RoomTableRow> tblRoom;

    @FXML
    private TextField txtCustomerNIC;

    @FXML
    private TextField txtPrice;

    private CustomerDto customerDto;

    private ObservableList<Integer> selectedRooms = FXCollections.observableArrayList();

    private ReservationTableRow data;

    private ReservationDto beforeUpdateReservationDto;

    @FXML
    void cancelReservation(ActionEvent event) {
        try {
            reservationService.cancelReservation(beforeUpdateReservationDto);
            goBack(event);
        } catch (Exception ex) {
            Logger.getLogger(ReservationDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void deleteReservation(ActionEvent event) {
        try {
            reservationService.delete(beforeUpdateReservationDto.getId());
            goBack(event);
        } catch (Exception ex) {
            System.out.println("RESERVATION DELETE ERROR");
            Logger.getLogger(ReservationDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void comboLoadTable(ActionEvent event) {
        try {
            RoomCategoryDto dto = roomCategoryService.getByName(cmbRoomCategory.getValue());
            List<RoomDto> roomDtos = roomService.getAllAvailableRoomsByCategory(dto);

            ObservableList<RoomTableRow> roomTableRows = FXCollections.observableArrayList();

            for (RoomDto roomDto : roomDtos) {
                String category = roomCategoryService.get(roomDto.getRoomCategory()).getName();
                String status = getRoomStatusName(roomDto.getRoomStatus());

                roomTableRows.add(
                        new RoomTableRow(
                                roomDto.getId(),
                                roomDto.getRoomNo(),
                                roomDto.getFloor(),
                                category,
                                roomDto.getPrice(),
                                status
                        )
                );
            }

            tblRoom.getItems().clear();
            setDataToTable(roomTableRows);

        } catch (Exception ex) {
            Logger.getLogger(ReservationCreateFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void updateReservation(ActionEvent event) {
        try {

            if (this.customerDto == null) {
                lblSearchWarningMsg.setText("Choose a customer before submiting the reservation");
                lblSearchWarningMsg.setVisible(true);
                return;
            }

            lblSearchWarningMsg.setVisible(false);

            List<Long> roomIds = new ArrayList<>();
            for (Integer selectedRoom : selectedRooms) {
                try {
                    RoomDto roomDto = roomService.getByRoomNo(selectedRoom);
                    roomIds.add(roomDto.getId());
                } catch (Exception ex) {
                    System.out.println("retriving room error");
                    Logger.getLogger(ReservationCreateFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            Integer packageId = reservationPackageService.getByName(cmbPackage.getValue()).getId();
            Integer status = getReservationStatusId(cmbStatus.getValue());

            ReservationDto reservationDto = new ReservationDto();
            reservationDto.setId(data.getId());
            reservationDto.setCustomerDto(this.customerDto);
            reservationDto.setRoomIds(roomIds);
            reservationDto.setPackageId(packageId);
            reservationDto.setStatus(status);
            reservationDto.setPrice(BigDecimal.valueOf(Double.parseDouble(txtPrice.getText())));
            reservationDto.setFrom(
                    Date.from(datePickerFrom.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())
            );

            reservationDto.setTo(
                    Date.from(datePickerTo.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())
            );

            reservationDto.setCheckIn(beforeUpdateReservationDto.getCheckIn());
            reservationDto.setCheckOut(beforeUpdateReservationDto.getCheckOut());

            // only when reservation status is SHOW
            if (cmbStatus.getValue().equals(ReservationStatus.SHOW.getName())) {

                reservationService.update(reservationDto);

                if (chbCheckOut.isSelected()) {
                    reservationService.checkOut(reservationDto);
                }
            }

            // only in confirmed and pending status will be updateded this way
            if (cmbStatus.getValue().equals(ReservationStatus.CONFIRMED.getName())
                    || cmbStatus.getValue().equals(ReservationStatus.PENDING.getName())) {

                reservationService.update(reservationDto);

                if (chbCheckIn.isSelected()) {
                    reservationService.checkIn(reservationDto);
                }

                if (chbCheckOut.isSelected()) {
                    reservationService.checkOut(reservationDto);
                }

            }

            goBack(event);

        } catch (Exception ex) {
            Logger.getLogger(ReservationCreateFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sunrisehms/view/ReservationMangementView.fxml"));
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
    void removeRoom(ActionEvent event) {
        Integer selectedItem = listSelectedRooms.getSelectionModel().getSelectedItem();
        listSelectedRooms.getItems().remove(selectedItem);
    }

    @FXML
    void searchCustomer(ActionEvent event) {
        try {
            lblSearchWarningMsg.setVisible(false);

            String searchText = txtCustomerNIC.getText();

            if (searchText.equals("")) {
                lblSearchWarningMsg.setText("Invalid Customer NIC");
                lblSearchWarningMsg.setVisible(true);
                return;
            }

            CustomerDto customerDto = customerService.getByNIC(searchText);

            if (customerDto == null) {
                lblSearchWarningMsg.setText("Invalid Customer NIC");
                lblSearchWarningMsg.setVisible(true);
                return;
            }

            String customerFullName = customerDto.getFullName();
            String customerContact = customerDto.getPhone();

            lblCustomerName.setText("Name : " + customerFullName);
            lblCustomerName.setVisible(true);

            lblCustomerContact.setText("Phone : " + customerContact);
            lblCustomerContact.setVisible(true);

            this.customerDto = customerDto;
//            System.out.println(this.customerDto);

        } catch (Exception ex) {
            Logger.getLogger(ReservationCreateFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            lblSearchWarningMsg.setVisible(false);
            lblCustomerName.setVisible(false);
            lblCustomerContact.setVisible(false);

            data = ReservationSingleton.getInstance().getReservation();

            this.customerDto = customerService.getByNIC(data.getCustomerNic());

            beforeUpdateReservationDto = reservationService.get(data.getId());
            
            boolean isCancellable = true;
            Long timDiffInMils = beforeUpdateReservationDto.getFrom().getTime() - new Date().getTime();
            Long timeDiffInHours = TimeUnit.MILLISECONDS.toHours(timDiffInMils);
            System.out.println(timeDiffInHours);
            if (timeDiffInHours < 24) {
                isCancellable = false;
            }
            btnCancelReservation.setDisable(!isCancellable);
            
            btnCancelReservation.setDisable(beforeUpdateReservationDto.getStatus().equals(ReservationStatus.DELETED.getId()));
            btnDeleteReservation.setDisable(beforeUpdateReservationDto.getStatus().equals(ReservationStatus.DELETED.getId()));
            btnReserve.setDisable(beforeUpdateReservationDto.getStatus().equals(ReservationStatus.DELETED.getId()));
            

            List<ReservationPackageDto> packageDtos = reservationPackageService.getAll();
            packageDtos.forEach((packageDto) -> {
                cmbPackage.getItems().add(packageDto.getName());
            });

            List<RoomCategoryDto> categoryDtos = roomCategoryService.getAll();
            categoryDtos.forEach((categoryDto) -> {
                cmbRoomCategory.getItems().add(categoryDto.getName());
            });

            ReservationStatus[] reservationStatuses = ReservationStatus.class.getEnumConstants();

            // if the current status SHOW, then dont set any other statuses
            if (!beforeUpdateReservationDto.getStatus().equals(ReservationStatus.SHOW.getId())) {

                for (ReservationStatus reservationStatuse : reservationStatuses) {
                    if (reservationStatuse.getId().equals(ReservationStatus.CANCELLED.getId())
                            || reservationStatuse.getId().equals(ReservationStatus.DELETED.getId())) {
                        continue;
                    }
                    cmbStatus.getItems().add(reservationStatuse.getName());
                }

            } else {
                cmbStatus.getItems().add(ReservationStatus.SHOW.getName());
            }

            // set the selected package in combo
            packageDtos.forEach((packageDto) -> {
                if (packageDto.getId().equals(beforeUpdateReservationDto.getPackageId())) {
                    cmbPackage.getSelectionModel().select(packageDto.getName());
                }
            });

            // set the reservation status in combo 
            for (ReservationStatus reservationStatuse : reservationStatuses) {
                if (reservationStatuse.getId().equals(beforeUpdateReservationDto.getStatus())) {
                    cmbStatus.getSelectionModel().select(reservationStatuse.getName());
                }
            }

            // set resrevation price
            txtPrice.setText(beforeUpdateReservationDto.getPrice().toString());

            // set reserved rooms
            beforeUpdateReservationDto.getRoomIds().forEach((roomId) -> {
                try {
                    selectedRooms.add(roomService.get(roomId).getRoomNo());
                } catch (Exception ex) {
                    Logger.getLogger(ReservationDetailsController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Room No Setting UP to ListView Failed");
                }
            });
            listSelectedRooms.setItems(selectedRooms);

            lblCustomerName.setText("Name : " + this.customerDto.getFullName());
            lblCustomerName.setVisible(true);

            lblCustomerContact.setText("Phone : " + this.customerDto.getPhone());
            lblCustomerContact.setVisible(true);

            txtCustomerNIC.setText(this.customerDto.getNic());

            datePickerFrom.setValue(beforeUpdateReservationDto.getFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            datePickerTo.setValue(beforeUpdateReservationDto.getTo().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

            

            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            
            // setup checkin
            // if there is a checkin date available, then show the lable, otherwise show checkin check box
            if (beforeUpdateReservationDto.getCheckIn() == null) {
                chbCheckIn.setVisible(true);
                lblCheckInTime.setVisible(false);
            } else {
                chbCheckIn.setVisible(false);
                lblCheckInTime.setText("CHECK IN @ " + dateFormatter.format(beforeUpdateReservationDto.getCheckIn()));
                lblCheckInTime.setVisible(true);
            }
            

            // setup checkout
            // if there is a checkout date available, then show the lable, otherwise show checkout check box
            if (beforeUpdateReservationDto.getCheckOut() == null) {
                chbCheckOut.setVisible(true);
                lblCheckOutTime.setVisible(false);
            } else {
                chbCheckOut.setVisible(false);
                lblCheckOutTime.setText("CHECK OUT @ " + dateFormatter.format(beforeUpdateReservationDto.getCheckOut()));
                lblCheckOutTime.setVisible(true);
            }

            // setup room table
            colFloor.setCellValueFactory(new PropertyValueFactory<>("floor"));
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));

            Callback<TableColumn<RoomTableRow, String>, TableCell<RoomTableRow, String>> cellFactory = (param) -> {

                final TableCell<RoomTableRow, String> cell = new TableCell<>() {

                    @Override
                    protected void updateItem(String text, boolean empty) {
                        super.updateItem(text, empty);

                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {

                            final Button btnOpen = new Button("ADD");

                            btnOpen.setOnAction((event) -> {

                                RoomTableRow r = getTableView().getItems().get(getIndex());
                                if (!selectedRooms.contains(r.getRoomNo())) {

                                    selectedRooms.add(r.getRoomNo());
                                    listSelectedRooms.setItems(selectedRooms);
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

        } catch (Exception ex) {
            Logger.getLogger(ReservationCreateFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadTable() {
        try {
            List<RoomDto> roomDtos = roomService.getAllAvailableRooms();
            ObservableList<RoomTableRow> roomTableRows = FXCollections.observableArrayList();

            for (RoomDto roomDto : roomDtos) {
                String category = roomCategoryService.get(roomDto.getRoomCategory()).getName();
                String status = getRoomStatusName(roomDto.getRoomStatus());

                roomTableRows.add(
                        new RoomTableRow(
                                roomDto.getId(),
                                roomDto.getRoomNo(),
                                roomDto.getFloor(),
                                category,
                                roomDto.getPrice(),
                                status
                        )
                );
            }

            setDataToTable(roomTableRows);
        } catch (Exception ex) {
            Logger.getLogger(RoomCategoryMangementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setDataToTable(ObservableList<RoomTableRow> roomTableRows) {
        tblRoom.setItems(roomTableRows);
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

    private Integer getReservationStatusId(String reservationStatusName) {
        ReservationStatus[] reservationStatuses = ReservationStatus.class.getEnumConstants();
        for (ReservationStatus reservationStatuse : reservationStatuses) {
            if (reservationStatuse.getName().equals(reservationStatusName)) {
                return reservationStatuse.getId();
            }
        }

        return null;
    }

}
