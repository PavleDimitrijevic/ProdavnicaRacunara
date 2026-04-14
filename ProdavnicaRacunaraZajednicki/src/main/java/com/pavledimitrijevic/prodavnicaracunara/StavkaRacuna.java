package com.pavledimitrijevic.prodavnicaracunara;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Predstavlja stavku racuna u prodavnici racunara. Svaka stavka pripada
 * odredjenom racunu i sadrzi redni broj, kolicinu, cenu i racunar na koji se
 * odnosi.
 *
 * @author PAVLE
 */
public class StavkaRacuna extends AbstractDomainObject {

    /**
     * Racun kome stavka pripada
     */
    private Racun racun;

    /**
     * Redni broj stavke kao int
     */
    private int rb;

    /**
     * Kolicina stavke na racunu kao int
     */
    private int kolicina;

    /**
     * Cena stavke kao double
     */
    private double cena;

    /**
     * Racunar na koji se stavka odnosi
     */
    private Racunar racunar;

    /**
     * Inicijalizuje objekat klase StavkaRacuna sa svim parametrima.
     *
     * @param racun Racun kojem stavka pripada. Ne sme biti null.
     * @param rb Redni broj stavke. Mora biti pozitivan broj.
     * @param kolicina Kolicina racunara. Mora biti veca od nule.
     * @param cena Cena stavke. Mora biti veca od nule.
     * @param racunar Racunar koji se nalazi u stavci. Ne sme biti null.
     */
    public StavkaRacuna(Racun racun, int rb, int kolicina, double cena, Racunar racunar) {
        setRacun(racun);
        setRb(rb);
        setKolicina(kolicina);
        setCena(cena);
        setRacunar(racunar);
    }

    /**
     * Inicijalizuje objekat klase StavkaRacuna sa atributima koji imaju default
     * vrednosti.
     */
    public StavkaRacuna() {
    }

    /**
     * Vraca racunar koji je deo stavke racuna.
     *
     * @return racunar
     */
    public Racunar getRacunar() {
        return racunar;
    }

    /**
     * Postavlja racunar za stavku racuna. Racunar ne sme biti null.
     *
     * @param racunar Racunar
     * @throws java.lang.NullPointerException ako je racunar null
     */
    public void setRacunar(Racunar racunar) {
        if (racunar == null) {
            throw new NullPointerException("Racunar ne sme biti null.");
        }
        this.racunar = racunar;
    }

    /**
     * Vraca racun kome stavka pripada.
     *
     * @return racun stavke
     */
    public Racun getRacun() {
        return racun;
    }

    /**
     * Postavlja racun kome stavka pripada. Racun ne sme biti null.
     *
     * @param racun Racun kome stavka pripada
     * @throws java.lang.NullPointerException ako je racun null
     */
    public void setRacun(Racun racun) {
        if (racun == null) {
            throw new NullPointerException("Racun ne sme biti null.");
        }
        this.racun = racun;
    }

    /**
     * Vraca redni broj stavke.
     *
     * @return redni broj
     */
    public int getRb() {
        return rb;
    }

    /**
     * Postavlja redni broj stavke. Redni broj mora biti pozitivan.
     *
     * @param rb Redni broj stavke
     * @throws java.lang.IllegalArgumentException ako redni broj nije pozitivan
     */
    public void setRb(int rb) {
        if (rb <= 0) {
            throw new IllegalArgumentException("Redni broj mora biti pozitivan broj.");
        }
        this.rb = rb;
    }

    /**
     * Vraca kolicinu racunara u stavci.
     *
     * @return kolicina
     */
    public int getKolicina() {
        return kolicina;
    }

    /**
     * Postavlja kolicinu racunara u stavci. Kolicina mora biti veca od nule.
     *
     * @param kolicina Kolicina racunara
     * @throws java.lang.IllegalArgumentException ako je kolicina manja ili
     * jednaka nuli
     */
    public void setKolicina(int kolicina) {
        if (kolicina <= 0) {
            throw new IllegalArgumentException("Kolicina mora biti veca od nule.");
        }
        this.kolicina = kolicina;
    }

    /**
     * Vraca cenu stavke racuna.
     *
     * @return cena stavke
     */
    public double getCena() {
        return cena;
    }

    /**
     * Postavlja cenu stavke racuna. Cena mora biti veca od nule.
     *
     * @param cena Cena stavke racuna
     * @throws java.lang.IllegalArgumentException ako je cena manja ili jednaka
     * nuli
     */
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
