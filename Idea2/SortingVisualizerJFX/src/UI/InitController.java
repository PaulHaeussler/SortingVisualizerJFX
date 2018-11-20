package UI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Controller;
import main.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InitController implements Initializable {

    @FXML public ScrollPane scrollPane;

    @FXML public ComboBox Cb_StrLength;

    @FXML public CheckBox Cb_upper;
    @FXML public CheckBox Cb_lower;
    @FXML public CheckBox Cb_digits;
    @FXML public CheckBox Cb_specChar;

    @FXML public Button Bt_info;
    @FXML public Button Bt_add;
    @FXML public Button Bt_generate;
    @FXML public Button Bt_start;

    @FXML public RadioButton Rb_LSD;
    @FXML public RadioButton Rb_MSD;

    @FXML public TextField Tb_charInput;
    @FXML public TextField Tb_position;
    @FXML public TextField Tb_entryCount;

    @FXML public TableView Table;
    @FXML public TableColumn Table_pos;
    @FXML public TableColumn Table_val;

    @FXML public Label Lb_error_algo;
    @FXML public Label Lb_error_symbols;
    @FXML public Label Lb_error_count;
    @FXML public Label Lb_error_count_out_of_bounds;
    @FXML public Label Lb_error_str_length;
    @FXML public Label Lb_error_empty_field;
    @FXML public Label Lb_error_no_entries;
    @FXML public Label Lb_error_pos_occupied;
    @FXML public Label Lb_error_wrong_pos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        ObservableList<Integer> list //
                = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6);



        Cb_StrLength.setItems(list);
        Cb_StrLength.getSelectionModel().select(0);
    }

    public void Bt_info_clicked() {
        Main.window.initializeHelp();
    }

    public void Bt_add_clicked() {
        if(Tb_charInput.getCharacters().toString().trim().equals("")){
            Lb_error_empty_field.setVisible(true);
            return;
        } else if(Tb_charInput.getCharacters().toString().length() < 1 || Tb_charInput.getCharacters().toString().length() > 6){
            Lb_error_str_length.setVisible(true);
            return;
        } else if(!(Tb_position.getCharacters().toString().matches("[0-9]+") || Tb_position.getCharacters().toString().equals(""))){
            Lb_error_wrong_pos.setVisible(true);
            return;
        }
        if(Tb_position.getCharacters().toString().trim().equals("")) {
            boolean found = false;
            int c = 0;
            while(!found){
                for(Object o:Table.getItems()){
                    if(!(o.toString().equals(c + ""))){
                        found = true;
                    }
                }
                c++;
            }
            Table.getItems().add(c-1, Tb_charInput.getCharacters().toString());
        } else {
            for(Object o:Table.getItems()){
                if(o.toString().equals(Tb_position.getCharacters().toString())){
                    Lb_error_pos_occupied.setVisible(true);
                    return;
                }
            }
            Table.getItems().add(Integer.parseInt(Tb_position.getCharacters().toString()), Tb_charInput.getCharacters().toString());
        }
    }

    public void Bt_generate_clicked() {

    }

    public void Bt_start_clicked() {

    }

    public void Rb_LSD_clicked() {

    }

    public void Rb_MSD_clicked() {

    }

    public void Tb_charInput_changed() {

    }
}
