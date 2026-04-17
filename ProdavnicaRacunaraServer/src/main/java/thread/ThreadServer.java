package thread;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author PAVLE
 */
public class ThreadServer extends Thread {

    private ServerSocket serverSocket;

    public ThreadServer() {
        try {
            serverSocket = new ServerSocket(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Klijent se povezao!");
                //ThreadClient th = new ThreadClient(socket);
                //th.start();
            }
        } catch (Exception e) {
            if (serverSocket.isClosed()) {
                System.out.println("Server je zatvoren.");
            } else {
                e.printStackTrace();
            }
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

}
