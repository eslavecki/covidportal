package main.sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PocetniEkranController implements Initializable {

    @FXML
    public void prikaziEkranZaPretraguZupanija() throws IOException {
        Parent pretragaZupanijaFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "pretragaZupanije.fxml")));
        Scene pretragaZupanijaScene = new Scene(pretragaZupanijaFrame, 600, 400);
        Main.getMainStage().setScene(pretragaZupanijaScene);
    }

    @FXML
    public void prikaziEkranZaPretraguSimptoma() throws IOException {
        Parent pretragaSimptomaFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "pretragaSimptoma.fxml")));
        Scene pretragaSimptomaScene = new Scene(pretragaSimptomaFrame, 600, 400);
        Main.getMainStage().setScene(pretragaSimptomaScene);
    }

    @FXML
    public void prikaziEkranZaPretraguBolesti() throws IOException {
        Parent pretragaBolestiFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "pretragaBolesti.fxml")));
        Scene pretragaBolestiScene = new Scene(pretragaBolestiFrame, 600, 400);
        Main.getMainStage().setScene(pretragaBolestiScene);
    }

    @FXML
    public void prikaziEkranZaPretraguVirusa() throws IOException {
        Parent pretragaVirusaFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "pretragaVirusa.fxml")));
        Scene pretragaVirusaScene = new Scene(pretragaVirusaFrame, 600, 400);
        Main.getMainStage().setScene(pretragaVirusaScene);
    }

    @FXML
    public void prikaziEkranZaPretraguOsoba() throws IOException {
        Parent pretragaOsobaFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "pretragaOsoba.fxml")));
        Scene pretragaOsobaScene = new Scene(pretragaOsobaFrame, 600, 400);
        Main.getMainStage().setScene(pretragaOsobaScene);
    }

    @FXML
    public void prikaziEkranZaNoviZapisZupanije() throws IOException {
        Parent noviZapisZupanijeFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "dodavanjeNoveZupanije.fxml")));
        Scene noviZapisZupanijeScene = new Scene(noviZapisZupanijeFrame, 600, 400);
        Main.getMainStage().setScene(noviZapisZupanijeScene);
    }

    @FXML
    public void prikaziEkranZaNoviZapisSimptoma() throws IOException {
        Parent noviZapisSimptomaFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "dodavanjeNovogSimptoma.fxml")));
        Scene noviZapisSimptomaScene = new Scene(noviZapisSimptomaFrame, 600, 400);
        Main.getMainStage().setScene(noviZapisSimptomaScene);
    }

    @FXML
    public void prikaziEkranZaNoviZapisBolesti() throws IOException {
        Parent noviZapisBolestiFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "dodavanjeNoveBolesti.fxml")));
        Scene noviZapisBolestiScene = new Scene(noviZapisBolestiFrame, 600, 400);
        Main.getMainStage().setScene(noviZapisBolestiScene);
    }

    @FXML
    public void prikaziEkranZaNoviZapisVirusa() throws IOException {
        Parent noviZapisVirusaFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "dodavanjeNovogVirusa.fxml")));
        Scene noviZapisVirusaScene = new Scene(noviZapisVirusaFrame, 600, 400);
        Main.getMainStage().setScene(noviZapisVirusaScene);
    }

    @FXML
    public void prikaziEkranZaNoviZapisOsobe() throws IOException {
        Parent noviZapisOsobeFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "dodavanjeNoveOsobe.fxml")));
        Scene noviZapisosobeScene = new Scene(noviZapisOsobeFrame, 600, 400);
        Main.getMainStage().setScene(noviZapisosobeScene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
