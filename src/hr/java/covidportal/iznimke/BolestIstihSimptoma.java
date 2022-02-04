package hr.java.covidportal.iznimke;

/**
 * Klasa <code>BolestIstihSimptoma</code> služi za konstrukciju iznimke.
 */
public class BolestIstihSimptoma extends RuntimeException{

    /**
     * Konstruktor koji služi za predaju argumenta poruka super klasi <code>RuntimeExeption</code>.
     * @param poruka String koji se ispisuje u slucaju iznimke.
     */
    public BolestIstihSimptoma(String poruka) {
        super(poruka);
    }

    /**
     * Konstruktor koji služi za predaju argumenata poruka i uzrok super klasi <code>RuntimeExeption</code>.
     * @param poruka String koji se ispisuje u slucaju iznimke.
     * @param uzrok Iznimka koja uzrokuje iznimku <code>BolestIstihSimptoma</code>.
     */
    public BolestIstihSimptoma(String poruka, Throwable uzrok) {
        super(poruka, uzrok);
    }

    /**
     * Konstruktor koji služi za predaju argumenta uzrok super klasi <code>RuntimeExeption</code>.
     * @param uzrok Iznimka koja uzrokuje iznimku <code>BolestIstihSimptoma</code>.
     */
    public BolestIstihSimptoma(Throwable uzrok) {
        super(uzrok);
    }
}
