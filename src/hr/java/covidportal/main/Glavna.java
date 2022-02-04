package hr.java.covidportal.main;

import hr.java.covidportal.enums.VrijednostSimptoma;
import hr.java.covidportal.generics.KlinikaZaInfektivneBolesti;
import hr.java.covidportal.iznimke.BolestIstihSimptoma;
import hr.java.covidportal.model.*;
import hr.java.covidportal.sort.CovidSorter;
import hr.java.covidportal.sort.ReverseVirusSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


/**
 * CovidPortal je aplikacija koja služi za unos simptoma, bolesti i pacijenata.
 *
 */
public class Glavna {

    private  static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    /**
     * Metoda <code>main</code> služi za deklaraciju potrebnih polja te poziv metoda za unos i ispis.
     * @param args Polje argumenata predatih sa komandne linije.
     */
    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("dat/bolesti.txt"));

            reader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Aplikacija CovidPortal je uspješno pokrenuta!");

        Scanner tipkovnica = new Scanner(System.in);
        List<Zupanija> zupanije = new LinkedList<>();
        List<Simptom> simptomi = new LinkedList<>();
        List<Bolest> bolesti = new LinkedList<>();
        Map<Bolest,List<Osoba>> zarazeni = new HashMap<>();
        List<Osoba> osobe = new LinkedList<>();

        File datotekaZupanije = new File("dat/zupanije.txt");
        File datotekaSimptomi = new File("dat/simptomi.txt");
        File datotekaBolesti = new File("dat/bolesti.txt");
        File datotekaVirusi = new File("dat/virusi.txt");
        File datotekaOsobe = new File("dat/osobe.txt");

        try {

            Scanner scannerZupanije = new Scanner(datotekaZupanije);
            Scanner scannerSimptomi = new Scanner(datotekaSimptomi);
            Scanner scannerBolesti = new Scanner(datotekaBolesti);
            Scanner scannerVirusi = new Scanner(datotekaVirusi);
            Scanner scannerOsobe = new Scanner(datotekaOsobe);

            zupanije = unosZupanija(scannerZupanije);

            simptomi = unosSimptoma(scannerSimptomi);

            bolesti = unosBolesti(scannerBolesti, simptomi);

            bolesti.addAll(unosVirusa(scannerVirusi, simptomi));

            osobe = unosOsoba(scannerOsobe, zupanije, bolesti);


        }catch (FileNotFoundException e) {

            System.out.println("Dogodila se pogreška kod otvaranja datoteka! Molimo pokušajte ponovno.");
            logger.error(e.getMessage());

        }

        int i = 0;

        for(Bolest bolest : bolesti){
            if(!zarazeni.containsKey(bolesti.get(i))){
                zarazeni.put(bolesti.get(i), new ArrayList<>());
            }
            i++;
        }


        for(Osoba osoba : osobe){

            for(Bolest bolest : zarazeni.keySet()){

                if(osoba.getZarazenBolescu().equals(bolest)){
                    zarazeni.get(bolest).add(osoba);
                }

            }

        }



        System.out.println("Popis osoba:");

        i = 0;

        for(Osoba osoba : osobe){
            ispisOsobe(osoba);
            logger.info(++i + ". osoba uspješno ispisana.");
        }

        ispisOsobaPoBolesti(zarazeni);

        SortedSet<Zupanija> sortiraneZupanije = new TreeSet<>(new CovidSorter());

        sortiraneZupanije.addAll(zupanije);

        System.out.println("Najviše zaraženih ima u županiji " + sortiraneZupanije.last().getNaziv() + ": " + sortiraneZupanije.last().getPostotak() + "%");


        List<Bolest> sviVirusi = bolesti.stream()
                                .filter(bolest -> bolest instanceof Virus)
                                .collect(Collectors.toList());

        List<Osoba> sveZarazeneOsobe = osobe.stream()
                                .filter(osoba -> osoba.getZarazenBolescu() instanceof Virus)
                                .collect(Collectors.toList());

        KlinikaZaInfektivneBolesti<Virus,Osoba> klinika = new KlinikaZaInfektivneBolesti(sviVirusi,sveZarazeneOsobe);

        System.out.println("Sortirani ispis svih virusa:");

        Instant start = Instant.now();
        klinika.getSviUneseniVirusi().stream()
                .sorted(new ReverseVirusSorter())
                .forEach(System.out::println);
        Instant end = Instant.now();

        System.out.println("Vrijeme izvršenja sorta pomocu lambdi: " + Duration.between(start,end).toMillis());


        start = Instant.now();
        klinika.getSviUneseniVirusi().sort(new ReverseVirusSorter());
        end = Instant.now();

        System.out.println("Vrijeme izvršenja sorta bez lambdi: " + Duration.between(start,end).toMillis());


        System.out.print("Unesite string za pretragu po prezimenu: ");

        String unosZaPretragu = tipkovnica.nextLine();
        pretragaOsobaPoPrezimenu(osobe, unosZaPretragu);

        bolesti.stream()
                .map(bolest -> bolest.getNaziv() + " ima " + bolest.getSimptomi().size() + " simptoma.")
                .forEach(System.out::println);


        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dat/serialazableZupanije.dat"))) {

            List<Zupanija> zaSerijalizaciju = zupanije.stream().filter(o -> o.getPostotak() > 2).collect(Collectors.toList());

            for(Zupanija zupanija : zaSerijalizaciju){
                out.writeObject(zupanija);
            }

        } catch (IOException ex) {
            System.err.println(ex);
            logger.error(ex.getMessage());
        }


    }

    /**
     * Metoda <code>pretragaOsobaPoPrezimenu</code> služi za pretragu unesenog stringa po prezimenima svih unesenih osoba.
     * @param osobe Lista koja sadrži sve unesene osobe.
     * @param unosZaPretragu Uneseni string koji se uspoređuje s prezimenima.
     */
    private static void pretragaOsobaPoPrezimenu(List<Osoba> osobe, String unosZaPretragu) {

        List<Osoba> rezultatPretrage = Optional.ofNullable(osobe
                                                            .stream()
                                                            .filter(osoba -> osoba.getPrezime().contains(unosZaPretragu))
                                                            .collect(Collectors.toList()))
                                                .stream()
                                                .findFirst()
                                                .orElse(null);

        if(!rezultatPretrage.isEmpty()){
            System.out.println("Popis osoba čije prezime sadrži " + unosZaPretragu + ":");
            rezultatPretrage.forEach(System.out::println);
        }else{
            System.out.println("Nije pronađena ni jedna osoba!");
        }
    }

    /**
     * Metoda <code>ispisOsobaPoBolesti</code> služi za ispis svih unesenih osoba grupiran po bolesti od koje boluju.
     * @param zarazeni Mapa koja sadrži unesene bolesti kao ključeve i listu osoba koje boluju od bolesti kao vrijednost.
     */
    private static void ispisOsobaPoBolesti(Map<Bolest, List<Osoba>> zarazeni) {

        for(Bolest bolest : zarazeni.keySet()){

                if(!zarazeni.get(bolest).isEmpty()){

                    System.out.print("Od ");

                    if(bolest instanceof Virus){
                        System.out.print("virusa ");
                    }else {
                        System.out.print("bolesti ");
                    }

                    System.out.print(bolest.getNaziv() + " boluju: ");

                    int i = 1;

                    for(Osoba osoba : zarazeni.get(bolest)){
                        System.out.print(osoba.getIme() + " " + osoba.getPrezime());

                        if(i != zarazeni.get(bolest).size()){
                            System.out.print(",");
                        }
                        i++;
                    }
                    System.out.println();
                }

        }
    }


    /**
     * Metoda <code>ispisOsobe</code> služi za ispis svih unesenih ososba.
     * @param osoba Objekt tipa <code>Osoba</code> koji je potrebno ispisati. .
     */
    private static void ispisOsobe(Osoba osoba) {
        System.out.println("Ime i prezime: " + osoba.getIme() + " " + osoba.getPrezime());
        System.out.println("Starost: " + osoba.getDatumRodjenja());
        System.out.println("Zupanija prebivalista: " + osoba.getZupanija().getNaziv());
        System.out.println("Zarazen bolescu: " + osoba.getZarazenBolescu().getNaziv());
        System.out.println("Kontaktirane osobe:");

        if(osoba.getKontaktiraneOsobe().isEmpty()){
            System.out.println("Nema kontaktiranih osoba.");
        }else{

            for(Osoba kontakt : osoba.getKontaktiraneOsobe()){

                System.out.println(kontakt.getIme() + " " + kontakt.getPrezime());

            }

        }
    }

    /**
     * Metoda <code>unosOsobe</code> služi za unos podataka jedne osobe.
     * @param scannerOsoba Objekt tipa <code>Scanner</code> koji služi za unos podataka s konzole.
     * @return Metoda vraća objekt tipa <code>Osoba</code>.
     */
    public static List<Osoba> unosOsoba(Scanner scannerOsoba, List<Zupanija> zupanije, List<Bolest> bolesti) {

        Long id;
        String ime,prezime;
        Date starost;
        Long idZupanije;
        Long idZarazenBolescu;
        Zupanija zupanijaPrebivalista = null;
        Bolest zarazenBolescu = null;
        String[] idKontakata;
        List<Osoba> procitaneOsobe = new ArrayList<>();

        while(scannerOsoba.hasNextLine()){

            List<Osoba> kontaktiraneOsobe = new ArrayList<>();

            id = Long.parseLong(scannerOsoba.nextLine());
            ime = scannerOsoba.nextLine();
            prezime = scannerOsoba.nextLine();
            //starost = scannerOsoba.nextLine();
            idZupanije = Long.parseLong(scannerOsoba.nextLine());
            idZarazenBolescu = Long.parseLong(scannerOsoba.nextLine());
            idKontakata = scannerOsoba.nextLine().split(",",0);

            for(int i = 0; i < idKontakata.length; i++){
                for(Osoba osoba : procitaneOsobe){

                    if(osoba.getId().equals(Long.parseLong(idKontakata[i]))){

                        kontaktiraneOsobe.add(osoba);

                    }

                }
            }

            for(Zupanija zupanija : zupanije){

                if(zupanija.getId().equals(idZupanije)){
                    zupanijaPrebivalista = zupanija;
                }

            }

            for(Bolest bolest : bolesti){

                if(bolest.getId().equals(idZarazenBolescu)){
                    zarazenBolescu = bolest;
                }

            }

            procitaneOsobe.add(new Osoba
                                    .UserBuilder(ime,prezime)
                                    .Id(id)
                                    .zupanija(zupanijaPrebivalista)
                                    .zarazenBolescu(zarazenBolescu)
                                    .kontaktiraneOsobe(kontaktiraneOsobe)
                                    .build());

        }

        logger.info("Uspješno unesene osobe iz datoteke.");

        return procitaneOsobe;

    }

    /**
     * Metoda <code>unosBolesti</code> služi za unos jedne bolesti.
     * @param scannerVirusa Objekt tipa <code>Scanner</code> koji služi sa unos podataka sa konzole.
     * @return vraća objekt tipa <code>Bolest</code>. U slučaju da je bolest zarazna vraća objekt tipa <code>Virus</code>.
     * @throws BolestIstihSimptoma Metoda baca iznimku <code>BolestIstihSiptoma</code> u slučaju da unesena boles ima indentično polje simptoma kao neka već unesena bolest.
     */
    public static List<Bolest> unosVirusa(Scanner scannerVirusa, List<Simptom> simptomi) {

        Long id;
        String naziv;
        String[] idSimptomaZaDodati;
        List<Bolest> procitaniVirusi = new LinkedList<>();

        while(scannerVirusa.hasNextLine()){

            Set<Simptom> simptomiVirusa = new LinkedHashSet<>();

            id = Long.parseLong(scannerVirusa.nextLine());
            naziv = scannerVirusa.nextLine();
            idSimptomaZaDodati = scannerVirusa.nextLine().split(",",0);

            for(int i = 0; i < idSimptomaZaDodati.length; i++){
                for(Simptom simptom : simptomi){

                    if(simptom.getId().equals(Long.parseLong(idSimptomaZaDodati[i]))){
                        simptomiVirusa.add(simptom);
                    }

                }
            }

            procitaniVirusi.add(new Virus(id,naziv,simptomiVirusa));


        }

        logger.info("Uspješno unesene Bolesti iz datoteke.");

        return procitaniVirusi;
    }

    /**
     * Metoda <code>unosBolesti</code> služi za unos jedne bolesti.
     * @param scannerBolesti Objekt tipa <code>Scanner</code> koji služi sa unos podataka sa konzole.
     * @return vraća objekt tipa <code>Bolest</code>. U slučaju da je bolest zarazna vraća objekt tipa <code>Virus</code>.
     * @throws BolestIstihSimptoma Metoda baca iznimku <code>BolestIstihSiptoma</code> u slučaju da unesena boles ima indentično polje simptoma kao neka već unesena bolest.
     */
    public static List<Bolest> unosBolesti(Scanner scannerBolesti, List<Simptom> simptomi) {

        Long id;
        String naziv;
        String[] idSimptomaZaDodati;
        List<Bolest> procitaneBolesti = new LinkedList<>();

        while(scannerBolesti.hasNextLine()){

            Set<Simptom> simptomiBolesti = new LinkedHashSet<>();

            id = Long.parseLong(scannerBolesti.nextLine());
            naziv = scannerBolesti.nextLine();
            idSimptomaZaDodati = scannerBolesti.nextLine().split(",",0);

            for(int i = 0; i < idSimptomaZaDodati.length; i++){
                for(Simptom simptom : simptomi){

                    if(simptom.getId().equals(Long.parseLong(idSimptomaZaDodati[i]))){
                        simptomiBolesti.add(simptom);
                    }

                }
            }

            procitaneBolesti.add(new Bolest(id,naziv));


        }

        logger.info("Uspješno unesene Bolesti iz datoteke.");

        return procitaneBolesti;
    }

    /**
     * Metoda <code>unosSimptoma</code> služi za čitanje podataka iz datoteke.
     * @param scannerSimptoma Objekt tipa <code>Scanner</code> koji služi za čitanje podataka iz tektualne datoteke.
     * @return Metoda vraća set tipa <code>Simptom</code> koji sadrži sve pročitane simptome.
     */
    public static List<Simptom> unosSimptoma(Scanner scannerSimptoma) {

        Long id;
        String naziv;
        VrijednostSimptoma vrijednost;
        List<Simptom> procitaniSimptomi = new LinkedList<>();

        while(scannerSimptoma.hasNextLine()){

            id = Long.parseLong(scannerSimptoma.nextLine());
            naziv = scannerSimptoma.nextLine();

            switch (scannerSimptoma.nextLine()){
                case "RIJETKO":
                    vrijednost = VrijednostSimptoma.Produktivni;
                    break;
                case "SREDNJE":
                    vrijednost = VrijednostSimptoma.Intenzivno;
                    break;
                case "ČESTO":
                    vrijednost = VrijednostSimptoma.Visoka;
                    break;
                default:
                    vrijednost = VrijednostSimptoma.VRIJEDNOST_NIJE_DEFINIRANA;
            }

            procitaniSimptomi.add(new Simptom(id,naziv,vrijednost));
        }

        logger.info("Uspješno uneseni simptomi iz datoteke.");

        return procitaniSimptomi;
    }

    /**
     * Metoda <code>unosZupanija</code> služi za čitanje svih županija iz datoteke.
     * @param scannerZupanija Objekt tipa <code>Scanner</code> koji služi za citanje podataka iz tekstualne datoteke.
     * @return Metoda vraća set tipa <code>Zupanija</code> koji sadrži sve pročitane županije.
     */
    public static List<Zupanija> unosZupanija(Scanner scannerZupanija) {

        int i = 1;
        Long id;
        Integer brojStanovnika, brojZarazenih;
        String naziv;
        List<Zupanija> procitaneZupanije = new LinkedList<>();

            while(scannerZupanija.hasNextLine()) {

                id = Long.parseLong(scannerZupanija.nextLine());
                naziv = scannerZupanija.nextLine();
                brojStanovnika = Integer.parseInt(scannerZupanija.nextLine());
                brojZarazenih = Integer.parseInt(scannerZupanija.nextLine());

                procitaneZupanije.add(new Zupanija(id, naziv, brojStanovnika, brojZarazenih));

            }

            logger.info("Uspješno unesene županije iz datoteke");

            return procitaneZupanije;
    }
}