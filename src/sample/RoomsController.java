package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RoomsController implements Initializable {

    @FXML
    private TableView<RoomsModel> roomsTable;
    public static ObservableList<RoomsModel> roomsList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<RoomsModel, String> roomnameCol;

    @FXML
    private Button deleteButton;

    @FXML
    private Button addButton;

    @FXML
    private TextField roomnameTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        roomnameCol.setCellValueFactory(new PropertyValueFactory<RoomsModel, String>("roomname"));
        loadData();
    }

    DatabaseHelper helper;

    private void loadData() {
        roomsList.clear();
        try {

            ArrayList<String> list = helper.getInstance().getAllRooms();
            for (int i = 0; i < list.size(); i++) {
                roomsList.add(new RoomsModel(list.get(i)));

                System.out.println(list.get(i));
            }

        } catch (Exception e) {
            alertMaker.showErrorMessage("Error", "Unable to load rooms data");
        }
        roomsTable.setItems(roomsList);
    }

    public void addRoom() {

        String s = roomnameTextField.getText();
        if (s != null && !s.isEmpty()) {
            helper.getInstance().addRoom(s);
        } else
            alertMaker.showErrorMessage("", "Cant leave text field empty");
        roomsList.clear();
        roomnameTextField.clear();
        initialize(null, null);
    }

    public void deleteRoom() {
        boolean ans = alertMaker.showConformationMessage("Delete ",
                "Are you sure you want to delete the Room", "Press OK to continue");
        if (ans == false)
            return;
        RoomsModel rowToDelete = roomsTable.getSelectionModel().getSelectedItem();
        if (rowToDelete == null) {
            System.out.println("select entry");
            alertMaker.showErrorMessage("", "Select entry from table");
        } else {

            try {
                helper.getInstance().deleteRoom(rowToDelete.getRoomname());
                initialize(null, null);
            } catch (Exception e) {
                e.printStackTrace();
                alertMaker.showErrorMessage("Failed", "Failed to delete question");
            }

        }
    }

    public void clearSelection() {
        roomsTable.getSelectionModel().clearSelection();

    }


}

