package Proj2;

import QueueList.Queue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class OrderStage extends Stage {
    BorderPane bp = new BorderPane();
    GridPane carGP = new GridPane();
    GridPane custGP = new GridPane();

    public OrderStage(String carBrand, Car car, Queue queue) {
        Button orderButton = new Button("Order");
        Button cancelButton = new Button("Cancel");

        HBox options = new HBox(10, orderButton, cancelButton);
        options.setAlignment(Pos.CENTER);
        bp.setBottom(options);

        Label orderLabel = new Label("Car Order");
        orderLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 27.5));
        orderLabel.setAlignment(Pos.CENTER);

        HBox hBox = new HBox(orderLabel);
        hBox.setAlignment(Pos.CENTER);

        // Car details
        Label carBrandLabel = new Label("Car Brand");
        carBrandLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));

        TextField carBrandTF = new TextField(carBrand);
        carBrandTF.setEditable(false);

        carGP.add(carBrandLabel, 0, 0);
        carGP.add(carBrandTF, 0, 1);

        Label carModelLabel = new Label("Car Model");
        carModelLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));

        TextField carModelTF = new TextField(car.getModel());
        carModelTF.setEditable(false);

        carGP.add(carModelLabel, 1, 0);
        carGP.add(carModelTF, 1, 1);

        Label carYearLabel = new Label("Car Year");
        carYearLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));

        TextField carYearTF = new TextField(car.getYear() + "");
        carYearTF.setEditable(false);

        carGP.add(carYearLabel, 2, 0);
        carGP.add(carYearTF, 2, 1);

        Label carColorLabel = new Label("Car Color");
        carColorLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));

        TextField carColorTF = new TextField(car.getColor());
        carColorTF.setEditable(false);

        carGP.add(carColorLabel, 3, 0);
        carGP.add(carColorTF, 3, 1);

        Label carPriceLabel = new Label("Car Price");
        carPriceLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));

        TextField carPriceTF = new TextField(car.getPrice() + "");
        carPriceTF.setEditable(false);

        carGP.add(carPriceLabel, 4, 0);
        carGP.add(carPriceTF, 4, 1);

        carGP.setHgap(10);
        carGP.setVgap(10);

        carGP.setAlignment(Pos.CENTER);

        // Customer details
        Label custNameLabel = new Label("Customer Name");
        custNameLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));

        TextField custNameTF = new TextField();

        TextFormatter<String> textFormatterName = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-Z\\s]*")) {
                return change;
            }
            return null;
        });

        // Set the TextFormatter for the TextField
        custNameTF.setTextFormatter(textFormatterName);

        custGP.add(custNameLabel, 0, 0);
        custGP.add(custNameTF, 0, 1);

        Label custMobileLabel = new Label("Customer Mobile");
        custMobileLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));

        TextField custMobileTF = new TextField();

        TextFormatter<String> textFormatterMobile = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        });

        // Set the TextFormatter for the TextField
        custMobileTF.setTextFormatter(textFormatterMobile);

        custGP.add(custMobileLabel, 1, 0);
        custGP.add(custMobileTF, 1, 1);

        Label orderDate = new Label("Order Date");
        orderDate.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));

        DatePicker datePicker = new DatePicker();
        datePicker.setEditable(false);

        custGP.add(orderDate, 2, 0);
        custGP.add(datePicker, 2, 1);

        custGP.setHgap(10);
        custGP.setVgap(10);

        custGP.setAlignment(Pos.CENTER);

        VBox info = new VBox(10, carGP, custGP);
        info.setAlignment(Pos.CENTER);

        bp.setTop(hBox);
        bp.setCenter(info);

        bp.setPadding(new Insets(15));

        orderButton.setOnAction(e -> {
            if (custNameTF.getText().isBlank() || custMobileTF.getText().isBlank()
                    || datePicker.getEditor().getText().isBlank()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Text Fields Can Not be left empty");
                alert.showAndWait();
            } else {
                String[] tkz = datePicker.getEditor().getText().split("/");
                int year = Integer.parseInt(tkz[2].trim());
                int month = Integer.parseInt(tkz[1].trim());
                int day = Integer.parseInt(tkz[0].trim());
                Order order = new Order(custNameTF.getText().trim(), custMobileTF.getText().trim(), carBrand, car, day,
                        month, year);

                queue.enQueue(order);
                close();

            }
        });

        cancelButton.setOnAction(e -> close());

        Scene scene = new Scene(bp);
        scene.getStylesheets().add("DarkMode.css");
        setTitle("Car Order");
        setScene(scene);
        setHeight(600);
        setWidth(800);

        show();
    }
}
