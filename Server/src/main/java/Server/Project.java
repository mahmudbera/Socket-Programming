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
	ArrayList<ServerClient> clientList = new ArrayList<>();
	
	public Project(String name)
	{
		this.projectName = name;
	}
}
