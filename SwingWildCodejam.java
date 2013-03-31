// Author: Cong Li
// Practiced on 3/27/2013
//
// https://code.google.com/codejam/contest/1842485/dashboard#s=p0&a=0
// 
// ================================================
// Actually this is a DP algorithm. dp[i] stores the maximum index
// that can be reached by swinging the i-th vine.
// It's not each to come up with this algorithm.
// Need to practice DP more.
// 
// For a smarter implementation, check monkeylyf's code at
// https://github.com/monkeylyf/careercup150/blob/3d409d956c3431416cadfc99c705a55ba8883243/codejam_Swinging_Wild.java

import java.io.*;
import java.util.*;

public class SwingWildCodejam implements Runnable {

	BufferedReader in;
	PrintWriter out;
	StringTokenizer tok = new StringTokenizer("");

	public static void main(String[] args) {
		new Thread(null, new Test5A(), "", 256 * (1L << 20)).start();
	}

	public void solve() throws IOException 
	{
		int num_tests = readInt();
		for(int i = 0; i < num_tests; ++i)
		{
			int N = readInt();
			int[] location = new int[N];
			int[] vineLength = new int[N];
			int destination = 0;
			for(int j = 0; j < N; ++j)
			{
				location[j] = readInt();
				vineLength[j] = readInt();
			}
			destination = readInt();
			calculate(location, vineLength, destination, i+1);
		}
	}
	
	private void calculate(int[] location, int[] vineLength, int destination, int case_num)
	{
		int[] dp = new int[location.length];
		dp[0] = 2 * location[0];
		if(dp[0] >= destination)
		{
			System.out.println("Case #" + case_num + ": YES");
			return;
		}
		
		for(int i = 1; i < location.length; ++i)
		{
			for(int j = i-1; j >= 0; --j)
			{
				if(dp[j] >= location[i])
				{
					dp[i] = Math.max(dp[i], location[i] + Math.min(vineLength[i], location[i]-location[j]));
				}
				if(dp[i] >= destination)
				{
					System.out.println("Case #" + case_num + ": YES");
					return;
				}
			}
		}
		System.out.println("Case #" + case_num + ": NO");
	}
	
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
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