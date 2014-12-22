package group_practice;

public class Hackerrank_AndProduct {
	public static void main(String[] args) {
		for (int a = 2; a < 10; ++a) {
			for (int b = a+1; b < 100; ++b) {
				if (groundTruth(a, b) != myMethod(a, b)) {
					System.out.println("Wrong");
					System.out.println(a + " " + b);
					return;
				}
			}
		}
	}
	
	public static int myMethod(int a, int b) {
		int i = 31;
        while ((((a >> i) & 1) == 0) && (((b >> i) & 1) == 0) && i >= 0) {
            --i;
        }
        
        int result = 0;
        while ((((a >> i) & 1) == 1) && (((b >> i) & 1) == 1) && i >= 0) {
        	result |= (1 << i);
            --i;
        }
        return result;
	}
	
	public static int groundTruth(int a, int b) {
		int temp = a;
		for (int i = a+1; i <= b; ++i) {
			temp &= i;
		}
		return temp;
	}
}
