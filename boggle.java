// Author: Cong Li
// Solve the "boggle" problem. http://en.wikipedia.org/wiki/Boggle
//
// =====================================
// For detailed discussion, check here: http://www.mitbbs.com/article_t/JobHunting/32333427.html
// 
// 1. Practice Trie!!!! Necessary!!!
// 2. Trie is super fast!!

import java.util.HashSet;

public class boggle {

	public static HashSet<String> dictionary = null;
	public static TrieNode trie = null;
	public static void main(String[] args) {
		
		boggle test = new boggle();
		String[] dic = {"cat", "cate", "apple", "pear", "av", "haha", "great"};
		dictionary = new HashSet<String>();
		for(String str : dic)
			dictionary.add(str);
		char[][] array = {{'a', 'b', 'c', 'd'},
						  {'v', 'r', 'a', 'f'},
						  {'j', 't', 'e', 'p'},
						  {'k', 'g', 'r', 'o'}};
		
		trie = new TrieNode('0', false); // '0' represents root.
		for(String str : dic)
			trie.add(str, 0);
		System.out.println("Print the trie....");
		for(TrieNode child : trie.children)
			if(child != null)
				child.print("");
		
		System.out.println("Start solving....");
		
		test.solve(array);
	}
	
	public void solve(char[][] array)
	{
		boolean[][] visited = new boolean[array.length][array[0].length];
		
		// Use trie.
		long t1 = System.currentTimeMillis();
		for(int i = 0; i < array.length; ++i)
			for(int j = 0; j < array[0].length; ++j)
				dfs_trie(array, i, j, visited, "", trie);
		long t2 = System.currentTimeMillis();
		System.out.println("Time = " + (t2 - t1));
		System.out.println();
		
		
		// Brute force.
		t1 = System.currentTimeMillis();
		for(int i = 0; i < array.length; ++i)
			for(int j = 0; j < array[0].length; ++j)
				dfs(array, i, j, visited, "");
		t2 = System.currentTimeMillis();
		System.out.println("Time = " + (t2 - t1));
	}
	
	public void dfs_trie(char[][] array, int x, int y, boolean[][] visited, String str, TrieNode node)
	{
		if(!valid(x, y, array.length, array[0].length) || visited[x][y]) return;
		
		int child_ind = 0;
		while(child_ind < 26)
		{
			if(node.children[child_ind] != null && node.children[child_ind].val == array[x][y])
				break;
			++child_ind;
		}
		if(child_ind == 26) return;
		
		String new_str = str + array[x][y];
		visited[x][y] = true;
		
		if(node.children[child_ind].end)
			System.out.println(new_str);
		
		TrieNode new_node = node.children[child_ind];
		
		dfs_trie(array, x+1, y+1, visited, new_str, new_node);
		dfs_trie(array, x+1, y, visited, new_str, new_node);
		dfs_trie(array, x+1, y-1, visited, new_str, new_node);
		dfs_trie(array, x, y+1, visited, new_str, new_node);
		dfs_trie(array, x, y-1, visited, new_str, new_node);
		dfs_trie(array, x-1, y+1, visited, new_str, new_node);
		dfs_trie(array, x-1, y, visited, new_str, new_node);
		dfs_trie(array, x-1, y-1, visited, new_str, new_node);
		
		visited[x][y] = false;
	}
	
	
	public void dfs(char[][] array, int x, int y, boolean[][] visited, String str)
	{
		if(!valid(x, y, array.length, array[0].length) || visited[x][y]) return;
		
		String new_str = str + array[x][y];
		visited[x][y] = true;
		
		if(dictionary.contains(new_str))
			System.out.println(new_str);
		
		dfs(array, x+1, y+1, visited, new_str);
		dfs(array, x+1, y, visited, new_str);
		dfs(array, x+1, y-1, visited, new_str);
		dfs(array, x, y+1, visited, new_str);
		dfs(array, x, y-1, visited, new_str);
		dfs(array, x-1, y+1, visited, new_str);
		dfs(array, x-1, y, visited, new_str);
		dfs(array, x-1, y-1, visited, new_str);
		
		visited[x][y] = false;
	}
	
	public boolean valid(int x, int y, int m, int n)
	{
		if(x >= 0 && x < m && y >= 0 && y < n) return true;
		return false;
	}
}

class TrieNode
{
	char val;
	TrieNode[] children;
	boolean end;
	public TrieNode(char val, boolean end)
	{
		this.val = val;
		this.end = end;
		children = new TrieNode[26];
	}
	public void add(String str, int index)
	{
		if(index == str.length()) return;
		
		int child_index = str.charAt(index) - 'a';
		if(children[child_index] == null)
			children[child_index] = new TrieNode(str.charAt(index), index == str.length()-1);
		else if(index == str.length()-1)
			children[child_index].end = true;
		
		children[child_index].add(str, index+1);
	}
	public void print(String str)
	{
		String new_str = str + val;
		if(end)
			System.out.println(new_str);
		
		for(TrieNode child : children)
			if(child != null)
				child.print(new_str);
	}
}