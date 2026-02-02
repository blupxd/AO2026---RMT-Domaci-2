/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threads;

import controller.Controller;
import domain.Korisnik;
import domain.Prijava;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import transfer.Request;
import transfer.Response;

/**
 *
 * @author matij
 */
public class ClientHandler extends Thread {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Korisnik ulogovaniKorisnik;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                Request request = (Request) in.readObject();
                Response response = new Response();
                try {
                    switch (request.getOperation()) {
                        case LOGIN:
                            Korisnik loginRequest = (Korisnik) request.getArgument();
                            Korisnik ulogovan = Controller.getInstance().login(loginRequest.getUsername(), loginRequest.getPassword());
                            for (ClientHandler ch : ServerThread.klijenti) {
                                if (ch.getUlogovaniKorisnik() != null) {
                                    if (ch.getUlogovaniKorisnik().getJmbg().equals(ulogovan.getJmbg())) {
                                        throw new Exception("Korisnik je već ulogovan na drugom mestu!");
                                    }
                                }
                            }
                            this.ulogovaniKorisnik = ulogovan;
                            response.setResult(ulogovan);
                            break;
                        case REGISTER:
                            Korisnik registerRequest = (Korisnik) request.getArgument();
                            Controller.getInstance().register(registerRequest);
                            response.setResult("Uspesna registracija!");
                            break;
                        case ADD_APPLICATION:
                            Prijava prijava = (Prijava) request.getArgument();
                            prijava.setKorisnik(ulogovaniKorisnik);
                            Controller.getInstance().addPrijava(prijava);
                            response.setResult("Prijava uspesno sacuvana!");
                            break;
                        case GET_USER_APPLICATIONS:
                            Korisnik onajKojiTrazi = (Korisnik) request.getArgument();
                            List<Prijava> lista = Controller.getInstance().getPrijaveZaKorisnika(onajKojiTrazi);
                            response.setResult(lista);
                            break;
                        case CANCEL_APPLICATION:
                            Prijava zaBrisanje = (Prijava) request.getArgument();
                            Controller.getInstance().obrisiPrijavu(zaBrisanje);
                            response.setResult("Prijava uspešno otkazana.");
                            break;
                        case EDIT_APPLICATION:
                            Prijava izmenjena = (Prijava) request.getArgument();
                            Controller.getInstance().izmeniPrijavu(izmenjena);
                            response.setResult("Prijava uspešno izmenjena!");
                            break;
                    }
                } catch (Exception e) {
                    response.setException(e);
                }
                out.writeObject(response);
            }
        } catch (Exception e) {
            System.out.println("Klijent se diskonektovao.");
            ServerThread.klijenti.remove(this);
        }
    }

    public void ugasiHandler() {
        try {

            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Korisnik getUlogovaniKorisnik() {
        return ulogovaniKorisnik;
    }

    public void setUlogovaniKorisnik(Korisnik ulogovaniKorisnik) {
        this.ulogovaniKorisnik = ulogovaniKorisnik;
    }
}
