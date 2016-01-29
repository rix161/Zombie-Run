package application;

import geography.GeographicPoint;

public class PlayerPawn extends Pawn {
	
	static final String iconPath ="player_small.png";
	
	public PlayerPawn(GeographicPoint pos) {
		super(iconPath);
		this.updatePosition(pos);
	}
	
}
