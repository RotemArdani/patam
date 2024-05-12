package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {
    private int port;
    private ClientHandler ch;
    private volatile boolean stop;

    public MyServer (int port, ClientHandler ch){
        this.port = port;
        this.ch = ch;
        this.stop = false;
    }

    public void start(){
        new Thread(()->runServer() ).start(); 
    }

    public void close(){
        this.stop = true;
    }

    private void runServer() {
        try {
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(1000);
            while (!stop) {
                try {
                    Socket aClient = server.accept(); //blocking call. timout after 1 second
                    try {
                        ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());

                        aClient.getInputStream().close();
                        aClient.getOutputStream().close();
                        aClient.close();
                    } catch (IOException e) {
                    }
                } catch (SocketTimeoutException e) {
                } 
            } server.close();
        } catch (Exception e) {
        }
    }
}
