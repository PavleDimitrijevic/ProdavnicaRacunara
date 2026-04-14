package com.pavledimitrijevic.prodavnicaracunara;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author PAVLE
 */
public class StavkaRacuna extends AbstractDomainObject {

    private Racun racun;
    private int rb;
    private int kolicina;
    private double cena;
    private Racunar racunar;

    public StavkaRacuna(Racun racun, int rb, int kolicina, double cena, Racunar racunar) {
        setRacun(racun);
        setRb(rb);
        setKolicina(kolicina);
        setCena(cena);
        setRacunar(racunar);
    }

    public StavkaRacuna() {
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

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        if (racun == null) {
            throw new NullPointerException("Racun ne sme biti null.");
        }
        this.racun = racun;
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

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        if (kolicina <= 0) {
            throw new IllegalArgumentException("Kolicina mora biti veca od nule.");
        }
        this.kolicina = kolicina;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        if (cena <= 0) {
            throw new IllegalArgumentException("Cena mora biti veca od nule.");
        }
        this.cena = cena;
    }

    @Override
    public String nazivTabele() {
        return " StavkaRacuna ";
    }

    @Override
    public String alijas() {
        return " sr ";
    }

    @Override
    public String join() {
        return " JOIN RACUNAR R ON (R.RACUNARID = SR.RACUNARID) "
                + "JOIN TIPRACUNARA TR ON (TR.TIPRACUNARAID = R.TIPRACUNARAID) "
                + "JOIN RACUN RA ON (RA.RACUNID = SR.RACUNID) "
                + "JOIN ADMINISTRATOR A ON (A.ADMINISTRATORID = RA.ADMINISTRATORID) ";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {
            Administrator a = new Administrator(rs.getLong("AdministratorID"),
                    rs.getString("Ime"), rs.getString("Prezime"),
                    rs.getString("Username"), rs.getString("Password"));

            Racun ra = new Racun(rs.getLong("racunID"), rs.getTimestamp("datumVreme"),
                    rs.getDouble("ra.cena"), a, null);

            TipRacunara tr = new TipRacunara(rs.getLong("TipRacunaraID"),
                    rs.getString("tr.Naziv"));

            Racunar r = new Racunar(rs.getLong("racunarID"), rs.getString("r.naziv"),
                    rs.getDouble("cenaPoKomadu"), rs.getString("opis"), tr, null);

            StavkaRacuna sr = new StavkaRacuna(ra, rs.getInt("rb"), rs.getInt("kolicina"),
                    rs.getDouble("sr.cena"), r);

            lista.add(sr);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (racunID, rb, kolicina, cena, racunarID) ";
    }

    @Override
    public String uslov() {
        return " racunID = " + racun.getRacunID() + " AND RB = " + rb;
    }

    @Override
    public String vrednostiZaInsert() {
        return " " + racun.getRacunID() + ", " + rb + ", "
                + " " + kolicina + ", " + cena + ", " + racunar.getRacunarID();
    }

    @Override
    public String vrednostiZaUpdate() {
        return "";
    }

    @Override
    public String uslovZaSelect() {
        return " WHERE RA.RACUNID = " + racun.getRacunID();
    }

}
