package main.sample;

import hr.java.covidportal.enums.VrijednostSimptoma;
import hr.java.covidportal.main.Glavna;
import hr.java.covidportal.model.Simptom;
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
import java.util.*;
import java.util.stream.Collectors;

import static main.sample.Main.getMainStage;

public class PretragaSimptomaController implements Initializable {

    private static ObservableList<Simptom> observableListaSvihSimptoma;

    private static ObservableList<Simptom> observableListaFiltriranihSimptoma;

    @FXML
    private TextField pojamZaPretragu;

    @FXML
    private Button gumbPretraga;

    @FXML
    TableView<Simptom> tablicaSimptoma;

    @FXML
    private TableColumn<Simptom, String> stupacNazivSimptoma;

    @FXML
    private TableColumn<Simptom, VrijednostSimptoma> stupacVrijednostSimptoma;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getMainStage().setTitle("Pretraga Simptoma");

        stupacNazivSimptoma
                .setCellValueFactory(new PropertyValueFactory<Simptom, String>("naziv"));

        stupacVrijednostSimptoma
                .setCellValueFactory(new PropertyValueFactory<Simptom, VrijednostSimptoma>("vrijednost"));


        List<Simptom> simptomi = new ArrayList<>();


            observableListaSvihSimptoma = FXCollections.observableArrayList();

            try {
                simptomi = BazaPodataka.getSveSimptome();
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

        observableListaSvihSimptoma.addAll(simptomi);
        tablicaSimptoma.setItems(observableListaSvihSimptoma);

    }

    public static ObservableList<Simptom> getObservableListaSvihSimptoma() {
        return observableListaSvihSimptoma;
    }

    public static void setObservableListaSvihSimptoma(ObservableList<Simptom> observableList) {
        observableListaSvihSimptoma = observableList;
    }

    public void pretraga() {
        String naziv = pojamZaPretragu.getText().toLowerCase();

        List<Simptom> filtriraniSimptomi = observableListaSvihSimptoma.stream()
                .filter(simptom -> simptom.getNaziv().toLowerCase().contains(naziv.toLowerCase()))
                .collect(Collectors.toList());

        if(observableListaFiltriranihSimptoma == null){
            observableListaFiltriranihSimptoma = FXCollections.observableArrayList();
        }else{
            observableListaFiltriranihSimptoma.clear();
        }

        observableListaFiltriranihSimptoma.addAll(FXCollections.observableArrayList(filtriraniSimptomi));

        tablicaSimptoma.setItems(observableListaFiltriranihSimptoma);
    }
}
