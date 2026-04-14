package com.pavledimitrijevic.prodavnicaracunara;

import java.io.Serializable;

/**
 *
 * @author PAVLE
 */
public class Garancija implements Serializable {

    private Long garancijaID;
    private int trajanjeMeseci;
    private String opis;

    public Garancija() {
    }

    public Garancija(Long garancijaID, int trajanjeMeseci, String opis) {
        setGarancijaID(garancijaID);
        setTrajanjeMeseci(trajanjeMeseci);
        setOpis(opis);
    }

    public Long getGarancijaID() {
        return garancijaID;
    }

    public void setGarancijaID(Long garancijaID) {
        if (garancijaID == null) {
            throw new NullPointerException("ID garancije ne sme biti null.");
        }

        if (garancijaID <= 0) {
            throw new IllegalArgumentException("ID garancije mora biti pozitivan broj.");
        }

        this.garancijaID = garancijaID;
    }

    public int getTrajanjeMeseci() {
        return trajanjeMeseci;
    }

    public void setTrajanjeMeseci(int trajanjeMeseci) {
        if (trajanjeMeseci <= 0) {
            throw new IllegalArgumentException("Trajanje garancije mora biti vece od nule.");
        }

        this.trajanjeMeseci = trajanjeMeseci;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        if (opis == null) {
            throw new NullPointerException("Opis garancije ne sme biti null.");
        }

        if (opis.trim().isEmpty()) {
            throw new IllegalArgumentException("Opis garancije ne sme biti prazan.");
        }

        this.opis = opis;
    }

}
