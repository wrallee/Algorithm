import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class InstallRouter_BOJ2110 {
	static int N, C, max;
	static int[] x;
	public static void main(String[] args) throws IOException {
		// BOJ2110: 공유기 설치
		// 공유기의 간격을 이분탐색으로 찾아본다
		// 시간복잡도는 N(N*log(10^9))
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String[] in = br.readLine().split(" ");
		N = Integer.parseInt(in[0]);
		C = Integer.parseInt(in[1]);
		x = new int[N];
		for (int i = 0; i < N; i++) {
			x[i] = Integer.parseInt(br.readLine());
		}
		Arrays.sort(x); // O(logN)
		
		int left = 1;
		int right = x[N-1] - x[0];
		binarySearch(left, right);
		System.out.println(max);
		
		br.close();
	}
	
	static void binarySearch(int left, int right) {
		while (left <= right) {
			int gap = (left + right) >> 1;
			int cnt = 1;
			int wifi = x[0];
			for (int i = 0; i < N; i++) {
				if (x[i] - wifi >= gap) {
					wifi = x[i];
					cnt++;
				}
			}
			
			if (cnt >= C) {
				max = gap; // 결국 가장 최적화된 답(left==right)이 입력된다.
				left = gap+1;
			} else {
				right = gap-1;
			}
		}
	}

}
