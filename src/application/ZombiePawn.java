package application;

import geography.GeographicPoint;
import roadgraph.MapGraph;

public class ZombiePawn extends Pawn {
	private int aggressionFactor;
	private int turnCount;
	
	public ZombiePawn(String iconPath,GeographicPoint pos) {
		super(iconPath);
		this.updatePosition(pos);
		aggressionFactor = 1;
		turnCount = 0;
	}
	
	protected void makeAngry(){
		if( aggressionFactor < (turnCount/10))
			aggressionFactor++;
	}
	protected void incrementTurnCount(){
		turnCount++;
	}
	protected int getAggressionFactor(){ return aggressionFactor;}
	
	public boolean checkAtPosition(GeographicPoint playerPos){
		if(lCurrPosition.equals(playerPos))
			return true;
		else
			return false;
	}
	
	void loiter(MapGraph lMap,GeographicPoint dest){
	};
	
	

}
