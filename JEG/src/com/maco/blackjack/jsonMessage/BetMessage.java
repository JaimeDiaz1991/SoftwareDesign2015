package com.maco.blackjack.jsonMessage;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class BetMessage extends JSONMessage {

	
		@JSONable
		private String text;
		
		public BetMessage(boolean isCommand) {
			super(false);
		}

		public String getText() {
			return this.text;
		}

	
}

