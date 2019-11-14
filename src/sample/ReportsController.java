package sample;

import com.sun.org.apache.xml.internal.security.Init;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {


    @FXML
    private TableView<ReportsModel> reportsTabel;

    @FXML
    private TableColumn<ReportsModel, String> reportsCol;
public DatabaseHelper handler=null;
   public static ObservableList<ReportsModel> reportssList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reportsCol.setCellValueFactory(new PropertyValueFactory<ReportsModel, String>("Report"));
        reportssList.clear();
        reportssList.addAll(handler.getInstance().getAllReports());
        reportsTabel.setItems(reportssList);
    }
}
