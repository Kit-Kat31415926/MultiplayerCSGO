// FOR REFERENCE ONLY; WILL RUN ON SERVER

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
/**
 * Handles server requests
 */
public class ServerSide {
    // Initialize socket and input stream
	private static final int PORT = 5000;
	/**
	 * Keeps accepting clients and creates Threads for each to handle multiplayer
	 * @author - Kaitlyn
	 * @param args - String array
	 * @throws Exception - 
	 */
	public static void main(String[] args) throws Exception{
		ServerSocket server = new ServerSocket(PORT);
		try {
			// Infinite loop to receive client requests
			while (true) {
				// Reset socket
				Socket s = null;
				// Receive incoming requests
				s = server.accept();
				System.out.println("Accepted");
				// Input and output streams
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                // Assigns new thread
                Thread t = new ClientHandler(server, in, out);
                t.start();
                System.out.println("created new thread");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

/**
 * Handles each host and player
 */
class ClientHandler extends Thread {
	private boolean readyToFight = false;
	private boolean done = false;
	private boolean correct = false;
	// Collection of active hosts
	private static ArrayList<ClientHandler> hostList = new ArrayList<ClientHandler>();
	private static int nextCode = (int) (Math.random() * 500_000 + 100_000);
	// True if user is host
	private boolean host;
	private int code;
	// Players associated with host
	private ArrayList<ClientHandler> playerList = new ArrayList<ClientHandler>();
	// Host associated with player
	private ClientHandler myHost;
	
	private ServerSocket server;
	private DataInputStream in;
	private DataOutputStream out;
	/**
	 * Creates new Thread handling the user
	 * @author - Kaitlyn
	 * @param server - ServerSocket for client
	 * @param in - Reads input
	 * @param out - Sends output
	 */
	public ClientHandler(ServerSocket server, DataInputStream in, DataOutputStream out) {
		// Assign instance variables
		this.server = server;
		this.in = in;
		this.out = out;
		try {
			// Reads user type from client
			String line = in.readUTF();
			if (line.equals("host")) {
				host = true;
				System.out.println("New host created");
			}
			if (line.equals("player")) {
				host = false;
				System.out.println("New player created");
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Creates new host and send new code to client side
	 * @author - Kaitlyn
	 */
	public void run() {
		if (host) {
			// Adds host to list of hosts and assigns next code
			hostList.add(this);
			code = nextCode;
			nextCode++;
			// Sends code to client
			try {
				out.writeUTF(code + "");
				System.out.println("code sent to client");
				// Starts accepting commands
				String line = in.readUTF();
				while (!line.equals("exit")) {
					switch (line) {
						case "give questions":
							System.out.println("giving questions");
							for (ClientHandler c : playerList) {
								c.out.writeUTF("start questions");
							}
							break;
						case "start boss fight":
							for (ClientHandler c : playerList) {
								c.out.writeUTF("start boss fight for player");
							}
							break;
					}
					line = in.readUTF();
				}
				// End game for all players
				for (ClientHandler c : playerList) {
					c.out.writeUTF("exit");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			try {
				String line = in.readUTF();
				while (!line.equals("exit")) {
					switch (line) {
						case "new code":
							System.out.println("expecting new code");
							// Reads player's code from client
							String newCode = in.readUTF();
							code = Integer.parseInt(newCode);
							System.out.println("received new code");
							boolean found = false;
							// Finds host associated with the code
							for (int i = 0; i < hostList.size(); i++) {
								if (hostList.get(i).getCode() == code) {
									hostList.get(i).addPlayer(this);
									myHost = hostList.get(i);
									found = true;
								}
							}
							if (!found) {
								out.writeUTF("invalid");
							} else {
								out.writeUTF("connected");
								myHost.out.writeUTF("light");
							}
							break;
						case "ready to fight":
							myHost.out.writeUTF("player ready to fight");
							readyToFight = true;
							boolean allReady = true;
							for (ClientHandler c : myHost.playerList) {
								if (!c.readyToFight) {
									allReady = false;
								}
							}
							if (allReady) {
								myHost.out.writeUTF("starting fight automatically");
							}
							break;
						case "done":
							myHost.out.writeUTF("player done");
							done = true;
							if (in.readUTF().equals("true")) {
								correct = true;
							}
							boolean allDone = true;
							int winners = 0;
							for (ClientHandler c : myHost.playerList) {
								if (!c.done) {
									allDone = false;
								}
								if (c.correct) {
									winners++;
								}
							}
							if (allDone) {
								myHost.out.writeUTF("display ending");
								myHost.out.writeUTF(((double) winners / myHost.playerList.size() > 0.8) ? "true" : "false");
							}
							break;
					}
					line = in.readUTF();
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	/**
	 * Adds player to a host's game
	 * @author - Kaitlyn
	 * @param c - Player to be added for respective host
	 */
	public void addPlayer(ClientHandler c) {
		playerList.add(c);
	}

	/**
	 * Gets code
	 * @author - Kaitlyn
	 * @return - Code connecting the player and host
	 */
	public int getCode() {
		return code;
	}
}