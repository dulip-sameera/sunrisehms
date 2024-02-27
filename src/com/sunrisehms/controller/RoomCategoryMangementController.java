package com.sunrisehms.controller;

import com.sunrisehms.dto.RoomCategoryDto;
import com.sunrisehms.enums.ServiceType;
import com.sunrisehms.exception.FailedToSaveTheLogException;
import com.sunrisehms.service.ServiceFactory;
import com.sunrisehms.service.custom.LoginService;
import com.sunrisehms.service.custom.RoomCategoryService;
import com.sunrisehms.util.RoomCategorySinngleton;
import com.sunrisehms.util.RoomCategoryTableRow;
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

public class RoomCategoryMangementController implements Initializable {

    LoginService loginService = (LoginService) ServiceFactory.getInstance().getService(ServiceType.LOGIN);
    RoomCategoryService roomCategoryService = (RoomCategoryService) ServiceFactory.getInstance().getService(ServiceType.ROOM_CATEGORY);

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnNewUser;

    @FXML
    private Button btnRefreshTable;

    @FXML
    private Button btnSearch;

    @FXML
    private TableView<RoomCategoryTableRow> tblRoomCategories;

    @FXML
    private TableColumn colAction;

    @FXML
    private TableColumn<RoomCategoryTableRow, String> colName;

    @FXML
    private TableColumn<RoomCategoryTableRow, Integer> colId;

    @FXML
    private TableColumn<RoomCategoryTableRow, Integer> colNoofBeds;

    @FXML
    private TableColumn<RoomCategoryTableRow, BigDecimal> colPrice;

    @FXML
    private TableColumn<RoomCategoryTableRow, String> colAC;

    @FXML
    private Label lblSearchWarningMsg;

    @FXML
    private Label lblUserDetails;

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
    void openCategoryCreationForm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sunrisehms/view/RoomCategoryCreateFromView.fxml"));
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
    void searchCategory(ActionEvent event) {

        try {
            String searchText = txtSerach.getText();
            if (searchText.equals("")) {
                lblSearchWarningMsg.setText("Enter Search ID");
                lblSearchWarningMsg.setVisible(true);
            } else {
                lblSearchWarningMsg.setVisible(false);
                RoomCategoryDto roomCategoryDto = roomCategoryService.get(Integer.valueOf(searchText));
                if (roomCategoryDto == null) {
                    lblSearchWarningMsg.setText("Invalid Category ID!");
                    lblSearchWarningMsg.setVisible(true);
                } else {
                    lblSearchWarningMsg.setVisible(false);
                    String ac = roomCategoryDto.getAc() ? "AC" : "WITHOUT AC";
                    tblRoomCategories.getItems().clear();
                    tblRoomCategories.getItems().add(
                            new RoomCategoryTableRow(
                                    roomCategoryDto.getId(),
                                    roomCategoryDto.getName(),
                                    roomCategoryDto.getNoOfBeds(),
                                    roomCategoryDto.getPrice(),
                                    ac
                            )
                    );
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(RoomCategoryMangementController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setDataToTable(ObservableList<RoomCategoryTableRow> roomCategoryTableRows) {
        tblRoomCategories.setItems(roomCategoryTableRows);
    }

    private void loadTable() {
        try {
            List<RoomCategoryDto> roomCategoryDtos = roomCategoryService.getAll();
            ObservableList<RoomCategoryTableRow> roomCategoryTableRows = FXCollections.observableArrayList();

            for (RoomCategoryDto roomCategoryDto : roomCategoryDtos) {
                String ac = roomCategoryDto.getAc() ? "AC" : "WITHOUT AC";
                roomCategoryTableRows.add(
                        new RoomCategoryTableRow(
                                roomCategoryDto.getId(),
                                roomCategoryDto.getName(),
                                roomCategoryDto.getNoOfBeds(),
                                roomCategoryDto.getPrice(),
                                ac
                        )
                );
            }

            setDataToTable(roomCategoryTableRows);
        } catch (Exception ex) {
            Logger.getLogger(RoomCategoryMangementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblSearchWarningMsg.setVisible(false);

        // setup table
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNoofBeds.setCellValueFactory(new PropertyValueFactory<>("noOfBeds"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colAC.setCellValueFactory(new PropertyValueFactory<>("ac"));

        Callback<TableColumn<RoomCategoryTableRow, String>, TableCell<RoomCategoryTableRow, String>> cellFactory = (param) -> {

            final TableCell<RoomCategoryTableRow, String> cell = new TableCell<>() {
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
                                  
                                    RoomCategoryTableRow r = getTableView().getItems().get(getIndex());
                                    RoomCategorySinngleton.getInstance().setRoomCategory(r);

                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sunrisehms/view/RoomCategoryDetailsView.fxml"));
                                    Parent root = loader.load();

                                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    stage.setScene(new Scene(root));
                                    stage.show();

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

}
