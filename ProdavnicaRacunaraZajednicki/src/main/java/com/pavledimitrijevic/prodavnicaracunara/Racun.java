package com.pavledimitrijevic.prodavnicaracunara;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author PAVLE
 */
public class Racun extends AbstractDomainObject {

    private Long racunID;
    private Date datumVreme;
    private double cena;
    private Administrator administrator;
    private ArrayList<StavkaRacuna> stavkeRacuna;

    public Racun(Long racunID, Date datumVreme, double cena, Administrator administrator, ArrayList<StavkaRacuna> stavkeRacuna) {
        setRacunID(racunID);
        setDatumVreme(datumVreme);
        setCena(cena);
        setAdministrator(administrator);
        setStavkeRacuna(stavkeRacuna);
    }

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

    public Long getRacunID() {
        return racunID;
    }

    public void setRacunID(Long racunID) {
        if (racunID == null) {
            throw new NullPointerException("ID ne sme biti null.");
        }

        if (racunID <= 0) {
            throw new IllegalArgumentException("ID mora biti pozitivan broj.");
        }
        this.racunID = racunID;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

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

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        if (cena <= 0) {
            throw new IllegalArgumentException("Cena mora biti veca od nule.");
        }

        this.cena = cena;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        if (administrator == null) {
            throw new NullPointerException("Racun mora imati administratora.");
        }
        this.administrator = administrator;
    }

    public ArrayList<StavkaRacuna> getStavkeRacuna() {
        return stavkeRacuna;
    }

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
