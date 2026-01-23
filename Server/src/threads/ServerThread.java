/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author matij
 */
public class ServerThread extends Thread {

    private ServerSocket serverSocket;
    private List<ClientHandler> klijenti;

    public ServerThread() {
        try {
            serverSocket = new ServerSocket(9000);
            klijenti = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Server je pokrenut. Ceka se na klijenta");
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Klijent je povezan");
                ClientHandler ch = new ClientHandler(socket);
                klijenti.add(ch);
                ch.start();
            }
        } catch (Exception e) {
            System.out.println("Server je zaustavljen.");
        }
    }

    public void stopServer() {
        try {
            for (ClientHandler klijent : klijenti) {
                klijent.ugasiHandler();
            }
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
