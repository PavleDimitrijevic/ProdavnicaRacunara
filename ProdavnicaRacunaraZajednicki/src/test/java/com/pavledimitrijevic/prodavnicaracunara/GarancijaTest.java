package com.pavledimitrijevic.prodavnicaracunara;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author PAVLE
 */
public class GarancijaTest {

    private Garancija garancija;

    @BeforeEach
    public void setUp() {
        garancija = new Garancija(1L, 24, "Standardna garancija");
    }

    @AfterEach
    public void tearDown() {
        garancija = null;
    }

    @Test
    public void GarancijaTestPrazan() {
        assertNotNull(garancija);
    }

    @Test
    public void GarancijaTest() {
        assertNotNull(garancija);
        assertEquals(1L, garancija.getGarancijaID());
        assertEquals(24, garancija.getTrajanjeMeseci());
        assertEquals("Standardna garancija", garancija.getOpis());
    }

    @Test
    public void testSetGarancijaID() {
        garancija.setGarancijaID(2L);
        assertEquals(2L, garancija.getGarancijaID());
    }

    @Test
    void testSetGarancijaIDNull() {
        assertThrows(java.lang.NullPointerException.class, () -> garancija.setGarancijaID(null));
    }

    @Test
    void testSetGarancijaIDNegative() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> garancija.setGarancijaID(-1L));
    }

    @Test
    void testSetGarancijaIDNula() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> garancija.setGarancijaID(0L));
    }

    @Test
    public void testSetTrajanjeMeseci() {
        garancija.setTrajanjeMeseci(36);
        assertEquals(36, garancija.getTrajanjeMeseci());
    }

    @Test
    void testSetTrajanjeMeseciNula() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> garancija.setTrajanjeMeseci(0));
    }

    @Test
    void testSetTrajanjeMeseciNegative() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> garancija.setTrajanjeMeseci(-12));
    }

    @Test
    public void testSetOpis() {
        garancija.setOpis("Produzena garancija");
        assertEquals("Produzena garancija", garancija.getOpis());
    }

    @Test
    void testSetOpisRazmak() {
        garancija.setOpis("Garancija 24 meseca");
        assertEquals("Garancija 24 meseca", garancija.getOpis());
    }

    @Test
    void testSetOpisNull() {
        assertThrows(java.lang.NullPointerException.class, () -> garancija.setOpis(null));
    }

    @Test
    void testSetOpisEmpty() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> garancija.setOpis(""));
    }

    @Test
    void testSetOpisBlankoZnak() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> garancija.setOpis("   "));
    }
}
