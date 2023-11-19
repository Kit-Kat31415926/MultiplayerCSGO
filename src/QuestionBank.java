import java.util.*;
import java.io.File;
public class QuestionBank {
	private ArrayList<Question> q;
	
	/** Constructor for the QuestionBank where questions are loaded in from text files
	  * @authors - Kaitlyn and Ritali
	 **/
	public QuestionBank() {
		try {
			q = new ArrayList<Question>();
			// Fill blank questions
			File File = new File("assets/questions.txt");
			Scanner scan = new Scanner(File);
			while (scan.hasNext()) {
				// Contains data for each question
				ArrayList<String> qproblem = new ArrayList<String>();
				ArrayList<String[]> qchoices = new ArrayList<String[]>();
				ArrayList<String> qanswer = new ArrayList<String>();
				String qmethod = "";
				// Scans problem and output
				String line = scan.nextLine();
				readAnswerChoices(line, scan, qproblem, qchoices, qanswer);
				line = scan.nextLine();
				// Scans actual method
				qmethod = readMethod(line, scan, qmethod);
				q.add(new Question(qproblem, qchoices, qanswer, qmethod));
			}
			scan.close();
			
			// Output questions is the same except for the output Arraylist

		} catch (Exception e) {
			System.out.println(e);
		}
	}

  /** helper method to read in problems for answer choices
  * @param - l - line to be read in
  * @author - Ritali
  **/
  private void readAnswerChoices(String l, Scanner scan, ArrayList<String> qp, ArrayList<String[]> qc, ArrayList<String> qa ) {
		if(l.equals("&&&")) {
			return;
		}else {
			// First line is the question
			qp.add(l);
			l = scan.nextLine();
			// Following line is possible choices
			String[] tempchoices = l.split("\\.\\.\\.");
			qc.add(tempchoices);
			l = scan.nextLine();
			// Last line is true answer
			qa.add(l);
			l = scan.nextLine();
			readAnswerChoices(l, scan, qp, qc, qa);
		}
	}

  /** helper method to read in the method provided for questions
  * @param - l - line to be read in
  * @author - Ritali
  **/

  	private String readMethod(String l, Scanner scan, String qm) {
		if(!scan.hasNext() || l.equals("&&&")) {
			if (l.equals("&&&")) {
				return qm;
			} else {
				return qm + "\n" + l;
			}
		}
		qm += "\n" + l;
		l = scan.nextLine();
		return readMethod(l, scan, qm);
	}
	
	public void getRandomQuestion(boolean finalQ) {
		int index = (int) (Math.random() * q.size());
		if (finalQ) {
			Main.getScreen().finalFight(q.get(index).getRandomQValues());
		} else {
			Main.getScreen().displayQ(q.get(index).getRandomQValues());
		}
	}
}