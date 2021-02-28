import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HitTheEggs_BOJ16987 {
	static int N, max;
	static int[] S, W;
	public static void main(String[] args) throws IOException {
		// dfs로 계란 선택을 순서대로 한다
		// dfs 실행 함수 내부에 for문으로 계란을 전부 돌리고
		// 깨진 계란과 현재 들고있는 계란은 skip한다
		// 깨지면 flag처리를 한다
		// visited는 비트마스크로 표기할 수 있지만
		// egg의 남은 내구도는 update와 rollback을 해줘야 한다
		
		// + 현재 들고있는 계란을 제외하고 모두 깨졌으면 카운트를 한다
		//   => visit | hand가 (1 << N)-1과 같을 때
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// INIT
		N = Integer.parseInt(br.readLine());
		S = new int[N];
		W = new int[N];
		for (int i = 0; i < N; i++) {
			String[] input = br.readLine().split(" ");
			S[i] = Integer.parseInt(input[0]);
			W[i] = Integer.parseInt(input[1]);
		}
		
		// SOLVE
		max = 0;
		dfs(0, 0);
		System.out.println(max);
		
		br.close();
	}
	
	static void dfs(int phase, int visit) {
		if (max == N)
			return;
		if (phase >= N
				|| (visit | (1 << phase)) == (1 << N)-1) {
			int cnt = 0;
			for (int i = 0; i < N; i++) {
				cnt += visit % 2;
				visit >>= 1;
			}
			max = Math.max(max, cnt);
			return;
		}
		
		if ((visit & (1 << phase)) > 0) {
			dfs(phase+1, visit);
		
		} else {
			for (int i = 0; i < N; i++) {
				if (i == phase)
					continue;
				if ((visit & (1 << i)) > 0)
					continue;
				
				// 내구도 backup
				int hand = S[phase];
				int target = S[i];
				
				// 내구도 update
				S[i] -= W[phase];
				S[phase] -= W[i];
				
				int broken = 0;
				if (S[phase] <= 0) broken |= 1 << phase;
				if (S[i] <= 0) broken |= 1 << i;
				
				dfs(phase+1, visit|broken);
				
				// 내구도 rollback
				S[phase] = hand;
				S[i] = target;
			}
		}
	}

}
