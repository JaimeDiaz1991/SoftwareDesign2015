package edu.uclm.esi.common.server.persistence;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOBlackJack {
	public static void registrarMovimiento(int iduser, String mov, int idpartida, int idgame) throws SQLException {
		Connection bd=Broker.get().getDBPrivilegiada();
		try {
			String sql="{call RegistrarMovimiento (?, ?, ?, ?, ?, ?, ?)}";
			CallableStatement cs=bd.prepareCall(sql);
			cs.setInt(1, iduser);
			cs.setString(2, mov);
			cs.setInt(3,idpartida);
			cs.setInt(4,idgame);

			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.registerOutParameter(7, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			String exito=cs.getString(5);
			// q1 y q2 se ponen simplemente con fines ilustrativos, para que se vea el resultado de la transacción del procedimiento almacenado.
			String q1=cs.getString(6);		
			String q2=cs.getString(7);
			if (exito!=null && !(exito.equals("OK")))
				throw new SQLException(exito);
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			bd.close();
		}
	}
}
