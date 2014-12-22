package group_practice;

public class Hackerrank_AndProduct {
	public static long myMethod(long a, long b) {
		int i = 31;
        long result = 0;
        while (((a >> i) & 1) == ((b >> i) & 1) && i >= 0) {
        	if (((a >> i) & 1) == 1) {
        		result |= (1L << i);
        	}
        	
            --i;
        }
        return result;
	}
}
