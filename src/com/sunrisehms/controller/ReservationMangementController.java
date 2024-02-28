package com.sunrisehms.controller;

import com.sunrisehms.dto.CustomerDto;
import com.sunrisehms.dto.ReservationDto;
import com.sunrisehms.enums.CustomerStatus;
import com.sunrisehms.enums.ReservationStatus;
import com.sunrisehms.enums.ServiceType;
import com.sunrisehms.exception.FailedToSaveTheLogException;
import com.sunrisehms.service.ServiceFactory;
import com.sunrisehms.service.custom.LoginService;
import com.sunrisehms.service.custom.ReservationService;
import com.sunrisehms.service.custom.UserService;
import com.sunrisehms.util.CustomerTableRow;
import com.sunrisehms.util.ReservationSingleton;
import com.sunrisehms.util.ReservationTableRow;
import com.sunrisehms.util.UserSession;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
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

public class ReservationMangementController implements Initializable {

    LoginService loginService = (LoginService) ServiceFactory.getInstance().getService(ServiceType.LOGIN);
    UserService userService = (UserService) ServiceFactory.getInstance().getService(ServiceType.USER);
    ReservationService reservationService = (ReservationService) ServiceFactory.getInstance().getService(ServiceType.RESERVATION);

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnNewReservation;

    @FXML
    private Button btnRefreshTable;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn colAction;

    @FXML
    private TableColumn<ReservationTableRow, String> colContact;

    @FXML
    private TableColumn<ReservationTableRow, String> colCustomerNIC;

    @FXML
    private TableColumn<ReservationTableRow, Date> colFrom;

    @FXML
    private TableColumn<ReservationTableRow, Long> colId;

    @FXML
    private TableColumn<ReservationTableRow, String> colStatus;

    @FXML
    private TableColumn<ReservationTableRow, Date> colTo;

    @FXML
    private Label lblSearchWarningMsg;

    @FXML
    private Label lblUserDetails;

    @FXML
    private TableView<ReservationTableRow> tblReservation;

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
    void openReservationForm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sunrisehms/view/ReservationCreateFormView.fxml"));
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
    void searchReservation(ActionEvent event) {
        try {
            String searchText = txtSerach.getText();
            if (searchText.equals("")) {
                lblSearchWarningMsg.setText("Enter Reservation ID");
                lblSearchWarningMsg.setVisible(true);
            } else {
                lblSearchWarningMsg.setVisible(false);
                ReservationDto reservationDto = reservationService.get(Long.valueOf(searchText));
                if (reservationDto == null) {
                    lblSearchWarningMsg.setText("Invalid Reservation ID!");
                    lblSearchWarningMsg.setVisible(true);
                } else {
                    lblSearchWarningMsg.setVisible(false);

                    tblReservation.getItems().clear();
                    tblReservation.getItems().add(
                            new ReservationTableRow(
                                    reservationDto.getId(),
                                    reservationDto.getCustomerDto().getNic(),
                                    reservationDto.getCustomerDto().getPhone(),
                                    reservationDto.getFrom(),
                                    reservationDto.getTo(),
                                    getReservationStatusName(reservationDto.getStatus())
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

        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colCustomerNIC.setCellValueFactory(new PropertyValueFactory<>("customerNic"));
        colFrom.setCellValueFactory(new PropertyValueFactory<>("from"));
        colTo.setCellValueFactory(new PropertyValueFactory<>("to"));
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadTable();

    }

    private void loadTable() {
        try {

            Callback<TableColumn<ReservationTableRow, String>, TableCell<ReservationTableRow, String>> cellFactory = (param) -> {

                final TableCell<ReservationTableRow, String> cell = new TableCell<>() {
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

                                    ReservationTableRow c = getTableView().getItems().get(getIndex());
                                    ReservationSingleton.getInstance().setReservation(c);

                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sunrisehms/view/ReservationDetailsView.fxml"));
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
            
            List<ReservationDto> reservationDtos = reservationService.getAll();
            ObservableList<ReservationTableRow> reservationTableRows = FXCollections.observableArrayList();

            for (ReservationDto reservationDto : reservationDtos) {

                reservationTableRows.add(
                        new ReservationTableRow(
                                reservationDto.getId(),
                                reservationDto.getCustomerDto().getNic(),
                                reservationDto.getCustomerDto().getPhone(),
                                reservationDto.getFrom(),
                                reservationDto.getTo(),
                                getReservationStatusName(reservationDto.getStatus())
                        )
                );

            }

            setDataToTable(reservationTableRows);

        } catch (Exception ex) {
            Logger.getLogger(ReservationMangementController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setDataToTable(ObservableList<ReservationTableRow> reservationTableRows) {
        tblReservation.setItems(reservationTableRows);
    }

    private String getReservationStatusName(Integer status) {
        ReservationStatus[] reservationStatuses = ReservationStatus.class.getEnumConstants();

        for (ReservationStatus reservationStatuse : reservationStatuses) {
            if (reservationStatuse.getId().equals(status)) {
                return reservationStatuse.getName();
            }
        }

        return null;
    }

}
