package com.pavledimitrijevic.prodavnicaracunara;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

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
public class RacunTest {

    private Administrator administrator;
    private Racun racun;

    @Mock
    private ResultSet rs;

    @BeforeEach
    public void setUp() {
        racun = new Racun();
        administrator = new Administrator(1L, "Pavle", "Dimitrijevic", "pavle123", "pavle123");
    }

    @AfterEach
    public void tearDown() {
        racun = null;
        administrator = null;
    }

    @Test
    public void RacunTestPrazan() {
        assertNotNull(racun);
    }

    @Test
    public void RacunTest() {
        Date datum = new Date();
        ArrayList<StavkaRacuna> stavkeRacuna = new ArrayList<>();
        stavkeRacuna.add(new StavkaRacuna());

        racun = new Racun(1L, datum, 200000.0, administrator, stavkeRacuna);

        assertNotNull(racun);
        assertEquals(1L, racun.getRacunID());
        assertEquals(datum, racun.getDatumVreme());
        assertEquals(200000.0, racun.getCena());
        assertEquals(administrator, racun.getAdministrator());
        assertEquals(stavkeRacuna, racun.getStavkeRacuna());
    }

    @Test
    public void testSetRacunID() {
        racun.setRacunID(1L);
        assertEquals(1L, racun.getRacunID());
    }

    @Test
    void testSetRacunIDNull() {
        assertThrows(java.lang.NullPointerException.class, () -> racun.setRacunID(null));
    }

    @Test
    void testSetRacunIDNegative() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> racun.setRacunID(-1L));
    }

    @Test
    void testSetRacunIDNula() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> racun.setRacunID(0L));
    }

    @Test
    public void testSetDatumVreme() {
        Date datum = new Date();
        racun.setDatumVreme(datum);
        assertEquals(datum, racun.getDatumVreme());
    }

    @Test
    void testSetDatumVremeNull() {
        assertThrows(java.lang.NullPointerException.class, () -> racun.setDatumVreme(null));
    }

    @Test
    void testSetDatumVremeFuture() {
        Date future = new Date(System.currentTimeMillis() + 100000);
        assertThrows(java.lang.IllegalArgumentException.class, () -> racun.setDatumVreme(future));
    }

    @Test
    public void testSetCena() {
        racun.setCena(200000.0);
        assertEquals(200000.0, racun.getCena());
    }

    @Test
    void testSetCenaNegative() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> racun.setCena(-200000.0));
    }

    @Test
    void testSetCenaNula() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> racun.setCena(0.0));
    }

    @Test
    public void testSetAdministrator() {
        racun.setAdministrator(administrator);
        assertEquals(administrator, racun.getAdministrator());
    }

    @Test
    void testSetAdministratorNull() {
        assertThrows(java.lang.NullPointerException.class, () -> racun.setAdministrator(null));
    }

    @Test
    public void testSetStavkeRacuna() {
        ArrayList<StavkaRacuna> stavkeRacuna = new ArrayList<>();
        stavkeRacuna.add(new StavkaRacuna());
        racun.setStavkeRacuna(stavkeRacuna);
        assertEquals(stavkeRacuna, racun.getStavkeRacuna());
    }

    @Test
    void testNazivTabele() {
        assertEquals(" Racun ", racun.nazivTabele());
    }

    @Test
    void testAlijas() {
        assertEquals(" ra ", racun.alijas());
    }

    @Test
    void testJoin() {
        assertEquals(" JOIN ADMINISTRATOR A ON (A.ADMINISTRATORID = RA.ADMINISTRATORID) ", racun.join());
    }

    @Test
    void testKoloneZaInsert() {
        assertEquals(" (datumVreme, cena, AdministratorID) ", racun.koloneZaInsert());
    }

    @Test
    void testUslov() {
        assertEquals("", racun.uslov());
    }

    @Test
    void testVrednostiZaInsert() {
        administrator.getAdministratorID();
        racun.setDatumVreme(new Timestamp(System.currentTimeMillis()));
        racun.setCena(200000.0);
        racun.setAdministrator(administrator);

        String expected = "'" + new Timestamp(racun.getDatumVreme().getTime()) + "', "
                + racun.getCena() + ",  " + racun.getAdministrator().getAdministratorID() + " ";
        assertEquals(expected, racun.vrednostiZaInsert());
    }

    @Test
    void testVrednostiZaUpdate() {
        assertEquals("", racun.vrednostiZaUpdate());
    }

    @Test
    void testUslovZaSelectAdmin() {
        administrator.getAdministratorID();
        racun.setAdministrator(administrator);
        assertEquals(" WHERE A.ADMINISTRATORID = 1", racun.uslovZaSelect());
    }

    @Test
    void testUslovZaSelect() {
        assertEquals("", racun.uslovZaSelect());
    }

    @Test
    void testVratiListu() throws SQLException {
        Long adminID = 1L;
        String adminIme = "Djorjde";
        String adminPrezime = "Djordjevic";
        String adminUser = "admin";
        String adminPass = "pass123456";

        Long racunID = 100L;
        Timestamp datumVreme = new Timestamp(new Date().getTime());
        double cena = 400000.50;

        when(rs.next()).thenReturn(true).thenReturn(false);

        when(rs.getLong("AdministratorID")).thenReturn(adminID);
        when(rs.getString("Ime")).thenReturn(adminIme);
        when(rs.getString("Prezime")).thenReturn(adminPrezime);
        when(rs.getString("Username")).thenReturn(adminUser);
        when(rs.getString("Password")).thenReturn(adminPass);

        when(rs.getLong("racunID")).thenReturn(racunID);
        when(rs.getTimestamp("datumVreme")).thenReturn(datumVreme);
        when(rs.getDouble("cena")).thenReturn(cena);

        ArrayList<AbstractDomainObject> lista = racun.vratiListu(rs);

        assertNotNull(lista);
        assertEquals(1, lista.size());

        Racun ra = (Racun) lista.get(0);
        assertEquals(racunID, ra.getRacunID());
        assertEquals(datumVreme, ra.getDatumVreme());
        assertEquals(cena, ra.getCena());

        Administrator a = ra.getAdministrator();
        assertNotNull(a);
        assertEquals(adminID, a.getAdministratorID());
        assertEquals(adminIme, a.getIme());
        assertEquals(adminPrezime, a.getPrezime());
        assertEquals(adminUser, a.getUsername());
        assertEquals(adminPass, a.getPassword());

        verify(rs, times(1)).close();
    }

}
