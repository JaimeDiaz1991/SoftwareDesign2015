package com.maco.blackjack.jsonMessage;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class BlackJackSendMoney extends JSONMessage {

	@JSONable
	private int text;

	public BlackJackSendMoney(int text) {
		super(false);
		this.text = text;
	}

	public int getText() {
		return this.text;
	}

}
