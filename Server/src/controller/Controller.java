/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import domain.Korisnik;
import domain.Prijava;
import forms.FrmServer;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author matij
 */
public class Controller {

    private static Controller instance;
    private List<Korisnik> korisnici;
    private List<Prijava> prijave;
    private FrmServer serverForm;
    private final String FILE_KORISNICI = "korisnici.dat";
    private final String FILE_PRIJAVE = "prijave.dat";

    private Controller() {
        korisnici = new ArrayList<>();
        prijave = new ArrayList<>();
        ucitajPodatke();
        if (korisnici.isEmpty()) {
            korisnici.add(new Korisnik("Matija", "Stefanovic", "1234567890123", "admin@gmail.com", "admin", "admin"));
            sacuvajPodatke();
        }
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public List<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(List<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    public List<Prijava> getPrijave() {
        return prijave;
    }

    public void setPrijave(List<Prijava> prijave) {
        this.prijave = prijave;
    }

    public void setServerForm(forms.FrmServer form) {
        this.serverForm = form;
    }

    public void register(Korisnik k) throws Exception {
        for (Korisnik postojeci : korisnici) {
            if (postojeci.getUsername().equals(k.getUsername())) {
                throw new Exception("Korisnik sa tim korisničkim imenom već postoji!");
            }
            if (postojeci.getJmbg().equals(k.getJmbg())) {
                throw new Exception("Korisnik sa tim JMBG-om već postoji u sistemu!");
            }
            if (postojeci.getEmail().equals(k.getEmail())) {
                throw new Exception("Korisnik sa tim email-om već postoji!");
            }
        }
        korisnici.add(k);
        sacuvajPodatke();
    }

    public Korisnik login(String username, String password) throws Exception {
        for (Korisnik k : korisnici) {
            if (k.getUsername().equals(username) && k.getPassword().equals(password)) {
                return k;
            }
        }
        throw new Exception("Neispravni kredencijali!");
    }

    public void addPrijava(Prijava novaPrijava) throws Exception {
        proveriOgranicenja(novaPrijava);

        prijave.add(novaPrijava);
        sacuvajPodatke();

        System.out.println("Nova prijava dodata. Osvežavam tabelu...");
        if (serverForm != null) {
            serverForm.osveziTabelu();
        }
    }

    private void proveriOgranicenja(Prijava nova) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String noviDatum = sdf.format(nova.getDatumVolontiranja());
        List<String> koriscenePozicije = new ArrayList<>();

        for (Prijava p : prijave) {
            if (p.getKorisnik().getJmbg().equals(nova.getKorisnik().getJmbg())) {

                String postojeciDatum = sdf.format(p.getDatumVolontiranja());
                if (postojeciDatum.equals(noviDatum)) {
                    throw new Exception("Već imate prijavu za ovaj datum (" + sdf.format(nova.getDatumVolontiranja()) + ")!");
                }

                String pozicija = p.getPozicija().toString();
                if (!koriscenePozicije.contains(pozicija)) {
                    koriscenePozicije.add(pozicija);
                }
            }
        }
        String novaPozicija = nova.getPozicija().toString();

        if (koriscenePozicije.size() >= 2 && !koriscenePozicije.contains(novaPozicija)) {
            throw new Exception("Možete birati najviše 2 različite pozicije tokom turnira! Već ste birali: " + koriscenePozicije);
        }
        int brVecernjih = 0;
        int brPopodnevnih = 0;

        for (Prijava p : prijave) {
            if (p.getKorisnik().getJmbg().equals(nova.getKorisnik().getJmbg())) {
                if (p.getId().equals(nova.getId())) {
                    continue;
                }

                String smena = p.getSmena().toString();
                if (smena.equalsIgnoreCase("VECERNJA")) {
                    brVecernjih++;
                } else if (smena.equalsIgnoreCase("POPODNEVNA")) {
                    brPopodnevnih++;
                }
            }
        }

        String novaSmena = nova.getSmena().toString();

        if (novaSmena.equalsIgnoreCase("VECERNJA")) {
            if (brVecernjih >= 3) {
                throw new Exception("Dostigli ste limit od 3 večernje smene!");
            }
        } else if (novaSmena.equalsIgnoreCase("POPODNEVNA")) {
            if (brPopodnevnih >= 5) {
                throw new Exception("Dostigli ste limit od 5 popodnevnih smena!");
            }
        }
    }

    public void obrisiPrijavu(Prijava p) throws Exception {
        prijave.removeIf(postojeca -> postojeca.getId().equals(p.getId()));

        sacuvajPodatke();
        if (serverForm != null) {
            serverForm.osveziTabelu();
        }
    }

    private void sacuvajPodatke() {
        try {
            ObjectOutputStream oosKorisnici = new ObjectOutputStream(new FileOutputStream(FILE_KORISNICI));
            oosKorisnici.writeObject(korisnici);
            oosKorisnici.close();

            ObjectOutputStream oosPrijave = new ObjectOutputStream(new FileOutputStream(FILE_PRIJAVE));
            oosPrijave.writeObject(prijave);
            oosPrijave.close();

        } catch (IOException ex) {
            System.out.println("Greska prilikom cuvanja fajlova: " + ex.getMessage());
        }
    }

    private void ucitajPodatke() {
        try {
            File fKorisnici = new File(FILE_KORISNICI);
            if (fKorisnici.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fKorisnici));
                korisnici = (List<Korisnik>) ois.readObject();
                ois.close();
            }

            File fPrijave = new File(FILE_PRIJAVE);
            if (fPrijave.exists()) {
                ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream(fPrijave));
                prijave = (List<Prijava>) ois2.readObject();
                ois2.close();
            }

        } catch (Exception ex) {
            System.out.println("Greska prilikom ucitavanja: " + ex.getMessage());
        }
    }

    public List<Prijava> getPrijaveZaKorisnika(Korisnik k) {
        List<Prijava> mojePrijave = new ArrayList<>();
        for (Prijava p : prijave) {
            if (p.getKorisnik().getJmbg().equals(k.getJmbg())) {
                mojePrijave.add(p);
            }
        }
        return mojePrijave;
    }

    public void izmeniPrijavu(Prijava novaVerzija) throws Exception {
        proveriOgranicenja(novaVerzija);

        for (int i = 0; i < prijave.size(); i++) {
            if (prijave.get(i).getId().equals(novaVerzija.getId())) {
                prijave.set(i, novaVerzija);
                break;
            }
        }

        sacuvajPodatke();
        if (serverForm != null) {
            serverForm.osveziTabelu();
        }
    }
}
