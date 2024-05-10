/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

/**
 *
 * @author mbera
 */
public class Request implements java.io.Serializable
{
	public enum requestType
	{
		Login
	}

	public requestType thisType;
	public Object request;
	
	public Request(requestType type)
	{
		this.thisType = type;
	}
}
