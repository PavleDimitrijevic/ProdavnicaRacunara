package com.pavledimitrijevic.prodavnicaracunara;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Predstavlja komponentu racunara.
 * <p>
 * Svaka komponenta ima redni broj, naziv, proizvodjaca i pripada nekom
 * racunaru.
 * </p>
 *
 * @author PAVLE
 */
public class Komponenta extends AbstractDomainObject {

    /**
     * Racunar kome komponenta pripada.
     * <p>
     * Obelezen je kao <b>transient</b> da se ne bi serijalizovao.
     * </p>
     */
    private transient Racunar racunar;

    /**
     * Redni broj komponente kao int
     */
    private int rb;

    /**
     * Naziv komponente kao string
     */
    private String naziv;

    /**
     * Proizvodjac komponente kao ProizvodjacKomponente
     */
    private ProizvodjacKomponente proizvodjac;

    /**
     * Inicijalizuje objekat klase Komponenta sa atributima koji imaju default
     * vrednosti.
     */
    public Komponenta() {
    }

    /**
     * Inicijalizuje objekat klase Komponenta sa parametrima.
     *
     * @param racunar Racunar kome komponenta pripada. Ne sme biti null.
     * @param rb Redni broj komponente. Mora biti veci od nule.
     * @param naziv Naziv komponente. Ne sme biti null niti prazan string.
     */
    public Komponenta(Racunar racunar, int rb, String naziv) {
        setRacunar(racunar);
        setRb(rb);
        setNaziv(naziv);
    }

    /**
     * Inicijalizuje objekat klase Komponenta sa svim parametrima.
     *
     * @param racunar Racunar kome komponenta pripada. Ne sme biti null.
     * @param rb Redni broj komponente. Mora biti veci od nule.
     * @param naziv Naziv komponente. Ne sme biti null niti prazan string.
     * @param proizvodjac Proizvodjac komponente. Ne sme biti null.
     */
    public Komponenta(Racunar racunar, int rb, String naziv, ProizvodjacKomponente proizvodjac) {
        setRacunar(racunar);
        setRb(rb);
        setNaziv(naziv);
        setProizvodjac(proizvodjac);
    }

    /**
     * Vraca tekstualni prikaz komponente.
     *
     * @return String sa podacima o rednom broju i nazivu komponente u formatu
     * rb + ". " + naziv
     */
    @Override
    public String toString() {
        return rb + ". " + naziv;
    }

    /**
     * Vraca naziv komponente.
     *
     * @return naziv komponente
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja naziv komponente. Naziv ne sme biti null niti prazan string.
     *
     * @param naziv Naziv
     * @throws java.lang.NullPointerException ako je naziv null
     * @throws java.lang.IllegalArgumentException ako je naziv prazan string
     */
    public void setNaziv(String naziv) {
        if (naziv == null) {
            throw new NullPointerException("Naziv ne sme biti null.");
        }

        if (naziv.trim().isEmpty()) {
            throw new IllegalArgumentException("Naziv ne sme biti prazan.");
        }
        this.naziv = naziv;
    }

    /**
     * Vraca racunar kome komponenta pripada.
     *
     * @return racunar
     */
    public Racunar getRacunar() {
        return racunar;
    }

    /**
     * Postavlja racunar kome komponenta pripada. Racunar ne sme biti null.
     *
     * @param racunar Racunar kome komponenta pripada
     * @throws java.lang.NullPointerException ako je racunar null
     */
    public void setRacunar(Racunar racunar) {
        if (racunar == null) {
            throw new NullPointerException("Racunar ne sme biti null.");
        }
        this.racunar = racunar;
    }

    /**
     * Vraca redni broj komponente.
     *
     * @return redni broj
     */
    public int getRb() {
        return rb;
    }

    /**
     * Postavlja redni broj komponente. Redni broj mora biti veci od nule.
     *
     * @param rb Redni broj
     * @throws java.lang.IllegalArgumentException ako je redni broj negativan
     * ili jednak nuli
     */
    public void setRb(int rb) {
        if (rb <= 0) {
            throw new IllegalArgumentException("Redni broj mora biti pozitivan broj.");
        }
        this.rb = rb;
    }

    /**
     * Vraca proizvodjaca komponente.
     *
     * @return proizvodjac
     */
    public ProizvodjacKomponente getProizvodjac() {
        return proizvodjac;
    }

    /**
     * Postavlja proizvodjaca komponente. Proizvodjac ne sme biti null.
     *
     * @param proizvodjac Proizvodjac komponente
     * @throws java.lang.NullPointerException ako je proizvodjac null
     */
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
