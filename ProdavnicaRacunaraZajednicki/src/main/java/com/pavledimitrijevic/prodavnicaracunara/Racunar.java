package com.pavledimitrijevic.prodavnicaracunara;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Predstavlja racunar koja se prodaje u prodavnici.
 * <p>
 * Svaki racunar ima jedinstveni ID, naziv, cenu po komadu, opis, tip racunara,
 * listu komponenti i garanaciju.
 * </p>
 *
 * @author PAVLE
 */
public class Racunar extends AbstractDomainObject {

    /**
     * ID racunara kao Long
     */
    private Long racunarID;

    /**
     * ID racunara kao Long
     */
    private String naziv;

    /**
     * Cena po komadu racunara kao double
     */
    private double cenaPoKomadu;

    /**
     * Opis racunara kao String
     */
    private String opis;

    /**
     * Tip racunara
     */
    private TipRacunara tipRacunara;

    /**
     * Lista komponenti racunara
     */
    private ArrayList<Komponenta> komponente;

    /**
     * Garancija na racunar
     */
    private Garancija garancija;

    /**
     * Vraca tekstualni prikaz racunara.
     *
     * @return String sa podacima o nazivu i ceni racunara u formatu naziv + "
     * (Cena po komadu: " + cenaPoKomadu + "din)"
     */
    @Override
    public String toString() {
        return naziv + " (Cena po komadu: " + cenaPoKomadu + "din)";
    }

    /**
     * Inicijalizuje objekat klase Racunar sa atributima koji imaju default
     * vrednosti.
     */
    public Racunar() {
    }

    /**
     * Inicijalizuje objekat klase Racunar sa parametrima.
     *
     * @param racunarID Jedinstveni ID racunara. Mora biti pozitivan broj i ne
     * sme biti null.
     * @param naziv Naziv racunara. Ne sme biti null ili prazan string.
     * @param cenaPoKomadu Cena jednoog racunara. Mora biti veca od nule.
     * @param opis Opis racunara. Ne sme biti null niti prazan string.
     * @param tipRacunara Tip racunara. Ne sme biti null.
     * @param komponente Lista komponenti. Ne sme biti null niti prazna.
     */
    public Racunar(Long racunarID, String naziv, double cenaPoKomadu, String opis, TipRacunara tipRacunara, ArrayList<Komponenta> komponente) {
        setRacunarID(racunarID);
        setNaziv(naziv);
        setCenaPoKomadu(cenaPoKomadu);
        setOpis(opis);
        setTipRacunara(tipRacunara);
        setKomponente(komponente);
    }

    /**
     * Inicijalizuje objekat klase Racunar sa svim parametrima.
     *
     * @param racunarID Jedinstveni ID racunara. Mora biti pozitivan broj i ne
     * sme biti null.
     * @param naziv Naziv racunara. Ne sme biti null ili prazan string.
     * @param cenaPoKomadu Cena jednoog racunara. Mora biti veca od nule.
     * @param opis Opis racunara. Ne sme biti null niti prazan string.
     * @param tipRacunara Tip racunara. Ne sme biti null.
     * @param komponente Lista komponenti. Ne sme biti null niti prazna.
     * @param garancija Garancija na racunar
     */
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

    /**
     * Vraca listu komponenti racunara.
     *
     * @return lista komponenti
     */
    public ArrayList<Komponenta> getKomponente() {
        return komponente;
    }

    /**
     * Postavlja listu komponenti.
     * Nema logicke kontrole za null ili praznu listu komponenti,
     * jer u metodi vratiListu() za listu komponenti prosledjujemo null.
     *
     * @param komponente Lista komponenti
     */
    public void setKomponente(ArrayList<Komponenta> komponente) {
        this.komponente = komponente;
    }

    /**
     * Vraca ID racunara.
     *
     * @return ID racunara
     */
    public Long getRacunarID() {
        return racunarID;
    }

    /**
     * Postavlja ID racunara. ID racunara mora biti pozitivan broj i ne sme biti
     * null.
     *
     * @param racunarID ID racunara
     * @throws java.lang.NullPointerException ako je ID null
     * @throws java.lang.IllegalArgumentException ako je ID manji ili jednak
     * nuli
     */
    public void setRacunarID(Long racunarID) {
        if (racunarID == null) {
            throw new NullPointerException("ID ne sme biti null.");
        }

        if (racunarID <= 0) {
            throw new IllegalArgumentException("ID mora biti pozitivan broj.");
        }

        this.racunarID = racunarID;
    }

    /**
     * Vraca naziv racunara.
     *
     * @return naziv racunara
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja naziv racunara. Naziv ne sme biti null niti prazan string.
     *
     * @param naziv Naziv racunara
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
     * Vraca cenu jednog racunara.
     *
     * @return cena po komadu
     */
    public double getCenaPoKomadu() {
        return cenaPoKomadu;
    }

    /**
     * Postavlja cenu jednog racunara. Cena mora biti veca od nule.
     *
     * @param cenaPoKomadu Cena racunara
     * @throws java.lang.IllegalArgumentException ako je cena manja ili jednaka
     * nuli
     */
    public void setCenaPoKomadu(double cenaPoKomadu) {
        if (cenaPoKomadu <= 0) {
            throw new IllegalArgumentException("Cena po komadu mora biti veca od nule.");
        }
        this.cenaPoKomadu = cenaPoKomadu;
    }

    /**
     * Vraca opis racunara.
     *
     * @return opis racunara
     */
    public String getOpis() {
        return opis;
    }

    /**
     * Postavlja opis racunara. Opis ne sme biti null niti prazan string.
     *
     * @param opis Opis racunara
     * @throws java.lang.NullPointerException ako je opis null
     * @throws java.lang.IllegalArgumentException ako je opis prazan string
     */
    public void setOpis(String opis) {
        if (opis == null) {
            throw new NullPointerException("Opis ne sme biti null.");
        }

        if (opis.trim().isEmpty()) {
            throw new IllegalArgumentException("Opis ne sme biti prazan.");
        }
        this.opis = opis;
    }

    /**
     * Vraca tip racunara.
     *
     * @return tip racunara
     */
    public TipRacunara getTipRacunara() {
        return tipRacunara;
    }

    /**
     * Postavlja tip racunara. Tip racunara ne sme biti null.
     *
     * @param tipRacunara Tip racunara
     * @throws java.lang.NullPointerException ako je tip racunara null
     */
    public void setTipRacunara(TipRacunara tipRacunara) {
        if (tipRacunara == null) {
            throw new NullPointerException("Tip racunara ne sme biti null.");
        }
        this.tipRacunara = tipRacunara;
    }

    /**
     * Vraca garanciju racunara.
     *
     * @return garancija
     */
    public Garancija getGarancija() {
        return garancija;
    }

    /**
     * Postavlja garanciju racunara.Garancija racunara ne sme biti null.
     *
     * @param garancija Garancija racunara
     * @throws java.lang.NullPointerException ako je garancija racunara null
     */
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
