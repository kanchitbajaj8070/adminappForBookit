package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UsersController implements Initializable {
    @FXML
    private Button addUserButton;

    @FXML
    private JFXTextField nameText;

    @FXML
    private JFXTextField usernameText;

    @FXML
    private JFXTextField emailText;
    @FXML
    private TableView<usersModel> usersTable;

    @FXML
    private TableColumn<usersModel, String> nameCol;

    @FXML
    private TableColumn<usersModel, String> usernameCol;

    @FXML
    private TableColumn<usersModel, String> passwordCol;

    @FXML
    private TableColumn<usersModel, String> emailCol;

    @FXML
    private Button deleteButton;

    public DatabaseHelper handler=null;
    public static ObservableList<usersModel> usersList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usersList.clear();
        usernameCol.setCellValueFactory(new PropertyValueFactory<usersModel, String>("username"));
        nameCol.setCellValueFactory(new PropertyValueFactory<usersModel, String>("name"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<usersModel, String>("password"));
        emailCol.setCellValueFactory(new PropertyValueFactory<usersModel, String>("email"));
        initUsersList();
    }

    private void initUsersList() {

        ArrayList<usersModel> list= handler.getInstance().getAllUsers();
        usersList.addAll(list);
        usersTable.setItems(usersList);
    }

    public void addUserFunction()
    {
        if(nameText.getText()!=null&&nameText.getText().isEmpty()==false&&usernameText.getText()!=null&&usernameText.getText().isEmpty()==false&&emailText.getText().isEmpty()==false&&emailText.getText()!=null)
        {
            String a= nameText.getText().toString();
            String b= usernameText.getText().toString();
            String c=emailText.getText().toString();
            handler.getInstance().addUser(a,b,c);
            nameText.clear();
            emailText.clear();
            usernameText.clear();
            initialize(null,null);
        }
        else {
            alertMaker.showErrorMessage("","No entries can  be left empty");
        }

    }
    public void clearSelection() {
       usersTable.getSelectionModel().clearSelection();

    }
    public void deleteUser()
    {
        boolean ans = alertMaker.showConformationMessage("Delete ",
                "Are you sure you want to delete the User", "Press OK to continue");
        if (ans == false)
            return;

        usersModel rowToDelete = usersTable.getSelectionModel().getSelectedItem();
        if (rowToDelete == null)
            alertMaker.showErrorMessage("","select entry");
else{

            try {
                int res = handler.getInstance().deleteUser(rowToDelete);
                if (res == 1) {
                    Platform.runLater(() -> {
                        usersTable.getItems().removeAll(rowToDelete);
                    });
                    alertMaker.showInfoMessage("Info","Deleted question succesfully","");
                    initialize(null,null);
                }
                if (res == 0)
                    alertMaker.showErrorMessage("Failed","Failed to delete question");

            } catch (Exception e) {
                e.printStackTrace();
                alertMaker.showErrorMessage("Failed","Failed to delete question");
            }


        }
    }
    }

