package so.administrator;

import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import com.pavledimitrijevic.prodavnicaracunara.Administrator;
import com.pavledimitrijevic.prodavnicaracunara.Racunar;
import db.DBBroker;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author PAVLE
 */
public class SOUpdateAdministratorTest {

    private SOUpdateAdministrator soUpdate;
    private Administrator administrator;

    @BeforeEach
    void setUp() {
        soUpdate = new SOUpdateAdministrator();
        administrator = new Administrator();
    }

    @AfterEach
    void tearDown() {
        soUpdate = null;
        administrator = null;
    }

    @Test
    @DisplayName("Uspesno azuriranje administratora u bazi")
    void testExecuteSuccessful() throws SQLException {
        administrator.setIme("TestUpdate");
        administrator.setPrezime("Execute");
        administrator.setUsername("user" + System.currentTimeMillis());
        administrator.setPassword("test12345");
        DBBroker.getInstance().insert(administrator); // dodavanje testnog administratora u bazu

        @SuppressWarnings("unchecked")
        ArrayList<Administrator> administratori = (ArrayList<Administrator>) (ArrayList<?>) DBBroker.getInstance()
                .select(administrator); // izvlacimo iz baze
        Administrator ubacen = administratori.stream().filter(a -> a.getUsername().equals(administrator.getUsername()))
                .findFirst().orElseThrow(() -> new RuntimeException("Administrator nije pronadjen nakon inserta!"));

        String noviUsername = "updatedUser" + System.currentTimeMillis();
        ubacen.setUsername(noviUsername);

        assertDoesNotThrow(() -> soUpdate.validate(ubacen));
        assertDoesNotThrow(() -> soUpdate.execute(ubacen));

        @SuppressWarnings("unchecked")
        ArrayList<Administrator> lista = (ArrayList<Administrator>) (ArrayList<?>) DBBroker.getInstance()
                .select(new Administrator());
        assertTrue(
                lista.stream()
                        .anyMatch(a -> a.getAdministratorID().equals(ubacen.getAdministratorID())
                        && a.getUsername().equals(noviUsername)),
                "Administrator nije azuriran sa novim username-om u bazi!");

        DBBroker.getInstance().delete(administrator); // ciscenje baze da se ne bi prenatrpala podacima

    }

    @Test
    @DisplayName("Pogresan tip podataka")
    void testValidationInvalidType() {
        assertThrows(java.lang.Exception.class, () -> soUpdate.validate(new Racunar()));
    }

    @Test
    @DisplayName("Username nije jedinstven u bazi")
    void testValidationDuplicateUsername() throws SQLException {
        String username = "user" + System.currentTimeMillis();

        Administrator prvi = new Administrator();
        prvi.setIme("ImePrvi");
        prvi.setPrezime("PrezimePrvi");
        prvi.setUsername(username);
        prvi.setPassword("sifraPrvi123");
        DBBroker.getInstance().insert(prvi);

        Administrator duplikat = new Administrator();
        duplikat.setIme("ImeDuplikat");
        duplikat.setPrezime("PrezimeDuplikat");
        duplikat.setUsername("UsernameDrugi" + System.currentTimeMillis());
        duplikat.setPassword("sifraDrugi123");
        DBBroker.getInstance().insert(duplikat);

        duplikat.setUsername(prvi.getUsername()); // postavljanje username-a da bude kao za prvog administratora

        assertThrows(java.lang.Exception.class, () -> soUpdate.validate(duplikat));

        DBBroker.getInstance().delete(prvi);
        DBBroker.getInstance().delete(duplikat);

    }

}
