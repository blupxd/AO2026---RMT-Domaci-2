/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author matij
 */
public class Prijava implements Serializable {

    private String id;
    private Korisnik korisnik;
    private Date datumVolontiranja;
    private Date datumPrijave;
    private Smena smena;
    private Pozicija pozicija;
    private String status;

    public Prijava() {
    }

    public Prijava(String id, Korisnik korisnik, Date datumVolontiranja, Date datumPrijave, Smena smena, Pozicija pozicija, String status) {
        this.id = id;
        this.korisnik = korisnik;
        this.datumVolontiranja = datumVolontiranja;
        this.datumPrijave = datumPrijave;
        this.smena = smena;
        this.pozicija = pozicija;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Date getDatumVolontiranja() {
        return datumVolontiranja;
    }

    public void setDatumVolontiranja(Date datumVolontiranja) {
        this.datumVolontiranja = datumVolontiranja;
    }

    public Date getDatumPrijave() {
        return datumPrijave;
    }

    public void setDatumPrijave(Date datumPrijave) {
        this.datumPrijave = datumPrijave;
    }

    public Smena getSmena() {
        return smena;
    }

    public void setSmena(Smena smena) {
        this.smena = smena;
    }

    public Pozicija getPozicija() {
        return pozicija;
    }

    public void setPozicija(Pozicija pozicija) {
        this.pozicija = pozicija;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Prijava [" + datumVolontiranja + " | " + smena + " | " + pozicija + "] - Status: " + status;
    }
}
