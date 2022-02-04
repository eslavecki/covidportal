package hr.java.covidportal.generics;

import hr.java.covidportal.model.Osoba;
import hr.java.covidportal.model.Virus;

import java.util.LinkedList;
import java.util.List;

/**
 * Generička klasa <code>KlinikaZaInfektivneBolesti</code> služi za inicijalizaciju klinike.
 * @param <T> Generički parametar <code>T</code> prestavlja bilo koji objekt tipa <code>Virus</code>
 *           ili tipa koji nasljeđuje <code>Virus</code>.
 * @param <S> Generički parametar <code>S</code> prestavlja bilo koji objekt tipa <code>Osoba</code>
 *  *        ili tipa koji nasljeđuje klasu <code>Osoba</code>.
 */
public class KlinikaZaInfektivneBolesti <T extends Virus,S extends Osoba> {

    List<T> sviUneseniVirusi = new LinkedList<>();
    List<S> sveOsobeZarazeneVirusima = new LinkedList<>();

    public KlinikaZaInfektivneBolesti(List<T> sviUneseniVirusi, List<S> sveOsobeZarazeneVirusima) {
        this.sviUneseniVirusi = sviUneseniVirusi;
        this.sveOsobeZarazeneVirusima = sveOsobeZarazeneVirusima;
    }

    public List<T> getSviUneseniVirusi() {
        return sviUneseniVirusi;
    }

    public void setSviUneseniVirusi(List<T> sviUneseniVirusi) {
        this.sviUneseniVirusi = sviUneseniVirusi;
    }

    public void addVirusAtIndex(T virus, Integer index) {
        this.sviUneseniVirusi.add(index, virus);
    }

    public void addVirus(T virus) {
        this.sviUneseniVirusi.add(virus);
    }

    public List<S> getSveOsobeZarazeneVirusima() {
        return sveOsobeZarazeneVirusima;
    }

    public void setSveOsobeZarazeneVirusima(List<S> sveOsobeZarazeneVirusima) {
        this.sveOsobeZarazeneVirusima = sveOsobeZarazeneVirusima;
    }

    public void addOsobaAtIndex(S osoba, Integer index) {
        this.sveOsobeZarazeneVirusima.add(index, osoba);
    }

    public void addOsoba(S osoba) {
        this.sveOsobeZarazeneVirusima.add(osoba);
    }
}
