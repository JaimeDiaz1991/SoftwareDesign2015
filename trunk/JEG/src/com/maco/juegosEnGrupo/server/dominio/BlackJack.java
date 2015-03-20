package com.maco.juegosEnGrupo.server.dominio;

import java.io.IOException;
import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.maco.blackjack.jsonMessage.BlackJackRequestCard;
import com.maco.juegosEnGrupo.server.dominio.Carta;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.server.domain.User;
import edu.uclm.esi.common.server.sockets.Notifier;

import java.util.*;


public class BlackJack extends Match {
	public static int BLACK_JACK = 2;
	private static int numeroBarajas = 1;
	public static ArrayList<Baraja> barajas= new ArrayList<Baraja>();
	private User userWithTurn;
	private Hashtable <Integer,ArrayList<Carta>> tapeteCartas= new Hashtable<Integer,ArrayList<Carta>>();
	private int [] tapetePuntuAcum;
	
	public BlackJack(Game game) {
		super(game);
		//creamos las barajas que tengamos elegidas en numeroBarajas
		
		for (int i=0; i<numeroBarajas; i++){
			barajas.add(new Baraja());
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

	protected void requestCard(User user, JSONObject jsoMovement) throws Exception {
		if (!jsoMovement.get("type").equals(BlackJackRequestCard.class.getSimpleName())) {
			throw new Exception("Can't request more cards");
		}
		boolean finish=true;
		while(finish){
			int numeroBaraja = (int) (Math.random()*(4-1)+1);
			int numeroCarta = (int) (Math.random()*(12-1)+1);
			int numeroPalo = (int) (Math.random()*(4-1)+1);
			
			JSONMessage result=null;
			//ahora comprobamos si esta y si no la marcamos como que ya no esta en la baraja
			if(comprobarEstaEnBaraja(numeroBaraja, numeroCarta, numeroPalo)){
				finish=true;
			}
			else{
				finish=false;
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

	private boolean comprobarEstaEnBaraja(int numeroBaraja, int numeroCarta, int numeroPalo) {
		String palo;
		Carta [] cartas;
		
		if(numeroPalo == 1){
			palo = "picas";
		}
		else if(numeroPalo == 2){
			palo = "diamantes";
		}
		else if(numeroPalo == 3){
			palo = "corazones";
		}
		else{
			palo = "treboles";
		}
		for(int i=0; i<numeroBarajas;i++){
			cartas= (Carta[]) barajas.get(i).getCartas();
			for(int j=0;j<cartas.length;j++){
				if(cartas[j].getPalo().equals(palo) && cartas[j].getNumero()==numeroCarta && cartas[j].getestaEnBaraja() == true){
					cartas[j].setestaEnBaraja(false);
					barajas.get(i).setCartas(cartas);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected void updateBoard(int row, int col, JSONMessage result) throws JSONException, IOException {
		

	}

	@Override
	protected void postMove(User user, JSONObject jsoMovement) throws Exception {	
	}




}
