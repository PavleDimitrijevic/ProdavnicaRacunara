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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author PAVLE
 */
@ExtendWith(MockitoExtension.class)
public class AdministratorTest {

    private Administrator administrator;

    @Mock
    private ResultSet rs;

    @BeforeEach
    public void setUp() {
        administrator = new Administrator(1L, "Pavle", "Dimitrijevic", "pavle123", "pavle123");
    }

    @AfterEach
    public void tearDown() {
        administrator = null;
    }

    @Test
    public void AdministratorTestPrazan() {
        assertNotNull(administrator);
    }

    @Test
    public void AdministratorTest() {
        assertNotNull(administrator);
        assertEquals(1L, administrator.getAdministratorID());
        assertEquals("Pavle", administrator.getIme());
        assertEquals("Dimitrijevic", administrator.getPrezime());
        assertEquals("pavle123", administrator.getUsername());
        assertEquals("pavle123", administrator.getPassword());
    }

    @ParameterizedTest
    @CsvSource({
        "1, 1, true",
        "1, 2, false"
    })
    public void testEquals(Long id1, Long id2, boolean expected) {

        Administrator administrator1 = new Administrator();
        administrator1.setAdministratorID(id1);

        Administrator administrator2 = new Administrator();
        administrator2.setAdministratorID(id2);

        assertEquals(expected, administrator1.equals(administrator2));
    }

    @Test
    public void testToString() {

        assertTrue(administrator.toString().contains("Pavle"));
        assertTrue(administrator.toString().contains("Dimitrijevic"));
    }

    @Test
    public void testSetAdministratorID() {
        assertEquals(1L, administrator.getAdministratorID());
    }

    @Test
    void testSetAdministratorIDNull() {
        assertThrows(java.lang.NullPointerException.class, () -> administrator.setAdministratorID(null));
    }

    @Test
    void testSetAdministratorIDNegative() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> administrator.setAdministratorID(-1L));
    }

    @Test
    void testSetAdministratorIDNula() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> administrator.setAdministratorID(0L));
    }

    @Test
    public void testSetIme() {
        assertEquals("Pavle", administrator.getIme());
    }

    @Test
    void testSetImeNull() {
        assertThrows(java.lang.NullPointerException.class, () -> administrator.setIme(null));
    }

    @Test
    void testSetImeEmpty() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> administrator.setIme(""));
    }

    @Test
    void testSetImeBlankoZnak() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> administrator.setIme("   "));
    }

    @Test
    void testSetImeRazmak() {
        administrator.setIme("Djordje David");
        assertEquals("Djordje David", administrator.getIme());
    }

    @Test
    public void testSetPrezime() {
        assertEquals("Dimitrijevic", administrator.getPrezime());
    }

    @Test
    void testSetPrezimeNull() {
        assertThrows(java.lang.NullPointerException.class, () -> administrator.setPrezime(null));
    }

    @Test
    void testSetPrezimeEmpty() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> administrator.setPrezime(""));
    }

    @Test
    void testSetPrezimeBlankoZnak() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> administrator.setPrezime("   "));
    }

    @Test
    void testSetPrezimeRazmak() {
        administrator.setPrezime("Dimitrijevic Aleksic");
        assertEquals("Dimitrijevic Aleksic", administrator.getPrezime());
    }

    @Test
    public void testSetUsername() {
        assertEquals("pavle123", administrator.getUsername());
    }

    @Test
    void testSetUsernameNull() {
        assertThrows(java.lang.NullPointerException.class, () -> administrator.setUsername(null));
    }

    @Test
    void testSetUsernameEmpty() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> administrator.setUsername(""));
    }

    @Test
    public void testSetPassword() {
        assertEquals("pavle123", administrator.getPassword());
    }

    @Test
    public void testSetPasswordNull() {
        assertThrows(java.lang.NullPointerException.class, () -> administrator.setPassword(null));

    }

    @Test
    void testSetPasswordManje() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> administrator.setPassword("pavle"));
    }

    @Test
    void testNazivTabele() {
        assertEquals(" administrator ", administrator.nazivTabele());
    }

    @Test
    void testAlijas() {
        assertEquals(" a ", administrator.alijas());
    }

    @Test
    void testJoin() {
        assertEquals("", administrator.join());
    }

    @Test
    void testKoloneZaInsert() {
        assertEquals(" (Ime, Prezime, Username, Password) ", administrator.koloneZaInsert());
    }

    @Test
    void testUslov() {
        assertEquals(" AdministratorID = 1", administrator.uslov());
    }

    @Test
    void testVrednostiZaInsert() {
        assertEquals("'Pavle', 'Dimitrijevic', 'pavle123', 'pavle123'", administrator.vrednostiZaInsert());
    }

    @Test
    void testVrednostiZaUpdate() {
        assertEquals(" Ime = 'Pavle', Prezime = 'Dimitrijevic', Username = 'pavle123', Password = 'pavle123' ",
                administrator.vrednostiZaUpdate());
    }

    @Test
    void testUslovZaSelect() {
        assertEquals("", administrator.uslovZaSelect());
    }

    @Test
    void testVratiListu() throws SQLException {
        Long testID = 123L;
        String testIme = "TestIme";
        String testPrezime = "TestPrezime";
        String testUser = "TestUser";
        String testPass = "TestPass";

        when(rs.next()).thenReturn(true).thenReturn(false);

        when(rs.getLong("AdministratorID")).thenReturn(testID);
        when(rs.getString("Ime")).thenReturn(testIme);
        when(rs.getString("Prezime")).thenReturn(testPrezime);
        when(rs.getString("Username")).thenReturn(testUser);
        when(rs.getString("Password")).thenReturn(testPass);

        ArrayList<AbstractDomainObject> lista = administrator.vratiListu(rs);

        assertNotNull(lista);
        assertEquals(1, lista.size());

        Administrator a = (Administrator) lista.get(0);
        assertEquals(testID, a.getAdministratorID());
        assertEquals(testIme, a.getIme());
        assertEquals(testPrezime, a.getPrezime());
        assertEquals(testUser, a.getUsername());
        assertEquals(testPass, a.getPassword());

        verify(rs, times(1)).close();
    }
}
