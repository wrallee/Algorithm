import java.util.*;

class LTC200_NumberOfIslands {
	int N, M;
	boolean[][] visit;
	public int numIslands(char[][] grid) {
		N = grid.length;
		M = grid[0].length;
		visit = new boolean[N][M];

		int result = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				result += spread(grid, i, j);
			}
		}

		return result;
	}

	public int spread(char[][] grid, int i, int j) {
		if (visit[i][j] || grid[i][j] == '0') {
			return 0;
		}

		int[] dy = {1,-1, 0, 0};
		int[] dx = {0, 0, 1,-1};
		Queue<Pair> q = new LinkedList<>();
		q.offer(new Pair(i, j));

		while (!q.isEmpty()) {
			Pair p = q.poll();
			for (int k = 0; k < 4; k++) {
				int nY = p.y + dy[k];
				int nX = p.x + dx[k];
				if (nY < 0 || nX < 0 || nY >= N || nX >= M)
					continue;
				if (visit[nY][nX])
					continue;
				if (grid[nY][nX] == '0')
					continue;

				visit[nY][nX] = true;
				q.offer(new Pair(nY, nX));
			}
		}

		return 1;
	}

	static class Pair {
		int y, x;
		public Pair(int y, int x) {
			this.y = y;
			this.x = x;
		}
	}
}