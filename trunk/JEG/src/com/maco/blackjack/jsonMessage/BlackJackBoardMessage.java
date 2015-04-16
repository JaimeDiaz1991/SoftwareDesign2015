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
        /*       @JSONable
        private String player3;
        @JSONable
        private String player4;
        @JSONable
        private String player5;
   */     @JSONable
        private String userWithTurn;

        public BlackJackBoardMessage(String board) throws JSONException {
            super(false);
            StringTokenizer st=new StringTokenizer(board, "#");
            this.tapete=st.nextToken();
            this.player1=st.nextToken();
            if (st.hasMoreTokens()) {
                this.player2 = st.nextToken();
                userWithTurn = st.nextToken();
            }/*else if (st.hasMoreTokens()) {
                this.player3 = st.nextToken();
                userWithTurn = st.nextToken();
            }else if (st.hasMoreTokens()) {
                this.player4 = st.nextToken();
                userWithTurn = st.nextToken();
            }else if (st.hasMoreTokens()) {
                this.player5 = st.nextToken();
                userWithTurn = st.nextToken();  
            }*/
        }

        public BlackJackBoardMessage(JSONObject jso) throws JSONException {
            super(false);
            this.tapete=jso.getString("squares");
            this.player1=jso.getString("player1");
            if (jso.optString("player2").length()>0) {
                this.player2=jso.getString("player2");
                this.userWithTurn=jso.getString("userWithTurn");
  /*          }else if (jso.optString("player3").length()>0) {
                this.player2=jso.getString("player3");
                this.userWithTurn=jso.getString("userWithTurn");
            }else if (jso.optString("player4").length()>0) {
                this.player2=jso.getString("player4");
                this.userWithTurn=jso.getString("userWithTurn");
            }else if (jso.optString("player5").length()>0) {
                this.player2=jso.getString("player5");
                this.userWithTurn=jso.getString("userWithTurn");
    */        }
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

      /*  public String getPlayer3() {
            return player3;
        }

        public String getPlayer4() {
            return player4;
        }

        public String getPlayer5() {
            return player5;
        }
*/
        public String getUserWithTurn() {
            return userWithTurn;
        }
    }
