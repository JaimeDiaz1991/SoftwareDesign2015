package com.maco.juegosEnGrupo.server.RMI;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;

import com.macro.clienteRMI.dominio.IClienteRMI;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.server.domain.User;
import edu.uclm.esi.common.server.domain.Manager;

public class ServerRMI extends UnicastRemoteObject implements IServerRMI {
	
	public static void main(String[] args){
		try {
			ServerRMI servidor= new ServerRMI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected ServerRMI() throws RemoteException, MalformedURLException {
		super();
		LocateRegistry.createRegistry(2995);
		try{
			Naming.bind("rmi://127.0.0.1:2995/serverrmi",this );
		}
		catch(AlreadyBoundException e){
			Naming.rebind("rmi://127.0.0.1:2995/juegosEnGrupo",this);
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public void login(String email, String pwd, IClienteRMI cliente) throws RemoteException, SQLException, IOException {
		Connection bd=User.identify(email, pwd);
		User user=new User(bd, email, JSONMessage.USER_RMI);
		user.setClienteRMI(cliente);
		Manager manager=Manager.get();
		manager.add(user, null);
	}

	@Override
	public void register(String email, String pwd1, String pwd2)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
