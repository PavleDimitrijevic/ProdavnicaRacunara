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
        this.garancijaID = garancijaID;
        this.trajanjeMeseci = trajanjeMeseci;
        this.opis = opis;
    }

    public Long getGarancijaID() {
        return garancijaID;
    }

    public void setGarancijaID(Long garancijaID) {
        this.garancijaID = garancijaID;
    }

    public int getTrajanjeMeseci() {
        return trajanjeMeseci;
    }

    public void setTrajanjeMeseci(int trajanjeMeseci) {
        this.trajanjeMeseci = trajanjeMeseci;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

}
