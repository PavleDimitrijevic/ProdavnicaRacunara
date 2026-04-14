package com.pavledimitrijevic.prodavnicaracunara;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author PAVLE
 */
public class Komponenta extends AbstractDomainObject {

    private transient Racunar racunar;
    private int rb;
    private String naziv;
    private ProizvodjacKomponente proizvodjac;

    public Komponenta() {
    }

    public Komponenta(Racunar racunar, int rb, String naziv) {
        setRacunar(racunar);
        setRb(rb);
        setNaziv(naziv);
    }

    public Komponenta(Racunar racunar, int rb, String naziv, ProizvodjacKomponente proizvodjac) {
        setRacunar(racunar);
        setRb(rb);
        setNaziv(naziv);
        setProizvodjac(proizvodjac);
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        if (naziv == null) {
            throw new NullPointerException("Naziv ne sme biti null.");
        }

        if (naziv.trim().isEmpty()) {
            throw new IllegalArgumentException("Naziv ne sme biti prazan.");
        }
        this.naziv = naziv;
    }

    public Racunar getRacunar() {
        return racunar;
    }

    public void setRacunar(Racunar racunar) {
        if (racunar == null) {
            throw new NullPointerException("Racunar ne sme biti null.");
        }
        this.racunar = racunar;
    }

    public int getRb() {
        return rb;
    }

    public void setRb(int rb) {
        if (rb <= 0) {
            throw new IllegalArgumentException("Redni broj mora biti pozitivan broj.");
        }
        this.rb = rb;
    }

    public ProizvodjacKomponente getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(ProizvodjacKomponente proizvodjac) {
        if (proizvodjac == null) {
            throw new NullPointerException("Proizvodjac ne sme biti null.");
        }
        this.proizvodjac = proizvodjac;
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
