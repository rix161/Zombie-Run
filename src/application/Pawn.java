package application;

import java.io.File;

import geography.GeographicPoint;
import gmapsfx.javascript.object.LatLong;
import gmapsfx.javascript.object.Marker;
import gmapsfx.javascript.object.MarkerOptions;

public class Pawn {
	private Marker lMarker;
	
	public Pawn(String iconPath) {
		iconPath = System.getProperty("user.dir")+File.separator+"data"+File.separator+"images"+File.separator+iconPath;
		MarkerOptions mo = new MarkerOptions().icon(iconPath).visible(true);
		lMarker = new Marker(mo);
	}
	
	public void updatePosition(GeographicPoint lPos){
		LatLong lLatLong = new LatLong(lPos.getX(),lPos.getY());
		lMarker.setPosition(lLatLong);
	}
	
	public GeographicPoint getCurrentPosition(){
		MarkerOptions lMO = lMarker.getMarkerOptions();
		LatLong lLatLong =  lMO.getPosition();
		GeographicPoint lGP = new GeographicPoint(lLatLong.getLatitude(), lLatLong.getLongitude());
		return lGP;
	}

	public Marker getMarker(){
		return lMarker;
	}
}
