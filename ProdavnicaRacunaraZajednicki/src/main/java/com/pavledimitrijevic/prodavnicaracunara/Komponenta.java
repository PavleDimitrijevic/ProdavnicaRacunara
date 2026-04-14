package com.pavledimitrijevic.prodavnicaracunara;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author PAVLE
 */
public class Komponenta extends AbstractDomainObject {

    private Racunar racunar;
    private int rb;
    private String naziv;

    public Komponenta() {
    }

    public Komponenta(Racunar racunar, int rb, String naziv) {
        this.racunar = racunar;
        this.rb = rb;
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Racunar getRacunar() {
        return racunar;
    }

    public void setRacunar(Racunar racunar) {
        this.racunar = racunar;
    }

    public int getRb() {
        return rb;
    }

    public void setRb(int rb) {
        this.rb = rb;
    }

    @Override
    public String nazivTabele() {
        return " Komponenta ";
    }

    @Override
    public String alijas() {
        return " k ";
    }

    @Override
    public String join() {
        return " JOIN RACUNAR R ON (R.RACUNARID = K.RACUNARID) "
                + "JOIN TIPRACUNARA TR ON (TR.TIPRACUNARAID = R.TIPRACUNARAID)";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {
            TipRacunara tr = new TipRacunara(rs.getLong("TipRacunaraID"),
                    rs.getString("tr.Naziv"));

            Racunar r = new Racunar(rs.getLong("racunarID"), rs.getString("r.naziv"),
                    rs.getDouble("cenaPoKomadu"), rs.getString("opis"), tr, null);

            Komponenta k = new Komponenta(r, rs.getInt("rb"), rs.getString("k.naziv"));

            lista.add(k);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (racunarID, rb, naziv) ";
    }

    @Override
    public String uslov() {
        return " racunarID = " + racunar.getRacunarID();
    }

    @Override
    public String vrednostiZaInsert() {
        return " " + racunar.getRacunarID() + ", " + rb + ", "
                + "'" + naziv + "' ";
    }

    @Override
    public String vrednostiZaUpdate() {
        return "";
    }

    @Override
    public String uslovZaSelect() {
        return " WHERE R.RACUNARID = " + racunar.getRacunarID();
    }

}
