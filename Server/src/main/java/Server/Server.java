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
	public static ServerSocket serverSocket;
	public static int port;
	public static int clientNumber = 0;
	public static ArrayList<ServerClient> clientList = new ArrayList<>();
	public static ArrayList<Project> projectList = new ArrayList<>();
	public static ServerListener listener;

	public static void Start(int portNumber)
	{
		try {
			Server.port = portNumber;
			Server.serverSocket = new ServerSocket(port);
			Server.listener = new ServerListener();
			Server.listener.start();
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void publicMessageSender(Request message)
	{
		for (ServerClient serverClient : clientList) {
			try {
				serverClient.sOutput.writeObject(message);
			} catch (IOException ex) {
				Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
	public static void Brodcast(Request request)
	{
		for (ServerClient serverClient : clientList) {
			try {
				serverClient.sOutput.writeObject(request);
			} catch (IOException ex) {
				Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
	public static void sendToClient(ServerClient client, Request request)
	{
		try {
			client.sOutput.writeObject(request);
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static Project getProject(String name)
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
	@Override
	public void run()
	{
		while (!Server.serverSocket.isClosed()) {
			try {
				Socket clientSocket = Server.serverSocket.accept();
				ServerClient newClient = new ServerClient(clientSocket, Server.clientNumber);
				Server.clientList.add(newClient);
				Server.clientNumber++;
				newClient.listener.start();
			} catch (IOException ex) {
				Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
