package application;

import java.awt.print.Book;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import application.services.GeneralService;
import geography.GeographicPoint;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MasterControl {
	private SelectManager lSelectManager;
	private MarkerManager lMarkerManager;
	private Text lPizzaStash;
	private DataSet lDataSet;
	private Vector<ZombiePawn> lZombieList;
	private PlayerPawn lPlayerPawn;
	private Random lRandomGene;
	private Set<GeographicPoint> lBlackList;
	private Vector<GeographicPoint> lVertices;
	
	private static int NUMBER_OF_SAFE_HOUSE = 3;
	
	public MasterControl(SelectManager manager, MarkerManager markerManager, Text pizzaStash) {
		this.lSelectManager = manager;
		this.lMarkerManager = markerManager;
		
		lDataSet = new DataSet(GeneralService.getDataSetDirectory()+"utc.map");
		
		lPizzaStash = pizzaStash;
		lPizzaStash.setText("0");
		
		lRandomGene = new Random();
		lBlackList = new HashSet<GeographicPoint>();
		lVertices = new Vector<>();
		
		lZombieList = new Vector<>();
	}
	
	public void SetMap(){
		this.displayIntersections();
		this.updateVertices();
		this.InitPizzaStash();
		
		this.setZombies();
		this.setPlayer();
	}
	
	public void updateState() {
		
		lMarkerManager.setSelectMarketVisibility(false);
		GeographicPoint nextPlayerPos = lSelectManager.getPoint();
		
		updatePlayerStatus(nextPlayerPos);
		updateZombieStatus(nextPlayerPos);
		
		checkState();
	}
	

	private void updateZombieStatus(GeographicPoint nextPlayerPos) {
		
		for(ZombiePawn zp:lZombieList)
			zp.loiter(lDataSet.graph, lPlayerPawn.getCurrentPosition());
		
	}

	private void updatePlayerStatus(GeographicPoint nextPlayerPos) {
		List<GeographicPoint> playerPath =  lDataSet.graph.bfs(lPlayerPawn.getCurrentPosition(), nextPlayerPos);
		int pathLength = 0;
		int pizzaConsumed = 0;
		int currPizzaStash = Integer.parseInt(lPizzaStash.getText());
		
		if(playerPath!=null){
			pathLength = playerPath.size() - 2;
			pizzaConsumed = (int) (Math.pow(2, pathLength ) -1) ;
			if(pizzaConsumed <= 0) pizzaConsumed = 1;
			
			lPlayerPawn.updatePosition(nextPlayerPos);
		}else{
			pizzaConsumed = 1;
		}
		
		Integer updateStash = (currPizzaStash - pizzaConsumed);
		lPizzaStash.setText(updateStash.toString());
		
		if(lDataSet.checkInSafeHouse(lPlayerPawn.getCurrentPosition()))
			lPlayerPawn.addVisitedSafeHouse();
	}

	private boolean checkState() {
		
		int currPizzaStat = Integer.parseInt(lPizzaStash.getText());
		GeographicPoint currPlayerPos = lPlayerPawn.getCurrentPosition();
		
		if(lPlayerPawn.checkSafeHouseStatus(NUMBER_OF_SAFE_HOUSE)){
			DisplayEnd(true);
			return true;
		}
		else if(currPizzaStat <= 0){
			DisplayEnd(false);
			return true;
		}
		else{
			for(ZombiePawn zp:lZombieList){
				if(zp.checkAtPosition(currPlayerPos)){
					DisplayEnd(false);
					return true;
				}
			}
		}	
		return false;
	}

	private void DisplayEnd(boolean isSuccess) {
		
		Stage result = new Stage();
		VBox box = new VBox();
		HBox tBox = new HBox();
		Text finalText = new Text();
		finalText.setFont(Font.font("Arial", FontWeight.BOLD,35));
		
		ImageView finalImage = null;
		if(isSuccess){
			finalText.setText("You Won!");
			File file = new File(System.getProperty("user.dir")+File.separator+"data"+File.separator+"images"+File.separator+"KingHomer.png");
			finalImage = new ImageView(file.toURI().toString());
		}
		else{
			finalText .setText("You Lost!");
			File file = new File(System.getProperty("user.dir")+File.separator+"data"+File.separator+"images"+File.separator+"ZombieHomer.jpg");
			finalImage = new ImageView(file.toURI().toString());
		}
		tBox.getChildren().add(finalText);
		tBox.setAlignment(Pos.CENTER);
		box.getChildren().addAll(tBox,finalImage);
		Scene scene = new Scene(box);
		result.setScene(scene);
		result.show();
		
	}

	

	private void InitPizzaStash() {
		this.updatePizzaStash((int) (lDataSet.getGraph().getNumVertices()* 1.5));
	}

	private void updateVertices() {
		Iterator<GeographicPoint> it = lDataSet.getGraph().getVertices().iterator();
		while(it.hasNext()){
			lVertices.add(it.next());
		}
	}

	private void setPlayer() {
		this.lPlayerPawn = new PlayerPawn(getFreeVertex());
		lMarkerManager.updatePawn(lPlayerPawn.getMarker());
	}
	
	private void setZombies() {
		
		BFSZombie lBFSZombie = new BFSZombie(getRandomVertex());
		DjikstraZombie lDijZombie = new DjikstraZombie(getRandomVertex());
		EulerZombie lEulerZombie = new EulerZombie(getRandomVertex());
		
		lZombieList.add(lBFSZombie);
		lZombieList.add(lDijZombie);
		lZombieList.add(lEulerZombie);
		
		for(ZombiePawn zp:lZombieList){
			lMarkerManager.updatePawn(zp.getMarker());
		}
		
	}
	
	private GeographicPoint getRandomVertex(){

		GeographicPoint freePos = lVertices.get(lRandomGene.nextInt(lVertices.size()));
		lBlackList.add(freePos);
		return freePos;
	}
	
	private GeographicPoint getFreeVertex(){
		
		GeographicPoint freePos = lVertices.get(lRandomGene.nextInt(lVertices.size()));
		while(lBlackList.contains(freePos))
			freePos = lVertices.get(lRandomGene.nextInt(lVertices.size()));
		lBlackList.add(freePos);
		
		return freePos;
	}
	public SelectManager getSelectManager(){
		return lSelectManager;
	}
	public MarkerManager getMarkerManager(){
		return lMarkerManager;
	}

	
	private void updatePizzaStash(Integer update){
		lPizzaStash.setText(update.toString());
	}
	
	private void displayIntersections(){
		lDataSet.initializeGraph();
		lDataSet.generateSafeHouses(NUMBER_OF_SAFE_HOUSE);
		lBlackList.addAll(lDataSet.getSafeHouse());
		lSelectManager.setAndDisplayData(lDataSet);
	}

}
