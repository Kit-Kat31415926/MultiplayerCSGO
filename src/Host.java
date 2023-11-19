import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
/**
 * Handles host side of game
 */
public class Host extends Thread {
	private boolean ready = false;
	private boolean readyToFight = false;
	private boolean done = false;
    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
	/**
	 * Creates new host
	 * @author - Kaitlyn
	 */
	public Host() {
        try {
            socket = new Socket(Main.ADDRESS, Main.PORT);
            System.out.println("Connection established with server"); // Test
            // Allows output to be sent and received to and from the socket
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            out.writeUTF("host");
        }
        catch (Exception e) {
            System.out.println(e);
        }
        host();
	}
	
	/**
	 * Begins hosting
	 * @author - Kaitlyn
	 */
	public void host() {
		String code = "";
		try {
			code = in.readUTF();
		} catch (Exception e) {
			System.out.println(e);
		}
		Main.getScreen().displayCode(code);
		start();
	}
	
	@Override
	public void run() {
		while (!ready) {
			try {
				if(in.readUTF().equals("light")) {
					Main.getScreen().addStar();
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		while (!readyToFight) {
			try {
				String line = in.readUTF();
				if (line.equals("player ready to fight")) {
					// Main.getScreen().addPlayer(); // Adds another player to roster of fighters somehow
				} else if (line.equals("starting fight automatically")) {
					readyToFight = true;
					startBossFight();
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		while (!done) {
			try {
				String line = in.readUTF();
				if (line.equals("player done")) {
					// Display that a player finished fighting somehow
				} else if (line.equals("display ending")) {
					done = true;
					boolean win = false;
					if (in.readUTF().equals("true")) {
						win = true;
					}
					Main.getScreen().displayEnding(win);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

  /**
   * Sends message to players to begin answering questions
   * @author - Kaitlyn
   */
	public void giveQuestions() {
		ready = true;
		try {
			out.writeUTF("give questions");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void startBossFight() {
		try {
			readyToFight = true;
			out.writeUTF("start boss fight");
			Main.getScreen().displayBossProgress();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
