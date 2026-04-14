package com.pavledimitrijevic.prodavnicaracunara;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author PAVLE
 */
public class TipRacunara extends AbstractDomainObject {

    private Long tipRacunaraID;
    private String naziv;

    @Override
    public String toString() {
        return naziv;
    }

    public TipRacunara() {
    }

    public TipRacunara(Long tipRacunaraID, String naziv) {
        this.tipRacunaraID = tipRacunaraID;
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Long getTipRacunaraID() {
        return tipRacunaraID;
    }

    public void setTipRacunaraID(Long tipRacunaraID) {
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
