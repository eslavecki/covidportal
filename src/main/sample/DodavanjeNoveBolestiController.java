package main.sample;

import hr.java.covidportal.model.Bolest;
import hr.java.covidportal.model.Simptom;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.database.BazaPodataka;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import static main.sample.Main.getMainStage;

public class DodavanjeNoveBolestiController implements Initializable {

    @FXML
    TextField naziv;

    @FXML
    ListView<Simptom> odabirSimptoma;

    @FXML
    RadioButton virus;

    @FXML
    RadioButton nijeVirus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getMainStage().setTitle("Dodavanje nove bolesti");

        try {

            odabirSimptoma.getItems().addAll(BazaPodataka.getSveSimptome());

            odabirSimptoma.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        }catch(SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    public void dodaj(){

        if(naziv.getText() != null && odabirSimptoma.getSelectionModel().getSelectedItems() != null){

            Set<Simptom> simptomiBolesti = new HashSet<>();

            simptomiBolesti.addAll(odabirSimptoma.getSelectionModel().getSelectedItems());

            try {

                BazaPodataka.insertBolest(new Bolest(null, naziv.getText(), simptomiBolesti));

            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert!");
            alert.setHeaderText("Uspjeh!");
            alert.setContentText("Bolest " + naziv.getText() + " uspješno unesena!");
            alert.showAndWait();

        }
    }
}
