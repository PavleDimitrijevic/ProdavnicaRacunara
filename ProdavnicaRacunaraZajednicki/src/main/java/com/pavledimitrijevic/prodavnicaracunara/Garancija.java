package com.pavledimitrijevic.prodavnicaracunara;

import java.io.Serializable;

/**
 * Predstavlja garanciju koja se vezuje za racunar.
 * <p>
 * Svaka garancija ima jedinstveni ID, trajanje u mesecima i opis.
 * </p>
 *
 * @author PAVLE
 */
public class Garancija implements Serializable {

    /**
     * ID garancije kao Long
     */
    private Long garancijaID;

    /**
     * Trajanje garancije u mesecima kao int
     */
    private int trajanjeMeseci;

    /**
     * Opis garancije kao String
     */
    private String opis;

    /**
     * Inicijalizuje objekat klase Garancija sa atributima koji imaju default
     * vrednosti.
     */
    public Garancija() {
    }

    /**
     * Inicijalizuje objekat klase Garancija sa parametrima.
     *
     * @param garancijaID Jedinstveni ID garancije. Mora biti pozitivan broj i
     * ne sme biti null.
     * @param trajanjeMeseci Trajanje garancije u mesecima. Mora biti vece od
     * nule.
     * @param opis Opis garancije. Ne sme biti null niti prazan string.
     */
    public Garancija(Long garancijaID, int trajanjeMeseci, String opis) {
        setGarancijaID(garancijaID);
        setTrajanjeMeseci(trajanjeMeseci);
        setOpis(opis);
    }

    /**
     * Vraca ID garancije.
     *
     * @return ID garancije
     */
    public Long getGarancijaID() {
        return garancijaID;
    }

    /**
     * Postavlja ID garancije. ID mora biti pozitivan broj i ne sme biti null.
     *
     * @param garancijaID ID garancije
     * @throws java.lang.NullPointerException ako je ID null
     * @throws java.lang.IllegalArgumentException ako je ID manji ili jednak
     * nuli
     */
    public void setGarancijaID(Long garancijaID) {
        if (garancijaID == null) {
            throw new NullPointerException("ID garancije ne sme biti null.");
        }

        if (garancijaID <= 0) {
            throw new IllegalArgumentException("ID garancije mora biti pozitivan broj.");
        }

        this.garancijaID = garancijaID;
    }

    /**
     * Vraca trajanje garancije u mesecima.
     *
     * @return trajanje garancije
     */
    public int getTrajanjeMeseci() {
        return trajanjeMeseci;
    }

    /**
     * Postavlja trajanje garancije. Mora biti vece od nule.
     *
     * @param trajanjeMeseci trajanje garancije
     * @throws java.lang.IllegalArgumentException ako je trajanje manje ili
     * jednako nuli
     */
    public void setTrajanjeMeseci(int trajanjeMeseci) {
        if (trajanjeMeseci <= 0) {
            throw new IllegalArgumentException("Trajanje garancije mora biti vece od nule.");
        }

        this.trajanjeMeseci = trajanjeMeseci;
    }

    /**
     * Vraca opis garancije.
     *
     * @return opis garancije
     */
    public String getOpis() {
        return opis;
    }

    /**
     * Postavlja opis garancije. Ne sme biti null niti prazan string.
     *
     * @param opis opis garancije
     * @throws java.lang.NullPointerException ako je opis null
     * @throws java.lang.IllegalArgumentException ako je opis prazan string
     */
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
