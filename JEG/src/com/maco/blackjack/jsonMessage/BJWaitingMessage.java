package com.maco.blackjack.jsonMessage;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class BJWaitingMessage extends JSONMessage {

	//ijgadsñfkjahñsdfkjahdskmfbasldjf
		@JSONable
		private String text;
		
		public BJWaitingMessage(String text) {
			super(false);
			this.text=text;
		}

		public String getText() {
			return this.text;
		}

	
}

