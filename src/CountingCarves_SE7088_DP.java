import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class CountingCarves_SE7088_DP {
	static int T, N, Q;
	static int[][] memoSet;
	static int capa;

	public static void main(String[] args) throws Exception {
		// SWEA 7088 은기의 송아지 세기
		// O(N * Q) -> 시간초과
		// 세그먼트 트리로 풀이 -> O(logN * Q)
		// -----------------------------
		// DP로 풀이 -> O(1*Q)
		// 품종은 0, 1, 2로 만든다
		// 배열은 1 ~ N으로 만든다
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		T = Integer.parseInt(br.readLine());

		StringTokenizer st = null;
		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			Q = Integer.parseInt(st.nextToken());

			memoSet = new int[3][N + 1];

			for (int i = 1; i <= N; i++) {
				for (int race = 0; race < 3; race++) {
					memoSet[race][i] = memoSet[race][i - 1];
				}

				int kind = Integer.parseInt(br.readLine()) - 1;
				memoSet[kind][i] += 1;
			}

			sb.append("#").append(tc).append("\n");
			for (int i = 0; i < Q; i++) {
				st = new StringTokenizer(br.readLine());
				int L = Integer.parseInt(st.nextToken());
				int R = Integer.parseInt(st.nextToken());
				for (int race = 0; race < 3; race++) {
					int result = memoSet[race][R] - memoSet[race][L - 1];
					sb.append(result).append(" ");
				}
				sb.append("\n");
			}
		}

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		bw.write(sb.toString());
		bw.flush();

		bw.close();
		br.close();
	}
}