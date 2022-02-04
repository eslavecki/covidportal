package hr.java.covidportal.model;

/**
 * Sučelje <code>Zarazno</code> služi za deklaraciju metoda koje koriste zarazne bolesti.
 */
public interface Zarazno {
    /**
     * Deklaracija metode <code>prelazakZarazeNaOsobu</code>.
     * @param osoba Objekt tipa <code>Osoba</code> koji je potrebno zaraziti.
     */
    void prelazakZarazeNaOsobu(Osoba osoba);
}
