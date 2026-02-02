/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package communication;

import java.net.Socket;
import domain.Korisnik;
import domain.Prijava;
import transfer.Request;
import cons.Operation;
import java.io.IOException;
import transfer.Response;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author matij
 */
public class Communication {

    private static Communication instance;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Communication() {
        try {
            socket = new Socket("localhost", 9000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Greška pri konenkciji");
            exitSocket();
        }
    }

    public static Communication getInstance() {
        if (instance == null) {
            instance = new Communication();
        }
        return instance;
    }

    private Response sendRequest(Operation operation, Object argument) throws Exception {
        try {
            Request request = new Request();
            request.setOperation(operation);
            request.setArgument(argument);
            out.writeObject(request);
            out.flush();

            return (Response) in.readObject();

        } catch (java.net.SocketException | java.io.EOFException e) {
            handleServerDown();
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    private void handleServerDown() {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null,
                    "Server je zaustavljen! Aplikacija će se ugasiti.",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(0);
        });
    }

    public Korisnik login(String username, String password) throws Exception {
        Korisnik k = new Korisnik();
        k.setUsername(username);
        k.setPassword(password);

        Response response = sendRequest(Operation.LOGIN, k);

        if (response.getException() != null) {
            throw response.getException();
        }
        return (Korisnik) response.getResult();
    }

    public void addPrijava(Prijava prijava) throws Exception {
        Response response = sendRequest(Operation.ADD_APPLICATION, prijava);

        if (response.getException() != null) {
            throw response.getException();
        }
    }

    public void register(Korisnik k) throws Exception {
        Response response = sendRequest(Operation.REGISTER, k);
        if (response.getException() != null) {
            throw response.getException();
        }
    }

    public List<Prijava> getUserApplications(Korisnik k) throws Exception {
        Response response = sendRequest(Operation.GET_USER_APPLICATIONS, k);
        if (response.getException() != null) {
            throw response.getException();
        }
        return (List<Prijava>) response.getResult();
    }

    public void otkaziPrijavu(Prijava p) throws Exception {
        Response response = sendRequest(Operation.CANCEL_APPLICATION, p);
        if (response.getException() != null) {
            throw response.getException();
        }
    }

    public void editPrijava(Prijava p) throws Exception {
        Response response = sendRequest(Operation.EDIT_APPLICATION, p);
        if (response.getException() != null) {
            throw response.getException();
        }
    }

    private void exitSocket() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socket != null || !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException ex) {
            System.getLogger(Communication.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
