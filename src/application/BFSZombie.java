package application;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import geography.GeographicPoint;
import roadgraph.MapGraph;
import roadgraph.MapNode;


public class BFSZombie extends ZombiePawn {
	private final static String iconPath = "bzombie_small.png"; 
	
	public BFSZombie(GeographicPoint pos) {
		super(iconPath, pos);
	}
	
	@Override
	public void loiter(MapGraph lMap,GeographicPoint dest){
		this.incrementTurnCount();
		this.makeAngry();
		List<GeographicPoint> lList =  lMap.bfs(getCurrentPosition(), dest);
		if(lList!=null){
			int steps = getAggressionFactor();
			if(lList.size() < steps) steps  = lList.size();
			this.updatePosition(lList.get(steps -1));
		}
		else{
			Vector<MapNode> neigh = lMap.getNeigbors(this.getCurrentPosition());
			Random rand = new Random();
			MapNode lGP = neigh.get(rand.nextInt(neigh.size()));
			this.updatePosition(lGP.getLocation());
		}
	}
}
