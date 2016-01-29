package application.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

import com.sun.javafx.geom.Rectangle;

import application.DataSet;
import application.MapApp;
import application.services.GeneralService;
import application.services.RouteService;
import gmapsfx.javascript.object.GoogleMap;
import gmapsfx.javascript.object.LatLong;
import gmapsfx.javascript.object.LatLongBounds;
import javafx.collections.ObservableSet;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import mapmaker.MapMaker;

public class FetchController {
	private GeneralService generalService;
    public FetchController(GeneralService generalService) {
        this.generalService = generalService;
        setupDisplayButton();
    }

    /**
     * Registers event to fetch data
     */
    private void setupDisplayButton() {
    	DataSet ds = new DataSet(GeneralService.getDataSetDirectory()+"ucsd.map");
    	generalService.displayIntersections(ds);
    }




}
