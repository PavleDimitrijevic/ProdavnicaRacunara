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
public class KomponentaTest {

    private Komponenta komponenta;
    private Racunar racunar;

    @Mock
    private ResultSet rs;

    @BeforeEach
    public void setUp() {
        racunar = new Racunar();
        komponenta = new Komponenta();
    }

    @AfterEach
    public void tearDown() {
        komponenta = null;
        racunar = null;
    }

    @Test
    public void KomponentaTestPrazan() {
        assertNotNull(komponenta);
    }

    @Test
    public void KomponentaTest() {
        komponenta = new Komponenta(racunar, 1, "Ryzen 9 procesor");

        assertNotNull(komponenta);
        assertEquals(racunar, komponenta.getRacunar());
        assertEquals(1, komponenta.getRb());
        assertEquals("Ryzen 9 procesor", komponenta.getNaziv());
    }

    @Test
    public void testToString() {
        komponenta.setRb(1);
        komponenta.setNaziv("Ryzen 9 procesor");

        assertTrue(komponenta.toString().contains("1"));
        assertTrue(komponenta.toString().contains("Ryzen 9 procesor"));
    }

    @Test
    public void testSetRacunar() {
        komponenta.setRacunar(racunar);
        assertEquals(racunar, komponenta.getRacunar());
    }

    @Test
    void testSetRacunarNull() {
        assertThrows(java.lang.NullPointerException.class, () -> komponenta.setRacunar(null));
    }

    @Test
    public void testSetRb() {
        komponenta.setRb(1);
        assertEquals(1, komponenta.getRb());
    }

    @Test
    void testSetRbNula() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> komponenta.setRb(0));
    }

    @Test
    void testSetRbNegative() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> komponenta.setRb(-1));
    }

    @Test
    public void testSetNaziv() {
        komponenta.setNaziv("SSD");
        assertEquals("SSD", komponenta.getNaziv());
    }

    @Test
    void testSetNazivRazmak() {
        komponenta.setNaziv("Ryzen 9 procesor");
        assertEquals("Ryzen 9 procesor", komponenta.getNaziv());
    }

    @Test
    void testSetNazivNull() {
        assertThrows(java.lang.NullPointerException.class, () -> komponenta.setNaziv(null));
    }

    @Test
    void testSetNazivEmpty() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> komponenta.setNaziv(""));
    }

    @Test
    void testSetNazivBlankoZnak() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> komponenta.setNaziv("   "));
    }

    @Test
    void testNazivTabele() {
        assertEquals(" Komponenta ", komponenta.nazivTabele());
    }

    @Test
    void testAlijas() {
        assertEquals(" k ", komponenta.alijas());
    }

    @Test
    void testJoin() {
        String expectedJoin = " JOIN RACUNAR R ON (R.RACUNARID = K.RACUNARID) "
                + "JOIN TIPRACUNARA TR ON (TR.TIPRACUNARAID = R.TIPRACUNARAID)";
        assertEquals(expectedJoin, komponenta.join());
    }

    @Test
    void testKoloneZaInsert() {
        racunar.setRacunarID(1L);
        komponenta = new Komponenta(racunar, 1, "Ryzen 9 procesor");
        assertEquals(" (racunarID, rb, naziv) ", komponenta.koloneZaInsert());
    }

    @Test
    void testUslov() {
        racunar.setRacunarID(1L);
        komponenta = new Komponenta(racunar, 1, "Ryzen 9 procesor");
        assertEquals(" racunarID = 1", komponenta.uslov());
    }

    @Test
    void testVrednostiZaInsert() {
        racunar.setRacunarID(1L);
        komponenta = new Komponenta(racunar, 1, "Ryzen 9 procesor");
        assertEquals(" 1, 1, 'Ryzen 9 procesor' ", komponenta.vrednostiZaInsert());
    }

    @Test
    void testVrednostiZaUpdate() {
        assertEquals("", komponenta.vrednostiZaUpdate());
    }

    @Test
    void testUslovZaSelect() {
        racunar.setRacunarID(1L);
        komponenta = new Komponenta(racunar, 1, "Ryzen 9 procesor");
        assertEquals(" WHERE R.RACUNARID = 1", komponenta.uslovZaSelect());
    }

    @Test
    void testVratiListu() throws SQLException {
        Long tipID = 11L;
        String tipNaziv = "Gaming racunar";

        Long racunarID = 5L;
        String racunarNaziv = "Gaming Beast X2";
        double cena = 200000.0;
        String opis = "Odlican racunar";

        int rb = 1;
        String komponentaNaziv = "Ryzen 9 procesor";

        when(rs.next()).thenReturn(true).thenReturn(false);

        when(rs.getLong("TipRacunaraID")).thenReturn(tipID);
        when(rs.getString("tr.Naziv")).thenReturn(tipNaziv);

        when(rs.getLong("racunarID")).thenReturn(racunarID);
        when(rs.getString("r.naziv")).thenReturn(racunarNaziv);
        when(rs.getDouble("cenaPoKomadu")).thenReturn(cena);
        when(rs.getString("opis")).thenReturn(opis);

        when(rs.getInt("rb")).thenReturn(rb);
        when(rs.getString("k.naziv")).thenReturn(komponentaNaziv);

        ArrayList<AbstractDomainObject> lista = komponenta.vratiListu(rs);

        assertNotNull(lista);
        assertEquals(1, lista.size());

        Komponenta k = (Komponenta) lista.get(0);
        assertEquals(rb, k.getRb());
        assertEquals(komponentaNaziv, k.getNaziv());

        Racunar r = k.getRacunar();
        assertNotNull(r);
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
