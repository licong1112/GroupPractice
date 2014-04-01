/**
 * Practiced on 3/31/2014
 * 
 * Facebook interview question:
 * 
 * Given a class Contact with fields: String name, List<String> email. Each
 * person can have several email addresses.
 * 
 * Now given an array of Contact:
 * 
 * #1 John [john@gmail.com]
 * #2 Mary [mary@gmail.com]
 * #3 John [john@yahoo.com]
 * #4 John [john@gmail.com, john@yahoo.com, john@hotmail.com]
 * #5 Bob [bob@gmail.com]
 * 
 * We know that if the same person has the same email address. Group the
 * contacts from the Contact array so that the contacts that are the same person
 * are grouped into the same group. For example, in this case, #1 #3 #4 are
 * the same person, while #2 and #5 are different.
 */

package group_practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class GroupContacts {
	public static void main(String[] args) {
		Contact[] contacts = new Contact[5];
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < contacts.length; ++i) {
			list.add(new ArrayList<String>());
		}
		list.get(0).add("john@gmail.com");
		list.get(1).add("mary@gmail.com");
		list.get(2).add("john@yahoo.com");
		list.get(3).add("john@gmail.com"); list.get(3).add("john@yahoo.com"); list.get(3).add("john@hotmail.com");
		list.get(4).add("bob@gmail.com");
		
		String[] names = {"john", "mary", "john", "john", "bob"};
		
		for (int i = 0; i < contacts.length; ++i) {
			contacts[i] = new Contact(names[i], list.get(i));
		}
		
		GroupContacts test = new GroupContacts();
		test.groupContacts(contacts);
	}
	
	public ArrayList<ArrayList<Contact>> groupContacts(Contact[] contacts) {
		// key: email; value: indexes of contacts that have the key
		HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
		for (int i = 0; i < contacts.length; ++i) {
			for (String str : contacts[i].email) {
				if (!map.containsKey(str)) {
					map.put(str, new ArrayList<Integer>());
				}
				map.get(str).add(i);
			}
		}
		
		// For contacts that share the same email, they should be union-ed.
		int[] parent = new int[contacts.length];
		for (int i = 0; i < parent.length; ++i) {
			parent[i] = i;
		}
		
		Iterator iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, ArrayList<Integer>> entry = (Map.Entry<String, ArrayList<Integer>>)iterator.next();
			ArrayList<Integer> index_list = entry.getValue();
			for (int i = 1; i < index_list.size(); ++i) {
				union(parent, index_list.get(0), index_list.get(i));
			}
		}
		
		// Now the same person have the same parent[] value
		ArrayList<ArrayList<Contact>> result = new ArrayList<ArrayList<Contact>>();
		ArrayList<ArrayList<Integer>> result_print = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < parent.length; ++i) {
			result.add(new ArrayList<Contact>());
			result_print.add(new ArrayList<Integer>());
		}
		
		// For contacts whose parent[] value are the same, add to the same row
		// of the result[][] list.
		for (int i = 0; i < parent.length; ++i) {
			result.get(parent[i]).add(contacts[i]);
			result_print.get(parent[i]).add(i);
		}
		
		// Remove empty rows
		for (int i = 0; i < result.size(); ++i) {
			if (result.get(i).size() == 0) {
				result.remove(i);
				result_print.remove(i);
				--i;
			}
		}
		
		// For printing purpose only
		for (ArrayList<Integer> list : result_print) {
			for (Integer i : list) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
		return result;
	}
	
	public int find(int[] parent, int i) {
		if (i == parent[i]) return i;
		return find(parent, parent[i]);
	}
	
	public void union(int[] parent, int i, int j) {
		int parent_i = find(parent, i);
		int parent_j = find(parent, j);
		if (parent_i == parent_j) return;
		parent[parent_i] = parent_j;
	}
}

class Contact {
	String name;
	ArrayList<String> email;
	public Contact(String n, ArrayList<String> e) {
		this.name = n;
		email = new ArrayList<String>();
		for (String str : e) {
			email.add(str);
		}
	}
}
