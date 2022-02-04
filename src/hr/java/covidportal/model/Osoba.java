package hr.java.covidportal.model;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Locale;

/**
 * Klasa <code>Osoba</code> služi za definiciju instance objekta tipa <code>Osoba</code>.
 */
public class Osoba implements Serializable {
    private Long id;
    private String ime,prezime;
    private LocalDate datumRodjenja;
    private Integer starost;
    private Zupanija zupanija;
    private Bolest zarazenBolescu;
    private List<Osoba> kontaktiraneOsobe;

    /**
     * Konstruktor koji služi za konstrukciju instance objekta tipa <code>Osoba</code>.
     * @param builder Objekt tipa <code>UserBuilder</code> koji služi za predaju argumenata osobe iz klase <code>UserBuilder</code>.
     */
    public Osoba(UserBuilder builder){
        this.id = builder.id;
        this.ime = builder.ime;
        this.prezime = builder.prezime;
        this.datumRodjenja = builder.datumRodjenja;
        this.zupanija = builder.zupanija;
        this.zarazenBolescu = builder.zarazenBolescu;
        this.kontaktiraneOsobe = builder.kontaktiraneOsobe;

        if(this.zarazenBolescu instanceof Virus && this.kontaktiraneOsobe != null){

                for(Osoba kontakt : this.kontaktiraneOsobe){

                    ((Virus) this.zarazenBolescu).prelazakZarazeNaOsobu(kontakt);

                }

        }

        if ((datumRodjenja != null)) {
            starost =  Period.between(datumRodjenja, LocalDate.now()).getYears();
        }
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) { this.ime = ime; }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public Zupanija getZupanija() {
        return zupanija;
    }

    public void setZupanija(Zupanija zupanija) {
        this.zupanija = zupanija;
    }

    public Bolest getZarazenBolescu() {
        return zarazenBolescu;
    }

    public void setZarazenBolescu(Bolest zarazenBolescu) {
        this.zarazenBolescu = zarazenBolescu;
    }

    public List<Osoba> getKontaktiraneOsobe() {
        return kontaktiraneOsobe;
    }

    public void setKontaktiraneOsobe(List<Osoba> kontaktiraneOsobe) {
        this.kontaktiraneOsobe = kontaktiraneOsobe;
    }

    public Integer getStarost() {
        return starost;
    }

    public void setStarost(Integer starost) {
        this.starost = starost;
    }

    public void addKontakt(Osoba kontakt){
        kontaktiraneOsobe.add(kontakt);
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return this.getIme() + " " + this.getPrezime();
    }

    /**
     * Klasa <code>UserBuilder</code> služi za postepenu konstrukciju instance objekta tipa <code>Osoba</code>.
     */
    public static class UserBuilder
    {
        private Long id;
        private String ime,prezime;
        private LocalDate datumRodjenja;
        private Zupanija zupanija;
        private Bolest zarazenBolescu;
        private List<Osoba> kontaktiraneOsobe;

        /**
         * Konstruktor koji prima i sprema argumente ime i prezime tipa <code>String</code>.
         * @param ime String koji sadrži ime osobe.
         * @param prezime String koji sadrži prezime osobe.
         */
        public UserBuilder(String ime, String prezime) {
            this.ime = ime;
            this.prezime = prezime;
        }

        public UserBuilder Id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * Konstruktor koji prima i sprema argument starost tipa <code>Integer</code>.
         * @param DOB Integer koji sadrži starost osobe.
         * @return Konstruktor vraća objekt tipa <code>UserBuilder</code>.
         */
        public UserBuilder datumRodjenja(LocalDate DOB) {
            this.datumRodjenja = DOB;
            return this;
        }

        /**
         * Konstruktor koji prima i sprema argument zupanija tipa <code>Zupanija</code>.
         * @param zupanija Objekt tipa <code>Zupanija</code> koji sadrži županiju prebivališsta osobe.
         * @return Konstruktor vraća objekt tipa <code>UserBuilder</code>.
         */
        public UserBuilder zupanija(Zupanija zupanija) {
            this.zupanija = zupanija;
            return this;
        }

        /**
         * Konstruktor koji prima i sprema argument zarazenBolescu tipa <code>Bolest</code>.
         * @param zarazenBolescu Objekt tipa <code>Bolest</code> koji sadrži bolest kojom je osoba zaražena.
         * @return Konstruktor vraća objekt tipa <code>UserBuilder</code>.
         */
        public UserBuilder zarazenBolescu(Bolest zarazenBolescu) {
            this.zarazenBolescu = zarazenBolescu;
            return this;
        }

        /**
         * Konstruktor koji prima i sprema kao argument polje kontaktiraneOsobe tipa <code>Osoba</code>.
         * @param kontaktiraneOsobe Polje tipa <code>Osoba</code> koje sadrži kontakte osobe.
         * @return Konstruktor vraća objekt tipa <code>UserBuilder</code>.
         */
        public UserBuilder kontaktiraneOsobe(List<Osoba> kontaktiraneOsobe) {
            this.kontaktiraneOsobe = kontaktiraneOsobe;
            return this;
        }

        /**
         * Metoda <code>build</code> služi za konstrukciju instance objekta <code>Osoba</code> predajom objekta tipa <code>UserBuilder</code>.
         * @return Konstruktor vraća objekt tipa <code>Osoba</code>.
         */
        public Osoba build() {
            Osoba osoba =  new Osoba(this);
            return osoba;
        }

    }
}
