import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MinimalEdit_BOJ15483 {
	static int[][] dp;
	static char[] a, b;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		a = ("0" + br.readLine()).toCharArray();
		b = ("0" + br.readLine()).toCharArray();
		dp = new int[b.length][a.length];
		
		getLCS();
		
		System.out.println(dp[b.length-1][a.length-1]);
		
		//for (int i = 0; i < b.length; i++) System.out.println(Arrays.toString(dp[i]));
		
		br.close();
	}
	
	static void getLCS() {
		for (int i = 1; i < b.length; i++) dp[i][0] = dp[i-1][0] + 1;
		for (int j = 1; j < a.length; j++) dp[0][j] = dp[0][j-1] + 1;
		
		for (int i = 1; i < b.length; i++) {
			for (int j = 1; j < a.length; j++) {
				// 치환은 i-1, j-1
				// 삽입은 j-1
				// 삭제는 i-1
				if (b[i] == a[j]) {
					dp[i][j] = dp[i-1][j-1];
				} else {
					dp[i][j] = Math.min(dp[i-1][j] + 1, dp[i][j-1] + 1);
					dp[i][j] = Math.min(dp[i][j], dp[i-1][j-1] + 1);
				}
			}
		}
	}
}