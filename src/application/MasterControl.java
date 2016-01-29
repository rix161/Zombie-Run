package application;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import application.services.GeneralService;
import geography.GeographicPoint;
import javafx.scene.text.Text;

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
		
		lDataSet = new DataSet(GeneralService.getDataSetDirectory()+"ucsd.map");
		
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
	

	private void InitPizzaStash() {
		this.updatePizzaStash((int) (lDataSet.getGraph().getNumVertices() * 1.5));
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

	public void updateState() {
		
	}
	
	private void updatePizzaStash(Integer update){
		lPizzaStash.setText(update.toString());
	}
	private void displayIntersections(){
		lDataSet.generateSafeHouses(NUMBER_OF_SAFE_HOUSE);
		lSelectManager.setAndDisplayData(lDataSet);
	}

}
