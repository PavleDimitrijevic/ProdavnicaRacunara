package com.pavledimitrijevic.prodavnicaracunara;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author PAVLE
 */
@ExtendWith(MockitoExtension.class)
public class RacunarTest {

    private Racunar racunar;
    private TipRacunara tipRacunara;
    private ArrayList<Komponenta> komponente;
    private Garancija garancija;

    @Mock
    private ResultSet rs;

    @BeforeEach
    public void setUp() {
        tipRacunara = new TipRacunara(1L, "Gaming racunar");
        komponente = new ArrayList<>();
        komponente.add(new Komponenta());

        garancija = new Garancija();

        racunar = new Racunar(1L, "Gaming Beast X2", 200000.0,
                "Odlican", tipRacunara, komponente, garancija);

    }

    @AfterEach
    public void tearDown() {
        racunar = null;
        tipRacunara = null;
        komponente = null;
        garancija = null;
    }

    @Test
    public void RacunarTestPrazan() {
        assertNotNull(racunar);
    }

    @Test
    public void RacunarTest() {
        assertNotNull(racunar);
        assertEquals(1L, racunar.getRacunarID());
        assertEquals("Gaming Beast X2", racunar.getNaziv());
        assertEquals(200000.0, racunar.getCenaPoKomadu());
        assertEquals("Odlican", racunar.getOpis());
        assertEquals(tipRacunara, racunar.getTipRacunara());
        assertEquals(komponente, racunar.getKomponente());
    }

    @Test
    public void RacunarTest2() {
        assertNotNull(racunar);
        assertEquals(1L, racunar.getRacunarID());
        assertEquals("Gaming Beast X2", racunar.getNaziv());
        assertEquals(200000.0, racunar.getCenaPoKomadu());
        assertEquals("Odlican", racunar.getOpis());
        assertEquals(tipRacunara, racunar.getTipRacunara());
        assertEquals(komponente, racunar.getKomponente());
        assertEquals(garancija, racunar.getGarancija());
    }

    @Test
    public void testToString() {
        racunar.setNaziv("Gaming Beast X2");
        racunar.setCenaPoKomadu(200000.0);
        assertTrue(racunar.toString().contains("Gaming Beast X2"));
        assertTrue(racunar.toString().contains("200000.0"));
    }

    @Test
    public void testSetRacunarID() {
        racunar.setRacunarID(1L);
        assertEquals(1L, racunar.getRacunarID());
    }

    @Test
    void testSetRacunarIDNull() {
        assertThrows(java.lang.NullPointerException.class, () -> racunar.setRacunarID(null));
    }

