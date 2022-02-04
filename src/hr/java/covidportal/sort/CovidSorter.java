package hr.java.covidportal.sort;

import hr.java.covidportal.model.Zupanija;

import java.util.Comparator;

/**
 * Klasa <code>CovidSorter</code> služi za definiranje parametaro po kojima će se sortirati županije.
 */
public class CovidSorter implements Comparator<Zupanija> {

    @Override
    public int compare(Zupanija o1, Zupanija o2) {

        return o1.getPostotak().compareTo(o2.getPostotak());

    }
}
