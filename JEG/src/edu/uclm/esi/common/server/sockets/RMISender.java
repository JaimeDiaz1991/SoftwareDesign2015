package edu.uclm.esi.common.server.sockets;

import java.rmi.RemoteException;

import edu.uclm.esi.common.server.domain.User;

public class RMISender extends Sender {

	@Override
	void send(User user, String msg) {
		try {
			user.getClienteRMI().receive(msg);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};

	}

}
