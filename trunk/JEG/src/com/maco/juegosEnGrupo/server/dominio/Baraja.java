package com.maco.juegosEnGrupo.server.dominio;

import java.util.ArrayList;

public class Baraja {

	public Carta cartas[];
	
	public void Baraja(){
		
		ArrayList<String> palos = new ArrayList<String>();
		palos.add("picas");
		palos.add("diamantes");
		palos.add("corazones");
		palos.add("treboles");
		boolean figura;
		for(int i = 0; i < palos.size(); i++){
			for(int j = 0; j < 12; j++){
				if(j >= 10)
					figura = true;
				else
					figura = false;
				cartas[i] = new Carta(palos.get(i), j, figura);
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
