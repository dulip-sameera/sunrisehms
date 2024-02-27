package com.sunrisehms.controller;

import com.sunrisehms.dto.RoomDto;
import com.sunrisehms.enums.RoomStatus;
import com.sunrisehms.enums.ServiceType;
import com.sunrisehms.exception.FailedToSaveTheLogException;
import com.sunrisehms.service.ServiceFactory;
import com.sunrisehms.service.custom.LoginService;
import com.sunrisehms.service.custom.RoomCategoryService;
import com.sunrisehms.service.custom.RoomService;
import com.sunrisehms.util.RoomSingleton;
import com.sunrisehms.util.RoomTableRow;
import com.sunrisehms.util.UserSession;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class RoomManagementController implements Initializable {

    LoginService loginService = (LoginService) ServiceFactory.getInstance().getService(ServiceType.LOGIN);
    RoomService roomService = (RoomService) ServiceFactory.getInstance().getService(ServiceType.ROOM);
    RoomCategoryService roomCategoryService = (RoomCategoryService) ServiceFactory.getInstance().getService(ServiceType.ROOM_CATEGORY);

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnNewRoom;

    @FXML
    private Button btnRefreshTable;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn colAction;

    @FXML
    private TableColumn<RoomTableRow, String> colCategory;

    @FXML
    private TableColumn<RoomTableRow, Integer> colFloor;

    @FXML
    private TableColumn<RoomTableRow, Long> colId;

    @FXML
    private TableColumn<RoomTableRow, BigDecimal> colPrice;

    @FXML
    private TableColumn<RoomTableRow, Integer> colRoomNo;

    @FXML
    private TableColumn<RoomTableRow, String> colStatus;

    @FXML
    private Label lblSearchWarningMsg;

    @FXML
    private Label lblUserDetails;

    @FXML
    private TableView<RoomTableRow> tblRoom;

    @FXML
    private TextField txtSerach;

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
    void openRoomCreateForm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sunrisehms/view/RoomCreateFormView.fxml"));
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
    void refreshTable(ActionEvent event) {
        txtSerach.clear();
        lblSearchWarningMsg.setVisible(false);
        loadTable();
    }

    @FXML
    void searchUser(ActionEvent event) {
        try {
            String searchText = txtSerach.getText();
            if (searchText.equals("")) {
                lblSearchWarningMsg.setText("Enter Room NO!");
                lblSearchWarningMsg.setVisible(true);
            } else {
                lblSearchWarningMsg.setVisible(false);
                RoomDto roomDto = roomService.getByRoomNo(Integer.valueOf(searchText));
                if (roomDto == null) {
                    lblSearchWarningMsg.setText("Invalid Room No!");
                    lblSearchWarningMsg.setVisible(true);
                } else {
                    lblSearchWarningMsg.setVisible(false);

                    String category = roomCategoryService.get(roomDto.getRoomCategory()).getName();
                    String status = getRoomStatusName(roomDto.getRoomStatus());

                    tblRoom.getItems().clear();
                    tblRoom.getItems().add(
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
            }

        } catch (Exception ex) {
            Logger.getLogger(RoomCategoryMangementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        lblSearchWarningMsg.setVisible(false);

        // setup table
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colFloor.setCellValueFactory(new PropertyValueFactory<>("floor"));
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // add open button
        // create cell factory
        Callback<TableColumn<RoomTableRow, String>, TableCell<RoomTableRow, String>> cellFactory = (param) -> {

            // makeing the table cell containning the button
            final TableCell<RoomTableRow, String> cell = new TableCell<>() {

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
                                RoomTableRow r = getTableView().getItems().get(getIndex());
                                RoomSingleton.getInstance().setRoom(r);

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sunrisehms/view/RoomDetailsView.fxml"));
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
        colAction.setCellFactory(cellFactory);

        loadTable();
    }

    private void loadTable() {
        try {
            List<RoomDto> roomDtos = roomService.getAll();
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

}
