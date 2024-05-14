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
					Request request = (Request) this.serverClient.sInput.readObject(); // BURASINI ÇÖZEMEDİK
					this.server.text += "request altı";
					switch (request.thisType) {
						case Login:
							boolean check = false;
							for (ServerClient client : this.server.clientList) {
								if (client.clientName.equals(request.request)) {
									check = true;
								}
							}
							if (check) {
								Request loginRespond = new Request(Request.requestType.Login);
								loginRespond.request = (int) 0; // Olumsuz Cevap
								this.server.sendToClient(this.serverClient, loginRespond);
							} else {
								Request loginRespond = new Request(Request.requestType.Login);
								loginRespond.request = (int) 1; // Olumlu Cevap
								this.server.sendToClient(this.serverClient, loginRespond);
							}
							break;
						case ClientConnected:
							Request respondClientConnected = new Request(Request.requestType.ClientConnected);
							respondClientConnected.request = this.serverClient.clientName;
							this.server.sendToClients(respondClientConnected);
							break;
						case CreateProject:
							String all[] = request.request.toString().split(",");
							String projectName = all[0];
							String password = all[1];
							String clientName = all[3];

							boolean checkName = false;
							for (Project project : this.server.projectList) {
								if (project.projectName.equals(projectName)) {
									checkName = true;
								}
							}

							if (checkName) {
								Request respondCreateProject = new Request(Request.requestType.CreateProject);
								respondCreateProject.request = (int) 0; // Olumsuz
								this.server.sendToClient(this.serverClient, respondCreateProject);
							} else {
								Project project = new Project(clientName, password, clientName);
								this.server.projectList.add(project);
								
								Request respondCreateProject = new Request(Request.requestType.CreateProject);
								respondCreateProject.request = (int) 1; // Olumlu
								this.server.sendToClient(this.serverClient, respondCreateProject);
								
								Request respondProjectCreated = new Request(Request.requestType.ProjectCreated);
								respondProjectCreated.request = projectName + " -> " + clientName;
								this.server.sendToClients(respondProjectCreated);
							}
						case ProjectCreated:
							
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
