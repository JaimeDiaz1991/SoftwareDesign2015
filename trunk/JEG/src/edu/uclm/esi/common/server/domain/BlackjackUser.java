package edu.uclm.esi.common.server.domain;

public class BlackjackUser extends User {

	private double dinero;

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
