package application;

import geography.GeographicPoint;
import gmapsfx.javascript.object.LatLong;

public class DjikstraZombie extends ZombiePawn {
	private final static String iconPath = "dzombie_small.png"; 
	
	public DjikstraZombie(GeographicPoint pos) {
		super(iconPath, pos);
	}
	
	@Override
	public void loiter(){
		
	}
}
