import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AtoB_BOJ16953 {
	static int A, B, min = 32;
	public static void main(String[] args) throws Exception {
		// BOJ16953 A → B
		// B를 기준으로 줄여나가자
		// 1) B의 1의 자리가 1이 아닌 홀수라면 A와 비교 후 종료
		// 2) B의 1의 자리가 1이면 >> 연산
		// 3) B의 1의 자리가 짝수라면 2로 나눈다
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String[] in = br.readLine().split(" ");
		A = Integer.parseInt(in[0]);
		B = Integer.parseInt(in[1]);
		
		dfs(A, B, 1);
		
		if (min == 32)
			min = -1;
		System.out.println(min);
		
		br.close();
	}
	
	public static void dfs(int a, int b, int cnt) {
		if (b < 1)
			return;
		if (a == b) {
			min = Math.min(min, cnt);
			return;
		}
		int remainder = b % 10;
		if (remainder % 2 == 1) { // 홀수라면
			if (remainder == 1) {
				dfs(a, b / 10, cnt+1);
			}
			// 1이 아닌 홀수라면 종료된다
		} else {
			dfs(a, b >> 1, cnt+1);
		}
	}

}
