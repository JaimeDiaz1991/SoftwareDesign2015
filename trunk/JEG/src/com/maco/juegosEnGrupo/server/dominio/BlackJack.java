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
	private int apuestas =1;
	private int ronda=1;
	
	public BlackJack(Game game) {
		super(game);
		
		for (int i=0; i<numeroBarajas; i++){
			barajas.add(new Baraja());
		}
		
		tapetePuntuAcum = new int[5];
		for (int i=0; i<5; i++){
			tapeteCartas.put(i, new ArrayList<Carta>());
			for (int j=0; j<2; j++){
					tapeteCartas.get(i).add(new Carta());		
			}
		}
	}

	@Override
	protected void postAddUser(User user) {
		
		if (this.players.size()==2) {
			try {
				this.userWithTurn=this.players.get(0);
				JSONMessage jsBoard=new BlackJackBoardMessage(this.toString());
				JSONMessage jsm=new BJWaitingMessage("Waiting for bet");
				Notifier.get().post(this.players.get(0), jsm);
				Notifier.get().post(this.players.get(1), jsm);
				Notifier.get().post(this.players.get(0), jsBoard);
				Notifier.get().post(this.players.get(1), jsBoard);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		else {
			JSONMessage jsm=new BJWaitingMessage("Waiting for more player");
			try {
				Notifier.get().post(this.players.get(0), jsm);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void rellenarTapete() {
		for(int i=0;i<this.players.size();i++){
			for (int j=0;j<tapeteCartas.get(i).size();j++){
				if(tapeteCartas.get(i).get(j).getPalo()==null){
						tapeteCartas.get(i).remove(j);
						tapeteCartas.get(i).add(j, elegirCartaAleatoria());
			}
		}
			}
		//Rellenar banca
			if(tapeteCartas.get(4).get(0).getPalo()==null){
				tapeteCartas.get(4).remove(0);
				tapeteCartas.get(4).add(0, elegirCartaAleatoria());
		}
			
		
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
		int acumu=0;
		int acumu2=0;
		for(int i=0;i<this.tapeteCartas.size();i++){
			acumu=0;
			acumu=0;
			for(int j=0; j<this.tapeteCartas.get(i).size();j++){
					r+=this.tapeteCartas.get(i).get(j).toString();
					if(this.tapeteCartas.get(i).get(j).isFigura())
						acumu=acumu+10;
					else{
					acumu=acumu+this.tapeteCartas.get(i).get(j).getNumero();
					}
					if(this.tapeteCartas.get(i).get(j).getNumero()==1)
							acumu2=acumu+acumu2+10;
					if(j < this.tapeteCartas.get(i).size()-1)
					r+="_";
			}
			r+="!"+Integer.toString(acumu);
			if(acumu2!=0)
				r+="/"+acumu2;
			r+="-";
		}
			r+="#" + this.players.get(0).getEmail() + "#";
			if (this.players.size()>=2) {
				for(int k=1;k<players.size();k++){
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

	protected void postRequestCard(User user, JSONObject jsoRequestCard) throws Exception {
		if (!jsoRequestCard.get("type").equals(BlackJackRequestCard.class.getSimpleName())) {
			throw new Exception("Can't request more cards");
		}
		JSONMessage result=null;
		JSONMessage result2=null;

		
		if(calcularSumaCartas(players, tapeteCartas)<=21){
			Carta carta = elegirCartaAleatoria();
			tapeteCartas.get(userWithTurn).add(carta);
			result = new BlackJackRequestCard(carta.toString());
			Notifier.get().post(userWithTurn, result);
			updateBoard(result);
		}
		else{
			result = new BlackJackRequestCard("You got more than 21");
			Notifier.get().post(userWithTurn, result);
			updateBoard(result2);
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
	
	/*protected void updateBoard(JSONMessage result) throws JSONException, IOException {
		if (result==null) {
			this.userWithTurn=this.players.get(1);
		} else {
			this.userWithTurn=this.players.get(0);

		}
		result=new BlackJackBoardMessage(this.toString());
		//ACTUALIZA EN TODOS LOS TABLEROS?
		Notifier.get().post(this.players, result);
	}*/

	protected void updateBoard(JSONMessage result) throws JSONException, IOException {
		if (result==null) {
			Iterator<User> itUser;
			itUser = players.iterator();
			User u;
			while(itUser.hasNext()){
				u = itUser.next();
				if(u == userWithTurn && itUser.hasNext()){
					userWithTurn = itUser.next();
				}else{
					turnoBanca();
				}
			}
			result=new BlackJackBoardMessage(this.toString());
			Notifier.get().post(this.players, result);
		}
		else{
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

	protected void postBet(User user, JSONObject jsoBet){
		if(apuestas == 2){
			try {
				for (int i = 0; i < players.size(); i++) {
					if (players.get(i).equals(user)) {
						players.get(i).setApuestas(ronda, jsoBet.getInt("bet"));
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONMessage jsTurn=new BJWaitingMessage("Match ready. You have the turn.");
			JSONMessage jsNoTurn=new BJWaitingMessage("Match ready. Wait for the opponent to move.");
			int numeroJugadores=players.size();
			try{
				Notifier.get().post(this.players.get(0), jsTurn);
				for(int i=1; i<numeroJugadores;i++){
					Notifier.get().post(this.players.get(i), jsNoTurn);
				}
				rellenarTapete();
				JSONMessage jsBoard=new BlackJackBoardMessage(this.toString());
				Notifier.get().post(this.players, jsBoard);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
			apuestas++;
			try {
				for (int i = 0; i < players.size(); i++) {
					if (players.get(i).equals(user)) {
						players.get(i).setApuestas(ronda, jsoBet.getInt("bet"));
					}
				}
				JSONMessage result = null;
				updateBoard(result);
				JSONMessage jsm=new BJWaitingMessage("Waiting for bet of other players");	
				Notifier.get().post(this.players.get(0), jsm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void postPlanted(User user, JSONObject jsoRec) throws Exception {
		if(jsoRec!=null){
			JSONMessage result = null;
			updateBoard(result);
		}
		
		
	}
	
	private void turnoBanca(){
		System.out.print("TURNO DE LA BANCA");
		
		
	}

}
