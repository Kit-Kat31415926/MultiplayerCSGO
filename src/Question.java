import java.util.ArrayList;

public class Question {
	
	private ArrayList<String> qProblems;
	private ArrayList<String[]> qChoices;
	private ArrayList<String> qAnswers;
	private String qMethods;
	
	/**
	 * Creates the Question
	 * @author - Ritali
	 * @param i - the arraylist of inputs
	 * @param o - the arraylist of corresponding outputs
	 * @param m - the code segment that belongs to the question
	 */
	public Question(ArrayList<String> p, ArrayList<String[]> c, ArrayList<String> a, String m) {
		qProblems = p;
		qChoices = c;
		qAnswers = a;
		qMethods = m;
	}
	
	/**
	 * Returns the key value pair of the problems, choices, and output
	 * @author - Ritali
	 * @return vals - returns corresponding problems, choices, and output
	 */
	public ArrayList<String> getRandomQValues() {
		ArrayList<String> res = new ArrayList<String>();
		int i = (int)(Math.random()*qProblems.size());
		res.add(qProblems.get(i));
		res.add(qMethods);
		// Temporary ArrayList to hold answer choices
		ArrayList<String> temp = new ArrayList<String>();
		for (int j = 0; j < 4; j++) {
			temp.add(qChoices.get(i)[j]);
		}
		// Provides answer choices in random order
		for (int k = 0; k < 4; k++) {
			int index = (int) (Math.random() * temp.size());
			res.add(temp.remove(index));
		}
		res.add(qAnswers.get(i));
		return res;
	}
}