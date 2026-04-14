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
        this.proizvodjacID = proizvodjacID;
        this.naziv = naziv;
    }

    public Long getProizvodjacID() {
        return proizvodjacID;
    }

    public void setProizvodjacID(Long proizvodjacID) {
        this.proizvodjacID = proizvodjacID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

}
