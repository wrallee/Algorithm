import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class LongestIncreasingSs_BOJ11053 {
	static int N;
	static int[] val;
	static int[] dp;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		val = new int[N+1];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			val[i] = Integer.parseInt(st.nextToken());
		}
		
		System.out.println(bottomUp());
		
		br.close();
	}
	
	static int bottomUp() {
		dp = new int[N+1];
		
		int max = 0;
		for (int i = 1; i <= N; i++) {
			dp[i] = 1;
			for (int j = 1; j < i; j++) {
				if (val[i] > val[j]) {
					dp[i] = Math.max(dp[i], dp[j] + 1);
				}
			}
			max = Math.max(max, dp[i]);
		}
		
		return max;
	}

}