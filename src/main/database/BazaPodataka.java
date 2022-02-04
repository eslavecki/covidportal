package main.database;

import hr.java.covidportal.enums.VrijednostSimptoma;
import hr.java.covidportal.model.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class BazaPodataka {

    private static final String PATH = "src/main/database/config.properties";

    public static Connection connect() throws ClassNotFoundException, SQLException {

        Properties properties = new Properties();

        try {
            properties.load(new FileReader(PATH));

        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        Connection veza;
        veza = DriverManager.getConnection (url, username,password);

        return veza;
    }

    public static void disconnect(Connection veza) throws SQLException {
        veza.close();
    }

    public static List<Simptom> getSveSimptome() throws SQLException, ClassNotFoundException {

        List<Simptom> simptomi = new ArrayList<>();

        Long id;
        String naziv;
        VrijednostSimptoma vrijednost;

        Connection veza = connect();
        PreparedStatement statement = veza.prepareStatement("SELECT * FROM SIMPTOM");
        ResultSet rs = statement.executeQuery();

            while(rs.next()){

                id = rs.getLong("ID");

                naziv = rs.getString("NAZIV");

                switch (rs.getString("VRIJEDNOST")){
                    case "Produktivni":
                        vrijednost = VrijednostSimptoma.Produktivni;
                        break;
                    case "Intenzivno":
                        vrijednost = VrijednostSimptoma.Intenzivno;
                        break;
                    case "Visoka":
                        vrijednost = VrijednostSimptoma.Visoka;
                        break;
                    case "Jaka":
                        vrijednost = VrijednostSimptoma.Jaka;
                        break;
                    default:
                        vrijednost = VrijednostSimptoma.VRIJEDNOST_NIJE_DEFINIRANA;
                }

                simptomi.add(new Simptom(id, naziv, vrijednost));
            }

        disconnect(veza);

        return simptomi;
    }

    public static List<Bolest> getSveBolesti() throws SQLException, ClassNotFoundException {

        List<Bolest> bolesti = new ArrayList<>();

        Set<Simptom> simptomi = new HashSet<>();

        Set<Simptom> simptomiBolesti = new HashSet<>();

        Long idBolesti, idSimptoma;
        String naziv;
        boolean virus;

        Connection veza = connect();
        PreparedStatement query = veza.prepareStatement("SELECT * FROM BOLEST");
        ResultSet rs = query.executeQuery();

        while(rs.next()){

            idBolesti = rs.getLong("ID");

            naziv = rs.getString("NAZIV");

            virus = rs.getBoolean("VIRUS");

            if(virus == true){
                bolesti.add(new Virus(idBolesti, naziv, new HashSet<>()));
            }else{
                bolesti.add(new Bolest(idBolesti, naziv, new HashSet<>()));
            }
        }

        query = veza.prepareStatement("SELECT * FROM BOLEST_SIMPTOM");

        rs = query.executeQuery();


        while(rs.next()){

            idBolesti = rs.getLong("BOLEST_ID");

            idSimptoma = rs.getLong("SIMPTOM_ID");

            for(Bolest bolest : bolesti){
                if(bolest.getId() == idBolesti){
                    bolest.addSimptom(getSimptomByID(idSimptoma));
                }
            }

        }

        disconnect(veza);

        return bolesti;
    }

    public static List<Zupanija> getSveZupanije() throws SQLException, ClassNotFoundException {

        List<Zupanija> zupanija = new ArrayList<>();

        Long id;
        String naziv;
        int brojStanovnika, brojZarazenih;

        Connection veza = connect();
        PreparedStatement query = veza.prepareStatement("SELECT * FROM ZUPANIJA");
        ResultSet rs = query.executeQuery();

        while(rs.next()){

            id = rs.getLong("ID");

            naziv = rs.getString("NAZIV");

            brojStanovnika = rs.getInt("BROJ_STANOVNIKA");

            brojZarazenih = rs.getInt("BROJ_ZARAZENIH_STANOVNIKA");

            zupanija.add(new Zupanija(id, naziv, brojStanovnika, brojZarazenih));
        }

        disconnect(veza);

        return zupanija;
    }

    public static List<Osoba> getSveOsobe() throws SQLException, ClassNotFoundException {

        List<Osoba> osobe = new ArrayList<>();

        List<Osoba> kontakti = new ArrayList<>();
        
        Long idOsobe, idZupanije, idBolesti, idKontakta;
        String ime, prezime;
        Date DOB;

        Connection veza = connect();
        PreparedStatement query = veza.prepareStatement("SELECT * FROM OSOBA");
        ResultSet rs = query.executeQuery();

        while(rs.next()){

            idOsobe = rs.getLong("ID");

            ime = rs.getString("IME");

            prezime = rs.getString("PREZIME");

            DOB = (Date) rs.getDate("DATUM_RODJENJA");

            idZupanije = rs.getLong("ZUPANIJA_ID");
            
            idBolesti = rs.getLong("BOLEST_ID");
            
            Zupanija zupanijaPrebivalista = getZupanijaByID(idZupanije);
            
            Bolest bolestOsobe = getBolestByID(idBolesti);


            Instant instant = Instant.ofEpochMilli(DOB.getTime());
            LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();

            osobe.add(new Osoba.UserBuilder(ime, prezime).Id(idOsobe).datumRodjenja(localDate).zarazenBolescu(bolestOsobe).zupanija(zupanijaPrebivalista).kontaktiraneOsobe(kontakti).build());
        }

        query = veza.prepareStatement("SELECT * FROM KONTAKTIRANE_OSOBE");

        rs = query.executeQuery();

        while (rs.next()){

            idOsobe = rs.getLong("OSOBA_ID");

            idKontakta = rs.getLong("KONTAKTIRANA_OSOBA_ID");

            for(Osoba osoba : osobe){
                if(osoba.getId() == idOsobe){
                    for(Osoba kontakt : osobe){
                        if(kontakt.getId() == idKontakta){
                            osoba.addKontakt(kontakt);
                        }
                    }
                }
            }

        }

        disconnect(veza);

        return osobe;
    }

    public static Simptom getSimptomByID(Long ID) throws SQLException, ClassNotFoundException {

        Long id = null;
        String naziv = null;
        VrijednostSimptoma vrijednost = null;

        Connection veza = connect();
        PreparedStatement query = veza.prepareStatement("SELECT * FROM SIMPTOM WHERE ID=?");
        query.setLong(1, ID);

        ResultSet rs = query.executeQuery();

        if(rs.next()){
            id = rs.getLong("ID");

            naziv = rs.getString("NAZIV");


            switch (rs.getString("VRIJEDNOST")){
                case "Produktivni":
                    vrijednost = VrijednostSimptoma.Produktivni;
                    break;
                case "Intenzivno":
                    vrijednost = VrijednostSimptoma.Intenzivno;
                    break;
                case "Visoka":
                    vrijednost = VrijednostSimptoma.Visoka;
                    break;
                case "Jaka":
                    vrijednost = VrijednostSimptoma.Jaka;
                    break;
                default:
                    vrijednost = VrijednostSimptoma.VRIJEDNOST_NIJE_DEFINIRANA;
            }
        }

        disconnect(veza);

        return new Simptom(id, naziv, vrijednost);
    }

    public static Bolest getBolestByID(Long ID) throws SQLException, ClassNotFoundException {

        List<Simptom> simptomi = getSveSimptome();

        Set<Simptom> simptomiBolesti = new HashSet<>();

        Long idBolesti = null, idSimptoma;
        String naziv = null;
        boolean virus = false;


        Connection veza = connect();
        PreparedStatement query = veza.prepareStatement("SELECT * FROM BOLEST WHERE ID=?");
        query.setLong(1, ID);
        ResultSet rs = query.executeQuery();

        if(rs.next()){
            idBolesti = rs.getLong("ID");

            naziv = rs.getString("NAZIV");

            virus = rs.getBoolean("VIRUS");

        }

        query = veza.prepareStatement("SELECT * FROM BOLEST_SIMPTOM WHERE BOLEST_ID=?");
        query.setLong(1,ID);
        rs = query.executeQuery();

        while (rs.next()){

            idBolesti = rs.getLong("BOLEST_ID");

            idSimptoma = rs.getLong("SIMPTOM_ID");

            simptomiBolesti.add(getSimptomByID(idSimptoma));

        }

        disconnect(veza);

        if(virus == true){
            return new Virus(idBolesti, naziv, simptomiBolesti);
        }else{
            return new Bolest(idBolesti, naziv, simptomiBolesti);
        }
    }

    public static Zupanija getZupanijaByID(Long ID) throws SQLException, ClassNotFoundException {

        Long id = null;
        String naziv = null;
        int brojStanovnika = 0, brojZarazenih = 0;

        Connection veza = connect();
        PreparedStatement query = veza.prepareStatement("SELECT * FROM ZUPANIJA WHERE ID=?");
        query.setLong(1, ID);
        ResultSet rs = query.executeQuery();

        if(rs.next()){
            id = rs.getLong("ID");

            naziv = rs.getString("NAZIV");

            brojStanovnika = rs.getInt("BROJ_STANOVNIKA");

            brojZarazenih = rs.getInt("BROJ_ZARAZENIH_STANOVNIKA");
        }

        disconnect(veza);

        return new Zupanija(id, naziv, brojStanovnika, brojZarazenih);
    }

    public static Osoba getOsobaByID(Long ID) throws SQLException, ClassNotFoundException {

        List<Osoba> kontakti = new ArrayList<>();

        Long id, idZupanije, idBolesti, idKontakta;
        String ime, prezime;
        Date DOB;

        Connection veza = connect();
        PreparedStatement query = veza.prepareStatement("SELECT * FROM OSOBA WHERE ID=?");
        query.setLong(1, ID);
        ResultSet rs = query.executeQuery();

        id = rs.getLong("ID");

        ime = rs.getString("IME");

        prezime = rs.getString("PREZIME");

        DOB = rs.getDate("DATUM_RODJENJA");

        idZupanije = rs.getLong("ZUPANIJA_ID");

        idBolesti = rs.getLong("BOLEST_ID");

        Zupanija zupanijaPrebivalista = getZupanijaByID(idZupanije);

        Bolest bolestOsobe = getBolestByID(idBolesti);

        Instant instant = Instant.ofEpochMilli(DOB.getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();

        query = veza.prepareStatement("SELECT * FROM KONTAKTIRANE_OSOBE WHERE OSOBA_ID=?");
        query.setLong(1, ID);
        rs = query.executeQuery();

        while (rs.next()){

            id = rs.getLong("OSOBA_ID");

            idKontakta = rs.getLong("KONTAKTIRANA_OSOBA_ID");

            kontakti.add(getOsobaByID(idKontakta));

        }

        disconnect(veza);

        return new Osoba.UserBuilder(ime, prezime).Id(id).datumRodjenja(localDate).zarazenBolescu(bolestOsobe).zupanija(zupanijaPrebivalista).kontaktiraneOsobe(kontakti).build();
    }

    public static void insertSimptom(Simptom simptom) throws SQLException, ClassNotFoundException {

        Connection veza = connect();
        PreparedStatement query = veza.prepareStatement("INSERT INTO SIMPTOM(NAZIV, VRIJEDNOST) VALUES (?,?)");

        query.setString(1,simptom.getNaziv());
        query.setString(2, simptom.getVrijednost().toString());
        
        query.executeUpdate();
        
        disconnect(veza);
    }

    public static void insertBolest(Bolest bolest) throws SQLException, ClassNotFoundException {

        Long id = null;

        boolean virus;

        if(bolest instanceof Virus){
            virus = true;
        }else{
            virus = false;
        }

        Connection veza = connect();
        PreparedStatement query = veza.prepareStatement("INSERT INTO BOLEST(NAZIV, VIRUS) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);

        query.setString(1, bolest.getNaziv());
        query.setBoolean(2, virus);

        query.executeUpdate();

        ResultSet rs = query.getGeneratedKeys();

        while (rs.next()){
            id = rs.getLong("ID");
        }


        for(Simptom simptom : bolest.getSimptomi()){
            query = veza.prepareStatement("INSERT INTO BOLEST_SIMPTOM(BOLEST_ID, SIMPTOM_ID) VALUES (?,?)");
            query.setLong(1, id);
            query.setLong(2, simptom.getId());
            query.executeUpdate();
        }

        disconnect(veza);
    }

    public static void insertZupanija(Zupanija zupanija) throws SQLException, ClassNotFoundException {

        Connection veza = connect();
        PreparedStatement query = veza.prepareStatement("INSERT INTO ZUPANIJA(NAZIV, BROJ_STANOVNIKA, BROJ_ZARAZENIH_STANOVNIKA) VALUES (?,?,?)");

        query.setString(1, zupanija.getNaziv());
        query.setInt(2, zupanija.getBrojStanovnika());
        query.setInt(3, zupanija.getBrojZarazenih());

        query.executeUpdate();

        disconnect(veza);
    }

    public static void insertOsoba(Osoba osoba) throws SQLException, ClassNotFoundException {

        Long id = null;

        Connection veza = connect();
        PreparedStatement query = veza.prepareStatement("INSERT INTO OSOBA(IME, PREZIME, DATUM_RODJENJA, ZUPANIJA_ID, BOLEST_ID) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

        query.setString(1, osoba.getIme());
        query.setString(2, osoba.getPrezime());
        query.setDate(3, Date.valueOf(osoba.getDatumRodjenja()));
        query.setLong(4, osoba.getZupanija().getId());
        query.setLong(5, osoba.getZarazenBolescu().getId());

        query.executeUpdate();

        ResultSet rs = query.getGeneratedKeys();

        while(rs.next()){
            id = rs.getLong("ID");
        }

        for(Osoba kontakt : osoba.getKontaktiraneOsobe()){
            query = veza.prepareStatement("INSERT INTO KONTAKTIRANE_OSOBE (OSOBA_ID, KONTAKTIRANA_OSOBA_ID) VALUES (?,?)");

            query.setLong(1, id);
            query.setLong(2, kontakt.getId());
        }

        query.executeUpdate();

        disconnect(veza);
    }

}
