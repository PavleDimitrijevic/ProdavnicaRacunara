package com.pavledimitrijevic.prodavnicaracunara;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author PAVLE
 */
public class Administrator extends AbstractDomainObject {

    private Long administratorID;
    private String ime;
    private String prezime;
    private String username;
    private String password;

    public Administrator() {
    }

    public Administrator(Long administratorID, String ime, String prezime, String username, String password) {
        setAdministratorID(administratorID);
        setIme(ime);
        setPrezime(prezime);
        setUsername(username);
        setPassword(password);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Administrator other = (Administrator) obj;
        if (!Objects.equals(this.administratorID, other.administratorID)) {
            return false;
        }
        return true;
    }

    public Long getAdministratorID() {
        return administratorID;
    }

    public void setAdministratorID(Long administratorID) {
        if (administratorID == null) {
            throw new NullPointerException("ID ne sme biti null.");
        }

        if (administratorID <= 0) {
            throw new IllegalArgumentException("ID mora biti pozitivan broj.");
        }

        this.administratorID = administratorID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null) {
            throw new NullPointerException("Username ne sme biti null.");
        }

        if (username.isEmpty()) {
            throw new IllegalArgumentException("Username ne sme biti prazan.");
        }

        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null) {
            throw new NullPointerException("Password ne sme biti null.");
        }

        if (password.length() < 8) {
            throw new IllegalArgumentException("Password mora imati najmanje 8 karaktera.");
        }

        this.password = password;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        if (ime == null) {
            throw new NullPointerException("Ime ne sme biti null.");
        }

        if (ime.trim().isEmpty()) {
            throw new IllegalArgumentException("Ime ne sme biti prazno.");
        }

        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        if (prezime == null) {
            throw new NullPointerException("Prezime ne sme biti null.");
        }

        if (prezime.trim().isEmpty()) {
            throw new IllegalArgumentException("Prezime ne sme biti prazno.");
        }

        this.prezime = prezime;
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    @Override
    public String nazivTabele() {
        return " administrator ";
    }

    @Override
    public String alijas() {
        return " a ";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {
            Administrator a = new Administrator(rs.getLong("AdministratorID"),
                    rs.getString("Ime"), rs.getString("Prezime"),
                    rs.getString("Username"), rs.getString("Password"));

            lista.add(a);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (Ime, Prezime, Username, Password) ";
    }

    @Override
    public String uslov() {
        return " AdministratorID = " + administratorID;
    }

    @Override
    public String vrednostiZaInsert() {
        return "'" + ime + "', '" + prezime + "', "
                + "'" + username + "', '" + password + "'";
    }

    @Override
    public String vrednostiZaUpdate() {
        return " Ime = '" + ime + "', Prezime = '" + prezime + "', "
                + "Username = '" + username + "', Password = '" + password + "' ";
    }

    @Override
    public String uslovZaSelect() {
        return "";
    }
}
