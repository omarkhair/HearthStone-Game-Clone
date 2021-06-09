package view;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
	ThreadListener listener;
	ObjectInputStream ois;
	// DataOutputStream dos;
	Socket s;
	private int port;
	private boolean exit;
	private ServerSocket ss;
	//boolean waiting;
	public ClientHandler(ThreadListener listener,int p) {
		this.listener=listener;
		port=p;
		exit=false;
	}
	public void run() {
	//	System.out.println("active");
		try {
			ss = new ServerSocket(port);
			while (!exit) {
				//System.out.println("done");
				s = ss.accept();
				//System.out.println("accepted");
				ois = new ObjectInputStream(s.getInputStream());
				listener.onGettingInput(ois);
				//System.out.println("notified");
				//ss.close();
				s.close();
				
			}
			ss.close();
			System.out.println("should die");
		} catch (IOException e) {
			//System.out.println("here");
			//e.printStackTrace();
		}
		
	}
	public void terminate() {
		exit=true;
		try {
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void start() {
		exit=false;
		super.start();
		
	}
}
