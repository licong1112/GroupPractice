// Author: Cong Li
// Practiced on 4/2/2013
//
// Given a 0-1 random generator, write a function to uniformly generate
// a numbers between 0 and n-1.
//
// =====================================
// Rejection sampling.

public class GenerateUniformlyDistributedNumbers {

	public static void main(String[] args) {
		GenerateUniformlyDistributedNumbers test = new GenerateUniformlyDistributedNumbers();
		
		int n = 3;
		int[] array = new int[n];
		for(int i = 0; i < n*1000; ++i)
			array[test.gen_uniform_n(n)]++;
		
		for(int i = 0; i < n; ++i)
			System.out.println(array[i]);
	}

	public int gen_uniform_n(int n)
	{
		if(n < 2) return 0;
		if(n == 2) return myrand(); 
		
		int n_temp = n;
		int count = 0;
		while(n_temp > 0)
		{
			n_temp = (n_temp >> 1);
			++count;
		}
		
		int new_count = count;
		int result = Integer.MAX_VALUE;
		while(result >= n)
		{
			result = 0;
			new_count = count;
			while(new_count-- > 0)
				result = (result << 1) | (myrand());
		}
		return result;
	}
	
	
	public int myrand()
	{
		return Math.random() > 0.5 ? 1 : 0;
	}
}
