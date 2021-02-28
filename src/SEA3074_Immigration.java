import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Immigration_SEA3074 {
	static long min, M;
	static int T, N;
	static int[] t;
	public static void main(String[] args) throws Exception {
		// SWEA3074: 입국심사
		// 어떻게 통과시키느냐보다는 몇초가 걸리느냐에 초점을 맞춘다
		// 몇초가 걸리는지는 Binary Search로 범위를 줄여나간다
		// 시간복잡도는 O(N*logM)
		// 처음 start는 0, mid, max로 시작
		//System.setIn(new FileInputStream("TC4.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		//long stt = System.currentTimeMillis();
		
		T = Integer.parseInt(br.readLine());
		t = new int[100001];
		StringBuilder sb = new StringBuilder();
		for (int testcase = 1; testcase <= T; testcase++) {
			String[] in = br.readLine().split(" ");
			N = Integer.parseInt(in[0]);
			M = Integer.parseInt(in[1]);
			
			// INIT
			int max = 0;
			for (int k = 1; k <= N; k++) {
				t[k] = Integer.parseInt(br.readLine());
				
				max = Math.max(max, t[k]);
			}
			
			// SOLVE
			min = M*max;
			binarySearch(0, M*max); // 묵시적 형변환
			sb.append("#").append(testcase);
			sb.append(" ").append(min).append("\n");
		}
		
		//double time = (System.currentTimeMillis() - stt) / 1000.0; sb.append(time);
		System.out.println(sb.toString());
		br.close();
	}
	
	static void binarySearch(long start, long end) {
		while (start <= end) {
			long mid = (start + end) / 2; // mid초에 끝난다고 가정해본다
			long cnt = 0; // int cnt = 0; // 여기서 틀린 것 같다. 나누는 값이지만 충분히 int를 넘을 수 있다
			for (int k = 1; k <= N; k++) {
				cnt += (mid / t[k]); // k번째 심사대를 통과한 사람 수
			}
			// cnt -> mid초까지 모든 심사대가 검사한 총 사람 수
			if (cnt < M) { // 사람수에 못미치면 mid를 늘린다
				start = mid+1;
			} else { // 사람수와 같거나 넘어섰으면 mid를 줄인다
				min = Math.min(min, mid);
				end = mid-1;
			}
		}
	}
}
