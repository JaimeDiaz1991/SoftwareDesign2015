package com.maco.blackjack.domainBlackJack;

/**
 * Created by Luis on 11/03/2015.
 */
public class Carta {

    public String palo;
    public int numero;
    public boolean estaEnBajraja;



    public Carta(String palo, int numero, boolean estaEnBajraja) {
        super();
        this.palo = palo;
        this.numero = numero;
        this.estaEnBajraja = estaEnBajraja;
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
    public boolean getEstaEnBajraja() {
        return estaEnBajraja;
    }

    public void setEstaEnBajraja(boolean estaEnBajraja) {
        this.estaEnBajraja = estaEnBajraja;
    }

}