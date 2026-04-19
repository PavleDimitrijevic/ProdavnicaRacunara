package so;

import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import java.sql.SQLException;

/**
 * Apstraktna klasa koja definise sablon (template method) za izvrsavanje svih
 * sistemskih operacija.
 * <p>
 * Implementira logiku koja obezbedjuje jedinstven tok svake operacije:
 * validacija, izvrsavanje, i zatim commit ili rollback u zavisnosti od
 * rezultata. Svaka konkretna sistemska operacija mora da implementira metode
 * validate() i execute().
 * </p>
 *
 * <p>
 * Ova klasa primenjuje <b>Template Method pattern</b>
 * </p>
 *
 * @author PAVLE
 */
public abstract class SistemskeOperacije {

    /**
     * Metoda koja vrsi validaciju prosledjenog domen objekta.
     *
     * Konkretne klase moraju implementirati ovu metodu da bi proverile
     * ispravnost podataka pre nego sto se izvrsi operacija nad bazom.
     *
     *
     * @param ado Domen objekat koji se validira
     * @throws java.lang.Exception ako prosledjeni objekat nije ispravan
     */
    protected abstract void validate(AbstractDomainObject ado) throws Exception;

    /**
     * Metoda koja izvrsava konkretnu sistemsku operaciju nad prosledjenim domen
     * objektom. Implementacija ove metode zavisi od konkretne sistemske
     * operacije (npr. login, unos, izmena...).
     *
     *
     * @param ado Domen objekat nad kojim se izvrsava operacija
     * @throws java.lang.Exception ako dodje do greske prilikom izvrsavanja
     * operacije
     */
    protected abstract void execute(AbstractDomainObject ado) throws Exception;

    /**
     * Metoda koja poziva metode validate() i execute() i automatski obavlja
     * commit ili rollback.
     * <p>
     * U slucaju uspeha vrsi se commit transakcije, a u slucaju greske rollback.
     * </p>
     *
     * @param ado Domen objekat nad kojim se izvrsava operacija
     * @throws java.lang.Exception ako dodje do greske tokom validacije ili
     * izvrsavanja
     */
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

    /**
     * Potvrdjuje (commit) transakciju u okviru trenutne konekcije.
     *
     * @throws java.sql.SQLException ako dodje do greske prilikom commit-a
     */
    private void commit() throws SQLException {
        DBBroker.getInstance().getConnection().commit();
    }

    /**
     * Vraca (rollback) sve promene u bazi u slucaju greske tokom izvrsavanja
     * sistemske operacije.
     *
     * @throws java.sql.SQLException ako dodje do greske prilikom rollback-a
     */
    private void rollback() throws SQLException {
        DBBroker.getInstance().getConnection().rollback();
    }

}
