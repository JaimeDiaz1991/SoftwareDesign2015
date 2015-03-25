package com.maco.blackjack.jsonMessage;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class BlackJackRequestCard extends JSONMessage{
	@JSONable
	private String test;
	
	public BlackJackRequestCard(String test) {
		super(false);
		this.test = test;
		
	}
	
	public String getTest() {
		return test;
	}

}
