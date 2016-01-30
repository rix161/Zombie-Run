package application;

import geography.GeographicPoint;

public class PlayerPawn extends Pawn {
	
	static final String iconPath ="player_small.png";
	private int safeHouseVisited = 0;
	
	public PlayerPawn(GeographicPoint pos) {
		super(iconPath);
		this.updatePosition(pos);
		safeHouseVisited = 0;
	}
	
	public void addVisitedSafeHouse(){ safeHouseVisited++;}
	
	public boolean checkSafeHouseStatus(int totalSafeHouse){
		if(safeHouseVisited >= totalSafeHouse)
			return true;
		else
			return false;
	}
	
}
