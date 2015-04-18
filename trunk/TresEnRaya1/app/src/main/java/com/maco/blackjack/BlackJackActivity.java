package com.maco.blackjack;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.maco.blackjack.domainBlackJack.BlackJack;
import com.maco.blackjack.jsonMessagesBlackJack.BJWaitingMessage;
import com.maco.blackjack.jsonMessagesBlackJack.BlackJackBet;
import com.maco.blackjack.jsonMessagesBlackJack.BlackJackBoardMessage;
import com.maco.blackjack.jsonMessagesBlackJack.BlackJackMatchReadyMessage;
import com.maco.blackjack.jsonMessagesBlackJack.BlackJackPlanted;
import com.maco.blackjack.jsonMessagesBlackJack.BlackJackRequestCard;
import com.maco.tresenraya.R;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uclm.esi.common.androidClient.dialogs.Dialogs;
import edu.uclm.esi.common.androidClient.domain.Store;
import edu.uclm.esi.common.androidClient.http.Proxy;
import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONParameter;


public class BlackJackActivity extends ActionBarActivity {

    private BlackJack match;
    private TextView tvPlayer;
    private TextView tvMessage;
    private TextView tvOpponent;
    private TextView [] cartas;
    private Button[] btns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_jack);

        this.tvPlayer = (TextView) this.findViewById(R.id.textViewBJPlayer);
        this.tvMessage = (TextView) this.findViewById(R.id.textViewMessageBJ);
        cartas=new TextView[5];
        for (int cont=0;cont<cartas.length;cont++) {
            int resId=this.getResources().getIdentifier("textViewCartas"+cont, "id", "com.maco.tresenraya");
            this.cartas[cont] = (TextView) findViewById(resId);

        }
        this.btns=new Button[3];


        for (int contador=0; contador<3; contador++) {

            int resId = this.getResources().getIdentifier("button"+contador, "id", "com.maco.tresenraya");
            this.btns[contador]=(Button) findViewById(resId);
            JSONObject tag=new JSONObject();
            try {
                if(contador == 0){
                    tag.put("planted",contador);
                    //HE CAMBIADO LO DE PLANTED AL TEXTO EN VEZ DE A LAS TAG
                    btns[contador].setText("PLANTED");
                }
                else if(contador == 1) {
                    tag.put("requestCard", contador);
                    btns[contador].setText("REQUESTCARD");
                }
                else if(contador == 2) {
                    tag.put("bet", contador);
                    btns[contador].setText("BET");

                }
            } catch (JSONException e) {}
            this.btns[contador].setTag(tag);
            this.btns[contador].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (match.getOpponent()==null) {
                        Dialogs.showOneButtonDialog(BlackJackActivity.this, "Attention", "Wait for the opponent", "OK");
                    } else if (!match.getUserWithTurn().equals(Store.get().getUser().getEmail())) {
                        Dialogs.showOneButtonDialog(BlackJackActivity.this, "Attention", "It's not your turn", "OK");
                    } else {
                        JSONObject jso=(JSONObject) v.getTag();
                        try{
                            if(jso.getInt("planted")==0){
                                BlackJackPlanted bjp;
                                bjp = new BlackJackPlanted("planted");
                                match.put(bjp);
                            }else if(jso.getInt("requestCard")==1){
                                BlackJackRequestCard bjrc;
                                bjrc = new  BlackJackRequestCard("requestCard");
                                match.put(bjrc);
                            }else if(jso.getInt("bet")==2){
                                BlackJackBet bjb;
                                bjb = new BlackJackBet("bet");
                                match.put(bjb);
                            }
                        }catch(Exception e){}
                    }
                }
            });

        }
        //this.tvOpponent = (TextView) this.findViewById(R.id.textViewOpponentBJ);

    this.match=new BlackJack(this);
        loadMatch();
    }

    private void loadMatch() {
        Store store=Store.get();
        this.tvPlayer.setText(store.getUser().getEmail());
        JSONParameter jspIdUser=new JSONParameter("idUser", ""+store.getUser().getId());
        JSONParameter jspIdGame=new JSONParameter("idGame", ""+store.getIdGame());
        JSONParameter jspIdMatch=new JSONParameter("idMatch", ""+store.getIdMatch());
        try {
            JSONMessage jsm= Proxy.get().postJSONOrderWithResponse("GetBoard.action", jspIdUser, jspIdGame, jspIdMatch);
            if (jsm.getType().equals(BlackJackBoardMessage.class.getSimpleName())) {
                loadBoard(jsm);
            } else {
                ErrorMessage em=(ErrorMessage) jsm;
                Toast.makeText(this, em.getText(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_black_jack, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadReadyMessage(BlackJackMatchReadyMessage rm) {
    }

    public void loadBoard(JSONMessage jsm) throws JSONException {
        BlackJackBoardMessage board = (BlackJackBoardMessage) jsm;
        String r = "";
        if (this.match == null)
            this.match = new BlackJack(this);
        this.match.load(board);

        for(int i = 0; i < this.match.getTapete().length; i++){
            r="";
            for(int j = 0; j< this.match.getTapete()[i].length;j++) {
                r+=this.match.getTapete()[i][j] + " ";
            }
            int resId=this.getResources().getIdentifier("textViewCartas"+i, "id", "com.maco.tresenraya");
            this.cartas[i] = (TextView) findViewById(resId);
            this.cartas[i].setText(r);
        }

    }

    public void loadMessage(BJWaitingMessage wm) {
        this.tvMessage.setText(wm.getText());
    }
}
