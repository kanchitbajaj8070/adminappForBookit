package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button roomsButton;

    @FXML
    private Button timeslotsButton;

    @FXML
    private Button usersButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
DatabaseHelper handler= DatabaseHelper.getInstance();



    }
    @FXML
    public void openRoomsWindows() {
        try {
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Rooms.fxml"));
            primaryStage.setTitle("Rooms");
            primaryStage.initStyle(StageStyle.UTILITY);
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void openTimeSlotsWindows() throws Exception
    {
        Stage primaryStage= new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/TimeSlot.fxml"));
        primaryStage.setTitle("TimeSlots");
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setScene(new Scene(root));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.show();
    }
    public void openUsersWindows() throws Exception
    {
        Stage primaryStage= new Stage();
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Users.fxml"));
        primaryStage.setTitle("Users");
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setScene(new Scene(root));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.show();
    }
    public void openReportsWindows() throws Exception
    {
        Stage primaryStage= new Stage();
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Reports.fxml"));
        primaryStage.setTitle("Reports");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.show();
    }

}
