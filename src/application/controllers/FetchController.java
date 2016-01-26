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
    private static final int ROW_COUNT = 5;
    private GeneralService generalService;
    private RouteService routeService;
    private Node container;
    private Button fetchButton;
    private Button displayButton;
    private ComboBox<DataSet> dataChoices;
    // maybe choice map
    private TextField writeFile;
    private String filename = "data.map";

    // path for mapfiles to load when program starts
    private String persistPath = "data/maps/mapfiles.list";


    public FetchController(GeneralService generalService, RouteService routeService) {
        this.generalService = generalService;
        this.routeService = routeService;
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
