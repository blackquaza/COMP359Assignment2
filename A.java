package A;

/* A.java */

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Input {
	
	// n      = length of ferry
	// m      = number of carrs
	// cars[] = the array of car lengths
	// num    = the number of cars before hitting the max length
	
	public int n = 0;
	public int m = 0;
	
	public int cars[];
	public int num = 0;
	
}

class Values {
	
	public int weight = 0;
	public int dist = 0;
	
}

class A {
	
	static Values run(int i, Values x, int cars[]) {
		//System.out.println(i);
		
		if (i == 0 || x.weight < 0) return x;
		
		Values y = new Values();
		y.weight = x.weight - cars[i-1];
		y.dist = x.dist;
		Values z = new Values();
		z.weight = x.weight;
		z.dist = x.dist;
		y = run(i-1, y, cars); // Check weight if included
		z = run(i-1, z, cars); // Check weight if not.
		//System.out.println("y" + y.weight + "z" + z.weight);
		// If it is
		if (y.weight < z.weight && y.weight >= 0) {
			
			//y.weight -= cars[i-1];
			y.dist += Math.pow(2, i-1);
			return y;
			
		// If it isn't
		} else {
			
			return z;
			
		}
		
	}
	
	public static void main (String arg[]) throws FileNotFoundException {
		
		// Get the list of inputs and put them in a queue.
       	Scanner fin = new Scanner(new FileReader("input.txt"));
       	Queue<Input> input = new LinkedList<Input>();
       	
		while (true) {
			
			int n = fin.nextInt();
			int m = fin.nextInt();
			
			if (n == 0 && m == 0) break;
			
			Input temp = new Input();
			
			temp.n = n*100; 
			temp.m = m;
			temp.cars = new int[m];
			int carlength = 0;
			
			for (int i = 0; i < m; i++) {
				
				temp.cars[i] = fin.nextInt();
				carlength += temp.cars[i];
				
				// Use this opportunity to calculate the maximum possible
				// number of cars. We don't care about cars after this, since
				// it's impossible for them to board.
				if (carlength <= temp.n * 2) temp.num = i+1;
				//System.out.println("test" + carlength);
				
			}
			
			input.add(temp);
			
		}
		
		fin.close();
		Queue<String> output = new LinkedList<String>();
		
		// Pop each item in the input and add them to the output queue.
		
		while (input.peek() != null) {
			
			Input in = input.poll();
			
			// Variation of the sum subset problem. Get as close as possible to
			// the total, then sum the ones not chosen. If they also add up to
			// less than the total, we have a solution. Otherwise, subtract the
			// last car and try again.
			
			int solution = 0;
			
			do {
				
				Values temp = new Values();
				temp.weight = in.n;
				//System.out.println(in.num);
				temp = run(in.num, temp, in.cars);
				//System.out.println("dist" + temp.dist);
				int test = 0;
				for (int j = in.num; j > 0; j--) {
				//System.out.println("zero" + Math.pow(2, j));
					if ((temp.dist % (int) Math.pow(2, j) / (int) Math.pow(2, j-1)) == 0) {
						
						test += in.cars[j-1];
						//System.out.println("cars" + (j-1));
					}
					
				}
				
				//System.out.println("total" + test);
				
				if (test < in.n) solution = temp.dist;
				
				in.num --;
				
			} while (solution == 0);

			output.add(String.valueOf(++in.num));
			
			for (int j = 1; j <= in.num; j++) {
				
				if ((solution % (int) Math.pow(2, j)) / (int) Math.pow(2, j-1) == 1) {
					
					output.add("port");
					
				} else {
					
					output.add("starboard");
					
				}
				
			}
			
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