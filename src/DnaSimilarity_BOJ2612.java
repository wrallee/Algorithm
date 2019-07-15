import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DnaSimilarity_BOJ2612 {
	static int l1, l2, max;
	static int[] maxEnd;
	static char[] dna1, dna2;
	static int[][] dp;
	static int[][][] parent;
	static final int Y = 1, X = 0;

	public static void main(String[] args) throws Exception {
		// System.setIn(new FileInputStream("TC.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		l1 = Integer.parseInt(br.readLine());
		dna1 = ("0" + br.readLine()).toCharArray();
		l2 = Integer.parseInt(br.readLine());
		dna2 = ("0" + br.readLine()).toCharArray();

		dp = new int[l1 + 1][l2 + 1];
		parent = new int[l1 + 1][l2 + 1][2];
		maxEnd = new int[2]; // Y, X

		getLCS();

		StringBuilder sb1 = new StringBuilder("");
		StringBuilder sb2 = new StringBuilder("");
		findParent(maxEnd[Y], maxEnd[X], sb1, sb2);

		StringBuilder sb = new StringBuilder();
		sb.append(max).append("\n");
		sb.append(sb1).append("\n");
		sb.append(sb2).append("\n");
		System.out.print(sb.toString());

		br.close();
	}

	static void getLCS() {
		parent[0][0][Y] = -1;
		parent[0][0][X] = -1;
		for (int i = 1; i <= l1; i++) {
			for (int j = 1; j <= l2; j++) {
				if (dna1[i] == dna2[j]) {
					if (dp[i - 1][j - 1] + 3 <= 3) {
						dp[i][j] = 3;
						parent[i][j][Y] = -1;
						parent[i][j][X] = -1;
					} else {
						dp[i][j] = dp[i - 1][j - 1] + 3;
						parent[i][j][Y] = i - 1;
						parent[i][j][X] = j - 1;
					}
				} else {
					// 대각선 위
					dp[i][j] = dp[i - 1][j - 1] - 2;
					parent[i][j][Y] = i - 1;
					parent[i][j][X] = j - 1;
					
					if (dp[i - 1][j] - 2 > dp[i][j]) {
						// 위쪽
						dp[i][j] = dp[i - 1][j] - 2;
						parent[i][j][Y] = i - 1;
						parent[i][j][X] = j;
					}
					if (dp[i][j - 1] - 2 > dp[i][j]) {
						// 왼쪽
						dp[i][j] = dp[i][j - 1] - 2;
						parent[i][j][Y] = i;
						parent[i][j][X] = j - 1;
					}
					
					// 음수가 되면 다시시작
					if (dp[i][j] <= 0)
						dp[i][j] = 0;
				}


				if (dp[i][j] > max) {
					max = dp[i][j];
					maxEnd[Y] = i;
					maxEnd[X] = j;
				}
				//System.out.print(dp[i][j] + " ");
				//System.out.print("["+parent[i][j][Y] + "," + parent[i][j][X]+"] ");
			}
			//System.out.println();
		}
	}

	static void findParent(int y, int x, StringBuilder sb1, StringBuilder sb2) {
		if (y == -1 && x == -1)
			return;
		
		int[] end = parent[y][x];
		findParent(end[Y], end[X], sb1, sb2);

		if (y != end[Y])
			sb1.append(dna1[y]);
		if (x != end[X])
			sb2.append(dna2[x]);
	}
}