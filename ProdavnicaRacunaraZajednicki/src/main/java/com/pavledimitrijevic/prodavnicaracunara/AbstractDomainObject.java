package com.pavledimitrijevic.prodavnicaracunara;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Apstraktna klasa koja predstavlja osnovu svih domenskih objekata. Sadrzi
 * apstraktne metode koje svaki konkretan domenski objekat mora da implementira.
 *
 * @author PAVLE
 */
public abstract class AbstractDomainObject implements Serializable {

    /**
     * Vraca naziv tabele u bazi podataka koja odgovara konkretnoj domenskoj
     * klasi.
     *
     * @return naziv tabele u bazi podataka
     */
    public abstract String nazivTabele();

    /**
     * Vraca skraceni naziv tabele za koriscenje u SQL upitima.
     *
     * @return alijas tabele
     */
    public abstract String alijas();

    /**
     * Vraca SQL JOIN uslove koje je potrebno primeniti prilikom pretrage vise
     * tabela.
     *
     * @return JOIN deo SQL upita
     */
    public abstract String join();

    /**
     * Mapira ResultSet iz baze u listu objekata konkretne domenske klase.
     *
     * @param rs ResultSet koji sadrzi podatke iz baze
     * @return lista objekata domenske klase
     * @throws java.sql.SQLException ako dodje do greske prilikom citanja iz
     * ResultSet-a
     */
    public abstract ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException;

    /**
     * Vraca string sa nazivima kolona koje se koriste u INSERT upitu.
     *
     * @return nazivi kolona za INSERT
     */
    public abstract String koloneZaInsert();

    /**
     * Vraca string koji predstavlja WHERE uslov za SQL upite.
     *
     * @return uslov za SELECT, UPDATE ili DELETE upit
     */
    public abstract String uslov();

    /**
     * Vraca string sa vrednostima koje se ubacuju u INSERT upit.
     *
     * @return vrednosti za INSERT
     */
    public abstract String vrednostiZaInsert();

    /**
     * Vraca string sa vrednostima koje se koriste u UPDATE upitu.
     *
     * @return vrednosti za UPDATE
     */
    public abstract String vrednostiZaUpdate();

    /**
     *
     * Vraca uslov koji se koristi prilikom SELECT upita.
     *
     * @return WHERE uslov za SELECT
     */
    public abstract String uslovZaSelect();
}
