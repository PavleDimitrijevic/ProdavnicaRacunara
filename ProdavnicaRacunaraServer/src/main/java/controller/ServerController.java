package controller;

import com.pavledimitrijevic.prodavnicaracunara.Administrator;
import com.pavledimitrijevic.prodavnicaracunara.Racun;
import com.pavledimitrijevic.prodavnicaracunara.Racunar;
import com.pavledimitrijevic.prodavnicaracunara.TipRacunara;
import java.util.ArrayList;
import so.administrator.SOAddAdministrator;
import so.administrator.SODeleteAdministrator;
import so.administrator.SOGetAllAdministrator;
import so.administrator.SOUpdateAdministrator;
import so.login.SOLogin;
import so.racun.SOAddRacun;
import so.racun.SOGetAllRacun;
import so.racunar.SOAddRacunar;
import so.racunar.SODeleteRacunar;
import so.racunar.SOGetAllRacunar;
import so.racunar.SOUpdateRacunar;
import so.tipRacunara.SOAddTipRacunara;
import so.tipRacunara.SODeleteTipRacunara;
import so.tipRacunara.SOGetAllTipRacunara;
import so.tipRacunara.SOUpdateTipRacunara;

/**
 *
 * @author PAVLE
 */
public class ServerController {

    private static ServerController instance;
    private ArrayList<Administrator> ulogovaniAdministratori = new ArrayList<>();

    private ServerController() {
    }

    public static ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }
        return instance;
    }

    public ArrayList<Administrator> getUlogovaniAdministratori() {
        return ulogovaniAdministratori;
    }

    public void setUlogovaniAdministratori(ArrayList<Administrator> ulogovaniAdministratori) {
        this.ulogovaniAdministratori = ulogovaniAdministratori;
    }

    public Administrator login(Administrator administrator) throws Exception {
        SOLogin so = new SOLogin();
        so.executeTemplate(administrator);
        return so.getUlogovani();
    }

    public void addAdministrator(Administrator administrator) throws Exception {
        (new SOAddAdministrator()).executeTemplate(administrator);
    }

    public void addTipRacunara(TipRacunara tipRacunara) throws Exception {
        (new SOAddTipRacunara()).executeTemplate(tipRacunara);
    }

    public void addRacun(Racun racun) throws Exception {
        (new SOAddRacun()).executeTemplate(racun);
    }

    public void addRacunar(Racunar racunar) throws Exception {
        (new SOAddRacunar()).executeTemplate(racunar);
    }

    public void deleteAdministrator(Administrator administrator) throws Exception {
        (new SODeleteAdministrator()).executeTemplate(administrator);
    }

    public void deleteTipRacunara(TipRacunara tipRacunara) throws Exception {
        (new SODeleteTipRacunara()).executeTemplate(tipRacunara);
    }

    public void deleteRacunar(Racunar racunar) throws Exception {
        (new SODeleteRacunar()).executeTemplate(racunar);
    }

    public void updateAdministrator(Administrator administrator) throws Exception {
        (new SOUpdateAdministrator()).executeTemplate(administrator);
    }

    public void updateTipRacunara(TipRacunara tipRacunara) throws Exception {
        (new SOUpdateTipRacunara()).executeTemplate(tipRacunara);
    }

    public void updateRacunar(Racunar racunar) throws Exception {
        (new SOUpdateRacunar()).executeTemplate(racunar);
    }

    public ArrayList<Administrator> getAllAdministrator() throws Exception {
        SOGetAllAdministrator so = new SOGetAllAdministrator();
        so.executeTemplate(new Administrator());
        return so.getLista();
    }

    public ArrayList<Racun> getAllRacun(Administrator admin) throws Exception {
        SOGetAllRacun so = new SOGetAllRacun();

        Racun ra = new Racun();
        ra.setAdministrator(admin);

        so.executeTemplate(ra);
        return so.getLista();
    }

    public ArrayList<Racunar> getAllRacunar(TipRacunara tr) throws Exception {
        SOGetAllRacunar so = new SOGetAllRacunar();

        Racunar r = new Racunar();
        r.setTipRacunara(tr);

        so.executeTemplate(r);
        return so.getLista();
    }

    public ArrayList<TipRacunara> getAllTipRacunara() throws Exception {
        SOGetAllTipRacunara so = new SOGetAllTipRacunara();
        so.executeTemplate(new TipRacunara());
        return so.getLista();
    }

    public void logout(Administrator ulogovani) {
        ulogovaniAdministratori.remove(ulogovani);
    }

}
