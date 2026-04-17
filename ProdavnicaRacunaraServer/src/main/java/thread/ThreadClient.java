package thread;

import controller.ServerController;
import com.pavledimitrijevic.prodavnicaracunara.Administrator;
import com.pavledimitrijevic.prodavnicaracunara.Racun;
import com.pavledimitrijevic.prodavnicaracunara.Racunar;
import com.pavledimitrijevic.prodavnicaracunara.TipRacunara;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import transfer.Request;
import transfer.Response;
import transfer.util.Operation;
import transfer.util.ResponseStatus;

/**
 *
 * @author PAVLE
 */
public class ThreadClient extends Thread {

    private Socket socket;

    ThreadClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Request request = (Request) in.readObject();
                Response response = handleRequest(request);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response handleRequest(Request request) {
        Response response = new Response(null, null, ResponseStatus.Success);
        try {
            switch (request.getOperation()) {
                case Operation.ADD_ADMINISTRATOR:
                    ServerController.getInstance().addAdministrator((Administrator) request.getData());
                    break;
                case Operation.ADD_RACUNAR:
                    ServerController.getInstance().addRacunar((Racunar) request.getData());
                    break;
                case Operation.ADD_TIP_RACUNARA:
                    ServerController.getInstance().addTipRacunara((TipRacunara) request.getData());
                    break;
                case Operation.ADD_RACUN:
                    ServerController.getInstance().addRacun((Racun) request.getData());
                    break;
                case Operation.DELETE_ADMINISTRATOR:
                    ServerController.getInstance().deleteAdministrator((Administrator) request.getData());
                    break;
                case Operation.DELETE_TIP_RACUNARA:
                    ServerController.getInstance().deleteTipRacunara((TipRacunara) request.getData());
                    break;
                case Operation.DELETE_RACUNAR:
                    ServerController.getInstance().deleteRacunar((Racunar) request.getData());
                    break;
                case Operation.UPDATE_ADMINISTRATOR:
                    ServerController.getInstance().updateAdministrator((Administrator) request.getData());
                    break;
                case Operation.UPDATE_TIP_RACUNARA:
                    ServerController.getInstance().updateTipRacunara((TipRacunara) request.getData());
                    break;
                case Operation.UPDATE_RACUNAR:
                    ServerController.getInstance().updateRacunar((Racunar) request.getData());
                    break;
                case Operation.GET_ALL_ADMINISTRATOR:
                    response.setData(ServerController.getInstance().getAllAdministrator());
                    break;
                case Operation.GET_ALL_RACUN:
                    response.setData(ServerController.getInstance().getAllRacun((Administrator) request.getData()));
                    break;
                case Operation.GET_ALL_RACUNAR:
                    response.setData(ServerController.getInstance().getAllRacunar((TipRacunara) request.getData()));
                    break;
                case Operation.GET_ALL_TIP_RACUNARA:
                    response.setData(ServerController.getInstance().getAllTipRacunara());
                    break;
                case Operation.LOGIN:
                    Administrator administrator = (Administrator) request.getData();
                    Administrator a = ServerController.getInstance().login(administrator);
                    response.setData(a);
                    break;
                case Operation.LOGOUT:
                    Administrator ulogovani = (Administrator) request.getData();
                    ServerController.getInstance().logout(ulogovani);
                    break;
                default:
                    return null;
            }
        } catch (Exception e) {
            response.setResponseStatus(ResponseStatus.Error);
            response.setException(e);
        }
        return response;
    }
}
