package com.maco.juegosEnGrupo.server.dominio;

public class Reloj extends Thread {
	BlackJack partida;
	long horaComienzo;
	boolean parado;
	public Reloj(BlackJack partida) {
		super();
		this.partida = partida;
		this.horaComienzo = System.currentTimeMillis()+1000;
		this.parado = false;
	}
	public void run(){
		while(System.currentTimeMillis()+1000<this.horaComienzo){
			try{
				Thread.sleep(50000);
			}
			catch(Exception e){}
		}
		if(!parado){
			this.partida.empezarPartida();
		}
	}
	public void parar(){
		this.parado=true;
	}
	

}
