package Proj2;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Developers extends BorderPane {

	public Developers(Stage stage, Scene scene) {
		ImageView abdullah = new ImageView("Abdullah.png");
		abdullah.setFitHeight(280);
		abdullah.setFitWidth(280);
		Circle borderAMJ = new Circle(abdullah.getFitWidth() / 2 + 5);
		borderAMJ.setFill(Color.WHITE);

		StackPane abdullahSP = new StackPane();
		abdullahSP.getChildren().addAll(borderAMJ, abdullah);

		ScaleTransition scaleTransitionAMJ = new ScaleTransition(Duration.seconds(0.2), abdullah);
		scaleTransitionAMJ.setToX(0.9);
		scaleTransitionAMJ.setToY(0.9);

		ColorAdjust colorFace = new ColorAdjust();
		colorFace.setHue(0);
		colorFace.setBrightness(0.1);
		colorFace.setSaturation(0.8);

		borderAMJ.setOnMouseEntered(event -> borderAMJ.setEffect(colorFace));
		borderAMJ.setOnMouseExited(event -> borderAMJ.setEffect(null));

		Label abdullahInfo1 = new Label();
		Text text1 = new Text("Abdullah Abduljalil ");
		Text text2 = new Text("second year");

		text1.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		text2.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

		abdullahInfo1.setGraphic(new HBox(text1, text2));
		text2.setStyle("-fx-fill: #960018;");
		text1.setStyle("-fx-fill: #FFFFFF;");

		Label abdullahInfo2 = new Label("\"Computer Science student || Interested in Full Stack Development\"");
		abdullahInfo2.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
		abdullahInfo2.setStyle("-fx-text-fill: #FFFFFF;");

		VBox vBox1 = new VBox(10, abdullahInfo1, abdullahInfo2);
		vBox1.setStyle(
				"-fx-border-color: white; -fx-border-width: 1px;-fx-border-radius: 20px;-fx-background-color: black;");
		vBox1.setPadding(new Insets(15));

		setMargin(vBox1, new Insets(250, 25, 250, 25));

		TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), vBox1);
		translateTransition.setFromY(-200);
		translateTransition.setToY(0);

		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), new KeyValue(vBox1.opacityProperty(), 1)));

		abdullah.setOnMouseEntered(event -> {
			setCenter(vBox1);

			translateTransition.play();
			timeline.play();

			borderAMJ.setEffect(colorFace);
			scaleTransitionAMJ.play();
		});

		abdullah.setOnMouseExited(event -> {
			getChildren().remove(vBox1);
			translateTransition.stop();
			timeline.stop();
			vBox1.setOpacity(0);

			abdullah.setEffect(null);
			borderAMJ.setEffect(null);
			scaleTransitionAMJ.stop();
			abdullah.setScaleX(1.0);
			abdullah.setScaleY(1.0);
		});


		VBox vBox = new VBox(10, abdullahSP);
		vBox.setAlignment(Pos.CENTER);

		setLeft(abdullahSP);

		Text text = new Text();
		text.setFont(Font.font("Verdana", FontWeight.BOLD, 41));
		text.setFill(Color.RED);
		text.setStroke(Color.BLACK);
		text.setStrokeWidth(2);
		text.setEffect(new DropShadow(10, Color.BLACK));
		setEffect(new Reflection());
		Button backBtn = new Button("Back");
		backBtn.setOnAction(e -> {
			stage.setScene(scene);
			stage.centerOnScreen();
		});
		setTop(backBtn);
		setPadding(new Insets(15));

	}

}
