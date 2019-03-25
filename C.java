package C;

/* C.java */

import java.io.*;
import java.util.Scanner;
import java.util.Queue;
import java.util.Arrays;
import java.util.LinkedList;

class C {
	
	public static void main (String arg[]) throws FileNotFoundException {
		
		// Get the list of inputs and put them in a queue.
       	Scanner fin = new Scanner(new FileReader("input.txt"));
       	Queue<String> input = new LinkedList<String>();
		
		while (true) {
			
			String in = fin.nextLine();
			
			if (in.equalsIgnoreCase(".")) break;
			
			input.add(in);
			//if (fin.hasNextDouble() == false) break;
			
			// Since there's only ever 1 decimal, handling as int.
			//input.add((int) (fin.nextDouble() * 10));
			
		}
		
		fin.close();
		Queue<Integer> output = new LinkedList<Integer>();
		
		// Pop each item in the input and add them to the output queue.
		while (input.peek() != null) {
			
			String temp = input.poll();
			temp = temp.substring(0,  temp.length() - 2) +
					temp.substring(temp.length() - 1, temp.length());
			//System.out.println("input" + temp);
			char price[] = temp.toCharArray();
			int place = price.length - 1;
			
			// First, check if flipping the last digit helps. If it's a 2 or a 6,
			// instant solve.
			if (price[place] == '2') {
				price[place] = '5';
				output.add(Integer.valueOf(String.valueOf(price)));
				continue;
			}
			else if (price[place] == '6') {
				price[place] = '9';
				output.add(Integer.valueOf(String.valueOf(price)));
				continue;
			}
			int current = Character.getNumericValue(price[place]);
			int last;
			
			while (true) {
				
				place --;
				if (place == -1) { // Out of digits. Already the largest value
					output.add(Integer.valueOf(String.valueOf(price)));
					break;
				}
				
				last = current;
				current = Character.getNumericValue(price[place]);

				if (last <= current) continue; // Loop until we find somewhere to improve
				
				int toswap = -1;
				int highest = -1;
				
				// Find next highest digit
				for (int i = place + 1; i < price.length; i++) {
					int test = Character.getNumericValue(price[i]);
					if (current < test && test > highest) {
						toswap = i;
						highest = test;
					}
					if (test != 2 && test != 5 && test != 6 && test != 9) continue;
					if (test == 2 || test == 6) test += 3;
					else if (test == 5 || test == 9) test -= 3;
					if (current < test && test > highest) {
						toswap = i;
						highest = test;
					}
				}
				
				// Swap digit with next highest digit
				price[toswap] = price[place];
				price[place] = (char) (highest + 48); // Type-casting back to char
				
				// Flip down all possible digits
				for (int i = place + 1; i < price.length; i++) {
					if (price[i] == '5') price[i] = '2';
					if (price[i] == '9') price[i] = '6';
				}
				
				// Sort in ascending order (smallest to largest)
				Arrays.sort(price, place + 1, price.length - 1);
				
				// Done. Stick in answer queue.
				//System.out.println(String.valueOf(price));
				output.add(Integer.valueOf(String.valueOf(price)));
				break;
				
			}
			
		}
		// Write all the outputs to the file.
		PrintWriter writer = new PrintWriter("output.txt");
		//System.out.println("testend");
		
		while (output.peek() != null) {
			
			double out = output.poll() / 10.0;
			//System.out.println(out);
			writer.println(out);
			
		}
		
		writer.close();
		
	}

}