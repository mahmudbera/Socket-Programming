/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

/**
 *
 * @author mbera
 */
public class Request implements java.io.Serializable
{

	public enum requestType
	{
		CheckUser, ClientConnected, CreateProject, UserDecline, GetProjects, GetProjectMembers, projectMemberstoChat, GetAllUsers, Login, PublicRoomText
	}

	public requestType thisType;
	public Object request;
	
	public Request(requestType type)
	{
		this.thisType = type;
	}
}
