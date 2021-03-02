import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ16985_Maaaaaaaaaze {
	static int N, min;
	static int[][][] cube;
	static int[][][] visited;
	static int[][][][] map;
	static Queue<int[]> q;
	static final int MAX = 126;
	
	static int[] dz = {0, 0, 0, 0, 1,-1};
	static int[] dy = {1,-1, 0, 0, 0, 0};
	static int[] dx = {0, 0, 1,-1, 0, 0};
	public static void main(String[] args) throws IOException {
		// 판을 돌리며 BFS를 수행한다
		// 최악의 케이스 (5*4*3*2*1)*4(4*4*4*4)*125*6(다 뚫려있을 경우 근사치)
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// INIT
		N = 5;
		map = new int[N][4][][];
		q = new LinkedList<>();
		for (int plateNumber = 0; plateNumber < N; plateNumber++) {
			map[plateNumber][0] = new int[N][N];
			for (int y = 0; y < N; y++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int x = 0; x < N; x++) {
					map[plateNumber][0][y][x] = Integer.parseInt(st.nextToken());
				}
			}
			for (int plateStatus = 1; plateStatus < 4; plateStatus++) {
				rotate(plateNumber, plateStatus);
			}
		}
		
		// SOLVE
		cube = new int[5][][];
		visited = new int[N][N][N];
		min = MAX;
		setCube(0, 0);
		
		if (min == 126)	min = -1;
		System.out.println(min);
		
		br.close();
	}
	
	static void setCube(int level, int used) {
		if (min == 12)
			return;
		
		if (level == 5) {
			if (cube[0][0][0] == 0 || cube[4][4][4] == 0)
				return;
			
			for (int z = 0; z < visited.length; z++) 
				for (int y = 0; y < N; y++) Arrays.fill(visited[z][y], -1);
			
			findExit(); // level0도 회전하기 때문에 0,0,0에서만 시작하면 된다
			
			if (visited[4][4][4] != -1)
				min = Math.min(min, visited[4][4][4]);
			
			return;
		}
		for (int plateNumber = 0; plateNumber < N; plateNumber++) {
			int useNow = 1 << plateNumber;
			if ((used & useNow) > 0)
				continue;
			
			for (int plateStatus = 0; plateStatus < 4; plateStatus++) {
				cube[level] = map[plateNumber][plateStatus];
				setCube(level+1, used | useNow);
			}
			
		}
	}
	
	static void findExit() {
		q.clear();
		
		visited[0][0][0] = 0;
		q.add(new int[] {0, 0, 0});
		while (!q.isEmpty()) {
			int[] point = q.poll();
			int presentStep = visited[point[0]][point[1]][point[2]];
			
			if (presentStep >= min)
				continue;
			
			for (int i = 0; i < 6; i++) {
				int nZ = point[0] + dz[i];
				int nY = point[1] + dy[i];
				int nX = point[2] + dx[i];
				if (outOfBound(nZ, nY, nX))
					continue;
				if (cube[nZ][nY][nX] == 0)
					continue;
				if (visited[nZ][nY][nX] != -1)
					continue;
				visited[nZ][nY][nX] = presentStep + 1;
				
				if (nZ == 4 && nY == 4 && nX == 4)
					return;
				
				q.add(new int[] {nZ, nY, nX});
			}
		}
	}
	
	static void rotate(int plateNumber, int plateStatus) {
		map[plateNumber][plateStatus] = new int[N][N];
		// 0,0 -> 4,0 // 4,0 -> 4,4 // 4,4 -> 0,4 // 0,4 -> 0,0 // 1,2 -> (4-2),1
		for (int y = 0; y < N; y++) {
			for (int x = 0; x < N; x++) {
				map[plateNumber][plateStatus][y][x]
						= map[plateNumber][plateStatus-1][(N-1)-x][y];
			}
		}
	}
	
	static boolean outOfBound(int z, int y, int x) {
		if (z < 0 || y < 0 || x < 0 || z >= 5 || y >= 5 || x >= 5)
			return true;
		return false;
	}

}
