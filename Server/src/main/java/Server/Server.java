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
	public ArrayList<ServerClient> clientList = new ArrayList<>();
	public ArrayList<Project> projectList = new ArrayList<>();
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

	public void publicMessageSender(Request message)
	{
		for (ServerClient serverClient : clientList) {
			try {
				serverClient.sOutput.writeObject(message);
			} catch (IOException ex) {
				Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public void personalMessageSender(ServerClient client, Request message)
	{
		
	}
	
	public void sendToClient(ServerClient client, Request request)
	{
		try {
			client.sOutput.writeObject(request);
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public Project getProject(String name)
	{
		for (Project project : projectList) {
			if (name.equalsIgnoreCase(project.projectName)) {
				return project;
			}
		}
		return null;
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
