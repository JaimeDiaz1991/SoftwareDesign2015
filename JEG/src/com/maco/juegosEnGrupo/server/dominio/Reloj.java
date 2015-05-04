package com.maco.juegosEnGrupo.server.dominio;

public class Reloj extends Thread {
	BlackJack partida;
	long horaComienzo;
	boolean parado;
	public Reloj(BlackJack partida) {
		super();
		this.partida = partida;
		this.horaComienzo = System.currentTimeMillis()+10000;
		this.parado = false;
	}
	public void run(){
		while(System.currentTimeMillis()<this.horaComienzo){
			try{
				Thread.sleep(1000);
				System.out.print(this.horaComienzo);
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
