/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import Message.Request;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginContext;

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
				Request request = new Request(Request.requestType.ClientDisconnected);
				request.request = this.clientName;
				this.sendToServer(request);

				this.listener.Disconnect();
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

	boolean isRunning = true;

	public ServerListener(Client client)
	{
		this.client = client;
	}

	public void Disconnect()
	{
		isRunning = false;
		interrupt();
	}

	@Override
	public void run()
	{
		while (isRunning) {
			try {
				Request request = (Request) client.sInput.readObject();
				switch (request.thisType) {
					case Login:
						if ((boolean) request.request == true) {
							this.client.respond = true;
							Request loginRespond = new Request(Request.requestType.ClientConnected);
							this.client.sendToServer(loginRespond);
							Login.nextFrame = new HomePage(this.client);
							Login.nextFrame.setVisible(true);
							Login.login.setVisible(false);
							Login.nextFrame.setTitle(this.client.clientName);
						}else{
							Login.showMessage();
							Login.client.Stop();
						}
						break;
					case ClientConnected:
						HomePage.DLMUsers.addElement((String) request.request);
						break;
					case GetUsers:
						ArrayList<String> userNames = (ArrayList<String>) request.request;
						HomePage.DLMUsers.removeAllElements();
						for (String name : userNames) {
							HomePage.DLMUsers.addElement(name);
						}
						break;
					case ClientDisconnected:
						for (int i = 0; i < HomePage.DLMUsers.size(); i++) {
							if (HomePage.DLMUsers.getElementAt(i).equals(request.request)) {
								HomePage.DLMUsers.removeElementAt(i);
							}
						}
						break;

					case CreateProject:
						if (request.request.equals(true)) {
							HomePage.groupChat = new GroupChat(this.client);
							HomePage.groupChat.setVisible(true);
							HomePage.groupChat.ProjectName = request.projectName;
							HomePage.groupChat.ClientName = this.client.clientName;
							HomePage.DLMUserProjects.addElement(request.projectName + " -> " + request.password);
						}
						break;
					case EnterGroupChat:
						if (request.request.equals(true)) {
							HomePage.groupChat = new GroupChat(client);
							HomePage.groupChat.ProjectName = request.projectName.toString();
							HomePage.groupChat.setVisible(true);
							HomePage.groupChat.getMessages();
						}
						break;
					case GetProjects:
						ArrayList<String> projectNames = (ArrayList<String>) request.request;
						HomePage.DLMAvailableProjects.removeAllElements();
						for (String projectName : projectNames) {
							HomePage.DLMAvailableProjects.addElement(projectName);
						}
						break;
					case GetProjectMembers:
						HomePage.getProjectUsers((ArrayList<String>) request.request);
						break;
					case ProjectCreated:
						HomePage.DLMAvailableProjects.addElement(request.request);
						break;

					case SendFileToGroup:
						ArrayList<Object> fileArrayList = (ArrayList<Object>) request.request;
						String file = System.getProperty("user.home") + "/Downloads/" + fileArrayList.get(5).toString() + fileArrayList.get(1);
						byte[] mybytearray = (byte[]) fileArrayList.get(2);
						OutputStream os = new FileOutputStream(file);
						os.write(mybytearray);
						//System.out.println((String) fileArrayList.get(1) + " geldi.");
						HomePage.groupChat.DLMMessageList.addElement(fileArrayList.get(5).toString() + ":(Dosya)" + fileArrayList.get(1).toString());
						os.close();
						break;
					case SendFileToPersonal:
						ArrayList<Object> pFileArrayList = (ArrayList<Object>) request.request;
						String pFile = System.getProperty("user.home") + "/Downloads/" + pFileArrayList.get(5).toString() + pFileArrayList.get(1);
						byte[] pmybytearray = (byte[]) pFileArrayList.get(2);
						OutputStream pOs = new FileOutputStream(pFile);
						pOs.write(pmybytearray);
						//System.out.println((String) fileArrayList.get(1) + " geldi.");
						if (HomePage.personalChat != null && HomePage.personalChat.isVisible() == true) {
							HomePage.personalChat.DLMMessagesList.addElement(pFileArrayList.get(5).toString() + ":(Dosya)" + pFileArrayList.get(1).toString());
						}
						pOs.close();
						break;
					case SendPersonalMessage:
						if (HomePage.personalChat != null && HomePage.personalChat.isVisible() == true) {
							HomePage.personalChat.DLMMessagesList.addElement(request.request);
						}
						break;
					case SendMessageToGroup:
						if (HomePage.groupChat.isVisible() == true) {
							HomePage.groupChat.DLMMessageList.addElement(request.request);
						}
						break;
					case GetGroupMessages:
						ArrayList<String> messages = (ArrayList<String>) request.request;
						HomePage.groupChat.DLMMessageList.removeAllElements();
						for (String message : messages) {
							HomePage.groupChat.DLMMessageList.addElement(message);
						}
						break;
					case GetPrivateMessages:
						ArrayList<String> pMessages = (ArrayList<String>) request.request;
						HomePage.personalChat.DLMMessagesList.removeAllElements();
						for (String pMessage : pMessages) {
							HomePage.personalChat.DLMMessagesList.addElement(pMessage);
						}
						break;

					case LoginPrivateChat:
						HomePage.personalChat = new PersonalChat(this.client);
						HomePage.personalChat.ToClient = request.request.toString();
						HomePage.personalChat.setVisible(true);
						HomePage.personalChat.getMessages();
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
