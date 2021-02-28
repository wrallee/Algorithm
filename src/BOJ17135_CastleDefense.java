import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class CastleDefense_BOJ17135 {
	static int N, M, D, max;
	static int[][] map;
	static int[] archer;
	static boolean[][] visit, flag;
	static Queue<Node> q, attackPoint;
	static int[] dy = { 0,-1, 0};
	static int[] dx = {-1, 0, 1};
	public static void main(String[] args) throws Exception {
		// BOJ17135: 캐슬 디펜스
		// 각 칸에 포함된 적의 수는 최대 하나
		// 맵의 마지막엔 한줄에 성이 배치되어있다
		// 성에 궁수 3명을 배치한다
		// 성 하나에 궁수 하나만 배치 가능
		// TURN1
		// 하나의 궁수는 적 하나만 공격, 한턴에 일제 공격
		// 거리가 D 이하인 적 중 가장 가까운 적을 공격
		// 여럿일 경우 가장 왼쪽의 적을 공격
		// TURN2
		// 적이 한칸 아래로 이동한다
		// 맵에서 나가면 그냥 사라진다
		// 궁수를 적절하게 배치해서 가장 많은 적을 죽여라
		// 거리는 맨해튼 거리로 계산
		
		// 같은 적이 여러 궁수한테 공격당할 수 있다
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		q = new LinkedList<>();
		archer = new int[3];
		attackPoint = new LinkedList<>();
		dfs(0, 0);
		System.out.println(max);
		
		br.close();
	}
	// 궁수 배치
	static void dfs(int idx, int cnt) {
		if (cnt == 3) {
			int score = 0;
			flag = new boolean[N][M];
			for (int i = 0; i < N; i++) {
				attack(archer[0], i);
				attack(archer[1], i);
				attack(archer[2], i);
				while (!attackPoint.isEmpty()) {
					Node node = attackPoint.poll();
					int y = node.y;
					int x = node.x;
					if (flag[y][x])
						continue;
					score++;
					flag[y][x] = true;
				}
			}
			max = Math.max(max, score);
			return;
		}
		
		for (int i = idx; i < M; i++) {
			archer[cnt] = i;
			dfs(i+1, cnt+1);
		}
	}
	
	// 궁수 공격 BFS
	static void attack(int archer, int phase) {
		visit = new boolean[N][M];
		visit[N-1-phase][archer] = true;
		q.add(new Node(N-1-phase, archer));
		while (!q.isEmpty()) {
			Node node = q.poll();
			int y = node.y;
			int x = node.x;
			if (map[y][x] == 1 && !flag[y][x]) {
				attackPoint.offer(node);
				q.clear();
				return;
			}
			for (int i = 0; i < 3; i++) {
				int nY = y + dy[i];
				int nX = x + dx[i];
				if (nY < 0 || nX < 0 || nY >= N || nX >= M)
					continue;
				if (visit[nY][nX])
					continue;
				int distance = getDistance(archer, nY, nX);
				if (distance > D+phase)
					continue;
				visit[nY][nX] = true;
				q.offer(new Node(nY, nX));
			}
		}
	}
	
	static int getDistance(int archer, int nY, int nX) {
		return Math.abs(archer - nX) + (N - nY);
	}
	
	static class Node {
		int y, x;
		public Node(int y, int x) {
			this.y = y;
			this.x = x;
		}
	}

}
