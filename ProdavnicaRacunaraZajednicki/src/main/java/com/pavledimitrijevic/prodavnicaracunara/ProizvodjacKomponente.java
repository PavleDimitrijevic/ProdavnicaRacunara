package com.pavledimitrijevic.prodavnicaracunara;

import java.io.Serializable;

/**
 *
 * @author PAVLE
 */
public class ProizvodjacKomponente implements Serializable {

    private Long proizvodjacID;
    private String naziv;

    public ProizvodjacKomponente() {
    }

    public ProizvodjacKomponente(Long proizvodjacID, String naziv) {
        setProizvodjacID(proizvodjacID);
        setNaziv(naziv);
    }

    public Long getProizvodjacID() {
        return proizvodjacID;
    }

    public void setProizvodjacID(Long proizvodjacID) {
        if (proizvodjacID == null) {
            throw new NullPointerException("ID proizvodjaca ne sme biti null.");
        }

        if (proizvodjacID <= 0) {
            throw new IllegalArgumentException("ID proizvodjaca mora biti pozitivan broj.");
        }

        this.proizvodjacID = proizvodjacID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        if (naziv == null) {
            throw new NullPointerException("Naziv proizvodjaca ne sme biti null.");
        }

        if (naziv.trim().isEmpty()) {
            throw new IllegalArgumentException("Naziv proizvodjaca ne sme biti prazan.");
        }

        this.naziv = naziv;
    }
}
