package org.javafx.devtools.agent.connector;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteConnector extends UnicastRemoteObject {
  protected RemoteConnector(int port) throws RemoteException {
    super(port);
  }
}
