package E;

/* E.java */

import java.io.*;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;

class Input {
	
	public String phrase;
	public String rule[][];
	
}

class E {
	
	static PriorityQueue<String> run(String current, PriorityQueue<String> phraselist, String[][] rule) {
		
		// If already hit, return.
		if (phraselist.contains(current) == true) {
			return phraselist;
		}

		phraselist.add(current);
		
		for (int i = 0; i < rule.length; i++) {
			String temp = current.replaceFirst(rule[i][0], rule[i][1]);
			run (temp, phraselist, rule);
		}
		
		return phraselist;
		
	}
	
	public static void main (String arg[]) throws FileNotFoundException {
		
		// Get the list of inputs and put them in a queue.
       	Scanner fin = new Scanner(new FileReader("input.txt"));
       	Queue<Input> input = new LinkedList<Input>();
		
		while (true) {
			
			String in = fin.nextLine();
			Input temp = new Input();
			temp.phrase = in.substring(0, in.lastIndexOf(" "));
			int num = Integer.valueOf(in.substring(in.lastIndexOf(" ") + 1, in.length()));
			
			if (temp.phrase.equals("0") && num == 0) break;
			
			temp.rule = new String[num][2];
			
			for (int i = 0; i < num; i++) {
				
				in = fin.nextLine();
				temp.rule[i][0] = in.substring(0, in.lastIndexOf(" "));
				temp.rule[i][1] = in.substring(in.lastIndexOf(" ") + 1, in.length());
				
			}
			
			input.add(temp);
			
		}
		
		fin.close();
		Queue<Integer> output = new LinkedList<Integer>();
		
		// Pop each item in the input and add them to the output queue.
		while (input.peek() != null) {
			
			Input in = input.poll();
			PriorityQueue<String> phraselist = new PriorityQueue<String>();
			
			phraselist = run(in.phrase, phraselist, in.rule);
			
			output.add(phraselist.size());
			
		}
		
		// Write all the outputs to the file.
		PrintWriter writer = new PrintWriter("output.txt");
		
		while (output.peek() != null) {
			
			int out = output.poll();
			//System.out.println(out);
			writer.println(out);
			
		}
		
		writer.close();
		
	}

}