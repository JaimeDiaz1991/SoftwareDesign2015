package com.maco.juegosEnGrupo.server.dominio;

import java.io.IOException;
import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.server.domain.User;

public class MatchBlackJack extends Match {
	public static int BLACK_JACK = 1;
	//public static char X='X', O='O', WHITE = ' ';
	public static Baraja baraja[];
	private User userWithTurn;
	
	private Hashtable <Integer,Carta[]> manos;
	private Hashtable <Integer,Carta> tapeteCartas;
	private int [] tapetePuntuAcum;
	public MatchBlackJack(Game game) {
		super(game);
		
		//CREAR TAPETE
		//tapeteCartas = new Carta[5][2];
		tapetePuntuAcum = new int[5];
		for (int i=0; i<5; i++)
			for (int j=0; j<2; j++)
				tapeteCartas.put(i, null);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void postAddUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isTheTurnOf(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void postMove(User user, JSONObject jsoMovement) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateBoard(int row, int col, JSONMessage result)
			throws JSONException, IOException {
		// TODO Auto-generated method stub

	}

}
