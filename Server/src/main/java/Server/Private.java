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
public class Private
{
	ArrayList<String> messages = new ArrayList<>();
	String client1;
	String client2;
	
	public Private(String client1, String client2)
	{
		this.client1 = client1;
		this.client2 = client2;
	}
}
