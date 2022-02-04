package main.sample;

import hr.java.covidportal.enums.VrijednostSimptoma;
import hr.java.covidportal.main.Glavna;
import hr.java.covidportal.model.Bolest;
import hr.java.covidportal.model.Osoba;
import hr.java.covidportal.model.Simptom;
import hr.java.covidportal.model.Zupanija;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class PretragaOsobaController implements Initializable {

    private static ObservableList<Osoba> observableListaSvihOsoba;

    private static ObservableList<Osoba> observableListaFiltriranihOsoba;

    @FXML
    private TextField pojamZaPretragu;

    @FXML
    private Button gumbPretraga;

    @FXML
    TableView<Osoba> tablicaOsoba;

    @FXML
    private TableColumn<Osoba, String> stupacImeOsobe;

    @FXML
    private TableColumn<Osoba, String> stupacPrezimeOsobe;

    @FXML
    private TableColumn<Osoba, Integer> stupacStarostOsobe;

    @FXML
    private TableColumn<Osoba, Zupanija> stupacZupanijaPrebivalista;

    @FXML
    private TableColumn<Osoba, Bolest> stupacZarazenBolescu;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Main.getMainStage().setTitle("Pretraga Osoba");

        stupacImeOsobe
                .setCellValueFactory(new PropertyValueFactory<Osoba, String>("ime"));

        stupacPrezimeOsobe
                .setCellValueFactory(new PropertyValueFactory<Osoba, String>("prezime"));

        stupacStarostOsobe
                .setCellValueFactory(new PropertyValueFactory<Osoba, Integer>("starost"));

        stupacZupanijaPrebivalista
                .setCellValueFactory(new PropertyValueFactory<Osoba, Zupanija>("zupanija"));

        stupacZarazenBolescu
                .setCellValueFactory(new PropertyValueFactory<Osoba, Bolest>("zarazenBolescu"));

        List<Osoba> osobe = new ArrayList<>();

            observableListaSvihOsoba = FXCollections.observableArrayList();

            try{
                osobe = BazaPodataka.getSveOsobe();
            }catch(SQLException | ClassNotFoundException ex){
                ex.printStackTrace();
            }


        observableListaSvihOsoba.addAll(osobe);
        tablicaOsoba.setItems(observableListaSvihOsoba);

    }

    public static ObservableList<Osoba> getObservableListaSvihOsoba() {
        return observableListaSvihOsoba;
    }

    public static void setObservableListaSvihOsoba(ObservableList<Osoba> observableList) {
        observableListaSvihOsoba = observableList;
    }

    public void pretraga() {
        String naziv = pojamZaPretragu.getText().toLowerCase();

        List<Osoba> filtriraneOsobe = observableListaSvihOsoba.stream()
                .filter(osoba -> osoba.getIme().toLowerCase().contains(naziv.toLowerCase()) || osoba.getPrezime().toLowerCase().contains(naziv.toLowerCase()))
                .collect(Collectors.toList());

        if(observableListaFiltriranihOsoba == null){
            observableListaFiltriranihOsoba = FXCollections.observableArrayList();
        }else{
            observableListaFiltriranihOsoba.clear();
        }

        observableListaFiltriranihOsoba.addAll(FXCollections.observableArrayList(filtriraneOsobe));

        tablicaOsoba.setItems(observableListaFiltriranihOsoba);
    }

}
