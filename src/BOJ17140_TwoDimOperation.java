import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class TwoDimOperation_BOJ17140 {
	static int r, c, k;
	static int xSize, ySize;
	static int[][] map;
	static int[] memSet;
	static PriorityQueue<Pair> pq;
	public static void main(String[] args) throws Exception {
		// BOJ17140 이차원 배열과 연산
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken()) - 1;
		c = Integer.parseInt(st.nextToken()) - 1;
		k = Integer.parseInt(st.nextToken());
		memSet = new int[101];
		map = new int[100][100];
		for (int i = 0; i < 3; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 3; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		ySize = xSize = 3;
		pq = new PriorityQueue<>();
		int hour = 0;
		while (map[r][c] != k && hour <= 100) {
			if (ySize >= xSize) {
				setRows();
			} else {
				setCols();
			}
			//printMap();
			//Thread.sleep(500);
			hour++;
			//if (hour == 101 && map[r][c] != k) break;
		}
		if (hour == 101)
			hour = -1;
		System.out.println(hour);
		
		br.close();
	}
	
	static void setRows() {
		int xSizeTmp = xSize;
		for (int r = 0; r < ySize; r++) {
			for (int c = 0; c < xSize; c++) {
				if (map[r][c] == 0)
					continue;
				memSet[map[r][c]]++;
				map[r][c] = 0;
			}
			for (int i = 0; i < memSet.length; i++) {
				if (memSet[i] == 0)
					continue;
				pq.offer(new Pair(i, memSet[i]));
				memSet[i] = 0;
			}
			int cnt = 0;
			while (!pq.isEmpty()) {
				Pair pair = pq.poll();
				map[r][cnt] = pair.num;
				map[r][cnt+1] = pair.cnt;
				cnt += 2;
				if (cnt >= 100) {
					pq.clear();
					break;
				}
			}
			xSizeTmp = Math.max(xSizeTmp, cnt);
		}
		xSize = Math.max(xSize, xSizeTmp);
	}

	static void setCols() {
		int ySizeTmp = ySize;
		for (int c = 0; c < xSize; c++) {
			for (int r = 0; r < ySize; r++) {
				if (map[r][c] == 0)
					continue;
				memSet[map[r][c]]++;
				map[r][c] = 0;
			}
			for (int i = 0; i < memSet.length; i++) {
				if (memSet[i] == 0)
					continue;
				pq.offer(new Pair(i, memSet[i]));
				memSet[i] = 0;
			}
			int cnt = 0;
			while (!pq.isEmpty()) {
				Pair pair = pq.poll();
				map[cnt][c] = pair.num;
				map[cnt+1][c] = pair.cnt;
				cnt += 2;
				if (cnt >= 100) {
					pq.clear();
					break;
				}
			}
			ySizeTmp = Math.max(ySizeTmp, cnt);
		}
		ySize = Math.max(ySize, ySizeTmp);
	}
	
	static void printMap() {
		System.out.println(ySize + " " + xSize);
		for (int i = 0; i < ySize; i++) {
			for (int j = 0; j < xSize; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	static class Pair implements Comparable<Pair> {
		int num, cnt;
		public Pair(int num, int cnt) {
			this.num = num;
			this.cnt = cnt;
		}
		@Override
		public int compareTo(Pair o) {
			if (this.cnt == o.cnt)
				return this.num - o.num;
			else
				return this.cnt - o.cnt;
		}
	}
}
