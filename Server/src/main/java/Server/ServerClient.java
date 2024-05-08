/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import static Server.Request.requestType.CheckUser;
import static Server.Request.requestType.CreateProject;
import static Server.Request.requestType.GetAllUsers;
import static Server.Request.requestType.GetProjectMembers;
import static Server.Request.requestType.GetProjects;
import static Server.Request.requestType.Login;
import static Server.Request.requestType.PublicRoomText;
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
	public ObjectOutputStream sOutput;
	public ObjectInputStream sInput;
	public String clientName = "";

	public ServerClient(Socket socketNumber, int clientId)
	{
		try {
			this.socket = socketNumber;
			this.id = clientId;
			this.sInput = new ObjectInputStream(socket.getInputStream());
			this.sOutput = new ObjectOutputStream(socket.getOutputStream());
			this.listener = new ClientListener(this);
		} catch (IOException ex) {
			Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public class ClientListener extends Thread implements java.io.Serializable
	{

		ServerClient serverClient;

		public ClientListener(ServerClient client)
		{
			this.serverClient = client;
		}

		@Override
		public void run()
		{
			while (serverClient.socket.isConnected()) {
				try {
					Request request = (Request) serverClient.sInput.readObject();
					switch (request.thisType) {
						case Login:
							String name = request.request.toString();
							serverClient.clientName = name;
							break;
						case ClientConnected:
							for (Project project : Server.projectList) {
								if (project.projectName.equals(request.request)) {
									project.clientList.add(serverClient);
								}
							}
							Request connectionRespond = new Request(Request.requestType.ClientConnected);
							
							break;
						case CreateProject:
							Project project = new Project(request.request.toString());
							Server.projectList.add(project);
							project.clientList.add(serverClient);
							Request respond = new Request(Request.requestType.CreateProject);
							respond.request = project.projectName;
							Server.sendToClient(serverClient, respond);
							break;
						case GetProjects:
							ArrayList<String> projectNamesArrayList = new ArrayList<>();
							for (Project project2 : Server.projectList) {
								projectNamesArrayList.add(project2.projectName);
							}
							Request sendProjectNames = new Request(Request.requestType.GetProjects);
							sendProjectNames.request = projectNamesArrayList;
							Server.Brodcast(sendProjectNames);
							break;
						case GetAllUsers:
							ArrayList<String> allUsers = new ArrayList<>();
							for (ServerClient client : Server.clientList) {
								allUsers.add(client.clientName);
							}
							Request getUsersRequest = new Request(Request.requestType.GetAllUsers);
							getUsersRequest.request = allUsers;
							Server.sendToClient(this.serverClient, getUsersRequest);
							break;
						case PublicRoomText:
							request.request = serverClient.clientName + " : " + request.request.toString();
							Server.publicMessageSender(request);
							break;

						case GetProjectMembers:
							ArrayList<String> projectMembersList = new ArrayList<>();
							Project project1 = Server.getProject(request.request.toString());
							for (ServerClient client : project1.clientList) {
								projectMembersList.add(client.clientName);
							}
							Request respondProject = new Request(Request.requestType.GetProjectMembers);
							respondProject.request = projectMembersList;
							Server.sendToClient(serverClient, request);
							break;
						case projectMemberstoChat:
							ArrayList<String> memberList = new ArrayList<>();
							Project projectMember = Server.getProject(request.request.toString());
							for (ServerClient client : projectMember.clientList) {
								memberList.add(client.clientName);
							}
							Request memberRespond = new Request(Request.requestType.projectMemberstoChat);
							memberRespond.request = memberList;
							Server.sendToClient(serverClient, request);
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
