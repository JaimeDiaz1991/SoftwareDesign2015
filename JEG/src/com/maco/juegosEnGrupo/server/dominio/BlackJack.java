

package com.maco.juegosEnGrupo.server.dominio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import com.maco.blackjack.jsonMessage.BlackJackRequestCard;
import com.maco.juegosEnGrupo.server.dominio.Carta;
import com.maco.tresenraya.jsonMessages.TresEnRayaBoardMessage;
import com.maco.tresenraya.jsonMessages.TresEnRayaWaitingMessage;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.server.domain.User;
import edu.uclm.esi.common.server.sockets.Notifier;

import java.util.*;

import javax.swing.Timer;


public class BlackJack extends Match {
	public static int BLACK_JACK = 2;
	private static int numeroBarajas = 1;
	public static ArrayList<Baraja> barajas= new ArrayList<Baraja>();
	private User userWithTurn;
	private Hashtable <Integer,ArrayList<Carta>> tapeteCartas= new Hashtable<Integer,ArrayList<Carta>>();
	private int [] tapetePuntuAcum;
	private int numeroJugadores;
	
	public BlackJack(Game game) {
		super(game);
		//creamos las barajas que tengamos elegidas en numeroBarajas
		
		for (int i=0; i<numeroBarajas; i++){
			barajas.add(new Baraja());
		}
		//CREAR TAPETE
		tapetePuntuAcum = new int[5];
		for (int i=0; i<5; i++){
			tapeteCartas.put(i, new ArrayList<Carta>());
			User user;
			JSONObject json;
			for (int j=0; j<2; j++){
					tapeteCartas.get(i).add(elegirCartaAleatoria());			
			}
		}
		// TODO Auto-generated constructor s
	}

	@Override
	protected void postAddUser(User user) {
		
		if (cuentaAtras()==true) {
			JSONMessage jsTurn=new TresEnRayaWaitingMessage("Match ready. You have the turn.");
			JSONMessage jsNoTurn=new TresEnRayaWaitingMessage("Match ready. Wait for the opponent to move.");
			int numeroJugadores=players.size();
			try{
				Notifier.get().post(this.players.get(0), jsTurn);
				for(int i=1; i<numeroJugadores;i++){
					Notifier.get().post(this.players.get(i), jsNoTurn);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try {
				//aqui se pasaria el toString del tapete a cada jugador
				JSONMessage jsBoard=new TresEnRayaBoardMessage(this.toString());
				Notifier.get().post(this.players.get(0), jsBoard);
				Notifier.get().post(this.players.get(1), jsBoard);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			JSONMessage jsm=new TresEnRayaWaitingMessage("Waiting for one more player");
			try {
				Notifier.get().post(this.players.get(0), jsm);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private boolean cuentaAtras() {
		JSONMessage jsm=new TresEnRayaWaitingMessage("El juego comenzará en 2 minutos");
		try {
			Notifier.get().post(this.players, jsm);	
		}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		try{
	          Thread.sleep(1000);
	    }catch(InterruptedException e){}
		return true;
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

	private void requestCard(User user, JSONObject jsoMovement) throws Exception {
		if (!jsoMovement.get("type").equals(BlackJackRequestCard.class.getSimpleName())) {
			throw new Exception("Can't request more cards");
		}
		boolean finish=true;
		while(finish){
			Carta carta= elegirCartaAleatoria();
			//Añadir carta al tapete del jugador
			
			JSONMessage result=null;

		}

	}

	private Carta elegirCartaAleatoria() {
		boolean terminar = true;
		Carta carta = null;
		Carta cartasBaraja [];
		int numeroBaraja;
		int numeroCarta;
		
		while(terminar){
			numeroBaraja = (int) (Math.random()*(4-1)+1);
			numeroCarta = (int) (Math.random()*(52-1)+1);
			cartasBaraja= barajas.get(numeroBaraja).getCartas();
			if(cartasBaraja[numeroCarta].estaEnBaraja==true){
				carta = cartasBaraja[numeroCarta];
				cartasBaraja[numeroCarta].setestaEnBaraja(false);
				terminar=false;
			}
		}
		return carta;
	}

	@Override
	protected void updateBoard(int row, int col, JSONMessage result) throws JSONException, IOException {
		

	}

	@Override
	protected void postMove(User user, JSONObject jsoMovement) throws Exception {	
	}




}

