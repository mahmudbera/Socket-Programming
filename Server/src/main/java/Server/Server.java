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
	public ArrayList<Private> privateList = new ArrayList<>();
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
	
	public Project getProject(String projectName)
	{
		for (Project project : projectList) {
			if (project.projectName.equals(projectName)) {
				return project;
			}
		}
		return null;
	}
	
	public ServerClient getClient(String clientName)
	{
		for (ServerClient serverClient : clientList) {
			if (serverClient.clientName.equals(clientName)) {
				return serverClient;
			}
		}
		return null;
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

	public Private getPrivate(String client1, String client2)
	{
		for (Private p : this.privateList) {
			if ((p.client1.equals(client1) || p.client2.equals(client1))
				&& (p.client1.equals(client2) || p.client2.equals(client2))) {
				return p;
			}
		}
		return null;
	}
	
	public boolean privateExist(String client1, String client2)
	{
		for (Private p : this.privateList) {
			if ((p.client1.equals(client1) || p.client2.equals(client1))
				&& (p.client1.equals(client2) || p.client2.equals(client2))) {
				return true;
			}
		}
		return false;
	}
	
	public void ClientDisconnected(ServerClient serverClient)
	{
		for (Project project : projectList) {
			if (project.owner.equals(serverClient.clientName)) {
				projectList.remove(project);
			}
		}
		
		for (Private privateChat : privateList) {
			if (privateChat.client1.equals(serverClient.clientName) || privateChat.client2.equals(serverClient.clientName) ) {
				privateList.remove(privateChat);
			}
		}
		
		this.clientList.remove(serverClient);
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
