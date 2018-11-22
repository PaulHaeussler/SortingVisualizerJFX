package UI;

import Calculations.RandomString;
import Calculations.SortingEntry;
import Calculations.StepController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML public Button Bt_remove;

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
    @FXML public Label Lb_error_exceed;

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
        if(Tb_charInput.getText().trim().equals("")){
            Lb_error_empty_field.setVisible(true);
            return;
        } else if(Tb_charInput.getText().length() < 1 || Tb_charInput.getText().length() > 6){
            Lb_error_str_length.setVisible(true);
            return;
        } else if(!(Tb_position.getText().matches("[0-9]+")) || Tb_position.getText().length() > 7){
            Lb_error_wrong_pos.setVisible(true);
            return;
        } else if(Integer.valueOf(Tb_charInput.getText()) + Table.getItems().size() > 200){
            Lb_error_exceed.setVisible(true);
            return;
        } else if(Tb_charInput.getText().contains("€")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Unzulässiges Sonderzeichen");
            alert.setHeaderText("Das Sonderzeichen € kann leider nicht verwendet werden.");
            alert.setContentText("Bitte wählen Sie ein anderes Zeichen und versuchen Sie es erneut.");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            return;
        }
        if(Tb_position.getText().trim().equals("")) {
            if(Table.getItems().isEmpty()) {
                addItemsToTable(Table, new SortingEntry(0, Tb_charInput.getText()));
                return;
            }
            addToMaxPosPlusOne(Tb_charInput.getText());
        } else {
            for(Object o:Table.getItems()){
                SortingEntry entry = (SortingEntry) o;
                if(entry.getIndex() == Integer.parseInt(Tb_position.getText())){
                    Lb_error_pos_occupied.setVisible(true);
                    return;
                }
            }
            addItemsToTable(Table, new SortingEntry(Integer.parseInt(Tb_position.getText()), Tb_charInput.getText()));
        }

    }

    private void addToMaxPosPlusOne(String input){
        int max = 0;
        for(Object o:Table.getItems()){
            SortingEntry entry = (SortingEntry) o;
            if(max < entry.getIndex()){
                max = entry.getIndex();
            }
        }
        addItemsToTable(Table, new SortingEntry(max+1, input));
    }


    private void addItemsToTable(TableView table, SortingEntry entry){
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<TableColumn<SortingEntry, ?>> columns = table.getColumns();

        for(TableColumn col:columns){
            String field = "";
            switch (col.getText()) {
                case "Position":
                    field = "Index";
                    break;
                case "Wert":
                    field = "Value";
                    break;
            }

            col.setCellValueFactory(new PropertyValueFactory<>(field));
        }
        table.getItems().add(entry + "GAY");
    }

    public void Bt_generate_clicked() {
        if(!(Cb_digits.isSelected() || Cb_upper.isSelected() || Cb_lower.isSelected() || Cb_specChar.isSelected())){
            Lb_error_symbols.setVisible(true);
        } else if(Tb_entryCount.getText().equals("")){
            Lb_error_count.setVisible(true);
        } else if(Tb_entryCount.getText().length() > 7) {
            Lb_error_count_out_of_bounds.setVisible(true);
        } else if(!(Tb_entryCount.getText().matches("[0-9]+")) || Integer.valueOf(Tb_entryCount.getText()) < 1 || Integer.valueOf(Tb_entryCount.getText()) > 200){
            Lb_error_count_out_of_bounds.setVisible(true);
        } else if(Integer.valueOf(Tb_entryCount.getText()) + Table.getItems().size() > 200){
            Lb_error_exceed.setVisible(true);
        } else {
            generateRandomEntries(Integer.valueOf(Tb_entryCount.getText()), Integer.valueOf(Cb_StrLength.getSelectionModel().getSelectedItem().toString()));
        }
    }

    private void generateRandomEntries(int entryCount, int strLength){
        RandomString rnd = new RandomString(Cb_upper.isSelected(), Cb_lower.isSelected(), Cb_digits.isSelected(), Cb_specChar.isSelected());
        for(int i = 0; i < entryCount * 10; i++){
            addToMaxPosPlusOne(rnd.newString(strLength));
        }
    }


    public void Bt_start_clicked() {
        if(!(Rb_MSD.isSelected() || Rb_LSD.isSelected())){
            Lb_error_algo.setVisible(true);
            return;
        } else if(Table.getItems().size() < 2){
            Lb_error_no_entries.setVisible(true);
            return;
        }
        ArrayList<SortingEntry> input = new ArrayList<>();
        for(Object o:Table.getItems()){
            SortingEntry entry = (SortingEntry) o;
            input.add(entry);
        }
        Main.input = input;
        if(Rb_LSD.isSelected()){
            Main.pickedAlgo = StepController.SortAlgos.RadixLSD;
        } else {
            Main.pickedAlgo = StepController.SortAlgos.RadixMSD;
        }
        Main.runVisualization();
    }


    public void Bt_remove_clicked() {
        ObservableList list = Table.getSelectionModel().getSelectedItems();
        Object[] arr = list.toArray();
        for(Object o:arr){
            SortingEntry selectedItem = (SortingEntry) o;
            Table.getItems().remove(selectedItem);
        }
        Table.refresh();
    }

    public void Rb_LSD_clicked() {
        Lb_error_algo.setVisible(false);
        Rb_MSD.setSelected(false);
    }

    public void Rb_MSD_clicked() {
        Lb_error_algo.setVisible(false);
        Rb_LSD.setSelected(false);
    }


    public void Tb_charInput_changed() {

        Lb_error_empty_field.setVisible(false);
        Lb_error_str_length.setVisible(false);
    }

    public void Tb_position_changed() {

        Lb_error_pos_occupied.setVisible(false);
        Lb_error_wrong_pos.setVisible(false);
    }

    public void Tb_entryCount_clicked(){
        Lb_error_exceed.setVisible(false);
        Lb_error_count.setVisible(false);
        Lb_error_count_out_of_bounds.setVisible(false);
    }

    public void Cb_upper_clicked(){
        Lb_error_symbols.setVisible(false);
    }
    public void Cb_lower_clicked(){
        Lb_error_symbols.setVisible(false);
    }
    public void Cb_digits_clicked(){
        Lb_error_symbols.setVisible(false);
    }
    public void Cb_specChar_clicked(){
        Lb_error_symbols.setVisible(false);
    }
}
