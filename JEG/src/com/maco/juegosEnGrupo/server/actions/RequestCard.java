package com.maco.juegosEnGrupo.server.actions;

import org.json.JSONException;
import org.json.JSONObject;

import com.maco.juegosEnGrupo.server.dominio.BlackJack;
import com.maco.juegosEnGrupo.server.dominio.Game;
import com.maco.juegosEnGrupo.server.dominio.Match;
import com.opensymphony.xwork2.ActionContext;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.OKMessage;
import edu.uclm.esi.common.server.actions.JSONAction;
import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.User;
import edu.uclm.esi.common.server.persistence.DAOBlackJack;
@SuppressWarnings("serial")

public class RequestCard extends JSONAction {
	private int idUser;
	private int idGame;
	private int idMatch;
	private JSONObject jsoRec;
	@Override
	
	protected String postExecute() {
		try {
			Manager manager=Manager.get();
			User user=manager.findUserById(this.idUser);
			if (user==null)
				throw new Exception("Usuario no autenticado");
			Game g=manager.findGameById(idGame);
			Match match=g.findMatchById(idMatch, idUser);
			match.requestCard(user, this.jsoRec,this.idMatch);
			String mov="\"RequestCard\"";
			String desc="\""+jsoRec.get("text")+"\"";
			BlackJack.insert_mov(idUser,mov, idMatch, idGame,desc);
			return SUCCESS;
		} catch (Exception e) {
			this.exception=e;
			ActionContext.getContext().getSession().put("exception", e);
			return ERROR;
		}
	}

	@Override
	public void setCommand(String cmd) {
		try {
			this.jsoRec = new JSONObject(cmd);
		} catch (JSONException e) {
			this.exception=e;
		}
		
	}

	@Override
	public String getResultado() {
		JSONMessage jso;
		if (this.exception!=null)
			jso=new ErrorMessage(this.exception.getMessage());
		else
			jso=new OKMessage();
		return jso.toJSONObject().toString();
	}
	
	public void setIdUser(int idUser) {
		this.idUser=idUser;
	}
	
	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}
	
	public void setIdMatch(int idMatch) {
		this.idMatch=idMatch;
	}
}
