package application;

import geography.GeographicPoint;
import gmapsfx.javascript.object.LatLong;

public class EulerZombie extends ZombiePawn {
	private final static String iconPath = "ezombie_small.png"; 
	
	public EulerZombie(GeographicPoint pos) {
		super(iconPath, pos);
	}
	
	@Override
	public void loiter(){
		
	}
}
