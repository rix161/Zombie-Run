package application;

import java.io.File;

import geography.GeographicPoint;
import gmapsfx.javascript.object.LatLong;
import gmapsfx.javascript.object.Marker;
import gmapsfx.javascript.object.MarkerOptions;

public class Pawn {
	private Marker lMarker;
	GeographicPoint lCurrPosition;
	
	public Pawn(String iconPath) {
		iconPath = System.getProperty("user.dir")+File.separator+"data"+File.separator+"images"+File.separator+iconPath;
		MarkerOptions mo = new MarkerOptions().icon(iconPath).visible(true);
		lMarker = new Marker(mo);
	}
	
	public void updatePosition(GeographicPoint lPos){
		LatLong lLatLong = new LatLong(lPos.getX(),lPos.getY());
		lCurrPosition = lPos;
		lMarker.setPosition(lLatLong);
	}
	
	public GeographicPoint getCurrentPosition(){
		return lCurrPosition;
	}

	public Marker getMarker(){
		return lMarker;
	}
}
