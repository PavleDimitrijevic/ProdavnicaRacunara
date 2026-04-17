package so;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import java.sql.SQLException;

/**
 *
 * @author PAVLE
 */
public abstract class SistemskeOperacije {

    protected abstract void validate(AbstractDomainObject ado) throws Exception;

    protected abstract void execute(AbstractDomainObject ado) throws Exception;

    public void executeTemplate(AbstractDomainObject ado) throws Exception {
        try {
            validate(ado);
            execute(ado);
            commit();
        } catch (Exception e) {
            rollback();
            throw e;
        }
    }

    private void commit() throws SQLException {
        DBBroker.getInstance().getConnection().commit();
    }

    private void rollback() throws SQLException {
        DBBroker.getInstance().getConnection().rollback();
    }

}
