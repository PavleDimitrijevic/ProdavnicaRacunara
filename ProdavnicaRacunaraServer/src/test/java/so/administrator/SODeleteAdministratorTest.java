package so.administrator;

import com.pavledimitrijevic.prodavnicaracunara.Administrator;
import com.pavledimitrijevic.prodavnicaracunara.Racunar;
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
public class SODeleteAdministratorTest {

    private SODeleteAdministrator soDelete;
    private Administrator administrator;

    @BeforeEach
    void setUp() {
        soDelete = new SODeleteAdministrator();
        administrator = new Administrator();
    }

    @AfterEach
    void tearDown() {
        soDelete = null;
        administrator = null;
    }

    @Test
    @DisplayName("Uspesno brisanje administratora")
    void testExecuteSuccessful() throws SQLException {
        administrator.setIme("TestDelete");
        administrator.setPrezime("Execute");
        administrator.setUsername("user" + System.currentTimeMillis());
        administrator.setPassword("test12345");

        DBBroker.getInstance().insert(administrator); // dodavanje testnog administratora za potrebe testa

        assertDoesNotThrow(() -> soDelete.validate(administrator));
        assertDoesNotThrow(() -> soDelete.execute(administrator));

        DBBroker.getInstance().delete(administrator); // ciscenje baze da se ne bi popunila podacima

    }

    @Test
    @DisplayName("Pogresan tip podataka")
    void testValidationInvalidType() {
        assertThrows(java.lang.Exception.class, () -> soDelete.validate(new Racunar()));

    }

    @Test
    @DisplayName("Validacija ispravnog tipa")
    void testValidation() {
        assertDoesNotThrow(() -> soDelete.validate(administrator));
    }
}
