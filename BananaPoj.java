// Author: Cong Li
// Practiced on 4/11/2013
//
// http://poj.org/problem?id=1838
//
// ========================================
// Union-Find is fun!


import java.io.*;
import java.util.*;

public class BananaPoj {

	BufferedReader in;
	PrintWriter out;
	StringTokenizer tok = new StringTokenizer("");

	public static void main(String[] args) {
		BananaPoj test = new BananaPoj();
		test.start();
	}

	public void solve() throws IOException 
	{
		int n = readInt();
		int k = readInt();
		Pair[] pairs = new Pair[n];
		int[] parents = new int[n];
		int[] size = new int[n];
		
		for(int i = 0; i < n; ++i)
		{
			pairs[i] = new Pair(i, readInt(), readInt());
			parents[i] = i;
			size[i] = 1;
		}
		
		Comparator<Pair> cx = new CompareX();
		Arrays.sort(pairs, cx);
		
		for(int i = 1; i < n; ++i)
			if(pairs[i].a == pairs[i-1].a && pairs[i].b - pairs[i-1].b == 1)
				union(parents, size, pairs[i].index, pairs[i-1].index);
		
		Comparator<Pair> cy = new CompareY();
		Arrays.sort(pairs, cy);

		for(int i = 1; i < n; ++i)
			if(pairs[i].b == pairs[i-1].b && pairs[i].a - pairs[i-1].a == 1)
				union(parents, size, pairs[i].index, pairs[i-1].index);
		
		for(int i = 0; i < n; ++i)
			if(parents[i] != i)
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

class Pair
{
	int index;
	int a;
	int b;
	public Pair(int index, int a, int b)
	{
		this.index = index;
		this.a = a;
		this.b = b;
	}
}

class CompareX implements Comparator<Pair>
{
	@Override
	public int compare(Pair arg0, Pair arg1) {
		Pair x = (Pair)arg0;
		Pair y = (Pair)arg1;
		if(x.a != y.a) return x.a - y.a;
		return x.b - y.b;
	}
}

class CompareY implements Comparator<Pair>
{
	@Override
	public int compare(Pair arg0, Pair arg1) {
		Pair x = (Pair)arg0;
		Pair y = (Pair)arg1;
		if(x.b != y.b) return x.b - y.b;
		return x.a - y.a;
	}
}