package com.maco.blackjack.jsonMessage;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class RequestCardMessage extends JSONMessage{
	@JSONable
	private String text;
	
	public RequestCardMessage(String text) {
		super(false);
		this.text = text;
		
	}
	
	public String getText() {
		return text;
	}

}
