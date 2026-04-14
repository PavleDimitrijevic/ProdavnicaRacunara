package com.pavledimitrijevic.prodavnicaracunara;

import java.io.Serializable;

/**
 * Predstavlja proizvodjaca komponente racunara.
 * <p>
 * Svaki proizvodjac ima jedinstveni ID i naziv.
 * </p>
 *
 * @author PAVLE
 */
public class ProizvodjacKomponente implements Serializable {

    /**
     * ID proizvodjaca kao Long
     */
    private Long proizvodjacID;

    /**
     * Naziv proizvodjaca kao String
     */
    private String naziv;

    /**
     * Inicijalizuje objekat klase ProizvodjacKomponente sa atributima koji
     * imaju default vrednosti.
     */
    public ProizvodjacKomponente() {
    }

    /**
     * Inicijalizuje objekat klase ProizvodjacKomponente sa parametrima.
     *
     * @param proizvodjacID Jedinstveni ID proizvodjaca. Mora biti pozitivan
     * broj i ne sme biti null.
     * @param naziv Naziv proizvodjaca. Ne sme biti null niti prazan string.
     */
    public ProizvodjacKomponente(Long proizvodjacID, String naziv) {
        setProizvodjacID(proizvodjacID);
        setNaziv(naziv);
    }

    /**
     * Vraca ID proizvodjaca.
     *
     * @return ID proizvodjaca
     */
    public Long getProizvodjacID() {
        return proizvodjacID;
    }

    /**
     * Postavlja ID proizvodjaca. Mora biti pozitivan broj i ne sme biti null.
     *
     * @param proizvodjacID ID proizvodjaca
     * @throws java.lang.NullPointerException ako je ID null
     * @throws java.lang.IllegalArgumentException ako je ID manji ili jednak
     * nuli
     */
    public void setProizvodjacID(Long proizvodjacID) {
        if (proizvodjacID == null) {
            throw new NullPointerException("ID proizvodjaca ne sme biti null.");
        }

        if (proizvodjacID <= 0) {
            throw new IllegalArgumentException("ID proizvodjaca mora biti pozitivan broj.");
        }

        this.proizvodjacID = proizvodjacID;
    }

    /**
     * Vraca naziv proizvodjaca.
     *
     * @return naziv proizvodjaca
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja naziv proizvodjaca. Ne sme biti null niti prazan string.
     *
     * @param naziv naziv proizvodjaca
     * @throws java.lang.NullPointerException ako je naziv null
     * @throws java.lang.IllegalArgumentException ako je naziv prazan string
     */
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
