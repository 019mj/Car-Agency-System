package Proj2;

import java.util.Date;
import java.util.function.Predicate;

import DoubleLinkedList.DoubleLinkedList;
import DoubleLinkedList.Node;
import LinkedList.LinkedList;
import QueueList.Queue;
import Tests.MultipleFilterTableExample.Person;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CustomerScr extends BorderPane {
	Node carNode;

	private ObservableList<Car> obsList = FXCollections.observableArrayList();

	private ObservableList<Car> filteredData = FXCollections.observableArrayList();

	TableView<Car> carTable;
	LinkedList cars;

	public CustomerScr(Stage stage, Scene scene, DoubleLinkedList list , Queue queue) {

		TextField modelTF = new TextField();
		modelTF.setPromptText("Car Model");

		TextField yearTF = new TextField();
		yearTF.setPromptText("Car Year");

		TextField colorTF = new TextField();
		colorTF.setPromptText("Car Color");

		TextField priceTF = new TextField();
		priceTF.setPromptText("Car Price");

		HBox filters = new HBox(10, modelTF, yearTF, colorTF, priceTF);
		filters.setAlignment(Pos.CENTER);

		ComboBox<String> cb = new ComboBox<String>();
		cb.setEditable(true);

		cb.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\D.*"))
				cb.getEditor().setText(newValue.replaceAll("\\d", ""));
		});

		ObservableList<String> items = FXCollections.observableArrayList();

		for (int i = 0; i < list.size(); i++) {
			items.add(((CarBrand) list.get(i)).getBrand());
		}

		// Create a FilteredList wrapping the ObservableList.
		FilteredList<String> filteredItems = new FilteredList<String>(items, p -> true);

		cb.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
			final TextField editor = cb.getEditor();
			final String selected = cb.getSelectionModel().getSelectedItem();

			Platform.runLater(() -> {
				if (selected == null || !selected.equals(editor.getText())) {
					filteredItems.setPredicate(item -> {
						if (item.toUpperCase().startsWith(newValue.trim().toUpperCase())) {
							cb.show();
							return true;
						} else {
							return false;
						}
					});
				}
			});
		});

		cb.setItems(filteredItems);

		Button searchButton = new Button("Search");

		Label brandLabel = new Label();
		brandLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 23));

		HBox searchBox = new HBox(10, brandLabel, cb, searchButton);
		searchBox.setAlignment(Pos.CENTER);

		Button next = new Button("Next"), prev = new Button("Previous");

		VBox nextBox = new VBox(next);
		nextBox.setAlignment(Pos.CENTER);

		VBox prevBox = new VBox(prev);
		prevBox.setAlignment(Pos.CENTER);

		VBox vBox = new VBox(10);

		vBox.setAlignment(Pos.CENTER);

		HBox content = new HBox(25, prevBox, vBox, nextBox);
		content.setAlignment(Pos.CENTER);

		setMargin(content, new Insets(65));

		Label note = new Label("Note : You can place an order by clicking on a specific car");
		
		// A button that search for a location and load all its statistics when it get
		// pressed
		searchButton.setOnAction(e -> {
			carNode = list.findNode(cb.getEditor().getText().trim().toUpperCase());
			if (carNode != null) {
				CarBrand carBrand = (CarBrand) carNode.getElement();
				if (carNode.getPrev() == null)
					prevBox.setDisable(true);
				else
					prevBox.setDisable(false);

				if (carNode.getNext() == null)
					nextBox.setDisable(true);
				else
					nextBox.setDisable(false);

				cars = carBrand.getCars();
				obsList.clear();
				filteredData.clear();
				for (int i = 0; i < cars.size(); i++)
					obsList.add((Car) cars.get(i));

				filteredData.addAll(obsList);
				carTable = createTable();
				
				ObjectProperty<Predicate<Car>> modelFilter = new SimpleObjectProperty<>();
				ObjectProperty<Predicate<Car>> colorFilter = new SimpleObjectProperty<>();
				ObjectProperty<Predicate<Car>> yearFilter = new SimpleObjectProperty<>();
				ObjectProperty<Predicate<Car>> priceFilter = new SimpleObjectProperty<>();

				modelFilter.bind(Bindings.createObjectBinding(
						() -> car -> car.getModel().toLowerCase().trim().startsWith(modelTF.getText().toLowerCase()),
						modelTF.textProperty()));

				colorFilter.bind(Bindings.createObjectBinding(
						() -> car -> car.getColor().toLowerCase().trim().startsWith(colorTF.getText().toLowerCase()),
						colorTF.textProperty()));
				
				yearFilter.bind(Bindings.createObjectBinding(
						() -> car -> (car.getYear()+"").toLowerCase().trim().startsWith(yearTF.getText().toLowerCase()),
						yearTF.textProperty()));

				priceFilter.bind(Bindings.createObjectBinding(
						() -> car -> (car.getPrice()+"").toLowerCase().trim().startsWith(priceTF.getText().toLowerCase()),
						priceTF.textProperty()));

		        FilteredList<Car> filteredCars = new FilteredList<>(obsList);
		        carTable.setItems(filteredCars);

		        carTable.setOnMouseClicked(event -> {
		            if (event.getClickCount() == 2) {
		                Car car = carTable.getSelectionModel().getSelectedItem();
		                if (car != null) {
		                	OrderStage orderStage = new OrderStage(((CarBrand)carNode.getElement()).getBrand(), car,queue );
		                }
		            }

		        });
		        
		        
		        
		        filteredCars.predicateProperty().bind(Bindings.createObjectBinding(
		                () -> modelFilter.get().and(colorFilter.get()).and(yearFilter.get()).and(priceFilter.get()), 
		                modelFilter, colorFilter , yearFilter , priceFilter));

				
				if (vBox.getChildren().isEmpty()) {
					vBox.getChildren().addAll(filters, carTable , note);
				} else {
					vBox.getChildren().clear();
					vBox.getChildren().addAll(filters, carTable , note);

				}
				brandLabel.setText((((CarBrand) carNode.getElement()).getBrand().toUpperCase().trim()));
				cb.getEditor().setText(".");
				setCenter(content);

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Can Not Find Brand!");
				alert.showAndWait();
				brandLabel.setText("");
				cb.getEditor().setText("");
				setCenter(null);

			}

		});

		// A button that loads all the next location statistics when it get pressed
		next.setOnAction(e -> {

			if (carNode.getNext().getNext() == null) {
				nextBox.setDisable(true);
				prevBox.setDisable(false);
			}
			prevBox.setDisable(false);
			carNode = carNode.getNext();
			brandLabel.setText((((CarBrand) carNode.getElement()).getBrand().toUpperCase().trim()));

			try {
				cars = ((CarBrand) carNode.getElement()).getCars();
				obsList.clear();
				filteredData.clear();
				for (int i = 0; i < cars.size(); i++)
					obsList.add((Car) cars.get(i));

				filteredData.addAll(obsList);

			} catch (Exception e2) {

			}

		});

		// A button that loads all the previous location statistics when it get pressed
		prev.setOnAction(e -> {
			if (carNode.getPrev().getPrev() == null) {
				prevBox.setDisable(true);
				nextBox.setDisable(false);
			}
			nextBox.setDisable(false);
			carNode = carNode.getPrev();
			brandLabel.setText((((CarBrand) carNode.getElement()).getBrand().toUpperCase().trim()));

			try {
				cars = ((CarBrand) carNode.getElement()).getCars();
				obsList.clear();
				filteredData.clear();
				for (int i = 0; i < cars.size(); i++)
					obsList.add((Car) cars.get(i));

				filteredData.addAll(obsList);

			} catch (Exception e2) {
			}

		});
		Button back = new Button("Back");

		BorderPane bp = new BorderPane();
		bp.setCenter(searchBox);
		bp.setLeft(back);

		setTop(bp);
		back.setOnAction(e -> {
			stage.setScene(scene);
			stage.centerOnScreen();
		});
		back.setAlignment(Pos.TOP_LEFT);

		setMargin(bp, new Insets(15));

	}

	public TableView<Car> createTable() {
		TableView<Car> tableView = new TableView<>();
		tableView.setEditable(true);

		TableColumn<Car, String> carModel = new TableColumn<Car, String>("Car Model");
		TableColumn<Car, Integer> carYear = new TableColumn<Car, Integer>("Year");
		TableColumn<Car, String> carColor = new TableColumn<Car, String>("Color");
		TableColumn<Car, String> carPrice = new TableColumn<Car, String>("Price");

		double columnWidth = 200;
		carModel.setPrefWidth(columnWidth);
		carYear.setPrefWidth(columnWidth);
		carColor.setPrefWidth(columnWidth);
		carPrice.setPrefWidth(columnWidth);

		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		tableView.getColumns().addAll(carModel, carYear, carColor, carPrice);

		carModel.setCellValueFactory(new PropertyValueFactory<Car, String>("model"));
		carYear.setCellValueFactory(new PropertyValueFactory<Car, Integer>("year"));
		carColor.setCellValueFactory(new PropertyValueFactory<Car, String>("color"));
		carPrice.setCellValueFactory(new PropertyValueFactory<Car, String>("price"));

		return tableView;

	}

}
