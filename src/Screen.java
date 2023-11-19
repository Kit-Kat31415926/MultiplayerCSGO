import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Game screen
 */
public class Screen extends JFrame implements ActionListener {
  private Host h;
  private Player p;
  private JPanel playGame, questionPanel1, questionPanel2, questionPanelOverall;
  private JButton host, player;
  private TextField display1, display2, output;
  private JLabel displayLabel1, displayLabel2;
  private ButtonGroup answers;
  private JRadioButton a1, a2, a3, a4;
  private ArrayList<String> currentQ;
  private int width, height, questionCount = 0;
  private Font newFont = new Font("Academy Engraved LET", Font.BOLD, 75);
  private Font inputFont = new Font("Palatino", Font.BOLD, 70);
  private Color c = new Color(238, 238, 238);

  /**
   * Creates new home screen
   * @author - Kaitlyn
   * @param title - Header of the frame
   */
  public Screen(String title) {
    super(title);
    // Sets game screen size to computer screen size
    setSize(Toolkit.getDefaultToolkit().getScreenSize());

    // Gets current height and width of screen
    height = (int) getSize().getHeight();
    width = (int) getSize().getWidth();
    createHomeScreen();
    setResizable(false);
  }

  /**
   * Creates home screen for user
   * 
   * @author - Nivedita and Kaitlyn
   */
  public void createHomeScreen() {
    // Create components
    playGame = new JPanel();
    playGame.setLayout(new FlowLayout());

    host = new JButton("Host Game");
    // Setting font for host
    host.setFont(newFont);
    host.setPreferredSize(new Dimension(width / 3, height / 3));
    host.setBackground(new Color(255, 197, 228));

    player = new JButton("Play Game");
    // Setting font for player
    player.setFont(newFont);
    player.setPreferredSize(new Dimension(width / 3, height / 3));
    player.setBackground(new Color(136, 221, 228));

    // Set commands
    host.setActionCommand("host");
    player.setActionCommand("player");

    // Set listeners
    host.addActionListener(this);
    player.addActionListener(this);

    // Add
    playGame.add(Box.createRigidArea(new Dimension(1, (int) (height * 2 / 3))));
    playGame.add(host);
    playGame.add(Box.createRigidArea(new Dimension(width / 6, 1)));
    playGame.add(player);

    try {
      String relName = "./assets/pinkstar.png";
      ImageIcon ii = new ImageIcon(relName);
      displayLabel1 = new JLabel();
      displayLabel1.setIcon(ii);
    } catch (NullPointerException exception) {
      exception.printStackTrace();
    }
    add(displayLabel1);
    add(playGame);
    playGame.setBackground(new Color(192, 207, 255));
    playGame.setPreferredSize(new Dimension(width, height));

    setLayout(new FlowLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
   * Creates host screen to display code for players
   * 
   * @author - Kaitlyn
   * @param c - Unique code
   */
  public void displayCode(String c) {
    // Clears screen
    getContentPane().removeAll();
    // new Panel
    playGame = new JPanel();
    playGame.setLayout(new BoxLayout(playGame, BoxLayout.Y_AXIS));
    // TextField with code displayed
    display1 = new TextField(10);
    display1.setEditable(false);
    display1.setText(c);
    display1.setPreferredSize(new Dimension(height / 3, width / 9));
    display1.setFont(newFont);
    host = new JButton("Start!");
    host.setActionCommand("start game");
    host.addActionListener(this);
    // Add to screen
    playGame.add(Box.createRigidArea(new Dimension(1, height / 3)));
    playGame.add(display1);
    playGame.add(host);
    add(playGame);
    repaint();
    setVisible(true);
  }

  /**
   * Adds star to host waiting screen
   * 
   * @author - Inaya
   */
  public void addStar() {
    JLabel star = new JLabel("✰");
    star.setForeground(Color.orange);
    star.setSize(new Dimension(width / 10, height / 10)); // make stars bigger
    playGame.add(star);
    repaint();
    setVisible(true);
    System.out.println("Created a star"); // Test
  }

  /**
   * Displays opening sequence for the player
   */
  public void displayOpening() {
    // @author - Nivedita and Kaitlyn
    getContentPane().removeAll();
    // Create starting screen
    // Reset panel
    playGame = new JPanel();
    questionPanelOverall = new JPanel();
    questionPanelOverall.setLayout(new BoxLayout(questionPanelOverall, BoxLayout.Y_AXIS));
    questionPanel1 = new JPanel();
    questionPanel1.setLayout(new BoxLayout(questionPanel1, BoxLayout.X_AXIS));
    questionPanel2 = new JPanel();
    questionPanel2.setLayout(new BoxLayout(questionPanel2, BoxLayout.X_AXIS));
    // TextField and JLabel for nickname
    displayLabel1 = new JLabel("Enter your nickname: ");
    // Setting font for displayLabel1
    displayLabel1.setFont(newFont);
    display1 = new TextField(15);
    display1.setEditable(true);
    display1.addActionListener(this);
    display1.setPreferredSize(new Dimension(height / 3, width / 9));
    display1.setFont(inputFont);
    // TextField and JLabel for code
    displayLabel2 = new JLabel("Enter your code: ");
    // Setting font for displayLabel2
    displayLabel2.setFont(newFont);
    display2 = new TextField(15);
    display2.setEditable(true);
    display2.addActionListener(this);
    display2.setPreferredSize(new Dimension(height / 3, width / 9));
    display2.setFont(inputFont);
    // Output TextField
    output = new TextField(15);
    output.setEditable(false);
    output.setFont(newFont);
    output.setBackground(c);
    // Add to screen
    questionPanel1.add(displayLabel1);
    questionPanel1.add(display1);
    questionPanel2.add(displayLabel2);
    questionPanel2.add(display2);
    questionPanelOverall.add(questionPanel1);
    questionPanelOverall.add(questionPanel2);
    questionPanelOverall.add(output);
    playGame.add(Box.createRigidArea(new Dimension(1, height / 3)), BorderLayout.NORTH);
    playGame.add(questionPanelOverall, BorderLayout.CENTER);
    add(playGame);
    repaint();
    setVisible(true);
    // Display animation here @April

  }

  /**
   * Displays player's progress in game on host screen
   */
  public void displayProgress() {
    getContentPane().removeAll();
    display1 = new TextField("GO PLAYERS GO!");
    host = new JButton("Fight boss now");
    host.addActionListener(this);
    host.setActionCommand("fight now");
    add(display1);
    add(host);
    repaint();
    setVisible(true);
  }

  /**
   * Displays a random output question
   * ArrayList q contains the problem, method, four answer choices, and the
   * answer in that respective order
   * @author - Kaitlyn and Ritali
   */
  public void displayQ(ArrayList<String> q) {
    currentQ = q;
    getContentPane().removeAll();
    questionPanel1 = new JPanel();
    questionPanel1.setLayout(new BoxLayout(questionPanel1, BoxLayout.Y_AXIS));
    questionPanel2 = new JPanel();
    questionPanel2.setLayout(new FlowLayout()); // Change this if needed
    questionPanelOverall = new JPanel();
    questionPanelOverall.setLayout(new BoxLayout(questionPanelOverall, BoxLayout.X_AXIS));

    displayLabel1 = new JLabel(currentQ.get(0));
    JTextArea displayLabelM = new JTextArea(currentQ.get(1));
    displayLabelM.setText(currentQ.get(1));
    displayLabelM.setEditable(false);
    questionPanel1.add(Box.createVerticalGlue());
    questionPanel1.add(displayLabel1);
    questionPanel1.add(Box.createVerticalGlue());
    questionPanel1.add(displayLabelM);

    answers = new ButtonGroup();
    a1 = new JRadioButton(currentQ.get(2));
    a2 = new JRadioButton(currentQ.get(3));
    a3 = new JRadioButton(currentQ.get(4));
    a4 = new JRadioButton(currentQ.get(5));
    answers.add(a1);
    answers.add(a2);
    answers.add(a3);
    answers.add(a4);
    questionPanel2.add(a1);
    questionPanel2.add(a2);
    questionPanel2.add(a3);
    questionPanel2.add(a4);

    questionPanelOverall.add(questionPanel1);
    questionPanelOverall.add(questionPanel2);

    player = new JButton("Submit");
    player.addActionListener(this);
    player.setActionCommand("submit");
    add(questionPanelOverall);
    add(player);
    repaint();
    setVisible(true);
  }

  public String showCorrect() {
    String a;
    if (a1.isSelected()) {
      a1.setBackground(Color.red);
      a = a1.getText();
    } else if (a2.isSelected()) {
      a2.setBackground(Color.red);
      a = a2.getText();
    } else if (a3.isSelected()) {
      a3.setBackground(Color.red);
      a = a3.getText();
    } else if (a4.isSelected()) {
      a4.setBackground(Color.red);
      a = a4.getText();
    } else {
      return null;
    }
    String answer = currentQ.get(6);
    if (a1.getText().equals(answer)) {
      a1.setBackground(Color.green);
    } else if (a2.getText().equals(answer)) {
      a2.setBackground(Color.green);
    } else if (a3.getText().equals(answer)) {
      a3.setBackground(Color.green);
    } else {
      a4.setBackground(Color.green);
    }
    a1.setOpaque(true);
    a2.setOpaque(true);
    a3.setOpaque(true);
    a4.setOpaque(true);
    a1.setEnabled(false);
    a2.setEnabled(false);
    a3.setEnabled(false);
    a4.setEnabled(false);
    return a;
  }

  /**
   * Displays player ready screen before host starts
   * 
   * @author - Ritali
   */
  public void playerReady() {
    getContentPane().removeAll();
    JPanel container = new JPanel();
    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
    displayLabel1 = new JLabel("Waiting for the host to start the game...");
    displayLabel1.setFont(newFont);
    display1.setPreferredSize(new Dimension(height / 3, width / 9));
    displayLabel2 = new JLabel();
    display2.setPreferredSize(new Dimension(height / 3, width / 9));

    try {
      String relName = "./assets/snoopy.gif";
      ImageIcon ii = new ImageIcon(relName);
      displayLabel2.setIcon(ii);
    } catch (NullPointerException exception) {
      exception.printStackTrace();
      displayLabel2.setText("Your game will begin shortly");
      displayLabel2.setFont(newFont);
    }
    this.setVisible(true);

    container.add(Box.createRigidArea(new Dimension(1, height / 9)));
    container.add(displayLabel1);
    container.add(Box.createRigidArea(new Dimension(1, height / 9)));
    container.add(Box.createRigidArea(new Dimension(height / 3, 1)));
    container.add(displayLabel2);
    container.add(Box.createRigidArea(new Dimension(height / 3, 1)));
    container.add(Box.createRigidArea(new Dimension(1, height / 9)));
    add(container);
    repaint();
    setVisible(true);
  }

  public void playerReadyToFightBoss() {
    getContentPane().removeAll();
    display1 = new TextField("Waiting upon companions...");
    add(display1);
    repaint();
    setVisible(true);
  }
  
  public void displayBossProgress() {
	  getContentPane().removeAll();
	  display1 = new TextField("Players are currently fighting...");
	  add(display1);
      repaint();
      setVisible(true);
  }
  
  public void finalFight(ArrayList<String> q) {
	  displayQ(q);
	  questionPanelOverall.add(new TextField("Answer this right and you shall deal a critical blow unto the boss!"
	  		+ "\nAnswer this wrong and drag your companions into the abyss with you..."));
	  player.setActionCommand("final");
  }
  
  public String getAnswer() {
	  return currentQ.get(6);
  }
  
  public void displayGoodEnding() {
	  getContentPane().removeAll();
	  display1 = new TextField("Your companions are glad to have you one their side!");
	  add(display1);
      repaint();
      setVisible(true);
	  
  }
  
  public void displayBadEnding() {
	  getContentPane().removeAll();
	  display1 = new TextField("You must rely on your companions to win...");
	  add(display1);
	  repaint();
	  setVisible(true);
	  
  }
  
  public void displayEnding(boolean win) {
	  getContentPane().removeAll();
	  String res = "";
	  if (win) {
		  res = "YOU WON!";
	  } else {
		  res = "GAME OVER";
	  }
	  display1 = new TextField(res);
	  add(display1);
	  repaint();
	  setVisible(true);
	  
  }

  /**
   * Called when button is clicked and redirects to respective user type
   * 
   * @author - Kaitlyn
   * @param evt - Generated event
   */
  public void actionPerformed(ActionEvent evt) {
    if (evt.getActionCommand().equals("host")) {
      h = new Host();
    } else if (evt.getActionCommand().equals("player")) {
      p = new Player();
    } else if (evt.getActionCommand().equals("start game")) {
      displayProgress();
      h.giveQuestions();
    } else if (evt.getActionCommand().equals("submit")) {
      String a = showCorrect();
      if (a != null) {
        p.checkAnswer(a.equals(currentQ.get(6)));
        player.setText("Next Question");
        player.setActionCommand("next");
      }
    } else if (evt.getActionCommand().equals("next")) {
      questionCount++;
      if (questionCount < 1) { // Change this later
        p.getQuestionBank().getRandomQuestion(false);
      } else {
        playerReadyToFightBoss();
        p.readyToFightBoss();
      }
    } else if (evt.getActionCommand().equals("final")) {
    	String a = showCorrect();
    	if (a.equals(getAnswer())) {
    		displayGoodEnding();
    		p.done(true);
    	} else {
    		displayBadEnding();
    		p.done(false);
    	}
    } else if (evt.getActionCommand().equals("fight now")) {
    	h.startBossFight();
    } else if (!display1.getText().equals("") && !display2.getText().equals("")) {
      System.out.println("setting code...");
      p.setNickname(display1.getText());
      if (p.setCode(display2.getText())) {
        playerReady();
        p.acceptingQuestions();
      } else {
        output.setText("Invalid code!");
      }
    }
  }
}
