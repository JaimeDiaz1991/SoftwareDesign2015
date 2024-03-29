package com.maco.juegosEnGrupo.server.dominio;

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
	@Override
	public String toString() {
		if(this.numero==1)
			return "AS "+ palo;
		else if(this.numero==11)
			return "J "+ palo;
		else if(this.numero==12)
			return "Q "+ palo;
		else if(this.numero==13)
			return "K "+ palo;
		else
		return numero + " " + palo;
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
	public boolean isFigura() {
		return figura;
	}
	public void setFigura(boolean figura) {
		this.figura = figura;
	}
}

