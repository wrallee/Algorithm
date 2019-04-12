import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BreakingWalls4_BOJ16946 {
	static int N, M;
	static char[][] map;
	static int[][] visit;
	static int groupNumber;
	
	static int[] dy = {1,-1, 0, 0};
	static int[] dx = {0, 0, 1,-1};
	static int[] group;
	static LinkedList<Integer> groupAdded;
	public static void main(String[] args) throws Exception {
		// BOJ16946: 벽 부수고 이동하기 4
		// 1이 나올때마다 부수고 네방향을 보려했으나..
		// 1이 1000개일 때 어림잡아서 1000*N*M = 10초 이상 걸릴 것 같다
		
		// 구역을 단 한번만 탐색하자
		// 단지번호를 붙이고 사이즈를 따로 정리한다
		// 1이 나오면 네방향만 탐색하고 미리 구한 사이즈를 더한다
		// 이미 더한 단지라면 더하지 않는다
		//System.setIn(new FileInputStream("TC.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		//long stt = System.currentTimeMillis();
		
		// INIT
		String[] in = br.readLine().split(" ");
		N = Integer.parseInt(in[0]);
		M = Integer.parseInt(in[1]);
		map = new char[N][M];
		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
		}
		visit = new int[N][M];
		group = new int[1000000];
		groupAdded = new LinkedList<>();
		
		// SOLVE
		groupNumber = 0;
		Queue<int[]> q = new LinkedList<>();
		for (int i = 0; i < N; i++)
			Arrays.fill(visit[i], -1);
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == '1')
					continue;
				if (visit[i][j] != -1)
					continue;
				visit[i][j] = groupNumber;
				q.add(new int[] {i, j});
				int size = bfs(q);
				
				group[groupNumber++] = size;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == '0') {
					sb.append(map[i][j]);
				} else {
					int space = getSpace(i, j);
					sb.append(space);
				}
			}
			sb.append("\n");
		}
		//sb.append("종료:"+(System.currentTimeMillis() - stt)/1000.0);
		System.out.print(sb.toString());
		
		br.close();
	}
	
	static int getSpace(int y, int x) {
		groupAdded.clear();
		int space = 1;
		for (int k = 0; k < 4; k++) {
			int nY = y + dy[k];
			int nX = x + dx[k];
			if (outOfBound(nY, nX))
				continue;
			if (visit[nY][nX] == -1)
				continue;
			int idx = visit[nY][nX];
			if (groupAdded.contains(idx))
				continue;
			groupAdded.add(idx);
			space += group[idx];
		}
		return space % 10;
	}
	
	static int bfs(Queue<int[]> q) {
		int size = 0;
		while (!q.isEmpty()) {
			size++;
			
			int[] p = q.poll();
			int y = p[0];
			int x = p[1];
			for (int i = 0; i < 4; i++) {
				int nY = y + dy[i];
				int nX = x + dx[i];
				if (outOfBound(nY, nX))
					continue;
				if (visit[nY][nX] != -1)
					continue;
				if (map[nY][nX] == '1')
					continue;
				
				visit[nY][nX] = groupNumber;
				q.offer(new int[] {nY, nX});
			}
		}
		return size;
	}
	
	static boolean outOfBound(int y, int x) {
		if (y < 0 || x < 0 || y >= N || x >= M)
			return true;
		return false;
	}

}
