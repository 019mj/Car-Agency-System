package Proj2;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

import DoubleLinkedList.DoubleLinkedList;
import LinkedList.LinkedList;
import Tests.TableViewExample.Person;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

/*
 * The CarsScr class represents the screen that displays and manages cars information.
 * It extends the BorderPane class to provide a layout container for its components.
 */

public class CarsScr extends BorderPane {

	private ObservableList<Car> obsList = FXCollections.observableArrayList();

	private ObservableList<Car> filteredData = FXCollections.observableArrayList();

	TableView<Car> carTable;
	LinkedList cars;

	public CarsScr(Stage stage, Scene scene, LinkedList cars, DoubleLinkedList list) {
		this.cars = cars;
		Button back = new Button("Back");

		back.setOnAction(e -> {
			BrandScr brandScr = new BrandScr(stage, scene, list);
			Scene brandScene = new Scene(brandScr, 1200, 700);
			brandScene.getStylesheets().add("DarkMode.css");
			stage.setScene(brandScene);
			stage.centerOnScreen();
		});
		setTop(back);
		setMargin(back, new Insets(0, 0, 20, 0));
		setPadding(new Insets(20));

		for (int i = 0; i < cars.size(); i++) {
			System.out.println(((Car) cars.get(i)).toString());
			obsList.add((Car) cars.get(i));
		}

		filteredData.addAll(obsList);
		carTable = createTable();

		Label search = new Label("Search");
		search.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

		TextField modelFil = new TextField();
		modelFil.setPromptText("Car Model");

		TextField yearFil = new TextField();
		yearFil.setPromptText("Car Year");

		TextField colorFil = new TextField();
		colorFil.setPromptText("Car Color");

		TextField priceFil = new TextField();
		priceFil.setPromptText("Car Price");

		HBox filters = new HBox(10, search, modelFil, yearFil, colorFil, priceFil);
		filters.setAlignment(Pos.CENTER);

		ObjectProperty<Predicate<Car>> modelFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Car>> colorFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Car>> yearFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Car>> priceFilter = new SimpleObjectProperty<>();

		modelFilter.bind(Bindings.createObjectBinding(
				() -> car -> car.getModel().toLowerCase().trim().startsWith(modelFil.getText().toLowerCase()),
				modelFil.textProperty()));

		colorFilter.bind(Bindings.createObjectBinding(
				() -> car -> car.getColor().toLowerCase().trim().startsWith(colorFil.getText().toLowerCase()),
				colorFil.textProperty()));

		yearFilter.bind(Bindings.createObjectBinding(
				() -> car -> (car.getYear() + "").toLowerCase().trim().startsWith(yearFil.getText().toLowerCase()),
				yearFil.textProperty()));

		priceFilter.bind(Bindings.createObjectBinding(
				() -> car -> (car.getPrice() + "").toLowerCase().trim().startsWith(priceFil.getText().toLowerCase()),
				priceFil.textProperty()));

		FilteredList<Car> filteredCars = new FilteredList<>(obsList);
		carTable.setItems(filteredCars);

		filteredCars.predicateProperty()
				.bind(Bindings.createObjectBinding(
						() -> modelFilter.get().and(colorFilter.get()).and(yearFilter.get()).and(priceFilter.get()),
						modelFilter, colorFilter, yearFilter, priceFilter));

		VBox centerBox = new VBox(10, filters, carTable);
		setCenter(centerBox);

		setMargin(centerBox, new Insets(0, 100, 0, 100));

		TextField modelTF = new TextField();
		modelTF.setPromptText("Car Model");

		modelTF.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("[a-zA-Z0-9 ]*")) {
				modelTF.setText(newValue.replaceAll("[^a-zA-Z0-9 ]", ""));
			}
			if (newValue.matches(".*\\s{2,}.*")) {
				modelTF.setText(newValue.replaceAll("\\s{2,}", " "));
			}
		});

		ComboBox yearComboBox = new ComboBox();
		yearComboBox.getItems().addAll(getYearRange(1970, 2023));

		TextField colorTF = new TextField();
		colorTF.setPromptText("Color");

		colorTF.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("[a-zA-Z ]*")) {
				colorTF.setText(newValue.replaceAll("[^a-zA-Z ]", ""));
			}
			if (newValue.matches(".*\\s{2,}.*")) {
				colorTF.setText(newValue.replaceAll("\\s{2,}", " "));
			}
		});

		TextField priceTF = new TextField();
		priceTF.setPromptText("Price");

		priceTF.setTextFormatter(new TextFormatter<>(change -> {
			String newText = change.getControlNewText();
			if (newText.matches("\\d*") && !newText.matches("[0]")) {
				return change;
			} else if (newText.isEmpty()) {
				return change;
			} else {
				return null;
			}
		}));
		Button addButton = new Button("Add");

		HBox data = new HBox(10, modelTF, yearComboBox, colorTF, priceTF , addButton);
		data.setAlignment(Pos.CENTER);

		Button deleteButton = new Button("Delete");
		Button clear = new Button("Delete All");

		HBox options = new HBox(10, deleteButton, clear);
		options.setAlignment(Pos.CENTER);

		VBox vBox = new VBox(10, data, options);
		vBox.setAlignment(Pos.CENTER);
		setBottom(vBox);

		addButton.setOnAction(e -> {
			try {
				Car car = new Car(modelTF.getText().trim().toUpperCase(),
						Integer.parseInt(yearComboBox.getSelectionModel().getSelectedItem().toString().trim()),
						colorTF.getText().trim(), Integer.parseInt(priceTF.getText().trim().toUpperCase()));

				cars.insertSort(car);

				obsList.add(car);
				filteredData.add(car);
				obsList.sort(Comparator.comparingInt(Car::getYear));
				filteredData.sort(Comparator.comparingInt(Car::getYear));

				modelTF.clear();
				yearComboBox.getEditor().clear();
				colorTF.clear();
				priceTF.clear();

			} catch (Exception e2) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Invaild Inputs!");
				alert.showAndWait();
			}

		});

		deleteButton.setOnAction(e -> {
			try {
				Car selectedCar = carTable.getSelectionModel().getSelectedItem();
				if (selectedCar != null) {
					obsList.remove(selectedCar);
					filteredData.remove(selectedCar);
					cars.remove(selectedCar);

					// Update the table view after removing the item
					carTable.refresh();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				if (carTable.getItems().isEmpty())
					alert.setHeaderText("There is no cars to remove");
				else
					alert.setHeaderText("Please select a car to remove");

				alert.showAndWait();
			}

		});

		clear.setOnAction(e -> {
			if (obsList.isEmpty())
				new IntegerStringConverter().showAlert("No Data", "There is no data to remove");
			else {
				Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
				confirmAlert.setTitle("Confirmation");
				confirmAlert.setHeaderText("Delete Confirmation");
				confirmAlert.setContentText("Are you sure you want to delete all cars?");
				Optional<ButtonType> result = confirmAlert.showAndWait();

				if (result.isPresent() && result.get() == ButtonType.OK) {

					obsList.clear();
					filteredData.clear();
					cars.clear();

					// Update the table view after removing the item
					carTable.refresh();

				}
			}
		});

	}

	public TableView<Car> createTable() {
		TableView<Car> tableView = new TableView<>();
		tableView.setEditable(true);

		TableColumn<Car, String> carModel = new TableColumn<Car, String>("Car Model");
		TableColumn<Car, Integer> carYear = new TableColumn<Car, Integer>("Year");
		TableColumn<Car, String> carColor = new TableColumn<Car, String>("Color");
		TableColumn<Car, Integer> carPrice = new TableColumn<Car, Integer>("Price");

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
		carPrice.setCellValueFactory(new PropertyValueFactory<Car, Integer>("price"));

		carPrice.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getPrice()));
		carPrice.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		carPrice.setOnEditCommit(event -> {
			Car car = event.getRowValue();
			Integer newValue = event.getNewValue();
			if (newValue != null) {
				if (newValue > 0)
					car.setPrice(newValue);
				else {
					new IntegerStringConverter().showAlert("Invalid Inputs", "Negative Numbers are not alowed");
					tableView.refresh();
				}
			} else
				tableView.refresh();
		});

		carColor.setCellFactory(TextFieldTableCell.<Car>forTableColumn());

		carColor.setOnEditCommit((CellEditEvent<Car, String> t) -> {
			((Car) t.getTableView().getItems().get(t.getTablePosition().getRow())).setColor(t.getNewValue()); // display
																												// //
																												// only
		});

		carModel.setCellFactory(TextFieldTableCell.<Car>forTableColumn());

		carModel.setOnEditCommit((CellEditEvent<Car, String> t) -> {
			((Car) t.getTableView().getItems().get(t.getTablePosition().getRow())).setModel(t.getNewValue()); // display
																												// //
																												// only
		});

		carYear.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getYear()));
		carYear.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		carYear.setOnEditCommit(event -> {
			Car car = event.getRowValue();
			Integer newValue = event.getNewValue();
			if (newValue != null) {
				if (newValue >= 1970 && newValue <= 2023) {
					car.setYear(newValue); // display
					obsList.sort(Comparator.comparingInt(Car::getYear));
					filteredData.sort(Comparator.comparingInt(Car::getYear));
					cars.sort(car);
				} else {
					new IntegerStringConverter().showAlert("Invalid Inputs", "Year is in range ot 1970 up to 2023");
					tableView.refresh();
				}
			} else
				tableView.refresh();
		});

		tableView.setItems(filteredData);
		return tableView;

	}

	private Integer[] getYearRange(int startYear, int endYear) {
		int numYears = endYear - startYear + 1;
		Integer[] years = new Integer[numYears];
		for (int i = 0; i < numYears; i++) {
			years[i] = startYear + i;
		}
		return years;
	}

	public static class IntegerStringConverter extends StringConverter<Integer> {

		@Override
		public String toString(Integer value) {
			if (value == null) {
				return "";
			}
			return value.toString();
		}

		@Override
		public Integer fromString(String value) {
			if (isValidInteger(value)) {
				return Integer.parseInt(value);
			} else {
				showAlert("Invalid Input", "Please enter a valid integer value.");
				return null;
			}
		}

		boolean isValidInteger(String value) {
			try {
				Integer.parseInt(value);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		void showAlert(String title, String message) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle(title);
			alert.setHeaderText(null);
			alert.setContentText(message);
			alert.showAndWait();
		}
	}

}
