package udp;

/**
 *
 * @author DNS
 */
public class Ware {

    private String bezeichnung;
    private int anzahl;
    private int abnahme;

    /**
     * @return the bezeichnung
     */
    public String getBezeichnung() {
        return bezeichnung;
    }

    /**
     * @param bezeichnung the bezeichnung to set
     */
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    /**
     * @return the anzahl
     */
    public int getAnzahl() {
        return anzahl;
    }

    /**
     * @param anzahl the anzahl to set
     */
    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public void entnahme() {
        int anzahlNeu = getAnzahl() - getAbnahme();
        setAnzahl(anzahlNeu);
    }

    public void zugabe(int zugabe) {
        int anzahlNeu = anzahl + zugabe;
        setAnzahl(anzahlNeu);
    }

    public int getAbnahme() {
        return abnahme;
    }

    public void setAbnahme(int abnahme) {
        this.abnahme = abnahme;
    }
}
