/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Message;

/**
 *
 * @author mbera
 */
public class Request implements java.io.Serializable
{

	public enum requestType
	{
		Login, ClientConnected, GetUsers, ClientDisconnected,
		CreateProject, GetProjects, GetProjectMembers, ProjectCreated, EnterGroupChat, 
		GetGroupMessages, GetPrivateMessages, SendPersonalMessage, SendMessageToGroup,
		LoginPrivateChat
	}

	public requestType thisType;
	public Object request;
	
	public Object client;
	public String projectName; // HomePage My Project sekmesine ekleme yapmak için kullandım.
	public String password;
	
	public boolean checkPassword = true;
	
	public String clientName;
	
	public Request(requestType type)
	{
		this.thisType = type;
	}
}
