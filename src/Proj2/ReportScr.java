package Proj2;

import java.io.File;
import java.io.PrintWriter;
import QueueList.Queue;
import StackList.Stack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 * The ReportScr class represents a screen for generating and displaying a report.
 * It extends the BorderPane class and provides functionalities for showing the report content,
 */

public class ReportScr extends BorderPane {
    private FileChooser fileChooser;

    public ReportScr(Stage stage, Scene scene, Stack stack) {
        fileChooser = new FileChooser();

        // Create and format the title label
        Label title = new Label("Report Screen");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 27.5));

        setAlignment(title, Pos.CENTER);

        // Create and format the text area for displaying the report
        TextArea ta = new TextArea();
        ta.setStyle("-fx-alignment: center;");
        setAlignment(ta, Pos.CENTER);
        ta.setEditable(false);

        // Create a temporary stack to preserve the original order of items in the stack
        Stack tempStack = new Stack();
        
        // Add header to the text area
        ta.appendText("CustomerName, CustomerMobile, Brand, Model, Year, Color, Price, OrderDate, OrderStatus\n");
        
        // Retrieve the top 10 items from the stack and display them in the text area
        for (int i = 0; !stack.isEmpty() && i < 10; i++) {
            ta.appendText(stack.peek().toString() + "\n");
            tempStack.push(stack.pop());
        }
        
        // Restore the items back to the original stack
        while (!tempStack.isEmpty())
            stack.push(tempStack.pop());

        // Create the back button
        Button backButton = new Button("Back");

        // Set the action for the back button to switch to the previous scene
        backButton.setOnAction(e -> {
            stage.setScene(scene);
            stage.centerOnScreen();
        });

        // Create a new border pane for the layout of the title and back button
        BorderPane bp = new BorderPane();
        bp.setCenter(title);
        bp.setLeft(backButton);

        setTop(bp);

        setMargin(ta, new Insets(150));
        setCenter(ta);

        // Create the "File Report" button
        Button toFile = new Button("File Report");
        setAlignment(toFile, Pos.CENTER);
        setBottom(toFile);

        setPadding(new Insets(15));

        // Set the action for the "File Report" button to save the report to a file
        toFile.setOnAction(e -> {
            saveReportToFile("CarsReport.txt", stack);
        });
    }

    // Method to save the report to a file
    private void saveReportToFile(String defaultFileName, Stack stack) {
        // Set the initial file name in the file chooser
        File file = new File(defaultFileName);
        fileChooser.setInitialFileName(file.getName());

        // Show the save dialog
        File selectedFile = fileChooser.showSaveDialog(null);

        // Save the data to the selected file
        try {
            PrintWriter pw = new PrintWriter(selectedFile);
            pw.println("CustomerName, CustomerMobile, Brand, Model, Year, Color, Price, OrderDate, OrderStatus");

            // Create a temporary stack to preserve the original order of items in the stack
            Stack tempStack = new Stack();
            
            // Retrieve the top 10 items from the stack and write them to the file
            for (int i = 0; !stack.isEmpty() && i < 10; i++) {
                pw.println(stack.peek().toString());
                tempStack.push(stack.pop());
            }

            // Restore the items back to the original stack
            while (!tempStack.isEmpty())
                stack.push(tempStack.pop());

            pw.close();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No file has been chosen");
            alert.showAndWait();
        }
    }
}
