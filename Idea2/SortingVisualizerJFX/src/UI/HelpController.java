package UI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the help window containing information about the used algorithms and the author
 * @author Paul Häussler
 */

public class HelpController implements Initializable {

    @FXML public TextArea Ta_RadixSort_LSD;
    @FXML public TextArea Ta_BubbleSort;
    @FXML public TextArea Ta_Author;

    /**
     * gets called on start of window. Calls the methods to read corresponding text files and inserts the into their
     * text areas.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Ta_RadixSort_LSD.setWrapText(true);
        Ta_RadixSort_LSD.appendText(readFile("RadixSort LSD.txt"));

        Ta_BubbleSort.setWrapText(true);
        Ta_BubbleSort.appendText(readFile("BubbleSort.txt"));

        Ta_Author.setWrapText(true);
        Ta_Author.appendText(readFile("Autor.txt"));

    }

    /**
     * Reads a file from a given internal link and returns the text content as a String
     * @param filename relative source path
     * @return content of the file as String
     */

    private String readFile(String filename){
        String str = "";
        StringBuffer buf = new StringBuffer();
        try {
            InputStream is = getClass().getResource("texts\\" + filename).openStream();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                if (is != null) {
                    while ((str = reader.readLine()) != null) {
                        buf.append(str + "\n");
                    }
                }
            } finally {
                try {
                    is.close();
                } catch (Throwable ignore) {
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return buf.toString();
    }
}
