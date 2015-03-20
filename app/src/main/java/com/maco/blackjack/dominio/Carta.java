package com.maco.blackjack.dominio;

/**
 * Created by Jaime on 19/03/2015.
 */
public class Carta {
    public String palo;
    public int numero;
    public boolean figura;
    public boolean estaEnBaraja;



    public Carta(String palo, int numero, boolean figura) {
        super();
        this.palo = palo;
        this.numero = numero;
        this.figura = figura;
        estaEnBaraja = true;
    }
    public Carta(){

    }

    public String getPalo() {
        return palo;
    }

    public void setPalo(String palo) {
        this.palo = palo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean getestaEnBaraja() {
        return estaEnBaraja;
    }

    public void setestaEnBaraja(boolean estaEnBaraja) {
        this.estaEnBaraja = estaEnBaraja;
    }
}
