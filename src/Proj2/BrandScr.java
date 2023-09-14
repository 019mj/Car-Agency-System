package Proj2;

import java.util.Optional;

import DoubleLinkedList.DoubleLinkedList;
import LinkedList.LinkedList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/*
 * The BrandScr class represents the screen that displays and manages brands information.
 * It extends the BorderPane class to provide a layout container for its components.
 */

public class BrandScr extends BorderPane {
    
    // Constructor for the BrandScr class.
    public BrandScr(Stage stage, Scene scene, DoubleLinkedList list) {
        
        // Create a ComboBox for selecting brand names.
        ComboBox<String> cb = new ComboBox<String>();
        cb.setEditable(true);

        // Restrict the input in the ComboBox to non-digit characters.
        cb.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\D.*"))
                cb.getEditor().setText(newValue.replaceAll("\\d", ""));
        });

        // Create an ObservableList to store the brand names.
        ObservableList<String> items = FXCollections.observableArrayList();

        // Populate the ObservableList with brand names from the DoubleLinkedList.
        for (int i = 0; i < list.size(); i++) {
            items.add(((CarBrand) list.get(i)).getBrand());
        }

        // Create a FilteredList wrapping the ObservableList.
        FilteredList<String> filteredItems = new FilteredList<String>(items, p -> true);

        // Add a listener to the text property of the ComboBox editor.
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

        // Set the items of the ComboBox to the filtered items.
        cb.setItems(filteredItems);

        // Create buttons for insert, delete, update, and search operations.
        Button insertButton = new Button("Insert");
        Button deleteButton = new Button("Delete");
        Button updateButton = new Button("Update");
        Button searchButton = new Button("Search");

        // Create an HBox to hold the buttons.
        HBox options = new HBox(10, insertButton, deleteButton, updateButton, searchButton);
        options.setAlignment(Pos.CENTER);

        // Create a VBox to hold the ComboBox and the options HBox.
        VBox vBox = new VBox(10, cb, options);
        vBox.setAlignment(Pos.CENTER);

        // Set the center of the BorderPane to the VBox.
        setCenter(vBox);

        // Event handler for the insertButton.
        insertButton.setOnAction(e -> {
            String brand = cb.getEditor().getText().trim().toUpperCase();
            if (!brand.isEmpty()) {
                if (list.contains(brand)) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Brand Already Exists");
                    alert.showAndWait();
                } else {
                    list.insertSort(brand, null);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Successful");
                    alert.setHeaderText("Brand Inserted Successfully");
                    alert.showAndWait();
                    items.add(brand);
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Brand Cannot Be Empty");
                alert.showAndWait();
            }
        });

        // Event handler for the deleteButton.
        deleteButton.setOnAction(e -> {
            String brand = cb.getSelectionModel().getSelectedItem().trim().toUpperCase();
            if (!brand.isEmpty()) {
                Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirmation");
                confirmAlert.setHeaderText("Delete Confirmation");
                confirmAlert.setContentText("Are you sure you want to delete this item?");
                Optional<ButtonType> result = confirmAlert.showAndWait();
                
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (list.remove(brand)) {
                        Alert successAlert = new Alert(AlertType.INFORMATION);
                        successAlert.setTitle("Successful");
                        successAlert.setHeaderText("Brand Deleted Successfully");
                        successAlert.showAndWait();
                        items.remove(brand);
                    } else {
                        Alert errorAlert = new Alert(AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText("Brand Not Found");
                        errorAlert.showAndWait();
                    }
                }
            } else {
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Brand Cannot Be Empty");
                errorAlert.showAndWait();
            }
        });

        // Event handler for the updateButton.
        updateButton.setOnAction(e -> {
            String brand = cb.getSelectionModel().getSelectedItem().toUpperCase();
            if (!brand.isEmpty()) {
                CarBrand carBrand = (CarBrand) list.get(brand);
                if (carBrand != null) {
                    Stage updateStage = new Stage();

                    Label info = new Label("Update " + brand + " Brand :");
                    info.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));

                    TextField updateTF = new TextField();
                    Button updateButt = new Button("Update");
                    VBox updateBox = new VBox(10, info, updateTF, updateButt);
                    updateBox.setPadding(new Insets(30));
                    updateBox.setAlignment(Pos.CENTER);

                    updateButt.setOnAction(ex -> {
                        String newBrand = updateTF.getText().trim().toUpperCase();
                        if (list.contains(newBrand)) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Brand Already Exists");
                            alert.showAndWait();
                        } else {
                            items.remove(brand);
                            carBrand.setBrand(newBrand);
                            list.sort(carBrand);
                            items.add(newBrand);

                            updateStage.close();
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Successful");
                            alert.setHeaderText("Brand Updated Successfully");
                            alert.showAndWait();
                        }
                    });

                    Scene updateScene = new Scene(updateBox);
                    updateScene.getStylesheets().add("DarkMode.css");
                    updateStage.setScene(updateScene);
                    updateStage.show();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Brand Does Not Found");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Brand Cannot Be Empty");
                alert.showAndWait();
            }
        });

        // Event handler for the searchButton.
        searchButton.setOnAction(e -> {
            String brand = cb.getSelectionModel().getSelectedItem().trim().toUpperCase();
            if (!brand.isEmpty()) {
                CarBrand carBrand = (CarBrand) list.get(brand);
                if (carBrand != null) {
                    LinkedList cars = carBrand.getCars();
                    CarsScr saveScreen = new CarsScr(stage, scene, cars, list);
                    Scene saveScene = new Scene(saveScreen, 1200, 700);
                    saveScene.getStylesheets().add("DarkMode.css");
                    stage.setScene(saveScene);
                    stage.centerOnScreen();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Brand Does Not Found");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Brand Cannot Be Empty");
                alert.showAndWait();
            }
        });

        // Button for navigating back to the previous scene.
        Button back = new Button("Back");

        back.setOnAction(e -> {
            stage.setScene(scene);
            stage.centerOnScreen();
        });

        setTop(back);

        setPadding(new Insets(15));
    }
}
