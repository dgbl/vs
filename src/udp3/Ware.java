/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp3;


/**
 *
 * @author DNS
 */
public class Ware {

    private String bezeichnung;
    private int anzahl;
    private double abnahme;

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
        int anzahlNeu = (int) (anzahl - getAbnahme());
        setAnzahl(anzahlNeu);
    }

    public void zugabe(int zugabe) {
        int anzahlNeu = anzahl + zugabe;
        setAnzahl(anzahlNeu);
    }

    /**
     * @return the id
     */
    public double getAbnahme() {
        return abnahme;
    }

    /**
     * @param id the id to set
     */
    public void setAbnahme(double abnahme) {
        this.abnahme = abnahme;
    }
}
