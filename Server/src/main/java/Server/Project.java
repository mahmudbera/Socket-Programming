/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.util.ArrayList;

/**
 *
 * @author mbera
 */
public class Project
{
	String projectName;
	ArrayList<String> clientList = new ArrayList<>();
	
	public Project(String name, String clientName)
	{
		this.projectName = name;
		clientList.add(clientName);
	}
}
