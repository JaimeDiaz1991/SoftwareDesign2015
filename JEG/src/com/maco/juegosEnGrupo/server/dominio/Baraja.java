package com.maco.juegosEnGrupo.server.dominio;

import java.util.ArrayList;

public class Baraja {

	public Carta cartas[]=new Carta[48];
	
	public Baraja(){
		
		ArrayList<String> palos = new ArrayList<String>();
		palos.add("picas");
		palos.add("diamantes");
		palos.add("corazones");
		palos.add("treboles");
		boolean figura;
		int cont = 0;
		for(int i = 0; i < palos.size(); i++){
			for(int j = 1; j <= 13; j++){
				if(j >= 10)
					figura = true;
				else
					figura = false;
				cartas[cont] = new Carta(palos.get(i), j, figura);
				cont++;
			}
		}
	}

	public Carta[] getCartas() {
		return cartas;
	}

	public void setCartas(Carta[] cartas) {
		this.cartas = cartas;
	}
	
	
}

