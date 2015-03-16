package com.maco.blackjack.jsonMessage;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class BlackJackRequestCard extends JSONMessage{
	@JSONable
	private boolean request;
	
	public BlackJackRequestCard(boolean request) {
		super(true);
		this.request = request;
		
	}
	
	public boolean getRow() {
		return request;
	}

	public boolean getCol() {
		return request;
	}
}
