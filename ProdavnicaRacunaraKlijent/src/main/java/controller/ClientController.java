package controller;

import com.pavledimitrijevic.prodavnicaracunara.Administrator;
import com.pavledimitrijevic.prodavnicaracunara.Racun;
import com.pavledimitrijevic.prodavnicaracunara.Racunar;
import com.pavledimitrijevic.prodavnicaracunara.TipRacunara;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import session.Session;
import transfer.Request;
import transfer.Response;
import transfer.util.Operation;
import transfer.util.ResponseStatus;

/**
 *
 * @author PAVLE
 */
public class ClientController {

    private static ClientController instance;

    private ClientController() {
    }

    public static ClientController getInstance() {
        if (instance == null) {
            instance = new ClientController();
        }
        return instance;
    }

    public Administrator login(Administrator administrator) throws Exception {
        return (Administrator) sendRequest(Operation.LOGIN, administrator);
    }

    public void logout(Administrator ulogovani) throws Exception {
        sendRequest(Operation.LOGOUT, ulogovani);
    }

    public void addAdministrator(Administrator administrator) throws Exception {
        sendRequest(Operation.ADD_ADMINISTRATOR, administrator);
    }

    public void addTipRacunara(TipRacunara tipRacunara) throws Exception {
        sendRequest(Operation.ADD_TIP_RACUNARA, tipRacunara);
    }

    public void addRacunar(Racunar racunar) throws Exception {
        sendRequest(Operation.ADD_RACUNAR, racunar);
    }

    public void addRacun(Racun racun) throws Exception {
        sendRequest(Operation.ADD_RACUN, racun);
    }

    public void deleteAdministrator(Administrator administrator) throws Exception {
        sendRequest(Operation.DELETE_ADMINISTRATOR, administrator);
    }

    public void deleteTipRacunara(TipRacunara tipRacunara) throws Exception {
        sendRequest(Operation.DELETE_TIP_RACUNARA, tipRacunara);
    }

    public void deleteRacunar(Racunar racunar) throws Exception {
        sendRequest(Operation.DELETE_RACUNAR, racunar);
    }

    public void updateAdministrator(Administrator administrator) throws Exception {
        sendRequest(Operation.UPDATE_ADMINISTRATOR, administrator);
    }

    public void updateRacunar(Racunar racunar) throws Exception {
        sendRequest(Operation.UPDATE_RACUNAR, racunar);
    }

    public void updateTipRacunara(TipRacunara tipRacunara) throws Exception {
        sendRequest(Operation.UPDATE_TIP_RACUNARA, tipRacunara);
    }

    public ArrayList<Administrator> getAllAdministrator() throws Exception {
        return (ArrayList<Administrator>) sendRequest(Operation.GET_ALL_ADMINISTRATOR, null);
    }

    public ArrayList<Racun> getAllRacun(Administrator a) throws Exception {
        return (ArrayList<Racun>) sendRequest(Operation.GET_ALL_RACUN, a);
    }

    public ArrayList<Racunar> getAllRacunar(TipRacunara tr) throws Exception {
        return (ArrayList<Racunar>) sendRequest(Operation.GET_ALL_RACUNAR, tr);
    }

    public ArrayList<TipRacunara> getAllTipRacunara() throws Exception {
        return (ArrayList<TipRacunara>) sendRequest(Operation.GET_ALL_TIP_RACUNARA, null);
    }

    private Object sendRequest(int operation, Object data) throws Exception {
        Request request = new Request(operation, data);

        ObjectOutputStream out = new ObjectOutputStream(Session.getInstance().getSocket().getOutputStream());
        out.writeObject(request);

        ObjectInputStream in = new ObjectInputStream(Session.getInstance().getSocket().getInputStream());
        Response response = (Response) in.readObject();

        if (response.getResponseStatus().equals(ResponseStatus.Error)) {
            throw response.getException();
        } else {
            return response.getData();
        }

    }
}
