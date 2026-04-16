package com.pavledimitrijevic.prodavnicaracunara;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author PAVLE
 */
public class ProizvodjacKomponenteTest {

    private ProizvodjacKomponente proizvodjac;

    @BeforeEach
    public void setUp() {
        proizvodjac = new ProizvodjacKomponente(1L, "AMD");
    }

    @AfterEach
    public void tearDown() {
        proizvodjac = null;
    }

    @Test
    public void ProizvodjacKomponenteTestPrazan() {
        assertNotNull(proizvodjac);
    }

    @Test
    public void ProizvodjacKomponenteTest() {
        assertNotNull(proizvodjac);
        assertEquals(1L, proizvodjac.getProizvodjacID());
        assertEquals("AMD", proizvodjac.getNaziv());
    }

    @Test
    public void testSetProizvodjacID() {
        proizvodjac.setProizvodjacID(2L);
        assertEquals(2L, proizvodjac.getProizvodjacID());
    }

    @Test
    void testSetProizvodjacIDNull() {
        assertThrows(java.lang.NullPointerException.class, () -> proizvodjac.setProizvodjacID(null));
    }

    @Test
    void testSetProizvodjacIDNegative() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> proizvodjac.setProizvodjacID(-1L));
    }

    @Test
    void testSetProizvodjacIDNula() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> proizvodjac.setProizvodjacID(0L));
    }

    @Test
    public void testSetNaziv() {
        proizvodjac.setNaziv("Intel");
        assertEquals("Intel", proizvodjac.getNaziv());
    }

    @Test
    void testSetNazivRazmak() {
        proizvodjac.setNaziv("Ryzen 9 procesor");
        assertEquals("Ryzen 9 procesor", proizvodjac.getNaziv());
    }

    @Test
    void testSetNazivNull() {
        assertThrows(java.lang.NullPointerException.class, () -> proizvodjac.setNaziv(null));
    }

    @Test
    void testSetNazivEmpty() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> proizvodjac.setNaziv(""));
    }

    @Test
    void testSetNazivBlankoZnak() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> proizvodjac.setNaziv("   "));
    }

}
