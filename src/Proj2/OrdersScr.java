package Proj2;

import java.util.Calendar;

import DoubleLinkedList.DoubleLinkedList;
import QueueList.Queue;
import StackList.Stack;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class OrdersScr extends BorderPane {

	GridPane gp = new GridPane();

	Button enQueueAgain = new Button("Re-Order");
	Button acceptOrder = new Button("Accept Order");
	Button deleteOrder = new Button("Delete Order");
	HBox options = new HBox(10, enQueueAgain, acceptOrder, deleteOrder);
	Order order;
	DoubleLinkedList list;
	Stack stack;
	Queue queue;
	Label isAvailable = new Label();

	Label custNameLabel = new Label("Customer Name");
	TextField custNameTF = new TextField();

	Label custMobileLabel = new Label("Customer Mobile");
	TextField custMobileTF = new TextField();

	Label orderDate = new Label("Order Date");
	TextField orderDateTF = new TextField();

	Label carBrandLabel = new Label("Car Brand");
	TextField carBrandTF = new TextField();

	Label carModelLabel = new Label("Car Model");
	TextField carModelTF = new TextField();

	Label carStatus = new Label("Car Status");
	TextField carPriceTF = new TextField();
	Label carPriceLabel = new Label("Car Price");

	TextField carColorTF = new TextField();
	Label carColorLabel = new Label("Car Color");

	TextField carYearTF = new TextField();
	Label carYearLabel = new Label("Car Year");

	public OrdersScr(Stage stage, Scene scene, Queue queue, Stack stack, DoubleLinkedList list) {
		this.list = list;
		this.stack = stack;
		this.queue = queue;
		Label custOrder = new Label("Customers Orders");
		custOrder.setFont(Font.font("Times New Roman", FontWeight.BOLD, 27.5));

		carStatus.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
		isAvailable.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));

		custNameLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
		custNameTF.setEditable(false);

		gp.add(custNameLabel, 0, 0);
		gp.add(custNameTF, 1, 0);

		custMobileLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
		custMobileTF.setEditable(false);

		gp.add(custMobileLabel, 0, 1);
		gp.add(custMobileTF, 1, 1);

		orderDate.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
		orderDateTF.setEditable(false);

		gp.add(orderDate, 0, 2);
		gp.add(orderDateTF, 1, 2);

		carBrandLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));

		carBrandTF.setEditable(false);

		gp.add(carBrandLabel, 0, 3);
		gp.add(carBrandTF, 1, 3);

		carModelLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));

		carModelTF.setEditable(false);

		gp.add(carModelLabel, 0, 4);
		gp.add(carModelTF, 1, 4);

		carYearLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));

		carYearTF.setEditable(false);

		gp.add(carYearLabel, 0, 5);
		gp.add(carYearTF, 1, 5);

		carColorLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));

		carColorTF.setEditable(false);

		gp.add(carColorLabel, 0, 6);
		gp.add(carColorTF, 1, 6);

		carPriceLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));

		carPriceTF.setEditable(false);

		gp.add(carPriceLabel, 0, 7);
		gp.add(carPriceTF, 1, 7);

		gp.add(carStatus, 0, 8);
		gp.add(isAvailable, 1, 8);

		gp.setHgap(10);
		gp.setVgap(10);

		gp.setAlignment(Pos.CENTER);

		gp.setStyle(
				"-fx-border-color: white;-fx-border-width: 2px;-fx-border-radius: 10px;-fx-background-color: black;");
		gp.setPadding(new Insets(10));

		setMargin(gp, new Insets(50, 350, 50, 350));

		Button backButton = new Button("Back");

		backButton.setOnAction(e -> {
			stage.setScene(scene);
			stage.centerOnScreen();

		});

		BorderPane bp = new BorderPane();
		bp.setCenter(custOrder);
		bp.setLeft(backButton);

		setTop(bp);

		options.setAlignment(Pos.CENTER);

		Label warning = new Label("THERE IS NO ORDERS");
		warning.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));

		if (queue.isEmpty()) {
			setBottom(null);
			setCenter(warning);
			warning.setPadding(new Insets(15));
			warning.setStyle(
					"-fx-border-color: white;-fx-border-width: 2px;-fx-border-radius: 10px;-fx-background-color: black;");

		} else {
			setOrderInfo();
		}

		enQueueAgain.setOnAction(e -> {
			queue.enQueue(queue.deQueue());
			setOrderInfo();

		});

		acceptOrder.setOnAction(e -> {
			Order order = (Order) queue.deQueue();
			stack.push(order);
			String brand = order.getBrand();
			((CarBrand) list.get(brand)).getCars().remove(order.getCar());
			if (queue.isEmpty()) {
				setBottom(null);
				setCenter(warning);
				warning.setPadding(new Insets(15));
				warning.setStyle(
						"-fx-border-color: white;-fx-border-width: 2px;-fx-border-radius: 10px;-fx-background-color: black;");

			} else {
				setOrderInfo();
			}
		});

		deleteOrder.setOnAction(e -> {
			queue.deQueue();
			if (queue.isEmpty()) {
				setBottom(null);
				setCenter(warning);
				warning.setPadding(new Insets(15));
				warning.setStyle(
						"-fx-border-color: white;-fx-border-width: 2px;-fx-border-radius: 10px;-fx-background-color: black;");

			} else {
				setOrderInfo();
			}
		});

		setPadding(new Insets(15));

	}

	private void setOrderInfo() {
		setBottom(options);
		setCenter(gp);
		Order order = (Order) queue.getFront();
		String brand = order.getBrand();

		if (list.contains(brand) && ((CarBrand) list.get(brand)).getCars().get(order.getCar()) != null) {
			isAvailable.setText("Available");
			acceptOrder.setDisable(false);
			isAvailable.setStyle("-fx-text-fill:green;");
		} else {
			isAvailable.setText("Not Available");
			acceptOrder.setDisable(true);
			isAvailable.setStyle("-fx-text-fill:red;");
		}
		custNameTF.setText(order.getName());
		custMobileTF.setText(order.getMobile());
		carBrandTF.setText(order.getBrand());
		orderDateTF.setText(order.getOrderDate().get(Calendar.MONTH) + 1 + "/" + order.getOrderDate().get(Calendar.DATE)
				+ "/" + order.getOrderDate().get(Calendar.YEAR));
		carModelTF.setText(order.getCar().getModel());
		carPriceTF.setText(order.getCar().getPrice() + "");
		carColorTF.setText(order.getCar().getColor());
		carYearTF.setText(order.getCar().getYear() + "");

	}
}
