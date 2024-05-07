/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

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
	public ArrayList<ServerClient>	clientList = new ArrayList<>();
	public HashMap<String, ArrayList<ServerClient>> rooms = new HashMap<String, ArrayList<ServerClient>>();
	public ServerListener listener;
	
	public Server(int portNumber)
	{
		try {
			port = portNumber;
			serverSocket = new ServerSocket(port);
			listener = new ServerListener(this);
			listener.start();
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}

class ServerListener extends Thread
{
	Server AppServer;
	
	public ServerListener(Server myServer)
	{
		AppServer = myServer;
	}
	
	@Override
	public void run()
	{
		while (!AppServer.serverSocket.isClosed()) {			
			try {
				Socket clientSocket = AppServer.serverSocket.accept();
				ServerClient newClient = new ServerClient(clientSocket, AppServer.clientNumber, AppServer);
				AppServer.clientList.add(newClient);
				AppServer.clientNumber++;
				newClient.listener.start();
			} catch (IOException ex) {
				Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
