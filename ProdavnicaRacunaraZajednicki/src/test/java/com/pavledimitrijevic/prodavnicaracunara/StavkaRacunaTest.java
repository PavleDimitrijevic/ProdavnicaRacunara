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
public class StavkaRacunaTest {

    private StavkaRacuna stavkaRacuna;
    private Racun racun;
    private Racunar racunar;

    @Mock
    private ResultSet rs;

    @BeforeEach
    public void setUp() throws Exception {
        Administrator administrator = new Administrator(1L, "Pavle", "Dimitrijevic", "pavle123", "pavle123");
        ArrayList<StavkaRacuna> stavke = new ArrayList<>();
        stavke.add(stavkaRacuna);
        racun = new Racun(1L, new Date(), 800000.0, administrator, stavke);

        TipRacunara tip = new TipRacunara(1L, "Gaming racunar");
        ArrayList<Komponenta> komponente = new ArrayList<>();
        komponente.add(new Komponenta());
        racunar = new Racunar(1L, "Gaming Beast X2", 200000.0, "Odlican", tip, komponente);

        stavkaRacuna = new StavkaRacuna(racun, 1, 2, 400000.0, racunar);
    }

    @AfterEach
    public void tearDown() {
        stavkaRacuna = null;
        racun = null;
        racunar = null;
    }

    @Test
    public void StavkaRacunaTestPrazan() {
        assertNotNull(stavkaRacuna);
    }

    @Test
    public void StavkaRacunaTest() {
        assertNotNull(stavkaRacuna);
        assertEquals(racun, stavkaRacuna.getRacun());
        assertEquals(1, stavkaRacuna.getRb());
        assertEquals(2, stavkaRacuna.getKolicina());
        assertEquals(400000.0, stavkaRacuna.getCena());
        assertEquals(racunar, stavkaRacuna.getRacunar());
    }

    @Test
    public void testSetRacun() {
        assertEquals(racun, stavkaRacuna.getRacun());
    }

    @Test
    void testSetRacunNull() {
        assertThrows(java.lang.NullPointerException.class, () -> stavkaRacuna.setRacun(null));
    }

    @Test
    public void testSetRb() {
        assertEquals(1, stavkaRacuna.getRb());
    }

