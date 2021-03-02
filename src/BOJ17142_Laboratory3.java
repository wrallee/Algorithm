import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class BOJ17142_Laboratory3 {
	static int N, M, min;
	static int[][] map, visit;
	static Node[] installed;
	static int[] dy = {1,-1, 0, 0};
	static int[] dx = {0, 0, 1,-1};
	static LinkedList<Node> q, install;
	static final int MAX = 2501;
	public static void main(String[] args) throws Exception {
		// BOJ17142: 연구소 3
		//System.setIn(new FileInputStream("TC.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		
		installed = new Node[M];
		q = new LinkedList<>();
		install = new LinkedList<>();
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 2)
					install.add(new Node(i, j));
			}
		}
		
		min = MAX;
		dfs(0, 0);
		if (min == MAX)
			min = -1;
		System.out.println(min);
		
		
		br.close();
	}
	
	static int check() {
		int max = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (visit[i][j] == -1 && map[i][j] == 0)
					return MAX;
				if (map[i][j] == 2)
					continue;
				max = Math.max(max, visit[i][j]);
			}
		}
		return max;
	}
	
	static void dfs(int index, int cnt) {
		if (cnt == M) {
			bfs(installed);
			min = Math.min(min, check());
			//printMap();
			return;
		}
		for (int i = index; i < install.size(); i++) {
			installed[cnt] = install.get(i);
			dfs(i+1, cnt+1);
		}
		
	}
	
	static void bfs(Node[] start) {
		visit = new int[N][N];
		for (int[] row : visit) {
			Arrays.fill(row, -1);
		}
		for (Node node : installed) {
			visit[node.y][node.x] = 0;
			q.offer(node);
		}
		
		while (!q.isEmpty()) {
			Node node = q.poll();
			int y = node.y;
			int x = node.x;
			for (int i = 0; i < 4; i++) {
				int nY = y + dy[i];
				int nX = x + dx[i];
				if (nY < 0 || nX < 0 || nY >= N || nX >= N)
					continue;
				if (map[nY][nX] == 1)
					continue;
				if (visit[nY][nX] != -1)
					continue;
				
				visit[nY][nX] = visit[y][x] + 1;
				q.add(new Node(nY, nX));
			}
		}
	}
	
	static class Node {
		int y, x;
		public Node(int y, int x) {
			this.y = y;
			this.x = x;
		}
	}
	
	static void printMap() {
		System.out.print("[");
		for (int i = 0; i < installed.length; i++) {
			Node node = installed[i];
			System.out.print(node.y + "," + node.x + " | ");
		}
		System.out.println("]");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (visit[i][j] == -1)
					System.out.print("* ");
				else
					System.out.print(visit[i][j] + " ");
			}
			System.out.println();
		}
	}

}
