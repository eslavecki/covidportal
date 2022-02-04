package main.sample;

import hr.java.covidportal.main.Glavna;
import hr.java.covidportal.model.Bolest;
import hr.java.covidportal.model.Osoba;
import hr.java.covidportal.model.Simptom;
import hr.java.covidportal.model.Zupanija;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.database.BazaPodataka;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import static main.sample.Main.getMainStage;

public class DodavanjeNoveOsobeController implements Initializable {

    @FXML
    TextField ime;

    @FXML
    TextField prezime;

    @FXML
    DatePicker starost;

    @FXML
    ChoiceBox<Zupanija> odabirZupanije;

    @FXML
    ChoiceBox<Bolest> odabirBolesti;

    @FXML
    ListView<Osoba> odabirKontakata;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getMainStage().setTitle("Dodavanje nove osobe");


        try {


            odabirZupanije.getItems().addAll(BazaPodataka.getSveZupanije());
            odabirBolesti.getItems().addAll(BazaPodataka.getSveBolesti());
            odabirKontakata.getItems().addAll(BazaPodataka.getSveOsobe());

            odabirZupanije.setValue(odabirZupanije.getItems().get(0));
            odabirBolesti.setValue(odabirBolesti.getItems().get(0));

            odabirKontakata.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void dodaj(){

        if(ime.getText() != null && prezime.getText() != null && starost.getValue() != null && odabirKontakata.getSelectionModel().getSelectedItems() != null){

            try {

                LocalDate datumRodjenja = starost.getValue();

                BazaPodataka.insertOsoba(new Osoba
                        .UserBuilder(ime.getText(), prezime.getText())
                        .datumRodjenja(datumRodjenja)
                        .zupanija(odabirZupanije.getValue())
                        .zarazenBolescu(odabirBolesti.getValue())
                        .kontaktiraneOsobe((List)odabirKontakata.getItems())
                        .build());

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert!");
            alert.setHeaderText("Uspjeh!");
            alert.setContentText("Osoba " + ime.getText() + " " + prezime.getText() + " uspješno unesena!");
            alert.showAndWait();

        }
    }
}
