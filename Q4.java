package com.congli.gcj;

import java.io.*;
import java.util.*;

public class Q4 {

	BufferedReader in;
	PrintWriter out;
	StringTokenizer tok = new StringTokenizer("");

	public static void main(String[] args) {
		Q4 test = new Q4();
		test.start();
	}

	public void solve() throws IOException 
	{
		int num_test = readInt();
		for(int i = 0; i < num_test; ++i)
		{
			int num_key = readInt();
			int num_chest = readInt();
			
			HashMap<Integer, Integer> key_map = new HashMap<Integer, Integer>();
			for(int k = 0; k < num_key; ++k)
			{
				int curr_key = readInt();
				if(key_map.containsKey(curr_key))
					key_map.put(curr_key, key_map.get(curr_key)+1);
				else
					key_map.put(curr_key, 1);
			}
			
			chest[] array = new chest[num_chest];
			for(int k = 0; k < num_chest; ++k)
			{
				int type = readInt();
				int num_curr_key = readInt();
				int[] keys = new int[num_curr_key];
				
				for(int ind = 0; ind < num_curr_key; ++ind)
					keys[ind] = readInt();
				
				array[k] = new chest(type, keys);
			}
			
			boolean[] visited = new boolean[array.length];
			Stack<Integer> result = new Stack<Integer>();
			
			if(dfs(array, visited, key_map, result))
			{
				System.out.print("Case #" + (i+1) + ":");
				while(!result.isEmpty())
				{
					System.out.print(" " + result.pop());
				}
				System.out.println();
			}
			else
				System.out.println("Case #" + (i+1) + ": IMPOSSIBLE");
		}
	}
	
	public boolean dfs(chest[] array, boolean[] visited, HashMap<Integer, Integer> key_map, Stack<Integer> result)
	{
		if(isAllVisited(visited)) return true;
		if(key_map.isEmpty()) return false;
		
		for(int i = 0; i < array.length; ++i)
		{
			if(!visited[i] && key_map.containsKey(array[i].type))
			{
				int curr_key = array[i].type;
				if(key_map.get(curr_key) == 1)
					key_map.remove(curr_key);
				else
					key_map.put(curr_key, key_map.get(curr_key)-1);
				
				for(int k : array[i].keys)
				{
					if(key_map.containsKey(k))
						key_map.put(k, key_map.get(k)+1);
					else
						key_map.put(k, 1);
				}
				visited[i] = true;
				
				if(dfs(array, visited, key_map, result))
				{
					result.push(i+1);
					return true;
				}
				
				visited[i] = false;
				for(int k : array[i].keys)
				{
					if(key_map.get(k) == 1)
						key_map.remove(k);
					else
						key_map.put(k, key_map.get(k)-1);
				}
				
				if(key_map.containsKey(curr_key))
					key_map.put(curr_key, key_map.get(curr_key)+1);
				else
					key_map.put(curr_key, 1);
			}
		}
		
		return false;
	}
	
	public boolean isAllVisited(boolean[] visited)
	{
		int v = 0;
		for(v = 0; v < visited.length; ++v)
		{
			if(!visited[v]) break;
		}
		if(v == visited.length) return true;
		return false;
	}
	
	
	public void start()
	{
		try {
			long t1 = System.currentTimeMillis();
			in = new BufferedReader(new FileReader("..\\GCJ\\src\\com\\congli\\gcj\\input.txt"));
			out = new PrintWriter(new File("..\\GCJ\\src\\com\\congli\\gcj\\output.txt"));
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

class chest
{
	int type;
	int[] keys;
	public chest(int type, int[] keys)
	{
		this.type = type;
		this.keys = keys.clone();
	}
}