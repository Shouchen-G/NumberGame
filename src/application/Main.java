package application;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Main extends Application {
	int attempt = 0;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Guessing Game");
		Text text = new Text("Guess a number between 1-10:");
		TextField textField = new TextField();
		textField.setMaxWidth(70);
		Button btn = new Button("Enter");
		Text response = new Text("Waiting for input....");
		Button newGame = new Button();
		Button result = new Button("View Results");

		ImageView myImage = new ImageView(new Image("guess.png"));
		myImage.setFitWidth(200);
		myImage.setFitHeight(200);
		StackPane stackPane = new StackPane(myImage);

		TranslateTransition translate = new TranslateTransition(Duration.seconds(3), stackPane);
		translate.setCycleCount(TranslateTransition.INDEFINITE);
		translate.setByY(150);
		translate.setAutoReverse(true);
		translate.play();

		VBox v = new VBox(15);
		v.setAlignment(Pos.CENTER);
		v.setPadding(new Insets(25, 25, 25, 25));
		v.setStyle("-fx-background-color: AQUAMARINE");

		HBox h = new HBox(10);
		h.setAlignment(Pos.CENTER);
		h.getChildren().add(textField);
		h.getChildren().add(btn);
		v.getChildren().addAll(stackPane, text, h, response);

		Scene scene = new Scene(v, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();

		int target = (int) Math.floor(Math.random() * 10) + 1;

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					int guess = Integer.parseInt(textField.getText());
					if (guess > target) {
						response.setFill(Color.BLUEVIOLET);
						response.setText("Too High, please try again");
						attempt++;
					} else if (guess < target) {
						response.setFill(Color.CHOCOLATE);
						response.setText("Too Low, please try again");
						attempt++;
					} else {
						attempt++;
						v.getChildren().clear();
						Label label = new Label("Congratulations!");
						Image imgW = new Image("win.png");
						ImageView viewW = new ImageView(imgW);
						viewW.setFitHeight(100);
						viewW.setPreserveRatio(true);
						label.setGraphic(viewW);
						v.setSpacing(30);
						v.getChildren().add(label);

						HBox view = new HBox(20);
						view.setAlignment(Pos.CENTER);
						Image imgN = new Image("newgame.png");
						ImageView viewN = new ImageView(imgN);
						viewN.setFitHeight(40);
						viewN.setPreserveRatio(true);
						newGame.setStyle("-fx-background-color: AQUAMARINE");
						newGame.setGraphic(viewN);
						view.getChildren().addAll(newGame, result);
						v.getChildren().add(view);
					}
				} catch (NumberFormatException e) {
					response.setFill(Color.RED);
					response.setText("Please enter an integer");
				}
			}
		});

		newGame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				attempt = 0;
				start(primaryStage);
			}
		});

		result.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				v.getChildren().clear();
				Text last = new Text("The correct answer is " + target + "\nTotal attempts: " + attempt);
				last.setFill(Color.DARKBLUE);
				v.getChildren().addAll(last,newGame);
			}
		});

	}

	public static void main(String[] args) {
		launch(args);
	}
}
