import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CareerBuilding {
	static int T, N, M;
	static int[] dp;
	static int[] salary;
	static int[][] lineInfo;
	static final int MAX_VAL = 1000000007;
	public static void main(String[] args) throws Exception {
		// Testcase 10개 | Java 1.5초
		// 1 <= N <= 100,000 | 0 <= M <= 300000
		
		// 한 사원이 연봉을 올리면서 이직하는 방법의 수를 찾는다
		// 회사는 N개, 중계소(선) M개가 있다
		// 이직은 중계소로 연결된 곳만 가능하다
		// 이직은 연봉이 낮은 곳 -> 높은 곳으로만 가능하다(같은곳도 불가)
		
		// 2개 이상의 회사를 거치는 경우의 수를 10000000007로 나눈 나머지 출력
		
		// 높은곳에서 낮은곳으로만 선을 놓는다
		// 높은곳에서 시작하여 내려간다
		// List를 쓸 경우 add속도 때문에 시간초과
		// -> 배열로 만들자
		System.setIn(new FileInputStream("TC2.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		long stt = System.currentTimeMillis();
		
		T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			// INIT
			dp = new int[N+1];
			salary = new int[N+1];
			int[] layout = new int[N+1];
			Pair[] connect = new Pair[M];

			lineInfo = new int[N+1][];
			
			st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= N; i++) {
				salary[i] = Integer.parseInt(st.nextToken());
			}
			
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int first = Integer.parseInt(st.nextToken());
				int second = Integer.parseInt(st.nextToken());
				
				if (first == second) continue;// 같은경우는 무시
				if (salary[first] == salary[second]) continue;
				
				int std = salary[first] > salary[second] ? first : second;
				int target = salary[first] > salary[second] ? second : first; 
				
				connect[i] = new Pair(std, target);
				layout[std]++;
			}
			for (int i = 1; i <= N; i++) {
				if (layout[i] != 0) {
					lineInfo[i] = new int[layout[i]];
				}
			}
			for (int i = 0; i < M; i++) {
				Pair p = connect[i];
				int idx = p.one;
				lineInfo[idx][--layout[idx]] = p.two;
			}
			
			// SOLVE
			Arrays.fill(dp, -1);
			int result = 0;
			for (int i = 1; i <= N; i++) {
				if (lineInfo[i] == null)
					continue;
				result += dfs(i);
				result %= MAX_VAL;
			}
			
			//System.out.println(Arrays.toString(dp));
			
			sb.append("#").append(t).append(" ").append(result).append("\n");
			
		}
		
		long end = System.currentTimeMillis();
		sb.append((end - stt) / 1000.0);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		bw.write(sb.toString());
		bw.flush();
		bw.close();
		//System.out.println(sb.toString());
		
		br.close();
		
	}
	
	static int dfs(int idx) {
		if (dp[idx] != -1)
			return dp[idx];
		
		if (lineInfo[idx] == null) {
			return dp[idx] = 0;
		}
		
		int result = 0;
		for (int target : lineInfo[idx]) {
			result += dfs(target) + 1;
			result %= MAX_VAL;
		}
		
		return dp[idx] = result;
	}
	
	static class Pair implements Comparable<Pair> {
		int one;
		int two;
		public Pair(int one, int two) {
			this.one = one;
			this.two = two;
		}
		@Override
		public int compareTo(Pair o) {
			return this.two - o.two;
		}
	}

}
