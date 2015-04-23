package edu.uclm.esi.common.server.domain;

import java.util.LinkedList;

public class BlackjackUser extends User {

	private double dinero;
	private LinkedList<Integer> apuestas;

	public int getApuestas(int i) {
		return apuestas.get(i);
	}

	public void setApuestas(int i, int apuesta) {
		this.apuestas.set(i, apuesta);
	}

	public BlackjackUser(double dinero) {
		super();
		this.dinero = dinero;
	}

	public double getDinero() {
		return dinero;
	}

	public void setDinero(double dinero) {
		this.dinero = dinero;
	}
	
}
