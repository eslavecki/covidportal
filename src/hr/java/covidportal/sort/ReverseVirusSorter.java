package hr.java.covidportal.sort;

import hr.java.covidportal.model.Virus;

import java.util.Comparator;

public class ReverseVirusSorter<T extends Virus> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        return Character.compare(o2.getNaziv().charAt(0),o1.getNaziv().charAt(0));
    }

}
