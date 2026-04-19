package so.administrator;

import com.pavledimitrijevic.prodavnicaracunara.Administrator;
import com.pavledimitrijevic.prodavnicaracunara.Racunar;
import db.DBBroker;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author PAVLE
 */
public class SOGetAllAdministratorTest {

    private SOGetAllAdministrator soGetAll;
    private Administrator administrator;

    @BeforeEach
    void setUp() {
        soGetAll = new SOGetAllAdministrator();
        administrator = new Administrator();
    }

    @AfterEach
    void tearDown() {
        soGetAll = null;
        administrator = null;
    }

    @Test
    @DisplayName("Uspesno vracanje liste svih administratora")
    void testExecuteSuccessful() throws SQLException {
        administrator.setIme("TestGetAll");
        administrator.setPrezime("Execute");
        administrator.setUsername("user" + System.currentTimeMillis());
        administrator.setPassword("test12345");
        DBBroker.getInstance().insert(administrator); // dodavanje testnog administratora u bazu

        assertDoesNotThrow(() -> soGetAll.validate(administrator));
        assertDoesNotThrow(() -> soGetAll.execute(administrator));

        ArrayList<Administrator> lista = soGetAll.getLista();
        assertNotNull(lista); // provera da je lista inicijalizovana
        assertTrue(lista.stream().anyMatch(a -> a.getUsername().equals(administrator.getUsername()))); // u listi
        // trazimo
        // administratora
        // kog smo
        // dodali po
        // username-u

        DBBroker.getInstance().delete(administrator); // ciscenje baze da se ne bi bila prenatrpala podacima

    }

    @Test
    @DisplayName("Pogresan tip podataka")
    void testValidationInvalidType() {
        assertThrows(java.lang.Exception.class, () -> soGetAll.validate(new Racunar()));

    }

    @Test
    @DisplayName("Validacija ispravnog objekta")
    void testValidationValidObject() {
        administrator.setIme("Djorjde");
        administrator.setPrezime("Djordjevic");
        administrator.setUsername("valid" + System.currentTimeMillis());
        administrator.setPassword("valid12345");

        assertDoesNotThrow(() -> soGetAll.validate(administrator));
    }
}
