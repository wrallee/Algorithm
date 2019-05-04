import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BirthdayParty_SEA5521 {
	static int T, N, M;
	static boolean[] visit;
	static boolean[][] relations;
	static final boolean[] falseArr = new boolean[501];
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(br.readLine());
		visit = new boolean[501];
		relations = new boolean[501][501];
		
		StringBuilder sb = new StringBuilder();
		for (int testcase = 1; testcase <= T; testcase++) {
			String[] in = br.readLine().split(" ");
			N = Integer.parseInt(in[0]);
			M = Integer.parseInt(in[1]);
			for (int i = 0; i < M; i++) {
				in = br.readLine().split(" ");
				int x = Integer.parseInt(in[0]);
				int y = Integer.parseInt(in[1]);
				relations[x][y] = relations[y][x] = true;
			}
			
			sb.append("#").append(testcase);
			sb.append(" ").append(invite()).append("\n");
			
			// RESET
			System.arraycopy(falseArr, 0, visit, 0, 501);
			for (int i = 1; i <= N; i++) System.arraycopy(falseArr, 0, relations[i], 0, 501);
		}
		System.out.print(sb.toString());
		
		br.close();
	}
	
	static int invite() {
		int cnt = 0;
		visit[1] = true;
		// 직접친구
		for (int i = 1; i < relations[1].length; i++) {
			if (relations[1][i]) {
				visit[i] = true;
				cnt++;
			}
		}
		
		// 건너친구
		for (int i = 0; i < relations[1].length; i++) {
			if (relations[1][i]) {
				for (int j = 0; j < relations[i].length; j++) {
					if (relations[i][j] && !visit[j]) {
						visit[j] = true;
						cnt++;
					}
				}
			}
		}
		
		return cnt;
	}

}
