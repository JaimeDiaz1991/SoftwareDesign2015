package com.maco.blackjack.dominio;

import com.maco.blackjack.jsonMessages.BlackJackActivity;
import com.maco.blackjack.jsonMessages.BlackJackBoardMessage;
import com.maco.blackjack.jsonMessages.BlackJackRequestCard;
import com.maco.tresenraya.jsonMessages.TresEnRayaBoardMessage;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Hashtable;

import edu.uclm.esi.common.androidClient.domain.Store;

/**
 * Created by Luis on 06/03/2015.
 */
public class BlackJack {
    public static int BJ = 2;
    private BlackJackActivity ctx;
    private String opponent;
    private String userWithTurn;
    private char[][] [] tapete; //PARA ACLARARNOS, pero creo que el tercer array sobra
    private Hashtable<Integer,ArrayList<Carta>> tapeteCartas; //ELEGIR ESTA LINEA O LA ANTERIOR
    private int [] tapetePuntuAcum;
    private BlackJackBoardMessage board;

    public BlackJack(BlackJackActivity ctx) {
        this.ctx=ctx;
        tapete= new char[5] [2] [1];

    }
    public void put(BlackJackRequestCard mov){
        //IMPLEMENTAR PEDIR CARTA
    }
    public String getpunt(int idusu) {
        //MAL, aquí habría que hacer que devuelva la puntuación por ejemplo
        return "" + tapetePuntuAcum[idusu];
    }
    public void load(BlackJackBoardMessage board) throws JSONException {
        String tapetes=board.gettapete();
        int cont=0;
        //RELLENAR TABLERO


        if (board.getPlayer2()!=null) {
            if (Store.get().getUser().getEmail().equals(board.getPlayer1()))
                this.opponent=board.getPlayer2();
            else
                this.opponent=board.getPlayer1();
            this.userWithTurn=board.getUserWithTurn();
        }
        if (board.getPlayer3()!=null) {
            if (Store.get().getUser().getEmail().equals(board.getPlayer1()))
                this.opponent=board.getPlayer2();
            else
                this.opponent=board.getPlayer1();
            this.userWithTurn=board.getUserWithTurn();
        }
        if (board.getPlayer4()!=null) {
            if (Store.get().getUser().getEmail().equals(board.getPlayer1()))
                this.opponent=board.getPlayer2();
            else
                this.opponent=board.getPlayer1();
            this.userWithTurn=board.getUserWithTurn();
        }
    }

    public String getOpponent() {
        return opponent;
    }

    public String getUserWithTurn() {
        return userWithTurn;
    }

}
