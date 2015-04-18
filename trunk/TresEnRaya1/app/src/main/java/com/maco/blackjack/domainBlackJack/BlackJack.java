package com.maco.blackjack.domainBlackJack;

import android.widget.Toast;

import com.maco.blackjack.BlackJackActivity;
import com.maco.blackjack.jsonMessagesBlackJack.BlackJackBoardMessage;

import org.json.JSONException;

import edu.uclm.esi.common.androidClient.domain.Store;
import edu.uclm.esi.common.androidClient.http.Proxy;
import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONParameter;
import edu.uclm.esi.common.jsonMessages.OKMessage;

/**
 * Created by Luis on 06/03/2015.
 */
public class BlackJack {
    public static int BLACK_JACK = 2;
    private BlackJackActivity ctx;
    private String opponent;
    private String userWithTurn;
    private String [][] tapete= {{"", ""},{"",""},{"",""},{"",""},{"",""}};

    public BlackJack(BlackJackActivity ctx) {
        this.ctx=ctx;
        //tapete=new String[5][2];
    }

    public void load(BlackJackBoardMessage board) throws JSONException{
        //String tapete=board.getTapete();
        int col=0,row=0;
        for (int i=0;i<board.getTapete().length()-1;i++)
            if(board.getTapete().charAt(i)=='_')
                if(col==1)
                    col=0;
                else
                col++;
            else if(board.getTapete().charAt(i)=='-')
                row++;
            else
                tapete[row][col]=tapete[row][col]+board.getTapete().charAt(i);

    }


    public String[][]getTapete(){
        return tapete;
    }
    public void put(JSONMessage mov) {
        Store store=Store.get();
        JSONParameter jspIdUser=new JSONParameter("idUser", ""+ store.getUser().getId());
        JSONParameter jspIdGame=new JSONParameter("idGame", ""+store.getIdGame());
        JSONParameter jspIdMatch=new JSONParameter("idMatch", ""+store.getIdMatch());
        try {
            JSONMessage jsm= Proxy.get().postJSONOrderWithResponse("SendMovement.action", mov, jspIdUser, jspIdGame, jspIdMatch);
            if (jsm.getType().equals(OKMessage.class.getSimpleName())) {
                Toast.makeText(this.ctx, "Succesfully sent", Toast.LENGTH_LONG).show();
            } else {
                ErrorMessage em=(ErrorMessage) jsm;
                Toast.makeText(this.ctx, em.getText(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this.ctx, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public String getOpponent() {
        return opponent;
    }

    public String getUserWithTurn() {
        return userWithTurn;
    }
}
