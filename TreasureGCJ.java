import java.io.*;
import java.util.*;

public class TreasureGCJ {

	BufferedReader in;
	PrintWriter out;
	StringTokenizer tok = new StringTokenizer("");

	public static void main(String[] args) {
		TreasureGCJ test = new TreasureGCJ();
		test.start();
	}

	public void solve() throws IOException 
	{
		int num_test = readInt();
		for(int i = 0; i < num_test; ++i)
		{
			int num_key = readInt();
			int num_chest = readInt();
			
			int[] key_map = new int[201];
			for(int k = 0; k < num_key; ++k)
			{
				int curr_key = readInt();
				key_map[curr_key]++;
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
			
			if(dfs(array, visited, key_map, num_key, result))
			{
				out.print("Case #" + (i+1) + ":");
				while(!result.isEmpty())
				{
					out.print(" " + result.pop());
				}
				out.println();
			}
			else
				out.println("Case #" + (i+1) + ": IMPOSSIBLE");
		}
	}
	
	public boolean dfs(chest[] array, boolean[] visited, int[] key_map, int num_key, Stack<Integer> result)
	{
		if(isAllVisited(visited)) return true;
		if(num_key == 0) return false;
		if(!isValid(visited, array, key_map)) return false;
		if(!isValid2(visited, array, key_map)) return false;
		
		for(int i = 0; i < array.length; ++i)
		{
			int curr_key = array[i].type;
			if(!visited[i] && key_map[curr_key] > 0)
			{
				key_map[curr_key]--;
				num_key--;
				for(int k : array[i].keys)
				{
					key_map[k]++;
					num_key++;
				}
				visited[i] = true;
				
				if(dfs(array, visited, key_map, num_key, result))
				{
					result.push(i+1);
					return true;
				}
				
				visited[i] = false;
				for(int k : array[i].keys)
				{
					key_map[k]--;
					num_key--;
				}
				
				key_map[curr_key]++;
				num_key++;
			}
		}
		
		return false;
	}
	
	public boolean isValid(boolean[] visited, chest[] array, int[] map_key)
	{
		// For current unvisited chests, count the number of keys required
		// for each key type. If there is one type that require more keys
		// than existing keys, then return false.
		
		int[] map_temp = map_key.clone();
		int[] map_needed = new int[map_temp.length];
		
		for(int i = 0; i < visited.length; ++i)
		{
			if(!visited[i])
			{
				map_needed[array[i].type]++;
				for(int k : array[i].keys)
					map_temp[k]++;
			}
		}
		
		for(int i = 0; i < map_temp.length; ++i)
			if(map_temp[i] < map_needed[i])
				return false;
		return true;
	}
	
	public boolean isValid2(boolean[] visited, chest[] array, int[] map_key)
	{
		// For each unvisited chest, see what type of key it needs.
		// Look at all available keys in other unvisited chests, plus
		// current keys in hand. If no such key exists, then return false.
		
		for(int i = 0; i < visited.length; ++i)
		{
			if(visited[i])continue;
			if(map_key[array[i].type] > 0) continue;
			
			int j = 0;
			boolean isValid = false;
			for(j = 0; j < visited.length; ++j)
			{
				if(visited[j]) continue;
				if(j == i) continue;
				
				for(int k : array[j].keys)
				{
					if(k == array[i].type)
					{
						isValid = true;
						break;
					}
				}
				if(isValid)
					break;
			}
			if(j == visited.length) return false;
		}
		return true;
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