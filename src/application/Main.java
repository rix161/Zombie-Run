package application;
	



import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;

import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Zombie Run");
		BorderPane myBP = new BorderPane();
		myBP.setPadding(new Insets(5));
		
		myBP.setTop(getMainTitle());
		myBP.setCenter(getObjectives());
		myBP.setLeft(getPaddingBox());
		myBP.setRight(getPaddingBox());
		myBP.setBottom(getButtons(primaryStage));
		primaryStage.setScene(new Scene(myBP));
		primaryStage.show();
	}
	
	private HBox getButtons(Stage ps) {
		HBox box = new HBox();
		Button StartButton =  new Button(" RUN! ");
		StartButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
					MapApp app = new MapApp();
					try {
						app.start(ps);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				});
				
			}
		});
		
		Button HintButton = new Button(" Hint! ");
		HintButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Stage stage = new Stage();
				Rectangle2D screen = Screen.getPrimary().getVisualBounds();
				Text hBFSZombie = new Text("BFS Zombie: Does a BFS traversal to player current location as destination ");
				Text hDijkstraZombie = new Text("Dijkstra Zombie: Does a Dijkstra traversal to player current location as destination ");
				Text hEulerZombie = new Text("Euler Zombie: Hangs around nodes with odd degree ");
				
				VBox box = new VBox();
				box.getChildren().add(hEulerZombie);
				box.getChildren().add(hBFSZombie);
				box.getChildren().add(hDijkstraZombie);
				Scene hScene = new Scene(box);
				stage.setScene(hScene);
				stage.show();
				
			}
		});
		box.setPadding(new Insets(20));
		box.setSpacing(30);
		box.getChildren().addAll(StartButton,HintButton);
		box.setAlignment(Pos.CENTER);
		return box;
	}

	private Node getPaddingBox() {
		HBox paddingBox = new HBox();
		paddingBox.setMinWidth(50);
		return paddingBox;
	}

	private VBox getObjectives() {
		VBox box = new VBox();
		
		VBox titleBox = new VBox();
		titleBox.setAlignment(Pos.CENTER);
		Text title = new Text("OBJECTIVES");
		titleBox.getChildren().add(title);
		
		VBox objBox = new VBox();
		Text obj1 = new Text("\n 1) Visit every safe house atleast once");
		Text obj2 = new Text("\n 2) Don't run out of pizzas ... never!!");
		Text obj3 = new Text("\n 3) Don't get caught by zombies");
		
		objBox.getChildren().add(title);
		objBox.getChildren().add(obj1);
		objBox.getChildren().add(obj2);
		objBox.getChildren().add(obj3);
		objBox.setAlignment(Pos.CENTER_LEFT);
		
		box.getChildren().add(titleBox);
		box.getChildren().add(objBox);
		box.setAlignment(Pos.CENTER);
		box.setStyle("-fx-border-color: black;");
		box.setSpacing(10);
		box.setPadding(new Insets(20));
		return box;
	}

	private VBox getOptionBox() {
		VBox box = new VBox();
		return box;
	}

	private VBox getMainTitle() {
		VBox box = new VBox();
		
		Text backGroundTitle = new Text();
		backGroundTitle.setText(" Welcome to Zombie Run ");
		backGroundTitle.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		Text story = new Text(" \n You get up after finishing UCSD Graph Theory Class, "
							  +"\n To find that world is overrun with ZOMBIES!!! "
							  + "\n Can you Hit all the safe houses in the area,"
							  + "\n before your pizzas run out??");
		
		
		box.getChildren().add(backGroundTitle);
		box.getChildren().add(story);
		box.setAlignment(Pos.CENTER);
		box.setStyle("-fx-border-color: black;");
		box.setSpacing(10);
		box.setPadding(new Insets(50));
		return box;
	}

	public static void main(String[] args) {
		launch(args);
	}
}