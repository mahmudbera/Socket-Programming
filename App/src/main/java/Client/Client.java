/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import java.io.IOException;
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
public class Client implements java.io.Serializable
{
	public static Socket socket;
	public static ObjectInputStream sInput;
	public static ObjectOutputStream sOutput;
	public static ServerListener listener;
	
	public static void Start(String ip, int port, String Name)
	{
		try {
			Client.socket = new Socket(ip, port);
			Client.sInput = new ObjectInputStream(Client.socket.getInputStream());
			Client.sOutput = new ObjectOutputStream(Client.socket.getOutputStream());
			Client.listener = new ServerListener();
			Client.listener.start();
			
			Request request = new Request(Request.requestType.Login);
			request.request = Name;
			Client.sendToServer(request);
		} catch (IOException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void checkRegister(String ip, int port, String name)
	{
		try {
			socket = new Socket(ip, port);
			sInput = new ObjectInputStream(Client.socket.getInputStream());
			sOutput = new ObjectOutputStream(Client.socket.getOutputStream());
			listener = new ServerListener();
			listener.start();
			
			Request request = new Request(Request.requestType.Login);
			request.request = name;
			Client.sendToServer(request);
		} catch (IOException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void sendToServer(Request request)
	{
		try {
			Client.sOutput.flush();
			Client.sOutput.writeObject(request);
		} catch (IOException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
}

class ServerListener extends Thread implements java.io.Serializable
{
	@Override
	public void run()
	{
		while (Client.socket.isConnected()) {			
			try {
				Request request = (Request) Client.sInput.readObject();
				
				switch (request.thisType) {
					case Login:
						Thread.sleep(100);
						HomePage.HomePage.getUsers((ArrayList<String>) request.request);
						break;
					case CreateProject:
						//LoginPage.nextFrame.groupChat.Refresh();
						LoginPage.nextFrame.groupChat.ProjectName = request.request.toString();
						LoginPage.nextFrame.groupChat.setVisible(true);
						break;
					case GetAllUsers:
						for (String string : (ArrayList<String>) request.request) {
							HomePage.HomePage.DLMUsers.addElement(request);
						}
						break;
					case GetProjects:
						for (String string : (ArrayList<String>) request.request) {
							HomePage.HomePage.DLMProjects.addElement(request);
						}
						HomePage.HomePage.RefreshProjects();
						break;
					case projectMemberstoChat:
						LoginPage.nextFrame.groupChat.ALUsers.removeAllElements();
						ArrayList<String> members = (ArrayList<String>) request.request;
						for (String member : members) {
							LoginPage.nextFrame.groupChat.ALUsers.addElement(member);
						}
						break;
					default:
						throw new AssertionError();
				}
			} catch (IOException ex) {
				Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
			} catch (InterruptedException ex) {
				Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
}
		
