package hr.java.covidportal.model;

import java.util.Set;

/**
 * Klasa <code>Virus</code> služi za definiranje instance objekta tipa <code>Virus</code>.
 */
public class Virus extends Bolest implements Zarazno{

    /**
     * Konstruktor koji služi za predaju argumenata naziv i simptomi super klasi <code>Bolest</code>.
     * @param naziv String koji sadrži naziv virusa.
     * @param simptomi Polje tipa <code>Simptom</code> koje sadrži simptome virusa.
     * @param id
     */
    public Virus(Long id, String naziv, Set<Simptom> simptomi) {
        super(id, naziv, simptomi);
    }

    @Override
    public String toString() {
        return super.getNaziv();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * Metoda <code>prelazakZarazeNaOsobu</code> služi za zarazu predane osobe trenutnim virusom.
     * @param osoba Objekt tipa <code>Osoba</code>.
     */
    public void prelazakZarazeNaOsobu(Osoba osoba) {
        osoba.setZarazenBolescu(this);
    }
}
