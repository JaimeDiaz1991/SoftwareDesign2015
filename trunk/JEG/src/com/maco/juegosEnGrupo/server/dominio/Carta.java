package com.maco.juegosEnGrupo.server.dominio;

public class Carta {

	public String palo;
	public int numero;
	public boolean figura;
	
	

	public Carta(String palo, int numero, boolean figura) {
		super();
		this.palo = palo;
		this.numero = numero;
		this.figura = figura;
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
	
}
