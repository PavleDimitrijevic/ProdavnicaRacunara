package com.pavledimitrijevic.prodavnicaracunara;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * * Predstavlja racun koji se izdaje u prodavnici racunara.
 * <p>
 * Racun sadrzi jedinstveni ID, datum i vreme izdavanja, ukupnu cenu,
 * administratora koji ga je izdao i listu stavki racuna.
 * </p>
 *
 * Svaki racun mora imati bar jednu stavku i mora biti vezan za postojeceg
 * administratora.
 *
 *
 * @author PAVLE
 */
public class Racun extends AbstractDomainObject {

    /**
     * ID racuna kao Long
     */
    private Long racunID;

    /**
     * Datum i vreme izdavanja racuna kao Date
     */
    private Date datumVreme;

    /**
     * Ukupna cena na racunu kao double
     */
    private double cena;

    /**
     * Administrator koji je izdao racun
     */
    private Administrator administrator;

    /**
     * Lista stavki racuna
     */
    private ArrayList<StavkaRacuna> stavkeRacuna;

    /**
     * Inicijalizuje objekat klase Racun sa svim parametrima.
     *
     * @param racunID ID racuna.Mora biti pozitivan i ne sme biti null.
     * @param datumVreme Datum i vreme izdavanja. Ne sme biti null niti posle
     * trenutnog datuma.
     * @param cena Ukupna cena racuna. Mora biti veca od nule.
     * @param administrator Administrator koji izdaje racun. Ne sme biti null.
     * @param stavkeRacuna Lista stavki racuna. Ne sme biti null niti prazna.
     */
    public Racun(Long racunID, Date datumVreme, double cena, Administrator administrator, ArrayList<StavkaRacuna> stavkeRacuna) {
        setRacunID(racunID);
        setDatumVreme(datumVreme);
        setCena(cena);
        setAdministrator(administrator);
        setStavkeRacuna(stavkeRacuna);
    }

    /**
     * Inicijalizuje objekat klase Racun sa atributima koji imaju default
     * vrednosti.
     */
    public Racun() {
    }

    @Override
    public String nazivTabele() {
        return " Racun ";
    }

    @Override
    public String alijas() {
        return " ra ";
    }

    @Override
    public String join() {
        return " JOIN ADMINISTRATOR A ON (A.ADMINISTRATORID = RA.ADMINISTRATORID) ";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {
            Administrator a = new Administrator(rs.getLong("AdministratorID"),
                    rs.getString("Ime"), rs.getString("Prezime"),
                    rs.getString("Username"), rs.getString("Password"));

            Racun ra = new Racun(rs.getLong("racunID"), rs.getTimestamp("datumVreme"),
                    rs.getDouble("cena"), a, null);

            lista.add(ra);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (datumVreme, cena, AdministratorID) ";
    }

    @Override
    public String uslov() {
        return "";
    }

    @Override
    public String vrednostiZaInsert() {
        return "'" + new Timestamp(datumVreme.getTime()) + "', " + cena + ", "
                + " " + administrator.getAdministratorID() + " ";
    }

    @Override
    public String vrednostiZaUpdate() {
        return "";
    }

    @Override
    public String uslovZaSelect() {
        if (administrator != null) {
            return " WHERE A.ADMINISTRATORID = " + administrator.getAdministratorID();
        }
        return "";
    }

    /**
     * Vraca ID racuna.
     *
     * @return ID racuna
     */
    public Long getRacunID() {
        return racunID;
    }

    /**
     * Postavlja ID racuna. ID racuna mora biti pozitivan broj i ne sme biti
     * null.
     *
     * @param racunID ID racuna
     * @throws java.lang.NullPointerException ako je ID null
     * @throws java.lang.IllegalArgumentException ako ID nije pozitivan broj
     * veci od nule
     */
    public void setRacunID(Long racunID) {
        if (racunID == null) {
            throw new NullPointerException("ID ne sme biti null.");
        }

        if (racunID <= 0) {
            throw new IllegalArgumentException("ID mora biti pozitivan broj.");
        }
        this.racunID = racunID;
    }

    /**
     * Vraca datum i vreme kada je racun izdat.
     *
     * @return datum i vreme racuna
     */
    public Date getDatumVreme() {
        return datumVreme;
    }

    /**
     * Postavlja datum i vreme izdavanja racuna. Datum i vreme ne smeju biti
     * null niti u buducnosti.
     *
     * @param datumVreme Datum i vreme izdavanja
     * @throws java.lang.NullPointerException ako je datum null
     * @throws java.lang.IllegalArgumentException ako je datum u buducnosti
     */
    public void setDatumVreme(Date datumVreme) {
        if (datumVreme == null) {
            throw new NullPointerException("Datum i vreme ne mogu biti null.");
        }

        Date today = new Date();
        if (datumVreme.after(today)) {
            throw new IllegalArgumentException("Datum ne moze biti u buducnosti.");
        }
        this.datumVreme = datumVreme;
    }

    /**
     * Vraca ukupnu cenu racuna.
     *
     * @return ukupna cena
     */
    public double getCena() {
        return cena;
    }

    /**
     * Postavlja ukupnu cenu racuna. Cena mora biti veca od nule.
     *
     * @param cena Cena racuna
     * @throws java.lang.IllegalArgumentException ako je cena manja ili jednaka
     * nuli
     */
    public void setCena(double cena) {
        if (cena <= 0) {
            throw new IllegalArgumentException("Cena mora biti veca od nule.");
        }

        this.cena = cena;
    }

    /**
     * Vraca administratora koji je izdao racun.
     *
     * @return administrator
     */
    public Administrator getAdministrator() {
        return administrator;
    }

    /**
     * Postavlja administratora koji je izdao racun. Administrator ne sme biti
     * null.
     *
     * @param administrator Administrator koji je izdao racun
     * @throws java.lang.NullPointerException ako je administrator null
     */
    public void setAdministrator(Administrator administrator) {
        if (administrator == null) {
            throw new NullPointerException("Racun mora imati administratora.");
        }
        this.administrator = administrator;
    }

    /**
     * Vraca listu stavki na racunu.
     *
     * @return lista stavki
     */
    public ArrayList<StavkaRacuna> getStavkeRacuna() {
        return stavkeRacuna;
    }

    /**
     * Postavlja listu stavki na racunu. Lista stavki ne sme biti null niti
     * prazna.
     *
     * @param stavkeRacuna Lista stavki
     * @throws java.lang.NullPointerException ako je lista null
     * @throws java.lang.IllegalArgumentException ako je lista prazna
     */
    public void setStavkeRacuna(ArrayList<StavkaRacuna> stavkeRacuna) {
        if (stavkeRacuna == null) {
            throw new NullPointerException("Lista stavki ne sme biti null.");
        }

        if (stavkeRacuna.isEmpty()) {
            throw new IllegalArgumentException("Racun mora imati bar jednu stavku racuna.");
        }

        this.stavkeRacuna = stavkeRacuna;
    }

}
