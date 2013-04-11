import java.io.*;
import java.util.*;

public class banana_poj_fail {

	BufferedReader in;
	PrintWriter out;
	StringTokenizer tok = new StringTokenizer("");

	public static void main(String[] args) {
		banana_poj_fail test = new banana_poj_fail();
		test.start();
	}

	public void solve() throws IOException 
	{
		int n = readInt();
		int k = readInt();
		
		int[][] pairs = new int[n][2];
		int[][] connects = new int[n*n][2];
		
		int num_connects = 0;
		
		for(int i = 0; i < n; ++i)
		{
			pairs[i][0] = readInt();
			pairs[i][1] = readInt();
			
			for(int j = 0; j < i; ++j)
			{
				if(pairs[j][0] == pairs[i][0] && Math.abs(pairs[j][1] - pairs[i][1]) == 1)
				{
					connects[num_connects][0] = i;
					connects[num_connects++][1] = j;
				}
				if(pairs[j][1] == pairs[i][1] && Math.abs(pairs[j][0] - pairs[i][0]) == 1)
				{
					connects[num_connects][0] = i;
					connects[num_connects++][1] = j;
				}
			}
		}
		calculate(connects, pairs, n, k, num_connects);
	}

	public void calculate(int[][] connects, int[][] pairs, int n, int k, int num_connects)
	{
		int[] parents = new int[n];
		int[] size = new int[n];
		for(int i = 0; i < n; ++i)
		{
			parents[i] = i;
			size[i] = 1;
		}
		
		for(int i = 0; i < num_connects; ++i)
			union(parents, size, connects[i][0], connects[i][1]);
		
		for(int i = 0; i < n; ++i)
			if(find(parents, i) != i)
				size[i] = 0;
		
		Arrays.sort(size);
		
		int result = 0;
		for(int i = 0; i < k; ++i)
			result += size[n-i-1];
		
		out.println(result);
	}
	
	public void union(int[] parents, int[] size, int p, int q)
	{
		int i = find(parents, p);
		int j = find(parents, q);
		if(i == j) return;
		
		if(size[i] < size[j])
		{
			parents[i] = j;
			size[j] += size[i];
		}
		else
		{
			parents[j] = i;
			size[i] += size[j];
		}
	}
	
	public int find(int[] parents, int i)
	{
		while(i != parents[i])
			i = parents[i];
		return i;
	}
	
	public void start()
	{
		try {
			long t1 = System.currentTimeMillis();
			if (System.getProperty("ONLINE_JUDGE") != null) {
				in = new BufferedReader(new InputStreamReader(System.in));
				out = new PrintWriter(System.out);
			} else {
				in = new BufferedReader(new FileReader("..\\Codeforces\\src\\com\\congli\\codeforces\\input.txt"));
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