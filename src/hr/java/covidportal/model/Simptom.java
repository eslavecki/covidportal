package hr.java.covidportal.model;

import hr.java.covidportal.enums.VrijednostSimptoma;

/**
 * Klasa <code>Simptom</code> služi za definiranje instance objekta tipa <code>Simptom</code>.
 */
public class Simptom extends ImenovaniEntitet{

    private VrijednostSimptoma vrijednost;

    /**
     * Kopnstruktor koji služi konstrukciju instance objekta tipa <code>Simptom</code>.
     * @param naziv String koji sadrži naziv simptoma.
     * @param vrijednost String koji sadrži vrijednost simptoma.
     * @param id
     */
    public Simptom(Long id, String naziv, VrijednostSimptoma vrijednost) {
        super(naziv, id);
        this.vrijednost = vrijednost;
    }

    public VrijednostSimptoma getVrijednost() {
        return vrijednost;
    }

    public void setVrijednost(VrijednostSimptoma vrijednost) {
        this.vrijednost = vrijednost;
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
        return this.getNaziv() + " " + "[" + this.getVrijednost() + "]";
    }
}
