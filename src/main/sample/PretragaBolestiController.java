package main.sample;

import hr.java.covidportal.main.Glavna;
import hr.java.covidportal.model.Bolest;
import hr.java.covidportal.model.Simptom;
import hr.java.covidportal.model.Virus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.database.BazaPodataka;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static main.sample.Main.getMainStage;

public class PretragaBolestiController implements Initializable {

    private static ObservableList<Bolest> observableListaSvihBolesti;

    private static ObservableList<Bolest> observableListaFiltriranihBolesti;

    @FXML
    private TextField pojamZaPretragu;

    @FXML
    TableView <Bolest> tablicaBolesti;

    @FXML
    private TableColumn<Bolest, String> stupacNazivBolesti;

    @FXML
    private TableColumn<Bolest, Set<Simptom>> stupacSimptomiBolesti;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getMainStage().setTitle("Pretraga Bolesti");

        stupacNazivBolesti
                .setCellValueFactory(new PropertyValueFactory<Bolest, String>("naziv"));

        stupacSimptomiBolesti
                .setCellValueFactory(new PropertyValueFactory<Bolest, Set<Simptom>>("simptomi"));


        List<Bolest> procitaneBolesti = new ArrayList<>();

        observableListaSvihBolesti = FXCollections.observableArrayList();

        try{
            procitaneBolesti = BazaPodataka.getSveBolesti();

            procitaneBolesti = procitaneBolesti.stream().filter(bolest -> !(bolest instanceof Virus)).collect(Collectors.toList());
        }catch(SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }


        observableListaSvihBolesti.addAll(procitaneBolesti);
        tablicaBolesti.setItems(observableListaSvihBolesti);

    }

    public static ObservableList<Bolest> getObservableListaSvihBolesti() {
        return observableListaSvihBolesti;
    }

    public static void setObservableListaSvihBolesti(ObservableList<Bolest> observableList) {
        observableListaSvihBolesti = observableList;
    }

    public void pretraga() {
        String naziv = pojamZaPretragu.getText().toLowerCase();

        List<Bolest> filtriraneBolesti = observableListaSvihBolesti.stream()
                .filter(bolest -> bolest.getNaziv().toLowerCase().contains(naziv.toLowerCase()))
                .collect(Collectors.toList());

        if(observableListaFiltriranihBolesti == null){
            observableListaFiltriranihBolesti = FXCollections.observableArrayList();
        }else{
            observableListaFiltriranihBolesti.clear();
        }

        observableListaFiltriranihBolesti.addAll(FXCollections.observableArrayList(filtriraneBolesti));

        tablicaBolesti.setItems(observableListaFiltriranihBolesti);
    }
}