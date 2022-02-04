package main.sample;

import hr.java.covidportal.main.Glavna;
import hr.java.covidportal.model.Simptom;
import hr.java.covidportal.model.Virus;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import main.database.BazaPodataka;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;

import static main.sample.Main.getMainStage;

public class DodavanjeNovogVirusaController implements Initializable {

    @FXML
    TextField naziv;

    @FXML
    ListView<Simptom> odabirSimptoma;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getMainStage().setTitle("Dodavanje novog virusa");

        try {

            odabirSimptoma.getItems().addAll(BazaPodataka.getSveSimptome());

            odabirSimptoma.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    public void dodaj(){

        if(naziv.getText() != null && odabirSimptoma.getSelectionModel().getSelectedItems() != null){

            Set<Simptom> simptomiBolesti = new HashSet<>();

            simptomiBolesti.addAll(odabirSimptoma.getSelectionModel().getSelectedItems());

            try {

                BazaPodataka.insertBolest(new Virus(null, naziv.getText(), simptomiBolesti));

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert!");
            alert.setHeaderText("Uspjeh!");
            alert.setContentText("Virus " + naziv.getText() + " uspješno unesen!");
            alert.showAndWait();

        }
    }
}
