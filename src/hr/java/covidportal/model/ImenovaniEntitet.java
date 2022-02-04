package hr.java.covidportal.model;

import java.io.Serializable;

/**
 * Abstraktna klasa koja služi za definiranje imenovanog entiteta.
 */
public abstract class ImenovaniEntitet implements Serializable {

    private String naziv;
    private Long id;

    /**
     * Konstruktor koji služi za primanje i pohranjivanje stringa naziv iz konstruktora subklase.
     * @param naziv String koji sadrži naziv entiteta.
     * @param id Integer koji sadrži indentifikacijski broj imenovanog entiteta.
     */
    public ImenovaniEntitet(String naziv, Long id) {
        this.naziv = naziv;
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }
}
