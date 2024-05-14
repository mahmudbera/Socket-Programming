/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mbera
 */
public class Client implements java.io.Serializable
{

	public Socket socket;
	public ObjectInputStream sInput;
	public ObjectOutputStream sOutput;
	public ServerListener listener;
	public boolean respond = false;
	public String ip;
	public int port;
	public String clientName;

	public Client(String ip, int port, String Name)
	{
		try {
			this.port = port;
			this.ip = ip;
			this.clientName = Name;
			this.socket = new Socket(this.ip, this.port);
			this.sOutput = new ObjectOutputStream(this.socket.getOutputStream());
			this.sInput = new ObjectInputStream(this.socket.getInputStream());
			this.listener = new ServerListener(this);
			this.listener.start();
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void sendToServer(Request request)
	{
		try {
			this.sOutput.writeObject(request);
		} catch (IOException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void Stop()
	{
		try {
			if (this.socket != null) {
				this.socket.close();
				this.sOutput.close();
				this.sInput.close();
			}
		} catch (IOException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
}

class ServerListener extends Thread implements java.io.Serializable
{

	Client client;

	public ServerListener(Client client)
	{
		this.client = client;
	}

	@Override
	public void run()
	{
		while (this.client.socket.isConnected()) {
			try {
				Request request = (Request) client.sInput.readObject();
				switch (request.thisType) {
					case Login:
						if ((int) request.request == 1) {
							this.client.respond = true;
							Request loginRespond = new Request(Request.requestType.ClientConnected);
							this.client.sendToServer(loginRespond);
						}
						break;
					case ClientConnected:
						HomePage.DLMUsers.addElement(request.request);
						break;
					case CreateProject:
						if ((int) request.request == 1) {
							this.client.respond = true;
							
							Request projectRespond = new Request(Request.requestType.ProjectCreated);
							this.client.sendToServer(projectRespond);
						}
					case ProjectCreated:
						HomePage.DLMAvailableProjects.addElement(request.request);
						break;
					default:
						throw new AssertionError();
				}
			} catch (IOException ex) {
				Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

}
