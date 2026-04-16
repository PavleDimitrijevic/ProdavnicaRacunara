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
public class TipRacunaraTest {

    private TipRacunara tipRacunara;

    @Mock
    private ResultSet rs;

    @BeforeEach
    public void setUp() {
        tipRacunara = new TipRacunara();
    }

    @AfterEach
    public void tearDown() {
        tipRacunara = null;
    }

    @Test
    public void TipRacunaraTestPrazan() {
        assertNotNull(tipRacunara);
    }

    @Test
    public void TipRacunara() {
        tipRacunara = new TipRacunara(1L, "Gaming racunar");

        assertNotNull(tipRacunara);
        assertEquals(1L, tipRacunara.getTipRacunaraID());
        assertEquals("Gaming racunar", tipRacunara.getNaziv());
    }

    @Test
    public void testToString() {
        tipRacunara.setNaziv("Gaming racunar");
        assertTrue(tipRacunara.toString().contains("Gaming racunar"));
    }

    @Test
    public void testSetTipRacunaraID() {
        tipRacunara.setTipRacunaraID(1L);
        assertEquals(1L, tipRacunara.getTipRacunaraID());
    }

    @Test
    public void testSetTipRacunaraIDNull() {
        assertThrows(java.lang.NullPointerException.class, () -> tipRacunara.setTipRacunaraID(null));
    }

    @Test
    public void testSetTipRacunaraIDNegative() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> tipRacunara.setTipRacunaraID(-1L));
    }

    @Test
    public void testSetTipRacunaraIDNula() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> tipRacunara.setTipRacunaraID(0L));

    }

    @Test
    public void testSetNaziv() {
        tipRacunara.setNaziv("Gaming racunar");
        assertEquals("Gaming racunar", tipRacunara.getNaziv());

    }

    @Test
    void testSetNazivNull() {
        assertThrows(java.lang.NullPointerException.class, () -> tipRacunara.setNaziv(null));
    }

    @Test
    void testSetNazivEmpty() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> tipRacunara.setNaziv(""));
    }

    @Test
    void testNazivTabele() {
        assertEquals(" TipRacunara ", tipRacunara.nazivTabele());
    }

    @Test
    void testAlijas() {
        assertEquals(" tr ", tipRacunara.alijas());
    }

    @Test
    void testJoin() {
        assertEquals("", tipRacunara.join());
    }

    @Test
    void testKoloneZaInsert() {
        assertEquals(" (Naziv) ", tipRacunara.koloneZaInsert());
    }

    @Test
    void testUslov() {
        tipRacunara.setTipRacunaraID(1L);
        assertEquals(" TipRacunaraID = 1", tipRacunara.uslov());
    }

    @Test
    void testVrednostiZaInsert() {
        tipRacunara.setNaziv("Gaming racunar");
        assertEquals(" 'Gaming racunar' ", tipRacunara.vrednostiZaInsert());
    }

    @Test
    void testVrednostiZaUpdate() {
        tipRacunara.setNaziv("Gaming racunar");
        assertEquals(" Naziv = 'Gaming racunar' ", tipRacunara.vrednostiZaUpdate());
    }

    @Test
    void testUslovZaSelect() {
        assertEquals("", tipRacunara.uslovZaSelect());
    }

    @Test
    void testVratiListu() throws SQLException {
        Long testID = 10L;
        String testNaziv = "Gaming racunar";

        when(rs.next()).thenReturn(true).thenReturn(false);

        when(rs.getLong("TipRacunaraID")).thenReturn(testID);
        when(rs.getString("Naziv")).thenReturn(testNaziv);

        ArrayList<AbstractDomainObject> lista = tipRacunara.vratiListu(rs);

        assertNotNull(lista);
        assertEquals(1, lista.size());

        TipRacunara tr = (TipRacunara) lista.get(0);
        assertEquals(testID, tr.getTipRacunaraID());
        assertEquals(testNaziv, tr.getNaziv());

        verify(rs, times(1)).close();
    }
}
