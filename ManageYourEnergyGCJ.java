// Author: Cong Li
// Practiced on 5/7/2013
//
// https://code.google.com/codejam/contest/2418487/dashboard#s=p1&a=1
//
// =======================================================
//
// Two thing to notice:
// 1. next_high[i] stores the distance to the nearest more valuable activity, starting
// from the i-th activity.
//
// 2. energy_left + R * next_high[i] - E is the energy should be used for activity i,
// such that we can still get E energy when the nearest more valuable activity comes.


import java.io.*;
import java.util.*;

public class ManageYourEnergyGCJ {

	BufferedReader in;
	PrintWriter out;
	StringTokenizer tok = new StringTokenizer("");

	public static void main(String[] args) {
		ManageYourEnergyGCJ test = new ManageYourEnergyGCJ();
		test.start();
	}

	public void solve() throws IOException 
	{
		int num_test = readInt();
		for(int i = 0; i < num_test; ++i)
		{
			long E = readLong();
			long R = readLong();
			int num_actv = readInt();
			
			long[] activity = new long[num_actv];
			for(int j = 0; j < num_actv; ++j)
				activity[j] = readLong();
			
			calculate(E, R, activity, i+1);
		}
	}

	//---------------------Solution------------------------------------//
	public void calculate(long E, long R, long[] activity, int num_case)
	{
		int[] next_high = new int[activity.length];
		R = Math.min(E, R);
		
		get_next_high(next_high, activity);
		
		long sum = 0;
		long energy_left = E, energy_to_use = energy_left;
		for(int i = 0; i < activity.length; ++i)
		{
			energy_to_use = energy_left;			
			
			if(next_high[i] != 0)
				energy_to_use = Math.max(0, Math.min(energy_left, energy_left + R * next_high[i] - E));
			
			sum += activity[i] * energy_to_use;
			energy_left = Math.min(E, energy_left - energy_to_use + R);
		}
		
		out.println("Case #" + num_case + ": " + sum);
	}
	
	public void get_next_high(int[] next_high, long[] activity)
	{
		Stack<Integer> stack = new Stack<Integer>();
		Arrays.fill(next_high, 0);
		
		for(int i = 0; i < activity.length; ++i)
		{
			while(!stack.empty() && activity[stack.peek()] <= activity[i])
				next_high[stack.peek()] = i-stack.pop();
			stack.push(i);
		}
	}
	//---------------------Solution------------------------------------//
	
	public void start()
	{
		try {
			long t1 = System.currentTimeMillis();
			if (System.getProperty("ONLINE_JUDGE") != null) {
				in = new BufferedReader(new InputStreamReader(System.in));
				out = new PrintWriter(System.out);
			} else {
				in = new BufferedReader(new FileReader("..\\codeforces\\src\\com\\congli\\codeforces\\input.txt"));
				out = new PrintWriter(System.out);
			}
			Locale.setDefault(Locale.US);
			solve();
			in.close();
			out.close();
			long t2 = System.currentTimeMillis();
			//System.err.println("Time = " + (t2 - t1));
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