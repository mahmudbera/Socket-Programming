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
public class ServerClient
{
	public int id;
    public Socket socket;
    public ClientListener listener;
	private Server server;
    public ObjectOutputStream sOutput;
	public ObjectInputStream sInput;
	public String clientName = "";
	public String clientPassword = "";
	
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
		while (serverClient.socket.isConnected()) {			
			try {
				Request request = (Request) serverClient.sInput.readObject();
				switch (request.thisType) {
					case NameandPassword:
						boolean hasName = false;
						String message[] = request.request.toString().split(",");
						String name = message[0], password = message[1];
						for (ServerClient client : server.clientList) {
							if (client.clientName == name) {
								hasName = true;
							}
						}
						if (!hasName) {
							serverClient.clientName = name;
							serverClient.clientPassword = password;
							
							Request respond = new Request(Request.requestType.ClientConnected);
						}
						else
						{
							break;
						}
						break;
					case PublicRoomText:
						request.request = serverClient.clientName + " : " + request.request.toString();
						server.publicMessageSender(request);
						break;
					case GetProjects:
						ArrayList<String> projectNamesArrayList = new ArrayList<>();
						for (Project project : server.projectList) {
							projectNamesArrayList.add(project.projectName);
						}
						Request sendProjectNames = new Request(Request.requestType.GetProjects);
						sendProjectNames.request = projectNamesArrayList;
						server.sendToClient(serverClient, sendProjectNames);
						break;
					case GetProjectMembers:
						ArrayList<String> projectMembersList = new ArrayList<>();
						Project project = server.getProject(request.request.toString());
						for (ServerClient client : project.clientList) {
							projectMembersList.add(client.clientName);
						}
						Request respond = new Request(Request.requestType.GetProjectMembers);
						respond.request = projectMembersList;
						server.sendToClient(serverClient, request);
						break;
					default:
						throw new AssertionError();
				}
			} catch (IOException ex) {
				Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
