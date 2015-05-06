package com.maco.blackjack.jsonMessage;

import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;


    public class BlackJackBoardMessage extends JSONMessage {
        @JSONable
        private String tapete;
        @JSONable
        private String player1;
        @JSONable
        private String player2;
       /* @JSONable
        private String player3;
        @JSONable
        private String player4;*/
        @JSONable
        private String userWithTurn;

        public BlackJackBoardMessage(String board) throws JSONException {
            super(false);
            //int tokenacumu=1;
            StringTokenizer st=new StringTokenizer(board, "#");
            this.tapete=st.nextToken();
            this.player1=st.nextToken();
            //tokenacumu++;
            if(st.hasMoreTokens()){
            	this.player2=st.nextToken();
            	userWithTurn = st.nextToken();
            }
            /*if (tokenacumu<st.countTokens()-1) {
                this.player2 = st.nextToken();
            }else if (tokenacumu<st.countTokens()-1) {
                this.player3 = st.nextToken();
            }else if (tokenacumu<st.countTokens()-1) {
                this.player4 = st.nextToken();
            }*/
            //userWithTurn = st.nextToken();
        }

        public BlackJackBoardMessage(JSONObject jso) throws JSONException {
            super(false);
            this.tapete=jso.getString("tapete");
            this.player1=jso.getString("player1");
            if (jso.optString("player2").length()>0) {
                this.player2=jso.getString("player2");
                }/*else if (jso.optString("player3").length()>0) {
                this.player3=jso.getString("player3");
            }else if (jso.optString("player4").length()>0) {
                this.player4=jso.getString("player4");
                }*/
            this.userWithTurn=jso.getString("userWithTurn");
        }

        public String getTapete() {
            return tapete;
        }

        public String getPlayer1() {
            return player1;
        }

        public String getPlayer2() {
            return player2;
        }

      /*public String getPlayer3() {
            return player3;
        }

        public String getPlayer4() {
            return player4;
        }*/
        public String getUserWithTurn() {
            return userWithTurn;
        }
    }
