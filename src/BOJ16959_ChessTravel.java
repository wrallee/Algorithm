import java.io.*;
import java.util.*;

public class ChessTravel_BOJ16959 {
	static int T, N, sY, sX;
	static int[] ntY = { 2, 2, -2, -2, 1, -1, 1, -1 };
	static int[] ntX = { 1, -1, 1, -1, 2, 2, -2, -2 };
	static int[] bsY = { 1, 1, -1, -1 };
	static int[] bsX = { 1, -1, 1, -1 };
	static int[] rkY = { 1, 0, -1, 0 };
	static int[] rkX = { 0, 1, 0, -1 };
	public static void main(String[] args) throws Exception {
		// 16959 체스판 여행
		// 나이트의 이동 -> +2x, +1y / +1x, +2y
		// 룩의 이동 -> +nx / +ny
		// 비숍의 이동 -> +nx, +ny
		// 각 점에 대해 각각 BFS를 펼칠 경우 처음 도달하는놈이 최소값이 된다?
		//System.setIn(new FileInputStream("TC.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// INIT
		N = Integer.parseInt(br.readLine());
		int[][] map = new int[N+1][N+1];
		int[][][] visit = new int[3][N+1][N+1];
		
		StringTokenizer st;
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 1) {
					sY = i;
					sX = j;
				}
			}
		}

		// SOLVE
		System.out.println(solve(map, visit));

		br.close();
	}
	
	static int solve(int[][] map, int[][][] visit) {
		int[][] dy = {ntY, bsY, rkY};
		int[][] dx = {ntX, bsX, rkX};
		Queue<Point> q = new LinkedList<>();
		for (int i = 0; i < 3; i++) {
			visit[i][sY][sX] = 1;
			q.add(new Point(sY, sX, i, 1, 0));
		}
		
		while (!q.isEmpty()) {
			//System.out.println(q);
			Point p = q.remove();
			if (p.lvl == N*N && map[p.y][p.x] == N*N)
				return p.time;
			// 변경안함
			int iter = p.type == 0 ? 1 : N;
			for (int i = 0; i < dy[p.type].length; i++) {
				
				for (int j = 1; j <= iter; j++) {
					int nY = p.y + j*dy[p.type][i];
					int nX = p.x + j*dx[p.type][i];
					if (outOfBound(nY, nX))
						continue;
					if (visit[p.type][nY][nX] >= p.lvl)
						continue;
					
					int nextLevel = p.lvl;
					if (map[nY][nX] == p.lvl+1)
						nextLevel = p.lvl+1;
					visit[p.type][nY][nX] = nextLevel;
					q.add(new Point(nY, nX, p.type, nextLevel, p.time+1));
				}
			}
			// 변경함
			for (int i = 1; i <= 2; i++) {
				int nextType = (p.type + i) % 3;
				if (visit[nextType][p.y][p.x] >= p.lvl)
					continue;
				visit[nextType][p.y][p.x] = p.lvl; 
				q.add(new Point(p.y, p.x, nextType, p.lvl, p.time+1));
			}
		}
		return -1;
	}
	
	static boolean outOfBound(int y, int x) {
		if (y > N || x > N || y <= 0 || x <= 0)
			return true;
		return false;
	}
	
	static class Point {
		int y, x;
		int type, lvl, time;
		public Point(int y, int x, int type, int lvl, int time) {
			this.y = y;
			this.x = x;
			this.type = type;
			this.lvl = lvl;
			this.time = time;
		}
		@Override
		public String toString() {
			return y+","+x+"|"+lvl;
		}
	}

}