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
			this.listener.Disconnect();
		} catch (IOException ex) {
			Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public class ClientListener extends Thread implements java.io.Serializable
	{

		ServerClient serverClient;
		Server server;
		boolean check = true;
		
		public ClientListener(ServerClient client, Server server)
		{
			this.serverClient = client;
			this.server = server;
		}

		public void Disconnect()
		{
			check = false;
			interrupt();
		}
		
		@Override
		public void run()
		{
			while (check) {
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
							this.server.ClientDisconnected(this.serverClient);
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
							} else {
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
						case OutFromProject:
							Project po = this.server.getProject(request.request.toString());
							for (String string : po.clientList) {
								if (string.equals(this.serverClient.clientName)) {
									po.clientList.remove(string);
								}
							}
							break;
							
						case SendFileToGroup:
							ArrayList<Object> fileArrayList = (ArrayList<Object>) request.request;
							Project p = this.server.getProject(((ArrayList<Object>) request.request).get(0).toString());
							p.messageList.add(fileArrayList.get(5).toString() + ":(Dosya)" + fileArrayList.get(1).toString());
							for (String pUsers : p.clientList) {
								ServerClient toClient = this.server.getClient(pUsers);
								this.server.sendToClient(toClient, request);
							}
							break;
						case SendFileToPersonal:
							ArrayList<Object> pFileArrayList = (ArrayList<Object>) request.request;
							ServerClient sc = this.server.getClient(pFileArrayList.get(0).toString());
							this.server.sendToClient(sc, request);
							Private pp = this.server.getPrivate(pFileArrayList.get(5).toString(), pFileArrayList.get(0).toString());
							pp.messages.add(pFileArrayList.get(5).toString() + ":(Dosya)" + pFileArrayList.get(1).toString());
							break;
						case SendPersonalMessage:
							ServerClient serverToClient = null;
							for (ServerClient sClient : this.server.clientList) {
								if (sClient.clientName.equals(request.clientName)) {
									serverToClient = sClient;
								}
							}
							if (serverToClient != null) {
								String personelMessage = request.request.toString();
								Request respondSendPersonalMessage = new Request(Request.requestType.SendPersonalMessage);
								respondSendPersonalMessage.request = personelMessage;
								this.server.sendToClient(serverToClient, respondSendPersonalMessage);

								Private temp = this.server.getPrivate(this.serverClient.clientName, request.clientName);
								temp.messages.add(personelMessage);
							}
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
							ArrayList<String> groupMessages = new ArrayList<>();
							for (Project mProject : this.server.projectList) {
								if (mProject.projectName.equals(request.projectName)) {
									for (String groupMessage : mProject.messageList) {
										groupMessages.add(groupMessage);
									}
								}
							}
							Request respondGetGroupMessages = new Request(Request.requestType.GetGroupMessages);
							respondGetGroupMessages.request = groupMessages;
							this.server.sendToClient(this.serverClient, respondGetGroupMessages);
							break;
						case GetPrivateMessages:
							String clientNames[] = request.request.toString().split(",");
							String c1 = clientNames[0];
							String c2 = clientNames[1];
							ArrayList<String> privateMessages = new ArrayList<>();

							for (Private chat : this.server.privateList) {
								if ((chat.client1.equals(c1) || chat.client2.equals(c1))
										&& (chat.client1.equals(c2) || chat.client1.equals(c2))) {
									for (String message1 : chat.messages) {
										privateMessages.add(message1);
									}
								}
							}
							Request respondGetPrivateMessages = new Request(Request.requestType.GetPrivateMessages);
							respondGetPrivateMessages.request = privateMessages;
							this.server.sendToClient(this.serverClient, respondGetPrivateMessages);
							break;

						case LoginPrivateChat:
							String names[] = request.request.toString().split(",");
							String client1 = names[0];
							String client2 = names[1];

							boolean chatCheck = this.server.privateExist(client1, client2);
							if (chatCheck == false) {
								Private newPrivate = new Private(client1, client2);
								this.server.privateList.add(newPrivate);
							}
							Request respondLoginPrivateChat = new Request(Request.requestType.LoginPrivateChat);
							respondLoginPrivateChat.request = client2;
							this.server.sendToClient(this.serverClient, respondLoginPrivateChat);
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
