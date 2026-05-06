package session;

import com.pavledimitrijevic.prodavnicaracunara.Administrator;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author PAVLE
 */
public class Session {

    private static Session instance;
    private Socket socket;
    private Administrator ulogovani;

    private Session() {
        try {
            socket = new Socket("localhost", 10000);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setUlogovani(Administrator ulogovani) {
        this.ulogovani = ulogovani;
    }

    public Administrator getUlogovani() {
        return ulogovani;
    }
}
