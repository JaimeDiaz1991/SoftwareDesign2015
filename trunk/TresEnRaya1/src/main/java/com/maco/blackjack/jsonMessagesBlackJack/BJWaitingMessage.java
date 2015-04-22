package com.maco.blackjack.jsonMessagesBlackJack;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

/**
 * Created by Isi on 14/04/2015.
 */
public class BJWaitingMessage extends JSONMessage {
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
