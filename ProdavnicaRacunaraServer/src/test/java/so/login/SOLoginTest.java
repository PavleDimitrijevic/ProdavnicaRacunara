package so.login;

import com.pavledimitrijevic.prodavnicaracunara.Administrator;
import com.pavledimitrijevic.prodavnicaracunara.Racunar;
import controller.ServerController;
import db.DBBroker;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author PAVLE
 */
public class SOLoginTest {

    private SOLogin soLogin;
    private Administrator administrator;

    @BeforeEach
    void setUp() throws Exception {
        soLogin = new SOLogin();

        // testni administrator
        administrator = new Administrator();
        administrator.setIme("TestAdmin");
        administrator.setPrezime("TestPrezime");
        administrator.setUsername("user" + System.currentTimeMillis());
        administrator.setPassword("test12345");

        DBBroker.getInstance().insert(administrator); // dodavanje testnog administratora u bazu

    }

    @AfterEach
    void tearDown() throws SQLException {
        ServerController.getInstance().getUlogovaniAdministratori().clear(); // ciscenje liste ulogovanih
        DBBroker.getInstance().delete(administrator); // ciscenje baze da se ne bi prenatrpavala podacima
        soLogin = null;
        administrator = null;
    }

    @Test
    @DisplayName("Uspesan login administratora")
    void testExecuteSuccessful() {
        assertDoesNotThrow(() -> soLogin.validate(administrator));
        assertDoesNotThrow(() -> soLogin.execute(administrator));

        Administrator ulogovani = soLogin.getUlogovani();
        assertNotNull(ulogovani, "Ulogovani administrator ne sme biti null!");
        assertEquals(administrator.getUsername(), ulogovani.getUsername());
        assertTrue(ServerController.getInstance().getUlogovaniAdministratori().contains(ulogovani));
    }

    @Test
    @DisplayName("Pogresan tip objekta")
    void testValidationInvalidType() {
        assertThrows(java.lang.Exception.class, () -> soLogin.validate(new Racunar()));
    }

    @Test
    @DisplayName("Login sa pogresnim username-om")
    void testLoginInvalidUsername() {
        Administrator pogresanUsername = new Administrator();
        pogresanUsername.setIme("TestIme");
        pogresanUsername.setPrezime("TestPrezime");
        pogresanUsername.setUsername("nepostojeciUser" + System.currentTimeMillis());
        pogresanUsername.setPassword("test12345");

        assertDoesNotThrow(() -> soLogin.validate(pogresanUsername)); // prvo validiranje pravog tipa i da li je vec
        // ulogovan

        assertThrows(java.lang.Exception.class, () -> soLogin.execute(pogresanUsername));
    }

    @Test
    @DisplayName("Login sa pogresnom sifrom")
    void testLoginInvalidPassword() {
        Administrator pogresnaSifra = new Administrator();
        pogresnaSifra.setIme(administrator.getIme());
        pogresnaSifra.setPrezime(administrator.getPrezime());
        pogresnaSifra.setUsername(administrator.getUsername()); // isti username koji postoji u bazi
        pogresnaSifra.setPassword("pogresnaSifra");

        assertDoesNotThrow(() -> soLogin.validate(pogresnaSifra)); // prvo validiranje pravog tipa i da li je vec
        // ulogovan

        assertThrows(java.lang.Exception.class, () -> soLogin.execute(pogresnaSifra));
    }

    @Test
    @DisplayName("Login vec ulogovanog administratora")
    void testValidationAlreadyLoggedIn() throws Exception {
        ServerController.getInstance().getUlogovaniAdministratori().add(administrator); // dodavanje ulogovanog
        // administratora u listu pre
        // poziva validate()

        assertThrows(java.lang.Exception.class, () -> soLogin.validate(administrator));
    }

}
