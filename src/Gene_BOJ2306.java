import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Gene_BOJ2306 {
	static int len;
	static char[] dna;
	static int[][] dp;
	public static void main(String[] args) throws Exception {
		// dna서열을 i, j를 조절해가며 탐색한다(MCM)
		// 가장 상위 기준은 염색체 길이(L)
		// 두 번쨰 기준은 출발점(i), 도착점(j)
		// 세 번째 기준은 출발점과 도착점 사이의 임의의 점 k로 잡는다
		// -> 3중 반복문을 수행한다
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// INIT
		dna = ("0" + br.readLine()).toCharArray();
		len = dna.length - 1;
		
		getMCMResult();
		
		System.out.println(dp[1][len]);
		/*
		for (int i = 1; i < dp.length; i++) {
			for (int j = 1; j < dp[i].length; j++) {
				System.out.print(dp[i][j] + " ");
			}
			System.out.println();
		}
		*/
		br.close();
	}
	
	static void getMCMResult() {
		dp = new int[len+1][len+1];
		for (int L = 1; L <= len; L++) { // L = 2, len = 5일 때
			for (int i = 1; i <= len - L + 1; i++) { // i는 4까지
				int j = i + L - 1; // j는 5까지
				if ((dna[i] == 'a' && dna[j] == 't')
						|| (dna[i] == 'g' && dna[j] == 'c'))
					dp[i][j] = dp[i+1][j-1] + 2;
				for (int k = i; k < j; k++) { // k는 4 ~ 5
					int tmp = dp[i][k] + dp[k+1][j];
					if (tmp > dp[i][j])
						dp[i][j] = tmp;
				}
				//System.out.println(i+":"+dna[i]+", "+j+":"+dna[j] + " | " + dp[i][j]);
			}
			//System.out.println();
		}
	}

}
