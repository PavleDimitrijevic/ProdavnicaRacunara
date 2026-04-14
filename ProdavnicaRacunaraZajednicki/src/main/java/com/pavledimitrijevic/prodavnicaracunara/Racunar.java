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
    private Garancija garancija;

    @Override
    public String toString() {
        return naziv + " (Cena po komadu: " + cenaPoKomadu + "din)";
    }

    public Racunar() {
    }

    public Racunar(Long racunarID, String naziv, double cenaPoKomadu, String opis, TipRacunara tipRacunara, ArrayList<Komponenta> komponente) {
        setRacunarID(racunarID);
        setNaziv(naziv);
        setCenaPoKomadu(cenaPoKomadu);
        setOpis(opis);
        setTipRacunara(tipRacunara);
        setKomponente(komponente);
    }

    public Racunar(Long racunarID, String naziv, double cenaPoKomadu, String opis,
            TipRacunara tipRacunara, ArrayList<Komponenta> komponente,
            Garancija garancija) {
        setRacunarID(racunarID);
        setNaziv(naziv);
        setCenaPoKomadu(cenaPoKomadu);
        setOpis(opis);
        setTipRacunara(tipRacunara);
        setKomponente(komponente);
        setGarancija(garancija);
    }

    public ArrayList<Komponenta> getKomponente() {
        return komponente;
    }

    public void setKomponente(ArrayList<Komponenta> komponente) {
        if (komponente == null) {
            throw new NullPointerException("Lista komponenti ne sme biti null.");
        }

        if (komponente.isEmpty()) {
            throw new IllegalArgumentException("Racunar mora imati bar jedanu komponentu.");
        }
        this.komponente = komponente;
    }

    public Long getRacunarID() {
        return racunarID;
    }

    public void setRacunarID(Long racunarID) {
        if (racunarID == null) {
            throw new NullPointerException("ID ne sme biti null.");
        }

        if (racunarID <= 0) {
            throw new IllegalArgumentException("ID mora biti pozitivan broj.");
        }

        this.racunarID = racunarID;
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

    public double getCenaPoKomadu() {
        return cenaPoKomadu;
    }

    public void setCenaPoKomadu(double cenaPoKomadu) {
        if (cenaPoKomadu <= 0) {
            throw new IllegalArgumentException("Cena po komadu mora biti veca od nule.");
        }
        this.cenaPoKomadu = cenaPoKomadu;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        if (opis == null) {
            throw new NullPointerException("Opis ne sme biti null.");
        }

        if (opis.trim().isEmpty()) {
            throw new IllegalArgumentException("Opis ne sme biti prazan.");
        }
        this.opis = opis;
    }

    public TipRacunara getTipRacunara() {
        return tipRacunara;
    }

    public void setTipRacunara(TipRacunara tipRacunara) {
        if (tipRacunara == null) {
            throw new NullPointerException("Tip racunara ne sme biti null.");
        }
        this.tipRacunara = tipRacunara;
    }

    public Garancija getGarancija() {
        return garancija;
    }

    public void setGarancija(Garancija garancija) {
        if (garancija == null) {
            throw new NullPointerException("Garancija ne sme biti null.");
        }
        this.garancija = garancija;
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
