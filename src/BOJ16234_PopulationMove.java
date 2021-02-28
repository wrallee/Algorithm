import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PopulationMove_BOJ16234 {
	static int N, L, R;
	static int[][] group;
	static int groupSum;
	static int groupCnt;
	
	static int[][] map;
	static boolean[][] visited;
	static int[] dy = {1,-1, 0, 0};
	static int[] dx = {0, 0, 1,-1};
	public static void main(String[] args) throws Exception {
		// BOJ 16234 인구 이동
		// 아래 조건에 의한 이동이 더 없을때까지 진행한다
		// 1. 국경선을 공유하는 두 나라의 인구 차이가 L명이상 R명이하라면 두 나라의 공유하는 국경선을 하루동안 연다
		// 2. 위 조건의 국경선이 모두 열리면 인구이동 시작!
		// 3. 국경선이 열려있는 나라들은 하루동안 연합이라고 한다.
		// 4. 연합 칸의 인구수는 연합인구수 / 칸의갯수로 평준화된다. 소수점은 버린다.
		// 5. 연합 해체 및 국경선 폐쇄
		// 각 나라의 인구수가 주어졌을 때 인구이동이 몇 번 발생하는지 구하라
		
		// 단지번호붙이기 식으로 가자
		//System.setIn(new FileInputStream("TC.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		
		// INIT
		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		group = new int[2500][];
		
		// SOLVE
		int moveCnt = 0;
		while (true) {
			groupCnt = 0;
			visited = new boolean[N][N];
			boolean isMoved = false;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (visited[i][j]) continue;
					
					bfs(i, j);
					if (groupCnt == 1) continue;
					
					for (int k = 0; k < groupCnt; k++) {
						int[] pair = group[k];
						map[pair[0]][pair[1]] = groupSum / groupCnt;
					}
					isMoved = true;
				}
			}
			if (isMoved)
				moveCnt++;
			else
				break;
		}
		System.out.println(moveCnt);
		
		br.close();
	}
	
	static void bfs(int y, int x) {
		visited[y][x] = true;
		int sum = 0;
		int length = 1;
		
		insertIntoGroups(0, y, x);
		
		sum += map[y][x];
		for (int i = 0; i < length; i++) {
			int[] pair = group[i];
			for (int j = 0; j < 4; j++) {
				int nY = pair[0] + dy[j];
				int nX = pair[1] + dx[j];
				if (nY >= N || nX >= N || nY < 0 || nX < 0)
					continue;
				if (visited[nY][nX])
					continue;
				
				int std = Math.abs(map[pair[0]][pair[1]] - map[nY][nX]);
				if (L <= std && std <= R) {
					insertIntoGroups(length, nY, nX);
					visited[nY][nX] = true;
					sum += map[nY][nX];
					length++;
				}
			}
		}
		
		groupCnt = length;
		groupSum = sum;
	}
	
	static void insertIntoGroups(int length, int y, int x) {
		if (group[length] == null) {
			group[length] = new int[] {y, x};
		} else {
			group[length][0] = y;
			group[length][1] = x;
		}
	}
	
}