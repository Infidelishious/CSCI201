package edu.usc.ianglow.server;

import java.io.Serializable;

public class Message implements Serializable {
	
	public final static int ACCEPTED = 0, 
				DENIED = 1,
				FINISHED = 2;
	
	public String url;
	public int type;
}
