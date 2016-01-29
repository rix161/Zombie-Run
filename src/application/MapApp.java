/** JavaFX application which interacts with the Google
 * Maps API to provide a mapping interface with which
 * to test and develop graph algorithms and data structures
 * 
 * @author UCSD MOOC development team
 *
 */
package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import application.controllers.FetchController;
import application.controllers.RouteController;
import application.services.GeneralService;
import application.services.RouteService;
import gmapsfx.GoogleMapView;
import gmapsfx.MapComponentInitializedListener;
import gmapsfx.javascript.object.GoogleMap;
import gmapsfx.javascript.object.LatLong;
import gmapsfx.javascript.object.MapOptions;
import gmapsfx.javascript.object.MapTypeIdEnum;

public class MapApp extends Application
					implements MapComponentInitializedListener {

	protected GoogleMapView mapComponent;
	protected GoogleMap map;
  protected BorderPane bp;
  protected Stage primaryStage;

  // CONSTANTS
  private static final double MARGIN_VAL = 10;
  private static final double FETCH_COMPONENT_WIDTH = 160.0;

  public static void main(String[] args){
     launch(args);
  }

  /**
   * Application entry point
   */
	@Override
  public void start(Stage primaryStage) throws Exception {
    this.primaryStage = primaryStage;

		// MAIN CONTAINER
		bp = new BorderPane();

        // set up map
		mapComponent = new GoogleMapView();
		mapComponent.addMapInitializedListener(this);

		// initialize tabs for data fetching and route controls
		
	
    Tab routeTab = new Tab("Control Box");

    Text PizzaStash = new Text("0");
    PizzaStash.setFont(Font.font("Arial",FontWeight.BOLD,20));
    PizzaStash.setFill(Paint.valueOf("Black"));
    
    Button nextTurn = new Button("Finish Day");
    // Select and marker managers for route choosing and marker display/visuals
    // should only be one instance (singleton)
    SelectManager manager = new SelectManager();
    MarkerManager markerManager = new MarkerManager();
    markerManager.setSelectManager(manager);
    manager.setMarkerManager(markerManager);
    
    MasterControl myControl = new MasterControl(manager,markerManager,PizzaStash);
    
    
    // create components for route tab
    CLabel<geography.GeographicPoint> pointLabel = new CLabel<geography.GeographicPoint>("No point Selected.", null);
    manager.setPointLabel(pointLabel);
    setupRouteTab(routeTab,PizzaStash,nextTurn);

        // add tabs to pane, give no option to close
	TabPane tp = new TabPane(routeTab);
    tp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    // initialize Services and controllers after map is loaded
    mapComponent.addMapReadyListener(() -> {
        GeneralService gs = new GeneralService(mapComponent, myControl.getSelectManager(), myControl.getMarkerManager());
        myControl.SetMap();
        nextTurn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				myControl.updateState();
			}
		});
    });

		// add components to border pane
		bp.setRight(tp);
   
		bp.setCenter(mapComponent);

		Scene scene = new Scene(bp);
		scene.getStylesheets().add("html/routing.css");
		primaryStage.setScene(scene);
		primaryStage.show();

	}


	@Override
	public void mapInitialized() {

		LatLong center = new LatLong(32.8810, -117.2380);


		// set map options
		MapOptions options = new MapOptions();
		options.center(center)
		       .mapMarker(false)
		       .mapType(MapTypeIdEnum.ROADMAP)
		       //maybe set false
		       .mapTypeControl(true)
		       .overviewMapControl(false)
		       .panControl(true)
		       .rotateControl(false)
		       .scaleControl(false)
		       .streetViewControl(false)
		       .zoom(14)
		       .zoomControl(true);

		// create map;
		map = mapComponent.createMap(options);
        setupJSAlerts(mapComponent.getWebView());



	}


  /**
   * Setup layout of route tab and controls
   *
   * @param routeTab
   * @param box
   */
  private void setupRouteTab(Tab routeTab,Text pizzaStash,Button nextTurn) {
	  HBox MainBox = new HBox();
	  VBox stack = new VBox();
	  
	  HBox pizzaBox = new HBox();
	  File imageFile = new File("data/images/pizza.png");
	  Image rawPizzaImage = new Image(imageFile.toURI().toString());
	  ImageView pizzaImage = new ImageView(rawPizzaImage);
	  pizzaImage.setFitWidth(50);
	  pizzaImage.setFitHeight(50);
	  Text PizzaText = new Text(" Pizza Stash ");
	  PizzaText.setFont(Font.font("Arial",FontWeight.BOLD,20));
	  pizzaBox.setSpacing(20);
	  pizzaBox.setPadding(new Insets(20));
	  pizzaBox.getChildren().addAll(pizzaImage,PizzaText,pizzaStash);
	  
	  HBox btnBox = new HBox();
	  btnBox.setSpacing(20);
	  btnBox.setPadding(new Insets(20));
	  btnBox.getChildren().add(nextTurn);
	  
	  stack.getChildren().addAll(pizzaBox,btnBox);
	  MainBox.getChildren().add(stack);
	  routeTab.setContent(MainBox);
	  
  }

  private void setupJSAlerts(WebView webView) {
    webView.getEngine().setOnAlert( e -> {
        Stage popup = new Stage();
        popup.initOwner(primaryStage);
        popup.initStyle(StageStyle.UTILITY);
        popup.initModality(Modality.WINDOW_MODAL);

        StackPane content = new StackPane();
        content.getChildren().setAll(
          new Label(e.getData())
        );
        content.setPrefSize(200, 100);

        popup.setScene(new Scene(content));
        popup.showAndWait();
    });
  }



	/*
	 * METHODS FOR SHOWING DIALOGS/ALERTS
	 */

	public void showLoadStage(Stage loadStage, String text) {
		loadStage.initModality(Modality.APPLICATION_MODAL);
		loadStage.initOwner(primaryStage);
	  VBox loadVBox = new VBox(20);
	  loadVBox.setAlignment(Pos.CENTER);
	  Text tNode = new Text(text);
	  tNode.setFont(new Font(16));
	  loadVBox.getChildren().add(new HBox());
	  loadVBox.getChildren().add(tNode);
	  loadVBox.getChildren().add(new HBox());
	  Scene loadScene = new Scene(loadVBox, 300, 200);
	  loadStage.setScene(loadScene);
	  loadStage.show();
	}

	public static void showInfoAlert(String header, String content) {
	  Alert alert = getInfoAlert(header, content);
		alert.showAndWait();
	}

	public static Alert getInfoAlert(String header, String content) {
	  Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert;
	}

	public static void showErrorAlert(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("File Name Error");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}


}
