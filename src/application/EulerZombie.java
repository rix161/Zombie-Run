package application;

import java.util.Random;
import java.util.Vector;

import geography.GeographicPoint;
import gmapsfx.javascript.object.LatLong;
import roadgraph.MapGraph;
import roadgraph.MapNode;

public class EulerZombie extends ZombiePawn {
	private final static String iconPath = "ezombie_small.png"; 
	
	public EulerZombie(GeographicPoint pos) {
		super(iconPath, pos);
	}
	
	@Override
	public void loiter(MapGraph lMap,GeographicPoint dest){
		this.incrementTurnCount();
		this.makeAngry();
		
		Vector<MapNode> neigh = lMap.getNeigbors(this.getCurrentPosition());
		
		if( neigh !=null && neigh.size()%2 == 0){
			Random rand = new Random();
			MapNode lGP = neigh.get(rand.nextInt(neigh.size()));
			this.updatePosition(lGP.getLocation());
		}
		
	}
}
