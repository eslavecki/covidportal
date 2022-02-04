package main.sample;

import hr.java.covidportal.main.Glavna;
import hr.java.covidportal.model.Bolest;
import hr.java.covidportal.model.Simptom;
import hr.java.covidportal.model.Virus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

public class PretragaVirusaController implements Initializable {

    private static ObservableList<Bolest> observableListaSvihVirusa;

    private static ObservableList<Bolest> observableListaFiltriranihVirusa;

    @FXML
    private TextField pojamZaPretragu;

    @FXML
    private Button gumbPretraga;

    @FXML
    TableView <Bolest> tablicaVirusa;

    @FXML
    private TableColumn<Bolest, String> stupacNazivVirusa;

    @FXML
    private TableColumn<Bolest, Set<Simptom>> stupacSimptomiVirusa;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getMainStage().setTitle("Pretraga Virusa");

        stupacNazivVirusa
                .setCellValueFactory(new PropertyValueFactory<Bolest, String>("naziv"));

        stupacSimptomiVirusa
                .setCellValueFactory(new PropertyValueFactory<Bolest, Set<Simptom>>("simptomi"));


        List<Bolest> virusi = new ArrayList<>();

            observableListaSvihVirusa = FXCollections.observableArrayList();

            try{

                virusi = BazaPodataka.getSveBolesti();

                virusi = virusi.stream().filter(bolest -> bolest instanceof Virus).collect(Collectors.toList());

            }catch(SQLException | ClassNotFoundException ex){
                ex.printStackTrace();
            }


        observableListaSvihVirusa.addAll(virusi);
        tablicaVirusa.setItems(observableListaSvihVirusa);

    }

    public static ObservableList<Bolest> getObservableListaSvihVirusa() {
        return observableListaSvihVirusa;
    }

    public static void setObservableListaSvihVirusa(ObservableList<Bolest> observableList) {
        observableListaSvihVirusa = observableList;
    }

    public void pretraga() {
        String naziv = pojamZaPretragu.getText().toLowerCase();

        List<Bolest> filtriraniVirusi = observableListaSvihVirusa.stream()
                .filter(zupanija -> zupanija.getNaziv().toLowerCase().contains(naziv.toLowerCase()))
                .collect(Collectors.toList());

        if(observableListaFiltriranihVirusa == null){
            observableListaFiltriranihVirusa = FXCollections.observableArrayList();
        }else{
            observableListaFiltriranihVirusa.clear();
        }

        observableListaFiltriranihVirusa.addAll(FXCollections.observableArrayList(filtriraniVirusi));

        tablicaVirusa.setItems(observableListaFiltriranihVirusa);
    }
}