package com.maco.blackjack.jsonMessage;

import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class BlackJackBoardMessage extends JSONMessage{
	
	@JSONable
	private String tapete;
	@JSONable
	private String [] players = new String [5];
	private String userWithTurn;

	public BlackJackBoardMessage(String board) throws JSONException {
		super(false);
		int cont = 0;
		StringTokenizer st=new StringTokenizer(board, "#");
		this.tapete=st.nextToken();
		this.players[0]=st.nextToken();
		while (st.hasMoreTokens()) {
			this.players[cont]=st.nextToken();
			cont++;
		}
		userWithTurn=st.nextToken();
	}
	
	public BlackJackBoardMessage(JSONObject jso) throws JSONException {
		super(false);
		this.tapete=jso.getString("tapete");
		this.players[0]=jso.getString("player1");
		//BANCA
		this.tapete=jso.getString("tapete");
		this.players[4]=jso.getString("player5");
		
		if (jso.optString("player2").length()>0) {
			this.players[1]=jso.getString("player2");
			this.userWithTurn=jso.getString("userWithTurn");
		}
		if (jso.optString("player3").length()>0) {
			this.players[2]=jso.getString("player3");
			this.userWithTurn=jso.getString("userWithTurn");
		}
		if (jso.optString("player4").length()>0) {
			this.players[3]=jso.getString("player4");
			this.userWithTurn=jso.getString("userWithTurn");
		}
	}

	public String gettapete() {
		return tapete;
	}
	
	public String getPlayer1() {
		return players[0];
	}
	
	public String getPlayer2() {
		return players[1];
	}
	public String getPlayer3() {
		return players[2];
	}
	public String getPlayer4() {
		return players[3];
	}
	public String getPlayer5() {
		return players[4];
	}
	
	public String getUserWithTurn() {
		return userWithTurn;
	}

}