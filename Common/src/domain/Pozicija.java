/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package domain;

import java.io.Serializable;

/**
 *
 * @author matij
 */
public enum Pozicija implements Serializable {
    INFO_OSOBLJE("Osoblje za informacije"),
    REDAR("Redar"),
    MEDIJI("Mediji"),
    VIP_POSLUGA("Posluga u VIP zoni");

    private final String naziv;

    private Pozicija(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public String toString() {
        return naziv;
    }
}
