package com.pavledimitrijevic.prodavnicaracunara;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Predstavlja tip racunara u prodavnici racunara. Tip racunara se identifikuje
 * pomocu ID-a i naziva.
 *
 * @author PAVLE
 */
public class TipRacunara extends AbstractDomainObject {

    /**
     * ID tipa racunara kao Long
     */
    private Long tipRacunaraID;

    /**
     * Naziv racunara kao String
     */
    private String naziv;

    /**
     * Vraca tekstualni prikaz tipa racunara.
     *
     * @return naziv tipa racunara
     */
    @Override
    public String toString() {
        return naziv;
    }

    /**
     * Inicijalizuje objekat klase TipRacunara sa atributima koji imaju default
     * vrednosti.
     */
    public TipRacunara() {
    }

    /**
     * Inicijalizuje objekat klase TipRacunara sa svim parametrima.
     *
     * @param tipRacunaraID Jedinstveni ID tipa racunara. Ne sme biti null i
     * mora biti veci od nule.
     * @param naziv Naziv tipa racunara. Ne sme biti null niti prazan string.
     */
    public TipRacunara(Long tipRacunaraID, String naziv) {
        setTipRacunaraID(tipRacunaraID);
        setNaziv(naziv);
    }

    /**
     * Vraca naziv tipa racunara.
     *
     * @return naziv tipa racunara
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja naziv tipa racunara. Naziv tipa racunara ne sme biti null niti
     * prazan string.
     *
     * @param naziv Naziv tipa racunara
     * @throws java.lang.NullPointerException ako je naziv null
     * @throws java.lang.IllegalArgumentException ako je naziv prazan string
     */
    public void setNaziv(String naziv) {
        if (naziv == null) {
            throw new NullPointerException("Naziv ne sme biti null.");
        }

        if (naziv.isEmpty()) {
            throw new IllegalArgumentException("Naziv ne sme biti prazan.");
        }
        this.naziv = naziv;
    }

    /**
     * Vraca jedinstveni ID tipa racunara.
     *
     * @return ID tipa racunara
     */
    public Long getTipRacunaraID() {
        return tipRacunaraID;
    }

    /**
     * Postavlja jedinstveni ID tipa racunara. ID tipa racunara ne sme biti null
     * i mora biti veci od nule.
     *
     * @param tipRacunaraID ID tipa racunara
     * @throws java.lang.NullPointerException ako je tipRacunaraID null
     * @throws java.lang.IllegalArgumentException ako je tipRacunaraID manji ili
     * jednak nuli
     */
    public void setTipRacunaraID(Long tipRacunaraID) {
        if (tipRacunaraID == null) {
            throw new NullPointerException("ID ne sme biti null.");
        }

        if (tipRacunaraID <= 0) {
            throw new IllegalArgumentException("ID mora biti pozitivan broj.");
        }
        this.tipRacunaraID = tipRacunaraID;
    }

    @Override
    public String nazivTabele() {
        return " TipRacunara ";
    }

    @Override
    public String alijas() {
        return " tr ";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {
            TipRacunara tr = new TipRacunara(rs.getLong("TipRacunaraID"),
                    rs.getString("Naziv"));

            lista.add(tr);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (Naziv) ";
    }

    @Override
    public String uslov() {
        return " TipRacunaraID = " + tipRacunaraID;
    }

    @Override
    public String vrednostiZaInsert() {
        return " '" + naziv + "' ";
    }

    @Override
    public String vrednostiZaUpdate() {
        return " Naziv = '" + naziv + "' ";
    }

    @Override
    public String uslovZaSelect() {
        return "";
    }

}
