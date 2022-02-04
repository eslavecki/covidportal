package main.sample;

import hr.java.covidportal.main.Glavna;
import hr.java.covidportal.model.Zupanija;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.database.BazaPodataka;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static main.sample.Main.getMainStage;

public class PretragaZupanijeController implements Initializable {

    private static ObservableList<Zupanija> observableListaSvihZupanija;

    private static ObservableList<Zupanija> observableListaFiltriranihZupanija;

    @FXML
    private TextField pojamZaPretragu;

    @FXML
    TableView <Zupanija> tablicaZupanija;

    @FXML
    private TableColumn<Zupanija, String> stupacNazivZupanije;

    @FXML
    private TableColumn<Zupanija, Integer> stupacBrojStanovnika;

    @FXML
    private TableColumn<Zupanija, Integer> stupacBrojZarazenih;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getMainStage().setTitle("Pretraga Županija");

        stupacNazivZupanije
                .setCellValueFactory(new PropertyValueFactory<Zupanija, String>("naziv"));

        stupacBrojStanovnika
                .setCellValueFactory(new PropertyValueFactory<Zupanija, Integer>("brojStanovnika"));

        stupacBrojZarazenih
                .setCellValueFactory(new PropertyValueFactory<Zupanija, Integer>("brojZarazenih"));

        List<Zupanija> zupanije = new ArrayList<>();


                observableListaSvihZupanija = FXCollections.observableArrayList();

                try{
                    zupanije = BazaPodataka.getSveZupanije();
                }catch(SQLException | ClassNotFoundException ex){
                    ex.printStackTrace();
                }

            observableListaSvihZupanija.addAll(zupanije);
            tablicaZupanija.setItems(observableListaSvihZupanija);
    }

    public static ObservableList<Zupanija> getListaZupanija() {
        return observableListaSvihZupanija;
    }

    public static void setObservableListaSvihZupanija(ObservableList<Zupanija> observableList) {
        observableListaSvihZupanija = observableList;
    }

    public void pretraga() {
        String naziv = pojamZaPretragu.getText().toLowerCase();

        List<Zupanija> filtriraneZupanije = observableListaSvihZupanija.stream()
                .filter(zupanija -> zupanija.getNaziv().toLowerCase().contains(naziv.toLowerCase()))
                .collect(Collectors.toList());

        if(filtriraneZupanije.isEmpty()){
            getMainStage().getScene().getStylesheets().add(getClass().getClassLoader().getResource("red-border.css").toExternalForm());
        }else{
            pojamZaPretragu.setStyle("-fx-border-width: 0px");
        }

        if(observableListaFiltriranihZupanija == null){
            observableListaFiltriranihZupanija = FXCollections.observableArrayList();
        }else{
            observableListaFiltriranihZupanija.clear();
        }

        observableListaFiltriranihZupanija.addAll(FXCollections.observableArrayList(filtriraneZupanije));

        tablicaZupanija.setItems(observableListaFiltriranihZupanija);
    }
}
