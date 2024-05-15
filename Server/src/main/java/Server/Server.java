/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import Message.Request;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.security.util.KnownOIDs;

/**
 *
 * @author mbera
 */
public class Server
{

	public ServerSocket serverSocket;
	public int port;
	public int clientNumber = 0;
	public ArrayList<ServerClient> clientList = new ArrayList<>();
	public ArrayList<Project> projectList = new ArrayList<>();
	public ServerListener listener;

	public void Start(int portNumber)
	{
		try {
			this.port = portNumber;
			this.serverSocket = new ServerSocket(this.port);
			this.listener = new ServerListener(this);
			this.listener.start();
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void sendToClient(ServerClient client, Request request)
	{
		try {
			client.sOutput.writeObject(request);
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void sendToClients(Request request)
	{
		for (ServerClient serverClient : clientList) {
			try {
				serverClient.sOutput.writeObject(request);
			} catch (IOException ex) {
				Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public void sendToClients(ServerClient client, Request request)
	{
		for (ServerClient serverClient : clientList) {
			try {
				if (!serverClient.equals(client)) {
					serverClient.sOutput.writeObject(request);
				}
			} catch (IOException ex) {
				Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public class ServerListener extends Thread
	{

		Server server;

		public ServerListener(Server server)
		{
			this.server = server;
		}

		@Override
		public void run()
		{
			while (!this.server.serverSocket.isClosed()) {
				try { // Bu kısımda bir eksiklik yok.
					Socket clientSocket = this.server.serverSocket.accept();
					ServerClient newClient = new ServerClient(clientSocket, this.server.clientNumber, this.server);
					this.server.clientList.add(newClient);
					this.server.clientNumber++;
				} catch (IOException ex) {
					Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

}
