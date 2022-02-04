package main.sample;

import hr.java.covidportal.enums.VrijednostSimptoma;
import hr.java.covidportal.model.Simptom;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import main.database.BazaPodataka;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static main.sample.Main.getMainStage;

public class DodavanjeNovogSimptomaController implements Initializable {

    @FXML
    TextField naziv;

    @FXML
    ChoiceBox<VrijednostSimptoma> vrijednost;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getMainStage().setTitle("Dodavanje novog simptoma");

        vrijednost.getItems().addAll(VrijednostSimptoma.Produktivni
                                    ,VrijednostSimptoma.Intenzivno
                                    ,VrijednostSimptoma.Jaka
                                    ,VrijednostSimptoma.Visoka);

        vrijednost.setValue(VrijednostSimptoma.Produktivni);
    }

    public void dodaj(){

        if(naziv.getText() != null && vrijednost.getSelectionModel().getSelectedItem() != null){

            try {

                VrijednostSimptoma vs;

                switch (vrijednost.getSelectionModel().getSelectedItem().toString()){
                    case "Produktivni":
                        vs = VrijednostSimptoma.Produktivni;
                        break;
                    case "Intenzivno":
                        vs = VrijednostSimptoma.Intenzivno;
                        break;
                    case "Visoka":
                        vs = VrijednostSimptoma.Visoka;
                        break;
                    case "Jaka":
                        vs = VrijednostSimptoma.Jaka;
                        break;
                    default:
                        vs = VrijednostSimptoma.VRIJEDNOST_NIJE_DEFINIRANA;
                }

                BazaPodataka.insertSimptom(new Simptom(null, naziv.getText(), vs));

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert!");
            alert.setHeaderText("Uspjeh!");
            alert.setContentText("Simptom " + naziv.getText() + " uspješno unesen!");
            alert.showAndWait();

        }
    }
}
