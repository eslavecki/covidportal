package hr.java.covidportal.model;

import java.util.Set;

/**
 * Klasa <code>Bolest</code> služi za definiranje objekta tipa <code>Bolest</code>.
 */
public class Bolest extends ImenovaniEntitet{

    private Set<Simptom> simptomi;

    /**
     * Konstruktor koji služi za konstrukciju instance objekta tipa <code>Bolest</code>.
     * @param naziv String koji sadrži naziv instance objekta tipa <code>Bolest</code>.
     * @param simptomi Polje tipa <code>Simptom</code> koje sadrži simptome instance objekta tipa <code>Bolest</code>.
     * @param id
     */
    public Bolest(Long id, String naziv, Set<Simptom> simptomi) {
        super(naziv, id);
        this.simptomi = simptomi;
    }

    public Bolest(Long id, String naziv) {
        super(naziv, id);
    }

    public Set<Simptom> getSimptomi() {
        return simptomi;
    }

    public void setSimptomi(Set<Simptom> simptomi) {
        this.simptomi = simptomi;
    }

    public void addSimptom(Simptom simptom){
        this.simptomi.add(simptom);
    }

    public Long getId() {return super.getId();}

    @Override
    public String toString() {
        return this.getNaziv();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
