package so.administrator;

import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author PAVLE
 */
public class SOUpdateAdministratorTest {
    
    public SOUpdateAdministratorTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testValidate() throws Exception {
        System.out.println("validate");
        AbstractDomainObject ado = null;
        SOUpdateAdministrator instance = new SOUpdateAdministrator();
        instance.validate(ado);
        fail("The test case is a prototype.");
    }

    @Test
    public void testExecute() throws Exception {
        System.out.println("execute");
        AbstractDomainObject ado = null;
        SOUpdateAdministrator instance = new SOUpdateAdministrator();
        instance.execute(ado);
        fail("The test case is a prototype.");
    }
    
}
