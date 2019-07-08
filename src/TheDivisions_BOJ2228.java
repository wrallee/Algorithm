import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TheDivisions_BOJ2228 {
	static int N, M;
	static int[] s;
	static int[][] dp;
	static final int FLAG = -1000000000;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String[] in = br.readLine().split(" ");
		N = Integer.parseInt(in[0]);
		M = Integer.parseInt(in[1]);
		s = new int[N+1];
		for (int i = 1; i <= N; i++) {
			s[i] = s[i-1] + Integer.parseInt(br.readLine());
		}
		
		bottomUp();
		/*
		for (int i = 1; i <= M; i++) {
			for (int j = 1; j <= N; j++)
				System.out.print(dp[j][i] + " ");
			System.out.println();
		}
		*/
		
		System.out.println(dp[N][M]);
		
		br.close();
	}
	
	static void bottomUp() {
		dp = new int[N+1][M+1];
		
		Arrays.fill(dp[0], FLAG);
		
		// SOLVE
		for (int n = 1; n <= N; n++) {
			for (int m = 1; m <= M; m++) {
				// n이 포함 되지 않을 경우
				dp[n][m] = dp[n-1][m];
				
				// n이 포함 될 경우
				for (int i = n-1; i >= 2*(m-1); i--) {
					int band = s[n] - s[i];
					dp[n][m] = Math.max(dp[n][m],
							band + (m == 1 ? 0 : dp[i-1][m-1]));
				}
			}
		}
	}

}
