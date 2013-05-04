package com.congli.gcj;

import java.io.*;
import java.util.*;

public class r1B_1 {

	BufferedReader in;
	PrintWriter out;
	StringTokenizer tok = new StringTokenizer("");

	public static void main(String[] args) {
		r1B_1 test = new r1B_1();
		test.start();
	}

	public void solve() throws IOException 
	{
		int num_test = readInt();
		for(int i = 0; i < num_test; ++i)
		{
			int sum = readInt();
			int num_mote = readInt();
			int[] mote = new int[num_mote];
			for(int j = 0; j < num_mote; ++j)
				mote[j] = readInt();
			calculate(sum, mote, i+1);
		}
	}
	
	
	public void calculate(int sum, int[] mote, int case_num)
	{
		if(sum == 1)
		{
			out.println("Case #" + case_num + ": " + mote.length);
			return;
		}
		
		Arrays.sort(mote);

		int[] dp_add = new int[mote.length+1];
		int[] dp_remove = new int[mote.length+1];
		
		for(int i = 1; i <= mote.length; ++i)
		{
			if(sum > mote[i-1])
			{
				sum += mote[i-1];
				dp_add[i] = dp_add[i-1];
				dp_remove[i] = dp_remove[i-1];
			}
			else
			{
				int num_add = 0;
				while(sum <= mote[i-1])
				{
					sum += (sum-1);
					++num_add;
				}
				dp_add[i] = Math.min(dp_add[i-1], dp_remove[i-1]) + num_add;
				dp_remove[i] = Math.min(dp_add[i-1], dp_remove[i-1]) + (mote.length+1 - i);
			}
		}
		
		out.println("Case #" + case_num + ": " + Math.min(dp_add[mote.length], dp_remove[mote.length]));
	}
	
	
	
	public void start()
	{
		try {
			long t1 = System.currentTimeMillis();
			in = new BufferedReader(new FileReader("..\\GCJ\\src\\com\\congli\\gcj\\input.txt"));
			//out = new PrintWriter(new File("..\\GCJ\\src\\com\\congli\\gcj\\output.txt"));
			out = new PrintWriter(System.out);
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