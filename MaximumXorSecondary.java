// Author: Cong Li
// Practiced on 3/17/2013
// 
// http://codeforces.com/contest/281/problem/D
// 
// ===========================================
// The key observations are as follows:
// 1. We only need to find the largest two integers in a a certain interval.
// 2. Suppose a1, a2,..., am is decreasing. Then ai, a(i+1) are the largest
//    two elements of interval [ai, aj], for all i < j <= m. 
// 3. Suppose a1, a2,..., am is decreasing. If a(m+1) > am, and a(m+1) < ai
//    for some 1 <= i < m. Then a(m+1), aj, for all i <= j < m, are the largest
//    two elements of interval [aj, a(m+1)].

import java.io.*;
import java.util.*;

public class MaximumXorSecondary implements Runnable {

	BufferedReader in;
	PrintWriter out;
	StringTokenizer tok = new StringTokenizer("");

	public static void main(String[] args) {
		new Thread(null, new MaximumXorSecondary(), "", 256 * (1L << 20)).start();
	}
	
	// Here is the solution.
	public void solve() throws IOException 
	{
		int n = readInt();
		Stack<Integer> stack = new Stack<Integer>();
		int result = 0;
		for(int i = 0; i < n; ++i)
		{
			int current_digit = readInt();
			if(stack.empty())
				stack.push(current_digit);
			else
			{
				while(!stack.empty() && current_digit > stack.peek())
				{
					result = Math.max(result, current_digit^stack.peek());
					stack.pop();
				}
				if(!stack.empty())
					result = Math.max(result, current_digit^stack.peek());
				stack.push(current_digit);
			}
		}
		out.println(result);
	}
	
	// Ignore below.
	public void run() {
		try {
			long t1 = System.currentTimeMillis();
			in = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
			Locale.setDefault(Locale.US);
			solve();
			in.close();
			out.close();
			long t2 = System.currentTimeMillis();
			System.err.println("Time = " + (t2 - t1));
		} catch (Throwable t) {
			t.printStackTrace(System.err);
			System.exit(-1);
		}
	}

	public String readString() throws IOException {
		while (!tok.hasMoreTokens()) {
			tok = new StringTokenizer(in.readLine());
		}
		return tok.nextToken();
	}

	public int readInt() throws IOException {
		return Integer.parseInt(readString());
	}

	public long readLong() throws IOException {
		return Long.parseLong(readString());
	}

	public double readDouble() throws IOException {
		return Double.parseDouble(readString());
	}
}



