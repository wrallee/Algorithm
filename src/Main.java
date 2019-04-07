import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int N, M;
	static int max, maxSingle;
	static int none;
	static int[][] map;
	static Queue<int[]> q;
	static boolean[][] visited;
	static int[] dy = {0, 0, 1,-1};
	static int[] dx = {1,-1, 0, 0};
	static List<LinkedList<int[]>> starts;
	public static void main(String[] args) throws Exception {
		// 단지번호붙이기 식으로 진행
		// bfs를 진행하고 주변 빈 칸이 1개, 2개인 그룹에 대해 바둑돌을 놔 본다(사전에 빈칸 기록)
		// 다시 한 번 bfs를 진행해서 바둑돌을 놨을 때 얻는 점수를 기록한다
		// 최대 점수를 구한다(2 or 1+1 or 1)
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
		
		if (none == 2) {
			;
		} else {
			for (int y = 0; y < N; y++) {
				for (int x = 0; x < M; x++) {
					if (map[y][x] != 2 || visited[y][x])
						continue;
					visited[y][x] = true;
					q.add(new int[] {y, x});
					bfsBody('S', new LinkedList<>());
				}
			}
			
			for (int y = 0; y < N; y++)
				Arrays.fill(visited[y], false);
			
			// play games
			for (LinkedList<int[]> start : starts) {
				int size = start.size();
				
				for (int[] is : start) {
					System.out.print(is[0] + " " + is[1] + ":");
				}
				// TODO 그룹 사이에 있을 때 돌 하나로 두 그룹을 탐색하도록 했다
				// TODO -> 한쪽 그룹이 열린 상태여도 카운트해버린다
				int cnt = bfsBody('P', start);
				if (size == 1) { // 1+1 케이스를 고려한다
					max = Math.max(max, cnt + maxSingle);
					maxSingle = Math.max(maxSingle, cnt);
				} else {
					max = Math.max(max, cnt);
				}
				System.out.println(cnt + "|");
			}
		}
		
		System.out.println(max);
		
		br.close();
	}
	
	static int bfsBody(char mode, LinkedList<int[]> start) {
		if (mode == 'P') {
			for (int[] is : start) {
				visited[is[0]][is[1]] = true;
			}
			q = start;
		}
		int black = 0;
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
					black ++;
					q.add(new int[] {nY, nX});
				} else { // map[nY][nX] == 0
					if (mode == 'P')
						continue;
					if (start.size() > 2)
						continue;
					start.add(new int[] {nY, nX});
				}
				visited[nY][nX] = true;
			}
		}
		
		if (mode == 'S') {
			// 바둑돌을 놓을 위치가 다른그룹과 겹칠 수 있으므로 플래그 초기화
			for (int[] is : start) {
				visited[is[0]][is[1]] = false;
			}
			int size = start.size();
			if (size <= 2)
				starts.add(start);
		}
		
		return black;
		
	}
	
	static boolean outOfBound(int y, int x) {
		if (y < 0 || x < 0 || y >= N || x >= M)
			return true;
		return false;
	}

}
