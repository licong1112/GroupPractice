// Author: Cong Li
// Practiced on 3/20/2013
// https://www.hackerrank.com/challenges/lego-blocks
// 
// =========================================
// 1. Consider single layer first. It's a generated length-4 Fibonacci series with
//    F(1) = 1, F(2) = 2, F(3) = 4, F(4) = 8.
// 2. For N layers, and width-M, all possible combinations are F(M)^N.
// 3. Let dp[i] be the optimal solution for width-i, then we have
//    dp[i+1] = F(i+1)^N - sum{dp[1] * F(i)^N, ..., dp[i] * F(1)}.
// 
// WARNING!!!
// 1. It overflows quickly. Need BigInteger to store the F-series.
// 2. When calculating the power and multiplication, use the property
//    (a*b) % c = (a%c * b%c) % c (1), (a+b) % c = (a%c + b%c) % c (2),
//    (a-b) % c = (a%c - b%c) % c (3).
//    Be careful, when using property (3), if the result is negative, 
//    add an additional c to the result to make it possitive.
// 3. The power can be calculated in O(bit) time, where bit is the highest
//    set-bit of the power.


import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class LegoBlockHackerrank {
    private static BigInteger[] singleLayer = new BigInteger[1001];
	private static int[] singleLayerInt = new int[1001];
	private static int mod_num = 1000000007;
    public static void main(String[] args) {
        for(int i = 1; i <= 4; ++i)
            singleLayer[i] = new BigInteger(Integer.toString(1<<(i-1)));
        for(int i = 5; i <= 1000; ++i)
            singleLayer[i] = new BigInteger(singleLayer[i-1].add(singleLayer[i-2]).add(singleLayer[i-3]).add(singleLayer[i-4]).toString());
        BigInteger mod_big = new BigInteger(Integer.toString(mod_num));
        for(int i = 1; i <= 1000; ++i)
        	singleLayerInt[i] = singleLayer[i].mod(mod_big).intValue();
        
        Scanner sc = new Scanner(System.in);
        int num_tests = sc.nextInt();
        for(int i = 0; i < num_tests; ++i)
        {
            int height = sc.nextInt();
            int width = sc.nextInt();
            if(height  == 1)
            {
                System.out.println(width > 4 ? 0 : 1);
                continue;
            }
            calculate(height, width);
        }
    }
    
    public static void calculate(int height, int width)
    {
    	long[] dp = new long[width+1];
    	Arrays.fill(dp, 1);
    	long[] singleLayerPower = new long[width+1];
    	for(int i = 1; i <= width; ++i)
    		singleLayerPower[i] = pow_n(singleLayerInt[i], height);
    	
        for(int i = 1; i <= width; ++i)
        {
        	dp[i] = singleLayerPower[i];
            for(int j = 1; j < i; ++j)
            {            	
            	dp[i] -= ((dp[j] * singleLayerPower[i-j]) % mod_num);
            	if(dp[i] < 0)
            		dp[i] += mod_num;
            }
                
        }
        System.out.println((int)(dp[width]%mod_num));
    }
    
    private static long pow_n(int a, int n)
    {
    	long result = 1;
    	long temp = a;
    	while(n > 0)
    	{
    		if((n&1) == 1)
    		{
    			result *= temp;
    			result = (result % mod_num); // Don't write "result %= mod_num"!!!!
    		}
    		temp *= temp;
    		temp %= mod_num;
    		n = (n >> 1);
    	}
    	return result;
    }
}