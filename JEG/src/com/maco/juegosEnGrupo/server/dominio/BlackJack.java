package com.maco.juegosEnGrupo.server.dominio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;




import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import com.maco.blackjack.jsonMessage.BJWaitingMessage;
import com.maco.blackjack.jsonMessage.BlackJackBoardMessage;
import com.maco.blackjack.jsonMessage.BlackJackRequestCard;
import com.maco.juegosEnGrupo.server.dominio.Carta;



import com.maco.tresenraya.jsonMessages.TresEnRayaBoardMessage;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.server.domain.User;
import edu.uclm.esi.common.server.sockets.Notifier;

import java.util.*;




public class BlackJack extends Match {
	public static int BLACK_JACK = 2;
	private static int numeroBarajas = 4;
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
					tapeteCartas.get(i).add(new Carta());			
			}
		}
		// TODO Auto-generated constructor s
	}

	@Override
	protected void postAddUser(User user) {
		
		//if (cuentaAtras()==true) {
			JSONMessage jsTurn=new BJWaitingMessage("Match ready. You have the turn.");
			JSONMessage jsNoTurn=new BJWaitingMessage("Match ready. Wait for the opponent to move.");
			int numeroJugadores=players.size();
			this.userWithTurn=this.players.get(0);
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
				JSONMessage jsBoard=new BlackJackBoardMessage(this.toString());
				for(int i=1; i<numeroJugadores;i++){
				Notifier.get().post(this.players.get(i), jsBoard);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//} 
	}
	
	private boolean cuentaAtras() {
		JSONMessage jsm=new BJWaitingMessage("El juego comenzará en 2 minutos");
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
		String r="";
		ArrayList<Carta> cartas = null;
		for(int i=0;i<tapeteCartas.size();i++){
			cartas = tapeteCartas.get(i);
			for(int j=0; j<cartas.size();j++){
				r+=cartas.get(j).toString();
			}
		}
			r+="#" + this.players.get(0).getEmail() + "#";
			if (this.players.size()>=2) {
				for(int k=0;k<players.size();k++){
					r+=this.players.get(k).getEmail() + "#";
				}
				r+=this.userWithTurn.getEmail();
			}
			return r;
		}

		
	@Override
	protected boolean isTheTurnOf(User user) {
		return this.userWithTurn.equals(user);
	}

	private void requestCard(User user, JSONObject jsoMovement) throws Exception {
		if (!jsoMovement.get("type").equals(BlackJackRequestCard.class.getSimpleName())) {
			throw new Exception("Can't request more cards");
		}
		JSONMessage result=null;
		boolean finish=true;
		Iterator<User> jugadores;
		String cadena;
		jugadores = players.iterator();
		User u = null;
		int jugElegido = -1;
		
		if(calcularSumaCartas(players, tapeteCartas)<=21){
			Carta carta = elegirCartaAleatoria();
			while (jugadores.hasNext()) {
				u = jugadores.next();
				if (u == userWithTurn) {
					break;
				} else {
					jugElegido++;
				}
			}
			tapeteCartas.get(jugElegido).add(carta);
			updateBoard(carta,jugElegido, result);
		}
		else{
			
			result = new BlackJackRequestCard("You got more than 21");
			Notifier.get().post(userWithTurn, result);
		}
	}

	private int calcularSumaCartas(Vector<User> players,
			Hashtable<Integer, ArrayList<Carta>> tapeteCartas2) {
		Iterator<User> jugadores = players.iterator();
		Iterator<Carta> itCartas;
		int suma = 0;
		User u = null;
		int jugElegido = -1;
		while (jugadores.hasNext()) {
			u = jugadores.next();
			if (u == userWithTurn) {
				break;
			} else {
				jugElegido++;
			}
		}
		itCartas = tapeteCartas.get(jugElegido).iterator();
		while(itCartas.hasNext()){
			suma += itCartas.next().getNumero();
		}
		return suma;
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

	protected void updateBoard(Carta carta, int jugElegido, JSONMessage result) throws JSONException, IOException {
		if (result==null) {
			Iterator<User> itUser;
			itUser = players.iterator();
			User u;
			while(itUser.hasNext()){
				u = itUser.next();
				if(u == userWithTurn && itUser.hasNext()){
					userWithTurn = itUser.next();
				}else{
					//aqui se llamaria a un metodo que calculara las cartas de la banca y pagara o quitara el dinero de los usuarios
				}
			}
			result=new BlackJackBoardMessage(this.toString());
			Notifier.get().post(this.players, result);
		}

	}

	@Override
	protected void postMove(User user, JSONObject jsoMovement) throws Exception {	
	}

	@Override
	protected void updateBoard(int row, int col, JSONMessage result)
			throws JSONException, IOException {
		// TODO Auto-generated method stub
		
	}




}


