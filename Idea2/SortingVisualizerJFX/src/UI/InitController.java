package UI;

import Calculations.RandomString;
import Calculations.SortingEntry;
import Calculations.StepController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for the initialization window which controls the data input and selection
 * @author Paul Häussler
 */

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
    @FXML public RadioButton Rb_Bubble;

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

    /**
     * Gets called on window start. Initializes the combobox as well as the table
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        ObservableList<Integer> list
                = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6);


        Table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<TableColumn<SortingEntry, ?>> columns = Table.getColumns();

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

        Cb_StrLength.setItems(list);
        Cb_StrLength.getSelectionModel().select(0);
    }

    /**
     * Gets triggered when the help button was clicked. Calls the method to display the help window
     */

    public void Bt_info_clicked() {
        Main.window.initializeHelp();
    }

    /**
     * Gets triggered when the Add button for manual entries was clicked. Performs several checks on the input and then
     * inserts it into the table.
     */

    public void Bt_add_clicked() {
        if(Tb_charInput.getText().trim().equals("")){
            Lb_error_empty_field.setVisible(true);
            return;
        } else if(Tb_charInput.getText().length() < 1 || Tb_charInput.getText().length() > 6){
            Lb_error_str_length.setVisible(true);
            return;
        } else if(!(Tb_position.getText().equals("")) &&
                (!(Tb_position.getText().matches("[0-9]+")) || Tb_position.getText().length() > 7)){
            Lb_error_wrong_pos.setVisible(true);
            return;
        } else if(Table.getItems().size()+1 > 200){
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
                addItemsToTable(new SortingEntry(0, Tb_charInput.getText()));
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
            addItemsToTable(new SortingEntry(Integer.parseInt(Tb_position.getText()), Tb_charInput.getText()));
        }

    }

    /**
     * Puts a given String value behind the biggest index in the table
     * @param input any String value
     */

    private void addToMaxPosPlusOne(String input){
        int max = 0;
        for(Object o:Table.getItems()){
            SortingEntry entry = (SortingEntry) o;
            if(max < entry.getIndex()){
                max = entry.getIndex();
            }
        }
        addItemsToTable(new SortingEntry(max+1, input));
    }

    /**
     * Adds a SortingEntry object to the table
     * @param entry the added object
     */

    public void addItemsToTable(SortingEntry entry){

        Table.getItems().add(entry);
    }

    /**
     * Gets triggered when the generate button was clicked. Performs checks on the input parameters and then
     * generates random String entries with the given parameters
     */

    public void Bt_generate_clicked() {
        if(!(Cb_digits.isSelected() || Cb_upper.isSelected() || Cb_lower.isSelected() || Cb_specChar.isSelected())){
            Lb_error_symbols.setVisible(true);
        } else if(Tb_entryCount.getText().equals("")){
            Lb_error_count.setVisible(true);
        } else if(Tb_entryCount.getText().length() > 7) {
            Lb_error_count_out_of_bounds.setVisible(true);
        } else if(!(Tb_entryCount.getText().matches("[0-9]+")) || Integer.valueOf(Tb_entryCount.getText()) < 1
                || Integer.valueOf(Tb_entryCount.getText()) > 200){
            Lb_error_count_out_of_bounds.setVisible(true);
        } else if(Integer.valueOf(Tb_entryCount.getText()) + Table.getItems().size() > 200){
            Lb_error_exceed.setVisible(true);
        } else {
            generateRandomEntries(Integer.valueOf(Tb_entryCount.getText()),
                    Integer.valueOf(Cb_StrLength.getSelectionModel().getSelectedItem().toString()));
        }
    }

    /**
     * Generates random strings of a given length and count and adds them to the table after the last element
     * @param entryCount number of strings to be generated
     * @param strLength length of each individual string to be generated
     */

    private void generateRandomEntries(int entryCount, int strLength){
        RandomString rnd = new RandomString(Cb_upper.isSelected(), Cb_lower.isSelected(),
                Cb_digits.isSelected(), Cb_specChar.isSelected());
        for(int i = 0; i < entryCount; i++){
            addToMaxPosPlusOne(rnd.newString(strLength));
        }
    }

    /**
     * Gets triggered when the start button gets clicked. Performs checks on viable input as well as a picked algorithm
     * an the calls the Visualization window.
     */

    public void Bt_start_clicked() {
        if(!(Rb_Bubble.isSelected() || Rb_LSD.isSelected())){
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
        } else if(Rb_Bubble.isSelected()) {
            Main.pickedAlgo = StepController.SortAlgos.BubbleSort;
        } else {
            return;
        }
        Main.runVisualization();
    }

    /**
     * Gets called when the remove button was clicked. Removes the elements currently selected in the table.
     */

    public void Bt_remove_clicked() {
        ObservableList list = Table.getSelectionModel().getSelectedItems();
        Object[] arr = list.toArray();
        for(Object o:arr){
            SortingEntry selectedItem = (SortingEntry) o;
            Table.getItems().remove(selectedItem);
        }
        Table.refresh();
    }

    /**
     * Radiobuttons, deselect the other if one was clicked.
     */

    public void Rb_LSD_clicked() {
        Lb_error_algo.setVisible(false);
        Rb_Bubble.setSelected(false);
    }

    public void Rb_Bubble_clicked() {
        Lb_error_algo.setVisible(false);
        Rb_LSD.setSelected(false);
    }

    /**
     * Methods to reset the error labels when the corresponding part gets edited.
     */

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
