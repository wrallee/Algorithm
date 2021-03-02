import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ17069_MovingPipe2 {
	static int N;
	static char[][] map;
	static long[][][] dp;
	static int[] dy = {0, 1, 1};
	static int[] dx = {1, 1, 0};
	public static void main(String[] args) throws Exception {
		// 파이프의 상태별로 3개의 차원으로 나눈다
		// 가로 -> 이전단계 가로+대각선
		// 세로 -> 이전단계 대각선+세로
		// 대각선 -> 이전단계 가로+대각선+세로
		
		// [0,1]에서 시작한다
		// [N-1, N-1] 까지 가는 모든 경우의 수
		// 0 - 가로도착 / 1 - 대각선도착 / 2 - 세로도착
		
		// 3^31는 int범위(2^31)를 넘는다
		// dp 배열의 자료형을 long(2^63)으로 변경한다
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// INIT
		N = Integer.parseInt(br.readLine());
		map = new char[N][N];
		dp = new long[3][N][N];
		
		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().replace(" ", "").toCharArray();
		}
		
		// SOLVE
		dp[0][0][1] = 1;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == '1')
					continue;
				for (int state = 0; state < 3; state++) {
					setNow(i, j, state);
				}
			}
		}
		
		long sum = 0;
		for (int i = 0; i < 3; i++) sum += dp[i][N-1][N-1];
		
		System.out.println(sum);
		
		br.close();
	}
	
	static void setNow(int y, int x, int state) {
		int nY, nX;
		
		if (state == 1) {
			nY = y-1; nX = x-1;
			if (!outOfBound(nY, nX)
					&& !outOfBound(y-1, x)
					&& !outOfBound(y, x-1)
					&& map[y-1][x] != '1'
					&& map[y][x-1] != '1') {
				dp[state][y][x] += dp[0][nY][nX];
				dp[state][y][x] += dp[1][nY][nX];
				dp[state][y][x] += dp[2][nY][nX];
			}
		} else if (state == 0) {
			nY = y; nX = x-1;
			if (!outOfBound(nY, nX)) {
				dp[state][y][x] += dp[0][nY][nX];
				dp[state][y][x] += dp[1][nY][nX];
			}
		} else if (state == 2) {
			nY = y-1; nX = x;
			if (!outOfBound(nY, nX)) {
				dp[state][y][x] += dp[2][nY][nX];
				dp[state][y][x] += dp[1][nY][nX];
			}
		}
	}
	
	static boolean outOfBound(int y, int x) {
		if (y < 0 || x < 0)
			return true;
		return false;
	}
}
