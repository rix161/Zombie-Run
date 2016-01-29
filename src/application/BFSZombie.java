package application;

import geography.GeographicPoint;


public class BFSZombie extends ZombiePawn {
	private final static String iconPath = "bzombie_small.png"; 
	
	public BFSZombie(GeographicPoint pos) {
		super(iconPath, pos);
	}
	
	@Override
	public void loiter(){
		
	}
}
