package application;

import geography.GeographicPoint;

public class ZombiePawn extends Pawn {
	private int aggressionFactor;
	
	public ZombiePawn(String iconPath,GeographicPoint pos) {
		super(iconPath);
		this.updatePosition(pos);
		aggressionFactor = 1;
	}
	
	public void makeAngry(int turnCount){
		if( aggressionFactor < (turnCount/10))
			aggressionFactor++;
	}
	
	void loiter(){};

}
