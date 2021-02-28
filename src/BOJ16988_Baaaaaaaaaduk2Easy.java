import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Baaaaaaaaaduk2Easy_BOJ16988 {
	static int N, M;
	static int max, maxSingle;
	static int none;
	static int[][] map;
	static Queue<int[]> q;
	static boolean[][] visited;
	static int[] dy = {0, 0, 1,-1};
	static int[] dx = {1,-1, 0, 0};
	static LinkedList<int[]> starts, blanks, tmp;
	public static void main(String[] args) throws Exception {
		// 단지번호붙이기 식으로 진행
		// bfs를 진행하고 주변 빈 칸이 1개, 2개인 그룹에 대해 바둑돌을 놔 본다(사전에 빈칸 기록)
		// 다시 한 번 bfs를 진행해서 바둑돌을 놨을 때 얻는 점수를 기록한다
		// 최대 점수를 구한다(2 or 1+1 or 1)
		
		// 그룹 사이에 있을 때 돌 하나로 두 그룹을 탐색하도록 했다
		// -> 한쪽 그룹이 열린 상태여도 카운트해버린다
		
		// -> 빈 공간이 아닌 그룹단위로 정리하자(시작점 저장)
		// -> 주변 빈 칸이 1개, 2개인 그룹의 빈 칸에서 2개를 선택하여 완전탐색한다
		
		// 너무 복잡하게 짠 것 같다.. 그래도 일단 커밋!
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// INIT
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 0) none++;
			}
		}
		
		// SOLVE
		// set start point
		q = new LinkedList<>();
		visited = new boolean[N][M];
		starts = new LinkedList<>();
		blanks = new LinkedList<>();
		tmp = new LinkedList<>();
		
		// play games
		for (int y = 0; y < N; y++) {
			for (int x = 0; x < M; x++) {
				if (map[y][x] != 2 || visited[y][x])
					continue;
				bfs('S', new int[] {y, x});
			}
		}
		for (int y = 0; y < N; y++)
			Arrays.fill(visited[y], false);
		
		dfs(0, 0);
		
		System.out.println(max);
		
		br.close();
	}
	
	static void dfs(int cnt, int idx) {
		int stone = 2;
		if (blanks.size() == 1)
			stone = 1;
		if (cnt >= stone) {
			int score = 0;
			for (int y = 0; y < N; y++)
				Arrays.fill(visited[y], false);
			for (int i = 0; i < starts.size(); i++) {
				int[] is = starts.get(i);
				score += bfs('P', is);
			}
			max = Math.max(max, score);
			return;
		}
		
		for (int i = idx; i < blanks.size(); i++) {
			int[] is = blanks.get(i);
			map[is[0]][is[1]] = 1;
			dfs(cnt+1, i+1);
			map[is[0]][is[1]] = 0;
		}
	}
	
	static int bfs(char mode, int[] start) {
		tmp.clear();
		q.clear();
		
		visited[start[0]][start[1]] = true;
		q.add(start);
		
		int black = 1;
		while (!q.isEmpty()) {
			int[] p = q.poll();
			for (int i = 0; i < 4; i++) {
				int nY = p[0] + dy[i];
				int nX = p[1] + dx[i];
				if (outOfBound(nY, nX))
					continue;
				if (visited[nY][nX])
					continue;
				
				if (map[nY][nX] == 1) {
					continue;
				}
				if (map[nY][nX] == 2) {
					if (mode == 'P') {
						black++;
					}
					q.add(new int[] {nY, nX});
				} else { // map[nY][nX] == 0
					if (mode == 'P')
						return 0;
					if (tmp.size() > 2)
						continue;
					tmp.add(new int[] {nY, nX});
				}
				visited[nY][nX] = true;
			}
		}
		if (mode == 'S') {
			int size = tmp.size();
			if (size <= 2) {
				starts.add(start);
				while (!tmp.isEmpty()) {
					blanks.add(tmp.poll()); // added for dfs
				}
			} else {
				while (!tmp.isEmpty()) {
					int[] p = tmp.poll();
					visited[p[0]][p[1]] = false; // rollback
				}
			}
		}
		
		return black;
		
	}
	
	static boolean outOfBound(int y, int x) {
		if (y < 0 || x < 0 || y >= N || x >= M)
			return true;
		return false;
	}

}
