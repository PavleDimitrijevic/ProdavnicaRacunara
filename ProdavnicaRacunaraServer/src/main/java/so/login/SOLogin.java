package so.login;

import controller.ServerController;
import db.DBBroker;
import com.pavledimitrijevic.prodavnicaracunara.AbstractDomainObject;
import com.pavledimitrijevic.prodavnicaracunara.Administrator;
import java.util.ArrayList;
import so.SistemskeOperacije;

/**
 * Sistemska operacija za prijavu administratora na sistem.
 * <p>
 * Klasa nasledjuje apstraktnu klasu SistemskeOperacije i implementira metode validate()
 * i execute().
 * </p>
 *
 * @author PAVLE
 */
public class SOLogin extends SistemskeOperacije {

    /**
     * Administrator koji je uspesno prijavljen na sistem.
     */
    Administrator ulogovani;

    /**
     * Proverava ispravnost prosledjenog objekta pre nego sto se izvrsi
     * prijavljivanje na sistem. Metoda proverava:
     * <ul>
     * <li>Da li je objekat instanca klase Administrator.</li>
     * <li>Da li je administrator vec prijavljen.</li>
     * </ul>
     * Ako bilo koji od uslova nije ispunjen, baca se izuzetak.
     *
     * @param ado Domen objekat koji se proverava
     * @throws java.lang.Exception ako objekat nije instanca klase Administrator
     * ili ako je administrator vec ulogovan
     */
    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Administrator)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Administrator!");
        }

        Administrator a = (Administrator) ado;

        for (Administrator administrator : ServerController.getInstance().getUlogovaniAdministratori()) {
            if (administrator.getUsername().equals(a.getUsername())) {
                throw new Exception("Ovaj administrator je vec ulogovan na sistem!");
            }
        }

    }

    /**
     * Izvrsava prijavu administratora na sistem.
     * <p>
     * Pretrazuje bazu i proverava da li postoji administrator sa prosledjenim
     * username-om i lozinkom. Ako je prijava uspesna, dodaje administratora u
     * listu ulogovanih korisnika.
     * </p>
     *
     * @param ado Domen objekat koji se pretrazuje
     * @throws java.lang.Exception ako username ili lozinka nisu ispravni
     */
    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {

        Administrator a = (Administrator) ado;

        ArrayList<Administrator> administratori
                = (ArrayList<Administrator>) (ArrayList<?>) DBBroker.getInstance().select(ado);

        for (Administrator administrator : administratori) {
            if (administrator.getUsername().equals(a.getUsername())
                    && administrator.getPassword().equals(a.getPassword())) {
                ulogovani = administrator;

                ServerController.getInstance().getUlogovaniAdministratori().add(administrator);
                return;
            }
        }

        throw new Exception("Korisnicko ime i sifra nisu ispravni.");

    }

    /**
     * Vraca administratora koji je uspesno prijavljen na sistem.
     *
     * @return ulogovani administrator ili null ako prijava nije izvrsena
     */
    public Administrator getUlogovani() {
        return ulogovani;
    }
}
