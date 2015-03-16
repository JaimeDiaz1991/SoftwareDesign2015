package com.maco.juegosEnGrupo.server.dominio;

import java.io.IOException;
import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.maco.blackjack.jsonMessage.BlackJackRequestCard;
import com.maco.tresenraya.jsonMessages.TresEnRayaMovement;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.server.domain.User;
import edu.uclm.esi.common.server.sockets.Notifier;

import java.util.*;
public class MatchBlackJack extends Match {
	public static int BLACK_JACK = 1;
	private static int numeroBarajas = 1;
	public static Baraja baraja[];
	private User userWithTurn;
	private Hashtable <Integer,ArrayList<Carta>> tapeteCartas;
	private int [] tapetePuntuAcum;
	
	public MatchBlackJack(Game game) {
		super(game);
		//creamos las barajas que tengamos elegidas en numeroBarajas
		
		for (int i=0; i<numeroBarajas; i++){
			baraja [i] = new Baraja();
		}
		//CREAR TAPETE
		//tapeteCartas = new Carta[5][2];
		tapetePuntuAcum = new int[5];
		for (int i=0; i<5; i++){
			tapeteCartas.put(i, new ArrayList<Carta>());
			for (int j=0; j<2; j++){
				tapeteCartas.get(i).add(new Carta());
			}
		}
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
		return this.userWithTurn.equals(user);
	}

	@Override
	protected void requestCard(User user, JSONObject jsoMovement) throws Exception {
		if (!jsoMovement.get("type").equals(BlackJackRequestCard.class.getSimpleName())) {
			throw new Exception("Can't request more cards");
		}
		boolean finish=true;
		while(finish){
			int numeroBaraja = (int) (Math.random()*(4-1)+1);
			int numeroCarta = (int) (Math.random()*(4-1)+1);
			JSONMessage result=null;
			//ahora comprobamos si esta y si no la marcamos como que ya no esta en la baraja
			if(comprobarEstaEnBaraja()){
				
			}
			else{
				
			}
		}
		
		
		
		
		
		
		/*int row=jsoMovement.getInt("row");
		int col=jsoMovement.getInt("col");
		JSONMessage result=null;
		if (this.squares[row][col]!=WHITE) {
			result=new ErrorMessage("Square busy");
			Notifier.get().post(user, result);
		} else if (!this.isTheTurnOf(user)) {
			result=new ErrorMessage("It's not your turn");
			Notifier.get().post(user, result);
		} 
		updateBoard(row, col, result);*/
	}

	@Override
	protected void updateBoard(int row, int col, JSONMessage result) throws JSONException, IOException {
		

	}

	@Override
	protected void postMove(User user, JSONObject jsoMovement) throws Exception {	
	}




}
