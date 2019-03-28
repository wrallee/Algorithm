import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class TeamAdminitration {
	// Testcase 30개 | Java 1.5초
	// 1 <= K < N <= 100,000
	
	// N명의 사람에게 1부터 N까지 번호를 붙인다(중복 가능)
	// 인접한 사람이 같은 번호이면 팀이 된다
	// 전체에서 K명을 제외 할 때, 구성할 수 있는 최대 팀원 수
	static int T, K, N, max;
	static LinkedList<Pair>[] memo;
	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("TC.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		Runtime runtime = Runtime.getRuntime();
		
		long stt = 0;
		long sttMem =  runtime.totalMemory() - runtime.freeMemory();
		
		T = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		for (int t = 1; t <= T; t++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			memo = new LinkedList[N+1]; // List에 들어가는 건 원소를 지운 Total 횟수
			st = new StringTokenizer(br.readLine());
			
			stt = System.currentTimeMillis();
			max = 1; // 적어도 1이다
			for (int i = 1; i <= N; i++) {
				int value = Integer.parseInt(st.nextToken());
				if (memo[value] == null) {
					memo[value] = new LinkedList<Pair>();
					memo[value].add(new Pair(i, 0));
				} else {
					Pair lastPair = memo[value].peekLast();
					int diff = i - lastPair.index - 1;
					int kTotalCnt = lastPair.kCnt + diff;
					
					int kRealCnt = kTotalCnt - memo[value].peekFirst().kCnt;
					
					while (!memo[value].isEmpty() &&
							(kRealCnt > K || N-K <= memo[value].size())) { // 현재 Pair를 추가하려면 N-K보다 작아야 한다
						kRealCnt = kTotalCnt - memo[value].pollFirst().kCnt;
					}
					memo[value].add(new Pair(i, kTotalCnt));
				}
				
				max = Math.max(max, memo[value].size());
			}
			sb.append("#").append(t).append(" ").append(max).append("\n");
			
		}
		long end = System.currentTimeMillis();
		long endMem = runtime.totalMemory() - runtime.freeMemory();
		
		sb.append("TIME: ").append((end - stt) / 1000.0).append("s").append("\n");
		sb.append("MEMORY: ").append((endMem - sttMem) / 1024 / 1024).append("MB");
		System.out.println(sb.toString());
		
		br.close();
	}
	
	static class Pair {
		int index;
		int kCnt;
		public Pair(int index, int kCount) {
			this.index = index;
			this.kCnt = kCount;
		}
		@Override
		public String toString() {
			return String.valueOf(kCnt);
		}
	}

}
