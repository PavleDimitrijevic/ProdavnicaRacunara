package com.pavledimitrijevic.prodavnicaracunara;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author PAVLE
 */
public class Racunar extends AbstractDomainObject {

    private Long racunarID;
    private String naziv;
    private double cenaPoKomadu;
    private String opis;
    private TipRacunara tipRacunara;
    private ArrayList<Komponenta> komponente;

    @Override
    public String toString() {
        return naziv + " (Cena po komadu: " + cenaPoKomadu + "din)";
    }

    public Racunar() {
    }

    public Racunar(Long racunarID, String naziv, double cenaPoKomadu, String opis, TipRacunara tipRacunara, ArrayList<Komponenta> komponente) {
        this.racunarID = racunarID;
        this.naziv = naziv;
        this.cenaPoKomadu = cenaPoKomadu;
        this.opis = opis;
        this.tipRacunara = tipRacunara;
        this.komponente = komponente;
    }

    public ArrayList<Komponenta> getKomponente() {
        return komponente;
    }

    public void setKomponente(ArrayList<Komponenta> komponente) {
        this.komponente = komponente;
    }

    public Long getRacunarID() {
        return racunarID;
    }

    public void setRacunarID(Long racunarID) {
        this.racunarID = racunarID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public double getCenaPoKomadu() {
        return cenaPoKomadu;
    }

    public void setCenaPoKomadu(double cenaPoKomadu) {
        this.cenaPoKomadu = cenaPoKomadu;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public TipRacunara getTipRacunara() {
        return tipRacunara;
    }

    public void setTipRacunara(TipRacunara tipRacunara) {
        this.tipRacunara = tipRacunara;
    }

    @Override
    public String nazivTabele() {
        return " Racunar ";
    }

    @Override
    public String alijas() {
        return " r ";
    }

    @Override
    public String join() {
        return " JOIN TIPRACUNARA TR ON (TR.TIPRACUNARAID = R.TIPRACUNARAID) ";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {
            TipRacunara tr = new TipRacunara(rs.getLong("TipRacunaraID"),
                    rs.getString("tr.Naziv"));

            Racunar r = new Racunar(rs.getLong("racunarID"), rs.getString("r.naziv"),
                    rs.getDouble("cenaPoKomadu"), rs.getString("opis"), tr, null);

            lista.add(r);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (naziv, cenaPoKomadu, opis, TipRacunaraID) ";
    }

    @Override
    public String uslov() {
        return " racunarID = " + racunarID;
    }

    @Override
    public String vrednostiZaInsert() {
        return "'" + naziv + "', " + cenaPoKomadu + ", "
                + "'" + opis + "', " + tipRacunara.getTipRacunaraID() + " ";
    }

    @Override
    public String vrednostiZaUpdate() {
        return " naziv = '" + naziv + "', cenaPoKomadu = " + cenaPoKomadu + ", "
                + "opis = '" + opis + "' ";
    }

    @Override
    public String uslovZaSelect() {
        return " ORDER BY R.RACUNARID ASC";
    }

}
