// Author: Cong Li
// Practiced on 5/12/2013
//
// https://code.google.com/codejam/contest/2437488/dashboard#s=p0
//
// ===================================================
// First for each array[i], calculate the smallest r, such that array[i-r],...,array[i] are
// all consonants.
//
// Then, let dp[i] represents the number of substrings, which starts somewhere before the i-th position, 
// and ends exactly at array[i], that satisfy the problem's requirement.
//
// We can have the following relation:
//
// if(consec[i] < n)
//   dp[i] = dp[i-1];      --------(1)
// else if(consec[i] == n)
//   dp[i] = (i-n+2);      --------(2)
// else
//   dp[i] = dp[i-1] + 1;  --------(3)
//
// (1) is easy to understand. 
// 
// For (2), there are exactly n consecutive consonants, whose end is
// array[i]. Consider substrings that ends at array[i]. Then all substrings with length larger than
// or equal to n are qualified. For example, "quartz" with n = 3, when i = 5, all substrings starts 
// before 't' are qualified substrs. Therefore the total number of qualified substrs are i-n+2.
//
// For (3), when consec[i] > n, all previous qualified substrings for i-1 are qualified now, if they add
// array[i] at the end. The substring that ends at array[i] and has length n should be added, because
// in the case of i-1, it is not qualified. For example, consider "quartzs" with n = 3. When i = 5,
// there are 4 qualified substrings: "quartz", "uartz", "artz", "rtz". However, "tz" is not qualified.
// However, when i = 6, "tzs" becomes qualified substring. This one should be added. Therefore there are
// 5 qualified substrings which ends at array[6].



package com.congli.gcj;

import java.io.*;
import java.util.*;

public class ConsonantsGCJ {

	BufferedReader in;
	PrintWriter out;
	StringTokenizer tok = new StringTokenizer("");
	
	HashSet<Character> vowels = new HashSet<Character>();
	public static void main(String[] args) {
		ConsonantsGCJ test = new ConsonantsGCJ();
		test.start();
	}

	public void solve() throws IOException 
	{
		vowels.add('a'); vowels.add('e'); vowels.add('i'); vowels.add('o'); vowels.add('u');
		int num_test = readInt();
		for(int i = 0; i < num_test; ++i)
		{
			String str = readString();
			char[] array = str.toCharArray();
			int n = readInt();
			calculate(array, n, i+1);
		}
	}
	
	
	public void calculate(char[] array, int n, int case_num)
	{
		int[] consec = new int[array.length];
		consec[0] = vowels.contains(array[0]) ? 0 : 1;
		for(int i = 1; i < array.length; ++i)
			consec[i] = vowels.contains(array[i]) ? 0 : consec[i-1]+1;
		
		long[] dp = new long[array.length];
		dp[0] = (consec[0] == 1 && n == 1) ? 1 : 0; 
		for(int i = 1; i < array.length; ++i)
		{
			if(consec[i] < n)
				dp[i] = dp[i-1];
			else if(consec[i] == n)
				dp[i] = (i-n+2);
			else
				dp[i] = dp[i-1] + 1;
		}
		
		long result = 0;
		for(int i = 0; i < dp.length; ++i)
			result += dp[i];
		
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