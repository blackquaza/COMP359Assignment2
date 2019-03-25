package D;

/* D.java */

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Input {
	
	public int total;
	public int bottle[][];
	public int min;
	
}

class Values {
	
	public int volume = 0;
	public int extra = 0;
	
}

class D {
	
	static Values run(Values x, int bottle[][], boolean br[], int min) {
		
		if (br[0] == true) return x;
		if (x.volume < 0) return x;
		
		// If the extra is greater than the size of the minimum bottle,
		// then no wine is wasted. Immediately terminate and return 0.
		if (x.extra > min) {
			br[0] = true;
			return new Values();
		}
		//System.out.println("run" + x.volume + "_" + x.extra);
		
		Values ret = new Values();
		ret.extra = x.extra;
		ret.volume = x.volume;
		
		for (int i = 0; i < bottle.length; i++) {
			
			Values temp = new Values();
			temp.extra = x.extra + bottle[i][1];
			temp.volume = x.volume - bottle[i][0];
			temp = run(temp, bottle, br, min);
			
			if (ret.volume > temp.volume && temp.volume >= 0) {
				ret.volume = temp.volume;
				ret.extra = temp.extra;
			}
			
			// Found a solution. Terminate.
			if (ret.volume >= 0 && ret.volume - ret.extra <= 0) br[0] = true;
			
		}
		
		return ret;
	}
	
	public static void main (String arg[]) throws FileNotFoundException {
		
		// Get the list of inputs and put them in a queue.
       	Scanner fin = new Scanner(new FileReader("input.txt"));
       	Queue<Input> input = new LinkedList<Input>();
		
		while (true) {
			
			Input in = new Input();
			
			in.total = fin.nextInt() * 1000;
			in.min = in.total;
			int temp = fin.nextInt();
			
			if (in.total == 0 && temp == 0) break;
			
			in.bottle = new int[temp][2];
			
			for (int i = 0; i < temp; i++) {
				in.bottle[i][0] = fin.nextInt();
				in.bottle[i][1] = fin.nextInt() - in.bottle[i][0];
				if (in.min > in.bottle[i][0]) in.min = in.bottle[i][0];
				//System.out.println(in.bottle[i][0] + " " + in.bottle[i][1]);
			}
			
			input.add(in);
			
		}
		
		fin.close();
		Queue<Integer> output = new LinkedList<Integer>();
		
		// Pop each item in the input and add them to the output queue.
		while (input.peek() != null) {
			
			// Variation of the sum subset problem, recording the "extra" that can be stored.
			Input in = input.poll();
			Values result = new Values();
			result.volume = in.total;
			boolean br[] = { false }; // Java doesn't like reference passing.
			
			result = run(result, in.bottle, br, in.min);
			
			//System.out.println("end" + in.total + "_" + result.volume + "_" + result.extra);
			int unused = in.total - ((in.total - result.volume) + result.extra);
			if (unused < 0) unused = 0;
			
			output.add(unused);
			
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