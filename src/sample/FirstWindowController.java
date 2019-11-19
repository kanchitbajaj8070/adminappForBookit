package sample;

import com.sun.org.apache.xml.internal.security.Init;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class FirstWindowController implements Initializable {
    @FXML
    private Tab roomsTab;

    @FXML
    private Tab usersTab;

    @FXML
    private Tab timeslotsTab;

    @FXML
    private Tab reportsTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Rooms.fxml"));
            root.prefWidth(roomsTab.getTabPane().getTabMaxWidth());
            root.minHeight(roomsTab.getTabPane().getTabMaxHeight());
            roomsTab.setContent(root);
            root = FXMLLoader.load(getClass().getResource("/fxmls/Users.fxml"));
            usersTab.setContent(root);
            root = FXMLLoader.load(getClass().getResource("/fxmls/TimeSlot.fxml"));
            timeslotsTab.setContent(root);
             root = FXMLLoader.load(getClass().getResource("/fxmls/Reports.fxml"));
            reportsTab.setContent(root);

        } catch (Exception e) {
        }
    }
    @FXML
    public void openRoomsWindows() {
        try {
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Rooms.fxml"));
            roomsTab.setContent(root);
            /*primaryStage.setTitle("Rooms");
            primaryStage.initStyle(StageStyle.UTILITY);
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.show();*/
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

    public void fun(Event event) {
        try {


            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Reports.fxml"));
            reportsTab.setContent(root);
        } catch (Exception e) {
        }
    }
}
