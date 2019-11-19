package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TimeSlotController implements Initializable {
    @FXML
    private Button addButton;
public DatabaseHelper handler=null;
    @FXML
    private ChoiceBox<String> roomChoicebox;

    @FXML
    private ChoiceBox<String> timeslotChoicebox;
    @FXML
    private Button deleteButton;
    @FXML
    private ChoiceBox<String> dayChoiceBox;
    public static ObservableList<String> roomsList = FXCollections.observableArrayList();
    public static ObservableList<String> timeslotsList = FXCollections.observableArrayList();
    public static ObservableList<String> daysList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roomsList.clear();
        timeslotsList.clear();
        daysList.clear();
        try {
            roomsList.addAll(handler.getInstance().getAllRooms());
            roomChoicebox.setItems(roomsList);
            timeslotsList.addAll(handler.getInstance().getAllTimeSlots());
            timeslotChoicebox.setItems(timeslotsList);
            daysList.addAll(handler.getInstance().getAllDays());
            dayChoiceBox.setItems(daysList);
        } catch (Exception e) {

      alertMaker.showErrorMessage(e.getLocalizedMessage(),";");
        }

    }
public void AddButtonAction()
{ boolean ans = alertMaker.showConformationMessage("Add",
        "Are you sure you want to Add this Timeslot", "Press OK to continue");
    if (ans == false)
        return;
    String a= roomChoicebox.getValue();
    String b =timeslotChoicebox.getValue();
    String c= dayChoiceBox.getValue();
    if(a==null||b==null||c==null)
    {
        alertMaker.showErrorMessage("","No entries can be empty");
    }
    else
    {
        int i=Integer.valueOf(b.substring(0,1));
        System.out.println(i+"!!!");
        if(i<8)
            i=i+12;

        c=c.substring(0,3);
        try {
            handler.getInstance().addTimeslot(a,i,c);
        alertMaker.showInfoMessage("","Added timeslot succesfully","");
        timeslotChoicebox.setValue(null);
        roomChoicebox.setValue(null);
        dayChoiceBox.setValue(null);
        }
        catch (Exception e)
        {
            alertMaker.showErrorMessage("","Error while adding timeslot");
        }


    }
}
    public void deleteButtonAction()
    { boolean ans = alertMaker.showConformationMessage("Delete ",
            "Are you sure you want to delete the Timeslot", "Press OK to continue");
        if (ans == false)
            return;
        String a= roomChoicebox.getValue();
        String b =timeslotChoicebox.getValue();
        String c= dayChoiceBox.getValue();
        if(a==null||b==null||c==null)
        {
            alertMaker.showErrorMessage("","No entries can be empty");

        }
        else
        {
            int i=Integer.valueOf(b.substring(0,1));
            System.out.println(i+"!!!");
            if(i<8)
                i=i+12;

            c=c.substring(0,3);
            try {
                handler.getInstance().deleteTimeslot(a,i,c);
                alertMaker.showInfoMessage("","Deletion timeslot succesfully","");
                timeslotChoicebox.setValue(null);
                roomChoicebox.setValue(null);
                dayChoiceBox.setValue(null);
            }
            catch (Exception e)
            {
                alertMaker.showErrorMessage("","Error while adding timeslot");
            }


        }
    }

    @FXML
    private Button clearButton;
    @FXML
    void clear() {
        timeslotChoicebox.setValue(null);
        roomChoicebox.setValue(null);
        dayChoiceBox.setValue(null);
    }
}
