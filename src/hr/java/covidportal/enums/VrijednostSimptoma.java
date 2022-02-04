package hr.java.covidportal.enums;

/**
 * Enumeracija <code>VrijednostSimptoma</code> služi za definiranje konstantnih vrijednosti simptoma.
 */
    public enum VrijednostSimptoma {

        VRIJEDNOST_NIJE_DEFINIRANA(""),
        Produktivni("Produktivni"),
        Intenzivno("Intenzivno"),
        Visoka("Visoka"),
        Jaka("Jaka");

       private String Vrijednost;

        VrijednostSimptoma(String vrijednost) {
            Vrijednost = vrijednost;
        }

        public String getVrijednost() {
            return Vrijednost;
        }

        public void setVrijednost(String vrijednost) {
            Vrijednost = vrijednost;
        }
    }

