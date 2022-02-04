package hr.java.covidportal.iznimke;

/**
 * Klasa <code>DuplikatKontaktiraneOsobe</code> služi za konstrukciju iznimke.
 */
public class DuplikatKontaktiraneOsobe extends Exception{

    /**
     * Konstruktor koji služi za predaju argumenta poruka super klasi <code>Exception</code>.
     * @param poruka String koji se ispisuje u slucaju iznimke.
     */
    public DuplikatKontaktiraneOsobe(String poruka) {
        super(poruka);
    }

    /**
     * Konstruktor koji služi za predaju argumenta uzrok super klasi <code>Exception</code>.
     * @param uzrok Iznimka koja uzrokuje iznimku <code>DuplikatKontaktiraneOsobe</code>.
     */
    public DuplikatKontaktiraneOsobe(Throwable uzrok) {
        super(uzrok);
    }

    /**
     * Konstruktor koji služi za predaju argumenata poruka i uzrok super klasi <code>Exception</code>.
     * @param poruka String koji se ispisuje u slucaju iznimke.
     * @param uzrok Iznimka koja uzrokuje iznimku <code>DuplikatKontaktiraneOsobe</code>.
     */
    public DuplikatKontaktiraneOsobe(String poruka, Throwable uzrok) {
        super(poruka,uzrok);
    }

}
