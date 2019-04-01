import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ConnectElectricity_SEA6855 {
	static int T, N, K;
	static int[] house;
	static int[] differ;
	public static void main(String[] args) throws Exception {
		// SWEA 6855 신도시 전기 연결하기
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {
			
			// INIT
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			house = new int[N];
			differ = new int[N-1];
			
			// SOLVE
			st = new StringTokenizer(br.readLine());
			house[0] = Integer.parseInt(st.nextToken());
			for (int i = 1; i < N; i++) {
				house[i] = Integer.parseInt(st.nextToken());
				differ[i-1] = house[i] - house[i-1];
			}
			
			Arrays.sort(differ);
			int minus = 0;
			for (int i = N-2, k = K-1;
					i >= 0 && k > 0; i--, k--) minus += differ[i];
			
			int result = house[N-1] - house[0] - minus;
			
			sb.append("#").append(t).append(" ").append(result).append("\n");
			
		}
		
		System.out.println(sb.toString());
		
		br.close();
	}
}