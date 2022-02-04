package hr.java.covidportal.model;

/**
 * Klasa <code>Zupanija</code> služi za definiranje instance objekta tipa <code>Zupanija</code>.
 */
public class Zupanija extends ImenovaniEntitet{

    private Integer brojStanovnika, brojZarazenihStanovnika;

    /**
     * Konstruktor koji služi za konstrukciju instance objekta tipa <code>Zupanija</code>.
     * @param naziv String koji sadrži naziv županije.
     * @param brojStanovnika Integer koji sadrži broj stanovnika županije.
     * @param id
     */
    public Zupanija(Long id, String naziv, Integer brojStanovnika, Integer brojZarazenih) {
        super(naziv, id);
        this.brojStanovnika = brojStanovnika;
        this.brojZarazenihStanovnika = brojZarazenih;
    }

    public Integer getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(Integer brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
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
        return this.getNaziv();
    }

    public Integer getBrojZarazenih() {
        return brojZarazenihStanovnika;
    }

    public void setBrojZarazenih(Integer brojZarazenih) {
        this.brojZarazenihStanovnika = brojZarazenih;
    }

    public Integer getPostotak(){
        return (int) (((float)100/(float)this.brojStanovnika) * (float)this.brojZarazenihStanovnika);
    }
}
