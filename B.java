package B;

/* B.java */

import java.io.*;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

class B {
	
	public static void main (String arg[]) throws FileNotFoundException {
		
		// Get the list of inputs and put them in a queue.
       	Scanner fin = new Scanner(new FileReader("input.txt"));
       	Queue<String> input = new LinkedList<String>();
		
		while (true) {
			
			if (fin.hasNextLine() == false) break;
			input.add(fin.nextLine());
			
		}
		
		fin.close();
		Queue<String> output = new LinkedList<String>();
		
		// Pop each item in the input and add them to the output queue.
		while (input.peek() != null) {
			
			String word = input.poll();
			word = word.toUpperCase(); // Just in case (HAH GET IT).
			int last = -1, current = -1;
			String out = "";
			
			for (int i = 0; i < word.length(); i++) {
				
				char letter = word.charAt(i);
				
				switch (letter) {
					
					case 'A':
					case 'E':
					case 'I':
					case 'O':
					case 'U':
					case 'H':
					case 'W':
					case 'Y':
						current = 0;
						break;
					case 'B':
					case 'F':
					case 'P':
					case 'V':
						current = 1;
						break;
					case 'C':
					case 'G':
					case 'J':
					case 'K':
					case 'Q':
					case 'S':
					case 'X':
					case 'Z':
						current = 2;
						break;
					case 'D':
					case 'T':
						current = 3;
						break;
					case 'L':
						current = 4;
						break;
					case 'M':
					case 'N':
						current = 5;
						break;
					case 'R':
						current = 6;
						break;
					default:
						System.err.println("Everything's broke Captain");
						return;
						
				}
				
				if (current != last && current != 0) {
					
					out += Integer.toString(current);
					
				}
				
				last = current;
				
			}
			
			output.add(out);
			
		}
		
		// Write all the outputs to the file.
		PrintWriter writer = new PrintWriter("output.txt");
		
		while (output.peek() != null) {
			
			String out = output.poll();
			//System.out.println(out);
			writer.println(out);
			
		}
		
		writer.close();
		
	}

}