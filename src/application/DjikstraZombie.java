package application;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import geography.GeographicPoint;
import gmapsfx.javascript.object.LatLong;
import roadgraph.MapGraph;
import roadgraph.MapNode;

public class DjikstraZombie extends ZombiePawn {
	private final static String iconPath = "dzombie_small.png"; 
	
	public DjikstraZombie(GeographicPoint pos) {
		super(iconPath, pos);
	}
	
	@Override
	public void loiter(MapGraph lMap,GeographicPoint dest){
		
		this.incrementTurnCount();
		this.makeAngry();
		List<GeographicPoint> lList =  lMap.dijkstra(getCurrentPosition(), dest);
		if(lList!=null){
			int steps = getAggressionFactor();
			if(lList.size() < steps) steps  = lList.size();
			this.updatePosition(lList.get(steps));
			lMap.resetPointMap();
		}
		else{
			Vector<MapNode> neigh = lMap.getNeigbors(this.getCurrentPosition());
			Random rand = new Random();
			MapNode lGP = neigh.get(rand.nextInt(neigh.size()));
			this.updatePosition(lGP.getLocation());
		}
		
	}
}
