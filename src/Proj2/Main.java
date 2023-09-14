package Proj2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import DoubleLinkedList.DoubleLinkedList;
import QueueList.Queue;
import StackList.Stack;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class Main extends Application {

	DoubleLinkedList list = new DoubleLinkedList();
	File file;
	Queue queue = new Queue();
	Stack stack = new Stack();

	public void start(Stage stage) throws Exception {
		BorderPane bp = new BorderPane();

		Button DevelopersBtn = new Button("Developers");
		bp.setTop(DevelopersBtn); // Set the button at the top of the border pane

		Label welcomeLabel = new Label("Comp242 Project II");
		welcomeLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 27.5));
		Label csaLabel = new Label("Car Agency System");
		csaLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));

		ImageView logoView = new ImageView("logo2.png");
		logoView.setFitHeight(289.5);
		logoView.setFitWidth(422.5);

		// Event handler for mouse entering the logo view
		logoView.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {

			Glow glow = new Glow();
			glow.setLevel(1.0);
			ColorAdjust colorAdjust = new ColorAdjust();
			colorAdjust.setBrightness(0.3);
			colorAdjust.setContrast(0.3);
			colorAdjust.setSaturation(0.3);
			colorAdjust.setHue(-0.05);
			glow.setInput(colorAdjust);
			logoView.setEffect(glow);
		});

		// Event handler for mouse exiting the logo view
		logoView.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			logoView.setEffect(null);
		});

		VBox vBox = new VBox(10, welcomeLabel, logoView, csaLabel);
		vBox.setAlignment(Pos.CENTER);
		bp.setCenter(vBox); // Set the vbox at the center of the border pane

		// Buttons for different actions
		Button loadCar = new Button("Load The Cars File");
		Button loadOrder = new Button("Load The Orders File");
		Button saveBtn = new Button("Save");
		Button brandBtn = new Button("Brands");
		Button customerBtn = new Button("Order A Car");
		Button manageBtn = new Button("Manage Orders");
		Button reportBtn = new Button("Report");

		HBox options1 = new HBox(7, loadCar, loadOrder, saveBtn);
		HBox options2 = new HBox(7, brandBtn, manageBtn, customerBtn, reportBtn);

		options1.setAlignment(Pos.CENTER);
		options2.setAlignment(Pos.CENTER);
		VBox optionsVbox = new VBox(7, options1, options2);

		optionsVbox.setAlignment(Pos.CENTER);
		bp.setBottom(optionsVbox); // Set the options vbox at the bottom of the border pane

		Scene scene = new Scene(bp, 1200, 700);

		// Event handler for manage button
		manageBtn.setOnAction(e -> {

			OrdersScr custOrderScreen = new OrdersScr(stage, scene, queue, stack, list);
			Scene custScr = new Scene(custOrderScreen, 1200, 700);
			custScr.getStylesheets().add("DarkMode.css");
			stage.setScene(custScr);
			stage.centerOnScreen();

		});

		// Event handler for report button
		reportBtn.setOnAction(e -> {

			ReportScr reportScr = new ReportScr(stage, scene, stack);
			Scene repScr = new Scene(reportScr, 1200, 700);
			repScr.getStylesheets().add("DarkMode.css");
			stage.setScene(repScr);
			stage.centerOnScreen();

		});

		// Event handler for save button
		saveBtn.setOnAction(e -> {
			SaveScreen saveScreen = new SaveScreen(stage, scene, stack, queue, list);
			Scene saveScene = new Scene(saveScreen, 1200, 700);
			saveScene.getStylesheets().add("DarkMode.css");
			stage.setScene(saveScene);
			stage.centerOnScreen();

		});

		bp.setPadding(new Insets(15, 15, 15, 15)); // Set padding for the border pane

		// Creating a file chooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("File Chooser");

		// Creating extension filters
		ExtensionFilter filterCSV = new ExtensionFilter("Text files", "*csv");
		ExtensionFilter filterTXT = new ExtensionFilter("Text files", "*txt");

		// Add the extension filter to the file chooser
		fileChooser.getExtensionFilters().addAll(filterTXT, filterCSV);

		// Event handler for load car button
		loadCar.setOnAction(e -> {
			file = fileChooser.showOpenDialog(stage);
			try {
				readCars();
			} catch (FileNotFoundException e2) {

			} catch (IOException e2) {

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Invalid File Format");
				alert.showAndWait();
			}

		});

		// Event handler for load order button
		loadOrder.setOnAction(e -> {
			file = fileChooser.showOpenDialog(stage);
			try {
				readOrders();
			} catch (FileNotFoundException e2) {
			} catch (IOException e2) {

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Invalid File Format");
				alert.showAndWait();
			}

		});

		Developers devTeam = new Developers(stage, scene);
		Scene devScene = new Scene(devTeam, 1200, 700);

		// Event handler for developers button
		DevelopersBtn.setOnAction(e -> {
			devScene.getStylesheets().add("DarkMode.css");
			stage.setScene(devScene);
			stage.centerOnScreen();

		});

		// Event handler for brand button
		brandBtn.setOnAction(e -> {
			BrandScr brandScr = new BrandScr(stage, scene, list);
			Scene brandScene = new Scene(brandScr, 1200, 700);
			brandScene.getStylesheets().add("DarkMode.css");
			stage.setScene(brandScene);
			stage.centerOnScreen();
		});

		// Event handler for customer button
		customerBtn.setOnAction(e -> {
			CustomerScr customerScr = new CustomerScr(stage, scene, list, queue);
			Scene customerScene = new Scene(customerScr, 1200, 700);
			customerScene.getStylesheets().add("DarkMode.css");
			stage.setScene(customerScene);
			stage.centerOnScreen();
		});
		scene.getStylesheets().add("DarkMode.css"); // Apply dark mode CSS to the scene
		stage.setScene(scene); // Set the scene to the stage
		stage.setTitle("Car Agancy"); // Set the title of the stage
		stage.show(); // Display the stage
	}

	public static void main(String[] args) {
		launch(args); // Launch the JavaFX application
	}
	
	// Read The cars from a specific file
	private void readCars() throws IOException {
		list.clear();

		String checker = "Brand, Model, Year, Color, Price";
		if (file == null)
			throw new FileNotFoundException();

		Scanner sc = new Scanner(file);

		if (!sc.nextLine().trim().equals(checker))
			throw new IOException();

		while (sc.hasNext()) {
			String s = sc.nextLine().trim();
			String[] tkz = s.split(",");

			String brand = tkz[0].trim().toUpperCase();
			String model = tkz[1].trim();
			int year = Integer.parseInt(tkz[2].trim());
			String color = tkz[3].trim();
			String price = tkz[4].trim();
			int price2 = Integer.parseInt(price.substring(0, price.length() - 1));

			if (price.charAt(price.length() - 1) == 'M')
				price2 *= 1000000;
			else if (price.charAt(price.length() - 1) == 'K')
				price2 *= 1000;

			Car car = new Car(model, year, color, price2);
			list.insertSort(brand, car);

		}

	}

	// Read The Orders from a specific file
	private void readOrders() throws IOException {
		queue.clear();
		stack.clear();
		String checker = "CustomerName, CustomerMobile, Brand, Model, Year, Color, Price, OrderDate, OrderStatus";
		if (file == null)
			throw new FileNotFoundException();

		Scanner sc = new Scanner(file);

		if (!sc.nextLine().trim().equals(checker))
			throw new IOException();

		while (sc.hasNext()) {
			String s = sc.nextLine().trim();
			String[] tkz = s.split(",");

			String name = tkz[0].trim();
			String mobile = tkz[1].trim();
			String brand = tkz[2].trim().toUpperCase();
			String model = tkz[3].trim();
			int year = Integer.parseInt(tkz[4].trim());
			String color = tkz[5].trim();
			String price = tkz[6].trim();

			int price2 = Integer.parseInt(price.substring(0, price.length() - 1));

			if (price.charAt(price.length() - 1) == 'M')
				price2 *= 1000000;
			else if (price.charAt(price.length() - 1) == 'K')
				price2 *= 1000;

			Car car = new Car(model, year, color, price2);

			String[] dateTkz = tkz[7].trim().split("/");
			int day = Integer.parseInt(dateTkz[0].trim());
			int month = Integer.parseInt(dateTkz[1].trim());
			int yearOrder = Integer.parseInt(dateTkz[2].trim());

			String status = tkz[8].trim();
			Order order = new Order(name, mobile, brand, car, day, month, yearOrder);

			if (status.equalsIgnoreCase("Finished"))
				stack.push(order);
			else if (status.equalsIgnoreCase("InProcess"))
				queue.enQueue(order);

		}

	}

}
