package Proj2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import DoubleLinkedList.DoubleLinkedList;
import DoubleLinkedList.Node;
import QueueList.Queue;
import StackList.Stack;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 * The Save Screen class represents a screen to save the files again.
 * It extends the BorderPane class
 */

public class SaveScreen extends BorderPane {
	private FileChooser fileChooser;

	public SaveScreen(Stage stage, Scene scene, Stack stack, Queue queue, DoubleLinkedList list) {
		fileChooser = new FileChooser();

		Label carLabel = new Label("Save All Available Cars");
		carLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));

		// Create the buttons and set their actions
		Button saveCars = new Button("Save Cars");
		saveCars.setOnAction(e -> saveCarsToFile("cars.txt" , list));

		VBox carBox = new VBox(10, carLabel, saveCars);
		carBox.setAlignment(Pos.CENTER);

		Label ordersLabel = new Label("Save All Available Orders");
		ordersLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));

		Button saveOrders = new Button("Save Orders");
		saveOrders.setOnAction(e -> saveOrdersToFile("orders.txt", stack , queue));

		VBox orderBox = new VBox(10, ordersLabel, saveOrders);
		orderBox.setAlignment(Pos.CENTER);

		VBox options = new VBox(10, carBox, orderBox);
		options.setAlignment(Pos.CENTER);

		HBox hBox = new HBox(options);
		hBox.setAlignment(Pos.CENTER);
		setCenter(hBox);
		
		Button backBtn = new Button("Back");
		backBtn.setOnAction(e -> {
			stage.setScene(scene);
			stage.centerOnScreen();
		});
		setTop(backBtn);

		
		setPadding(new Insets(15));

	}

	private void saveCarsToFile(String defaultFileName, DoubleLinkedList list) {
		// Set the initial file name in the file chooser
		File file = new File(defaultFileName);
		fileChooser.setInitialFileName(file.getName());

		// Show the save dialog
		File selectedFile = fileChooser.showSaveDialog(null);

		// Save the data to the selected file

		list.printToFile(selectedFile);

	}
	
	private void saveOrdersToFile(String defaultFileName, Stack stack , Queue queue) {
		// Set the initial file name in the file chooser
		File file = new File(defaultFileName);
		fileChooser.setInitialFileName(file.getName());

		// Show the save dialog
		File selectedFile = fileChooser.showSaveDialog(null);

		// Save the data to the selected file

		try {
			PrintWriter pw = new PrintWriter(selectedFile);
			pw.println("CustomerName, CustomerMobile, Brand, Model, Year, Color, Price, OrderDate, OrderStatus");
			stack.printToFile(pw);
			queue.printToFile(pw);
			pw.close();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No file has been choosen");
			alert.showAndWait();
		}
		
	}


}
