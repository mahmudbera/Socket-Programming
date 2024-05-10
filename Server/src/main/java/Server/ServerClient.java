/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mbera
 */
public class ServerClient implements java.io.Serializable
{

	public int id;
	public Socket socket;
	public ClientListener listener;
	public ObjectOutputStream sOutput;
	public ObjectInputStream sInput;
	public String clientName = "";

	public ServerClient(Socket socketNumber, int clientId, Server server)
	{
		try { // Bu kısımda bir eksiklik yok.
			this.socket = socketNumber;
			this.id = clientId;
			this.sInput = new ObjectInputStream(socket.getInputStream());
			this.sOutput = new ObjectOutputStream(socket.getOutputStream());
			this.listener = new ClientListener(this, server);
			this.listener.start();
		} catch (IOException ex) {
			Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public class ClientListener extends Thread implements java.io.Serializable
	{

		ServerClient serverClient;
		Server server;

		public ClientListener(ServerClient client, Server server)
		{
			this.serverClient = client;
			this.server = server;
		}

		@Override
		public void run()
		{
			while (serverClient.socket.isConnected()) {
				try {
					this.server.text += "+0";
					Request request = (Request) this.serverClient.sInput.readObject(); // BURASINI ÇÖZEMEDİK
					switch (request.thisType) {
						case Login:
							this.server.text += "+2";
							this.serverClient.clientName = request.request.toString();
							this.server.text += "+3";
							Request respond = new Request(Request.requestType.Login);
							this.server.text += "+4";
							respond.request = (int) 1;
							this.server.text += "+5";
							this.server.sendToClient(serverClient, request);
							this.server.text += "+6";
							break;
						default:
							throw new AssertionError();
					}
					
					this.server.text += "-1";
				} catch (IOException ex) {
					Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
				} catch (ClassNotFoundException ex) {
					Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}
}
