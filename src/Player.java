import java.util.Scanner;
import java.io.*;
import java.net.*;
/**
 * Handles player side of game
 */
public class Player extends Thread {
	private String name;
	private String code;
	private int score = 0;
	private static Scanner scan = new Scanner(System.in);
    // initialize socket and input output streams
    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private QuestionBank b;
	/**
	 * Creates new player
	 * @author - Kaitlyn
	 */
	public Player() {
        // Establish a connection with server
        try {
            socket = new Socket(Main.ADDRESS, Main.PORT);
            System.out.println("Connection established with server"); // Test

            // Allows output to be sent and received to and from the socket
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            out.writeUTF("player");
    		Main.getScreen().displayOpening();
        }
        catch (Exception e) {
            System.out.println(e);
        }
	}

    /**
	 * Setter for player nickname
	 * @author - Ritali
     * @param - n - player name
	 */
	public void setNickname(String n) {
		name = n;
	}

   /**
	 * Setter for player code
	 * @author - Ritali & Kaitlyn
     * @param - c - player code
	 */
	public boolean setCode(String c) {
		code = c;
		try {
      int chckedCode = Integer.parseInt(c);
			out.writeUTF("new code");
			out.writeUTF(c);
			if (in.readUTF().equals("invalid")) {
				return false;
			}
		} catch(NumberFormatException ne) {
			System.out.println(ne);
			return false;
			
		}catch (Exception e) {
			System.out.println(e);
		}
		return true;
	}
	
	public void acceptingQuestions() {
		start();
	}
	
	@Override
	public void run() {
		try {
			if (in.readUTF().equals("start questions")) {
				b = new QuestionBank();
				b.getRandomQuestion(false);
			}
			if (in.readUTF().equals("start boss fight for player")) {
				b.getRandomQuestion(true);
				Thread.sleep(6000);
		    	String a = Main.getScreen().showCorrect();
		    	if (a != null && a.equals(Main.getScreen().getAnswer())) {
		    		Main.getScreen().displayGoodEnding();
		    		done(true);
		    	} else {
		    		Main.getScreen().displayBadEnding();
		    		done(false);
		    	}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void readyToFightBoss() {
		try {
			out.writeUTF("ready to fight");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public QuestionBank getQuestionBank() {
		return b;
	}
	
	public void checkAnswer(boolean b) {
		if (b) {
			score++;
		}
	}
	
	public void done(boolean correct) {
		try {
			out.writeUTF("done");
			out.writeUTF(correct ? "true" : "false");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
