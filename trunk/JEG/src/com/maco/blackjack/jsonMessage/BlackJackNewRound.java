package com.maco.blackjack.jsonMessage;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class BlackJackNewRound extends JSONMessage {
	@JSONable
    private String text;
	public BlackJackNewRound(String text) {
		super(false);
		this.text=text;
		// TODO Auto-generated constructor stub
	}

}
