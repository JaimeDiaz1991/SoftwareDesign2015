package com.maco.juegosEnGrupo.server.dominio;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;




import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import com.maco.blackjack.jsonMessage.BJWaitingMessage;
import com.maco.blackjack.jsonMessage.BlackJackBoardMessage;
import com.maco.blackjack.jsonMessage.BlackJackNewRound;
import com.maco.blackjack.jsonMessage.BlackJackSendMoney;
import com.maco.blackjack.jsonMessage.RequestCardMessage;
import com.maco.juegosEnGrupo.server.dominio.Carta;












import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.server.domain.User;
import edu.uclm.esi.common.server.persistence.DAOBlackJack;
import edu.uclm.esi.common.server.persistence.DAOUser;
import edu.uclm.esi.common.server.sockets.Notifier;

import java.util.*;




public class BlackJack extends Match {
	public static int BLACK_JACK = 2;
	private static int numeroBarajas = 4;
	public static ArrayList<Baraja> barajas= new ArrayList<Baraja>();
	private User userWithTurn;
	private Hashtable <Integer,ArrayList<Carta>> tapeteCartas= new Hashtable<Integer,ArrayList<Carta>>();
	private int apuestas =1;
	private int ronda=1;
	private Reloj timer;
	private boolean empezar=false;
	
	public BlackJack(Game game) {
		super(game);
		for (int i=0; i<numeroBarajas; i++){
			barajas.add(new Baraja());
		}
		
		for (int i=0; i<5; i++){
			tapeteCartas.put(i, new ArrayList<Carta>());
			for (int j=0; j<2; j++){
					tapeteCartas.get(i).add(new Carta());		
			}
		}
	}

