/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mbera
 */
public class ServerClient
{
	public int id;
    public Socket socket;
    public ClientListener listener;
	private Server server;
    private ObjectOutputStream sOutput;
	
	public ServerClient(Socket socketNumber, int clientId, Server comServer)
	{
		try {
			this.server = comServer;
			this.socket = socketNumber;
			this.id = clientId;
			this.sOutput = new ObjectOutputStream(socket.getOutputStream());
			this.listener = new ClientListener(this, server);
		} catch (IOException ex) {
			Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}

class ClientListener extends Thread
{
	ServerClient serverClient;
	Server server;
	
	public ClientListener(ServerClient client, Server comServer)
	{
		this.serverClient = client;
		this.server = comServer;
	}

	@Override
	public void run()
	{
		
	}
}
