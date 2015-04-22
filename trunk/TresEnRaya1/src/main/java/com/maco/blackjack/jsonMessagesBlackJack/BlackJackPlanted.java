package com.maco.blackjack.jsonMessagesBlackJack;

import edu.uclm.esi.common.jsonMessages.JSONMessage;

/**
 * Created by Luis on 05/03/2015.
 */

public class BlackJackPlanted extends JSONMessage {
    private String cadena;
    public BlackJackPlanted(String cadena) {
        super(false);
        this.cadena=cadena;
    }
}