	@Override
	protected void postAddUser(User user) {
		this.userWithTurn=players.get(0);
		if (this.players.size()>=2 && this.players.size()<4) {
			if(this.timer==null){
				this.timer=new Reloj(this);
				this.timer.start();
			}
			
		}
		else if(this.players.size()==4){
			empezarPartida();
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

	@Override
	public String toString() {
		String r="";
		int acumu=0;
		int acumu2=0;
		for(int i=0;i<this.tapeteCartas.size();i++){
			acumu=0;
			acumu2=0;
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
		if (!jsoRequestCard.get("type").equals(RequestCardMessage.class.getSimpleName())) {
			throw new Exception("Can't request more cards");
		}
		JSONMessage result=null;
		JSONMessage result2=null;

		
		Carta carta = elegirCartaAleatoria();		
		Iterator<User> jugadores = this.players.iterator();
		int contador=0;
		User u;
		while (jugadores.hasNext()) {
			u = jugadores.next();
			if (u == this.userWithTurn) {
				break;
			} else {
				contador++;
			}
		}
		tapeteCartas.get(contador).add(carta);
		
		if(calcularSumaCartas(contador)<=21){
			result2 = new RequestCardMessage("NUEVA CARTA: "+ carta.toString());
			Notifier.get().post(userWithTurn, result2);
			updateBoard(result2);
		}
		else{
			result2 = new RequestCardMessage("NUEVA CARTA: "+ carta.toString()+" Te has pasado pollo");
			Notifier.get().post(userWithTurn, result2);
			updateBoard(result);
		}
	}

	private int calcularSumaCartas(int contador) {
		Iterator<Carta> itCartas=tapeteCartas.get(contador).iterator();
		int suma=0, suma2=0;
		Carta card;
		while(itCartas.hasNext()){
			card=itCartas.next();
			if(card.getNumero()==1){
				suma+=1;
				suma2+=11;
			}else if(card.isFigura()){
				suma+=10;
				suma2+=10;
			}else{
				suma+=card.getNumero();
				suma2+=card.getNumero();
			}
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
					break;
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
		if(apuestas == 2 && empezar){
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
				userWithTurn=players.get(0);
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
	private int puntuacionReal(int idjugador){
		int acumu = 0, acumu2 = 0, punt_real=-1;
		for (int i = 0; i < this.tapeteCartas.get(idjugador).size(); i++) {

			if (this.tapeteCartas.get(idjugador).get(i).isFigura()) {
				acumu = acumu + 10;
			} else {
				acumu = acumu + this.tapeteCartas.get(idjugador).get(i).getNumero();
				if (this.tapeteCartas.get(idjugador).get(i).getNumero() == 1) {
					acumu2 = acumu + 10;
				}
			}

		}
		if(acumu2!=0){
			if(acumu2>acumu && acumu2<=21)
				punt_real=acumu2;
			else
				punt_real=acumu;
		}
		else
			punt_real=acumu;
		return punt_real;
		
	}

	private void turnoBanca() {
		try {
			System.out.print("TURNO DE LA BANCA");
			JSONMessage result = null;
			int punt_realbanca;
			int todos_fuera = 0;

			if (tapeteCartas.get(4).get(tapeteCartas.get(4).size() - 1)
					.getPalo() == null) {
				tapeteCartas.get(4).remove(tapeteCartas.get(4).size() - 1);
				tapeteCartas.get(4).add(tapeteCartas.get(4).size() - 1,
						elegirCartaAleatoria());
				JSONMessage result2=null;
				result2 = new RequestCardMessage("BANCA: "+ tapeteCartas.get(4).get(tapeteCartas.get(4).size()-1)+ " :"+puntuacionReal(4));
				Notifier.get().post(players, result2);
			}
			punt_realbanca = puntuacionReal(4);
			// NO SE HAN PASADO TODOS
			for (int i = 0; i < this.players.size(); i++) {
				if (puntuacionReal(i) > 21) {
					todos_fuera++;
				}
			}
			if (todos_fuera < players.size()) {
				while (punt_realbanca <= 16) {
					// pedimos cartac
					tapeteCartas.get(4).add(elegirCartaAleatoria());
					
					JSONMessage result2=null;
					result2 = new RequestCardMessage("BANCA: "+ tapeteCartas.get(4).get(tapeteCartas.get(4).size()-1)+ " :"+puntuacionReal(4));
					Notifier.get().post(players, result2);
					punt_realbanca = puntuacionReal(4);
				}
				if (punt_realbanca == 21) {
					// ganadorbanca y notificamos a los players que han perdido y que la nueva ronda comienza
					RequestCardMessage jsPF = new RequestCardMessage(
							"La banca tiene Blackjack!! Esperando para iniciar nueva partida");
					Notifier.get().post(this.players, jsPF);
					
				} else if(punt_realbanca<21){
					System.out.print("Valoramos puntuaciones jugadores");
					ArrayList<Integer> idganadores = new ArrayList<Integer>();
					int[] punt = new int[4];
					for (int i = 0; i < this.players.size(); i++) {
						punt[i] = puntuacionReal(i);
						if (punt[i] > puntuacionReal(4) && punt[i] <= 21) {
							idganadores.add(i);
						}
					}
					for(int i=0;i<idganadores.size();i++){
						JSONMessage jsED = new BlackJackSendMoney(200);
						Notifier.get().post(players.get(idganadores.get(i)), jsED);
						RequestCardMessage jsPF = new RequestCardMessage("Has ganado 200");
						Notifier.get().post(this.players, jsPF);
					}
				}
				else{
					//doblariamos a los jugadores qeu tuvieran menos de 21
					ArrayList<Integer> idganadores = new ArrayList<Integer>();
					int[] punt = new int[4];
					for (int i = 0; i < this.players.size(); i++) {
						punt[i] = puntuacionReal(i);
						if (punt[i] <= 21) {
							idganadores.add(i);
						}
					}
					for(int i=0;i<idganadores.size();i++){
						JSONMessage jsED = new BlackJackSendMoney(200);
						Notifier.get().post(players.get(idganadores.get(i)), jsED);
						RequestCardMessage jsPF = new RequestCardMessage("Has ganado 200");
						int fichas=200;
						insert_ranking(players.get(idganadores.get(i)).getId(), this.game.getId(),fichas);
						this.game.getId();
						Notifier.get().post(this.players, jsPF);
					}
				}
			} else {
				System.out.print("Todos los jugadores se han pasado");
			}
			//aqui hay que reiniciar la partida enviandole de nuevo el board
			//
			barajas = new ArrayList<Baraja>();
			for (int i=0; i<this.numeroBarajas; i++){
				barajas.add(new Baraja());
			}
			tapeteCartas = new Hashtable<Integer,ArrayList<Carta>>();
			for (int i=0; i<5; i++){
				tapeteCartas.put(i, new ArrayList<Carta>());
				for (int j=0; j<2; j++){
						tapeteCartas.get(i).add(new Carta());		
				}
			}
			apuestas=1;
			empezar=false;
			this.userWithTurn=players.get(0);
			JSONMessage jsBoard=new BlackJackBoardMessage(this.toString());
			Notifier.get().post(this.players, jsBoard);
			
			BlackJackNewRound jsNR = new BlackJackNewRound("La nueva ronda comienza");
			Notifier.get().post(this.players, jsNR);
			empezarPartida();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void empezarPartida() {
		// TODO Auto-generated method stub
		this.timer.parar();
		empezar=true;
		try {
			this.userWithTurn=this.players.get(0);
			JSONMessage jsBoard=new BlackJackBoardMessage(this.toString());
			JSONMessage jsm=new BJWaitingMessage("Waiting for bet");
			Notifier.get().post(this.players, jsm);
			Notifier.get().post(this.players, jsBoard);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void insert_mov(int iduser, String mov, int idpartida, int idgame,String desc) throws SQLException {
		DAOBlackJack.registrarMovimiento(iduser, mov,idpartida,idgame,desc);
	}
	public static void insert_ranking(int iduser, int idgame,int fichas) throws SQLException {
		DAOBlackJack.registrarRanking(iduser,idgame,fichas);
	}

}
