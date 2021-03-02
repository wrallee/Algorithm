import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class SEA7088_CountingCalves_BU {
	static int T, N, Q;
	static int[][] treeSet;
	static int[] value;
	static int capa;

	public static void main(String[] args) throws Exception {
		// SWEA 7088 은기의 송아지 세기
		// O(N * Q) -> 시간초과
		// 세그먼트 트리로 풀이 -> O(logN * Q)
		// tree배열을 2차원으로 만들어 품종을 분류한다
		// 품종은 0, 1, 2로 만든다
		// 배열은 1 ~ N으로 만든다
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		T = Integer.parseInt(br.readLine());

		long start = System.currentTimeMillis();

		StringTokenizer st = null;
		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			Q = Integer.parseInt(st.nextToken());

			int h = (int) Math.ceil(Math.log(N) / Math.log(2)) + 1;
			treeSet = new int[3][1 << h];
			capa = treeSet[0].length / 2;

			for (int i = capa; i < capa + N; i++) {
				int race = Integer.parseInt(br.readLine()) - 1;
				treeSet[race][i] = 1;
			}
			for (int i = capa - 1; i > 0; i--) {
				for (int race = 0; race < 3; race++) {
					treeSet[race][i] = treeSet[race][2 * i] + treeSet[race][2 * i + 1];
				}
			}

			sb.append("#").append(tc).append("\n");
			for (int i = 0; i < Q; i++) {
				st = new StringTokenizer(br.readLine());
				int L = Integer.parseInt(st.nextToken());
				int R = Integer.parseInt(st.nextToken());
				int[] cnt = get(L, R);
				for (int race = 0; race < 3; race++) {
					sb.append(cnt[race]).append(" ");
				}
				sb.append("\n");
			}
		}

		long end = System.currentTimeMillis();
		sb.append((end - start) / 1000.0);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		bw.write(sb.toString());
		bw.flush();

		bw.close();
		br.close();
	}

	public static int[] get(int L, int R) {
		L += capa - 1;
		R += capa - 1;

		int[] res = new int[3];
		while (L < R) {
			if (L % 2 == 1) {
				for (int i = 0; i < 3; i++) {
					res[i] += treeSet[i][L];
				}
				L++;
			}
			if (R % 2 == 0) {
				for (int i = 0; i < 3; i++) {
					res[i] += treeSet[i][R];
				}
				R--;
			}
			L /= 2;
			R /= 2;
		}
		if (L == R) {
			for (int i = 0; i < 3; i++) {
				res[i] += treeSet[i][L];
			}
		}

		return res;
	}

}