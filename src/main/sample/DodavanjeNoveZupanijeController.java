package main.sample;

import hr.java.covidportal.model.Zupanija;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
import java.util.ResourceBundle;
import java.util.Scanner;


public class DodavanjeNoveZupanijeController implements Initializable{

    @FXML
    TextField naziv;

    @FXML
    TextField brojStanovnika;

    @FXML
    TextField brojZarazenih;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            Main.getMainStage().setTitle("Dodavanje nove županije");
    }

    public void dodaj(){

        if(naziv.getText() != null && brojStanovnika.getText() != null && brojZarazenih.getText() != null){


            try {

                BazaPodataka.insertZupanija(new Zupanija(null, naziv.getText(), Integer.parseInt(brojStanovnika.getText()), Integer.parseInt(brojZarazenih.getText())));

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert!");
            alert.setHeaderText("Uspjeh!");
            alert.setContentText("Zupanija " + naziv.getText() + " uspješno unesena!");
            alert.showAndWait();

        }
    }
}
