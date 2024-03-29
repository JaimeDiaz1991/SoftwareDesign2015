package edu.uclm.esi.common.server.persistence;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class DAOBlackJack {
	public static void registrarMovimiento(int iduser, String mov, int idpartida, int idgame,String desc) throws SQLException {
		Connection bd=Broker.get().getDBPrivilegiada();
		try {
			String sql="insert into movimiento (tipo,idpartida,iduser,idgame,descripcion) VALUES ("+mov+","+idpartida+","+iduser+","+idgame+","+desc+")";
			Statement statement=(Statement) bd.createStatement();
			statement.execute(sql);
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			bd.close();
		}
	}
	public static void registrarRanking(int iduser, int idgame,int fichas,int idpartida) throws SQLException {
		Connection bd=Broker.get().getDBPrivilegiada();
		try {
			String sql="insert into ranking (idUser,idGame,fichas,idpartida) VALUES ("+iduser+","+idgame+","+fichas+","+idpartida+")";
			Statement statement=(Statement) bd.createStatement();
			statement.execute(sql);
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			bd.close();
		}
	}
}
