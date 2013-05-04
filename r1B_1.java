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
	
	
	public void calculate(long sum, int[] mote, int case_num)
	{
		if(sum == 1)
		{
			out.println("Case #" + case_num + ": " + mote.length);
			return;
		}
		
		Arrays.sort(mote);

		int result = 0;
		for(int i = 0; i < mote.length; ++i)
		{
			int num_sum = 0;
			while(sum <= mote[i])
			{
				sum += (sum-1);
				++num_sum;
			}
			sum += mote[i];
			result += num_sum;
			
			if(result >= mote.length-i)
			{
				out.println("Case #" + case_num + ": " + result);
				return;
			}
		}
		
		out.println("Case #" + case_num + ": " + result);
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