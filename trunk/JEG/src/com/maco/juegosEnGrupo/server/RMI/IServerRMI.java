package com.maco.juegosEnGrupo.server.RMI;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import com.macro.clienteRMI.dominio.IClienteRMI;

public interface IServerRMI extends Remote {
	void login(String email, String pwd, IClienteRMI cliente)throws RemoteException, SQLException, IOException;
	void register(String email, String pwd1, String pwd2)throws RemoteException;
}