    @Test
    void testSetRacunarIDNegative() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> racunar.setRacunarID(-1L));
    }

    @Test
    void testSetRacunarIDNula() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> racunar.setRacunarID(0L));
    }

    @Test
    public void testSetNaziv() {
        racunar.setNaziv("Gaming Beast X2");
        assertEquals("Gaming Beast X2", racunar.getNaziv());
    }

    @Test
    void testSetNazivRazmak() {
        racunar.setNaziv("Gaming Beast X2");
        assertEquals("Gaming Beast X2", racunar.getNaziv());
    }

    @Test
    void testSetNazivNull() {
        assertThrows(java.lang.NullPointerException.class, () -> racunar.setNaziv(null));
    }

    @Test
    void testSetNazivEmpty() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> racunar.setNaziv(""));
    }

    @Test
    void testSetNazivBlankoZnak() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> racunar.setNaziv("   "));
    }

    @Test
    public void testSetCenaPoKomadu() {
        assertEquals(200000.0, racunar.getCenaPoKomadu());
    }

    @Test
    void testSetCenaPoKomaduNegative() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> racunar.setCenaPoKomadu(-200000.0));
    }

    @Test
    void testSetCenaPoKomaduNula() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> racunar.setCenaPoKomadu(0.0));
    }

    @Test
    public void testSetOpis() {
        assertEquals("Odlican", racunar.getOpis());
    }

    @Test
    void testSetOpisRazmak() {
        racunar.setOpis("Odlican racunar");
        assertEquals("Odlican racunar", racunar.getOpis());
    }

    @Test
    void testSetOpisNull() {
        assertThrows(java.lang.NullPointerException.class, () -> racunar.setOpis(null));
    }

    @Test
    void testSetOpisEmpty() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> racunar.setOpis(""));
    }

    @Test
    void testSetOpisBlankoZnak() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> racunar.setOpis("   "));
    }

    @Test
    public void testSetTipRacunara() {
        assertEquals(tipRacunara, racunar.getTipRacunara());
    }

    @Test
    void testSetTipRacunaraNull() {
        assertThrows(java.lang.NullPointerException.class, () -> racunar.setTipRacunara(null));
    }

    @Test
    public void testSetKomponente() {
        racunar.setKomponente(komponente);
        assertEquals(komponente, racunar.getKomponente());
    }

    @Test
    void testNazivTabele() {
        assertEquals(" Racunar ", racunar.nazivTabele());
    }

    @Test
    void testAlijas() {
        assertEquals(" r ", racunar.alijas());
    }

    @Test
    void testJoin() {
        assertEquals(" JOIN TIPRACUNARA TR ON (TR.TIPRACUNARAID = R.TIPRACUNARAID) ", racunar.join());
    }

    @Test
    void testKoloneZaInsert() {
        assertEquals(" (naziv, cenaPoKomadu, opis, TipRacunaraID) ", racunar.koloneZaInsert());
    }

    @Test
    void testVrednostiZaInsert() {
        String expected = "'" + racunar.getNaziv() + "', " + racunar.getCenaPoKomadu() + ", '"
                + racunar.getOpis() + "', " + racunar.getTipRacunara().getTipRacunaraID() + " ";
        assertEquals(expected, racunar.vrednostiZaInsert());
    }

    @Test
    void testVrednostiZaUpdate() {
        String expected = " naziv = '" + racunar.getNaziv() + "', cenaPoKomadu = " + racunar.getCenaPoKomadu()
                + ", opis = '" + racunar.getOpis() + "' ";
        assertEquals(expected, racunar.vrednostiZaUpdate());
    }

    @Test
    void testUslov() {
        assertEquals(" racunarID = " + racunar.getRacunarID(), racunar.uslov());
    }

    @Test
    void testUslovZaSelect() {
        assertEquals(" ORDER BY R.RACUNARID ASC", racunar.uslovZaSelect());
    }

    @Test
    void testVratiListu() throws SQLException {
        Long tipID = 101L;
        String tipNaziv = "Gaming racunar";

        Long racunarID = 50L;
        String racunarNaziv = "Gaming PC";
        double cena = 150000.00;
        String opis = "Opis racunara";

        when(rs.next()).thenReturn(true).thenReturn(false);

        // tip racunara
        when(rs.getLong("TipRacunaraID")).thenReturn(tipID);
        when(rs.getString("tr.Naziv")).thenReturn(tipNaziv);

        // racunar
        when(rs.getLong("racunarID")).thenReturn(racunarID);
        when(rs.getString("r.naziv")).thenReturn(racunarNaziv);
        when(rs.getDouble("cenaPoKomadu")).thenReturn(cena);
        when(rs.getString("opis")).thenReturn(opis);

        ArrayList<AbstractDomainObject> lista = racunar.vratiListu(rs);

        assertNotNull(lista);
        assertEquals(1, lista.size());

        Racunar r = (Racunar) lista.get(0);

        assertEquals(racunarID, r.getRacunarID());
        assertEquals(racunarNaziv, r.getNaziv());
        assertEquals(cena, r.getCenaPoKomadu());
        assertEquals(opis, r.getOpis());

        TipRacunara tr = r.getTipRacunara();
        assertNotNull(tr);
        assertEquals(tipID, tr.getTipRacunaraID());
        assertEquals(tipNaziv, tr.getNaziv());

        verify(rs, times(1)).close();
    }

}