    @Test
    void testSetRbNula() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> stavkaRacuna.setRb(0));
    }

    @Test
    void testSetRbNegative() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> stavkaRacuna.setRb(-1));
    }

    @Test
    public void testSetKolicina() {
        assertEquals(2, stavkaRacuna.getKolicina());
    }

    @Test
    void testSetKolicinaNula() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> stavkaRacuna.setKolicina(0));
    }

    @Test
    void testSetKolicinaNegative() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> stavkaRacuna.setKolicina(-1));
    }

    @Test
    public void testSetCena() {
        assertEquals(400000.0, stavkaRacuna.getCena());
    }

    @Test
    void testSetCenaNula() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> stavkaRacuna.setCena(0.0));
    }

    @Test
    void testSetCenaNegative() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> stavkaRacuna.setCena(-400000.0));
    }

    @Test
    public void testSetRacunar() {
        assertEquals(racunar, stavkaRacuna.getRacunar());
    }

    @Test
    public void testSetRacunarNull() {
        assertThrows(java.lang.NullPointerException.class, () -> stavkaRacuna.setRacunar(null));
    }

    @Test
    void testNazivTabele() {
        assertEquals(" StavkaRacuna ", stavkaRacuna.nazivTabele());
    }

    @Test
    void testAlijas() {
        assertEquals(" sr ", stavkaRacuna.alijas());
    }

    @Test
    void testJoin() {
        String expectedJoin = " JOIN RACUNAR R ON (R.RACUNARID = SR.RACUNARID) "
                + "JOIN TIPRACUNARA TR ON (TR.TIPRACUNARAID = R.TIPRACUNARAID) "
                + "JOIN RACUN RA ON (RA.RACUNID = SR.RACUNID) "
                + "JOIN ADMINISTRATOR A ON (A.ADMINISTRATORID = RA.ADMINISTRATORID) ";
        assertEquals(expectedJoin, stavkaRacuna.join());
    }

    @Test
    void testKoloneZaInsert() {
        assertEquals(" (racunID, rb, kolicina, cena, racunarID) ", stavkaRacuna.koloneZaInsert());
    }

    @Test
    void testUslov() {
        assertEquals(" racunID = 1 AND RB = 1", stavkaRacuna.uslov());
    }

    @Test
    void testVrednostiZaInsert() {
        assertEquals(" 1, 1,  2, 400000.0, 1", stavkaRacuna.vrednostiZaInsert());
    }

    @Test
    void testVrednostiZaUpdate() {
        assertEquals("", stavkaRacuna.vrednostiZaUpdate());
    }

    @Test
    void testUslovZaSelect() {
        assertEquals(" WHERE RA.RACUNID = 1", stavkaRacuna.uslovZaSelect());
    }

    @Test
    void testVratiListu() throws SQLException {
        Long adminID = 10L;
        String adminIme = "Admin";
        String adminPrezime = "Test";
        String adminUser = "admin1";
        String adminPass = "pass123456";

        Long racunID = 200L;
        Timestamp datumVreme = new Timestamp(new Date().getTime());
        double racunCena = 400000.0;

        Long tipID = 30L;
        String tipNaziv = "Gaming racunar";

        Long racunarID = 40L;
        String racunarNaziv = "Gaming Beast X2";
        double racunarCena = 200000.0;
        String opis = "Odlican";

        int rb = 1;
        int kolicina = 2;
        double stavkaCena = 400000.0;

        when(rs.next()).thenReturn(true).thenReturn(false);

        when(rs.getLong("AdministratorID")).thenReturn(adminID);
        when(rs.getString("Ime")).thenReturn(adminIme);
        when(rs.getString("Prezime")).thenReturn(adminPrezime);
        when(rs.getString("Username")).thenReturn(adminUser);
        when(rs.getString("Password")).thenReturn(adminPass);

        when(rs.getLong("racunID")).thenReturn(racunID);
        when(rs.getTimestamp("datumVreme")).thenReturn(datumVreme);
        when(rs.getDouble("ra.cena")).thenReturn(racunCena);

        when(rs.getLong("TipRacunaraID")).thenReturn(tipID);
        when(rs.getString("tr.Naziv")).thenReturn(tipNaziv);

        when(rs.getLong("racunarID")).thenReturn(racunarID);
        when(rs.getString("r.naziv")).thenReturn(racunarNaziv);
        when(rs.getDouble("cenaPoKomadu")).thenReturn(racunarCena);
        when(rs.getString("opis")).thenReturn(opis);

        when(rs.getInt("rb")).thenReturn(rb);
        when(rs.getInt("kolicina")).thenReturn(kolicina);
        when(rs.getDouble("sr.cena")).thenReturn(stavkaCena);

        ArrayList<AbstractDomainObject> lista = stavkaRacuna.vratiListu(rs);

        assertNotNull(lista);
        assertEquals(1, lista.size());

        StavkaRacuna sr = (StavkaRacuna) lista.get(0);
        assertEquals(rb, sr.getRb());
        assertEquals(kolicina, sr.getKolicina());
        assertEquals(stavkaCena, sr.getCena());

        Racunar r = sr.getRacunar();
        assertNotNull(r);
        assertEquals(racunarID, r.getRacunarID());
        assertEquals(racunarNaziv, r.getNaziv());
        assertEquals(racunarCena, r.getCenaPoKomadu());
        assertEquals(opis, r.getOpis());

        TipRacunara tr = r.getTipRacunara();
        assertNotNull(tr);
        assertEquals(tipID, tr.getTipRacunaraID());
        assertEquals(tipNaziv, tr.getNaziv());

        Racun ra = sr.getRacun();
        assertNotNull(ra);
        assertEquals(racunID, ra.getRacunID());
        assertEquals(datumVreme, ra.getDatumVreme());
        assertEquals(racunCena, ra.getCena());

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
