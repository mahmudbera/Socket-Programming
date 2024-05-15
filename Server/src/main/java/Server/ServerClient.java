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
import Message.Request;

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

	public void Disconnect()
	{
		try {
			this.socket.close();
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
					Request request = (Request) this.serverClient.sInput.readObject();
					switch (request.thisType) {
						case Login:
							boolean check = false;
							String name = request.request.toString();
							this.serverClient.clientName = name;
							for (ServerClient client : this.server.clientList) {
								if (client.clientName.equals(name) && !client.equals(this.serverClient)) {
									check = true;
									System.out.println(client.clientName);
								}
							}

							Request loginRespond;
							if (check == true) {
								loginRespond = new Request(Request.requestType.Login);
								loginRespond.request = false; // Olumsuz Cevap
								this.server.sendToClient(this.serverClient, loginRespond);
							} else {
								loginRespond = new Request(Request.requestType.Login);
								loginRespond.request = true; // Olumlu Cevap
								this.server.sendToClient(this.serverClient, loginRespond);
							}
							break;
						case ClientConnected:
							Request respondClientConnected = new Request(Request.requestType.ClientConnected);
							respondClientConnected.request = this.serverClient.clientName;
							this.server.sendToClients(this.serverClient, respondClientConnected);
							break;
						case GetUsers:
							ArrayList<String> userNames = new ArrayList<>();
							for (ServerClient serverClient : this.server.clientList) {
								if (!serverClient.equals(this.serverClient)) {
									userNames.add(serverClient.clientName);
								}
							}
							Request respondGetUsers = new Request(Request.requestType.GetUsers);
							respondGetUsers.request = userNames;
							this.server.sendToClient(this.serverClient, respondGetUsers);
							break;
						case ClientDisconnected:
							Request respondClientDisconnected = new Request(Request.requestType.ClientDisconnected);
							respondClientDisconnected.request = request.request;
							this.server.sendToClients(respondClientDisconnected);
							this.serverClient.Disconnect();
							break;

						case CreateProject:
							String all[] = request.request.toString().split(",");
							String projectName = all[0];
							String password = all[1];
							String clientName = all[2];

							boolean checkName = false;
							for (Project project : this.server.projectList) {
								if (project.projectName.equals(projectName)) {
									checkName = true;
								}
							}
							if (checkName == true) {
								Request respondCreateProject = new Request(Request.requestType.CreateProject);
								respondCreateProject.request = false; // Olumsuz
								this.server.sendToClient(this.serverClient, respondCreateProject);
							} else {
								Project project = new Project(clientName, password, clientName);
								this.server.projectList.add(project);

								Request respondCreateProject = new Request(Request.requestType.CreateProject);
								respondCreateProject.request = true; // Olumlu
								respondCreateProject.projectName = projectName;
								respondCreateProject.password = password;
								this.server.sendToClient(this.serverClient, respondCreateProject);

								Request respondProjectCreated = new Request(Request.requestType.ProjectCreated);
								respondProjectCreated.request = projectName;
								this.server.sendToClients(this.serverClient, respondProjectCreated);
							}
						case EnterGroupChat:
							boolean enterCheck = false;
							if (request.checkPassword == true) {	
								for (Project enProject : this.server.projectList) {
									if (enProject.projectName.equals(request.projectName) && enProject.password.equals(request.password)) {
										enterCheck = true;
										int added = 0;
										for (String nameChecker : enProject.clientList) {
											if (nameChecker.equals(request.request.toString())) {
												added = 1;
											}
										}
										if (added == 0) {
											enProject.clientList.add(request.request.toString());
										}
									}
								}
							}
							else{
								enterCheck = true;
							}

							Request respondEnterGroupChat = new Request(Request.requestType.EnterGroupChat);
							if (enterCheck == true) {
								respondEnterGroupChat.request = true;
								respondEnterGroupChat.projectName = request.projectName;
								this.server.sendToClient(this.serverClient, respondEnterGroupChat);
							} else {
								respondEnterGroupChat.request = false;
								this.server.sendToClient(this.serverClient, respondEnterGroupChat);
							}
							break;
						case GetProjects:
							ArrayList<String> projectNames = new ArrayList<>();
							for (Project project : this.server.projectList) {
								if (!project.owner.equals(this.serverClient.clientName)) {
									projectNames.add(project.projectName);
								}
							}
							Request respondGetProjects = new Request(Request.requestType.GetProjects);
							respondGetProjects.request = projectNames;
							this.server.sendToClient(this.serverClient, respondGetProjects);
							break;
						case GetProjectMembers:
							String pName = request.request.toString();
							ArrayList<String> projectUsers = new ArrayList<>();
							for (Project project : this.server.projectList) {
								if (project.projectName.equals(pName)) {
									for (String string : project.clientList) {
										projectUsers.add(string);
									}
								}
							}
							Request projectMembersRespond = new Request(Request.requestType.GetProjectMembers);
							projectMembersRespond.request = projectUsers;
							this.server.sendToClient(this.serverClient, projectMembersRespond);
							break;

						case SendMessageToGroup:
							String message = request.request.toString();
							for (Project project : this.server.projectList) {
								if (project.projectName.equals(request.projectName)) {
									for (String userName : project.clientList) {
										for (ServerClient seClient : this.server.clientList) {
											if (seClient.clientName.equals(userName)) {
												this.server.sendToClient(seClient, request);
											}
										}
									}
									project.messageList.add(message);
								}
							}
							break;
						case GetGroupMessages:
							System.out.println("GEldim");
							ArrayList<String> groupMessages = new ArrayList<>();
							for (Project mProject : this.server.projectList) {
								System.out.println("GEldim2");
								System.out.println(request.projectName);
								if (mProject.projectName.equals(request.projectName)) {
									System.out.println("GEldim3");
									for (String groupMessage : mProject.messageList) {
										System.out.println("GEldim4");
										groupMessages.add(groupMessage);
									}
								}
							}
							Request respondGetGroupMessages = new Request(Request.requestType.GetGroupMessages);
							respondGetGroupMessages.request = groupMessages;
							this.server.sendToClient(this.serverClient, respondGetGroupMessages);
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
}
