import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;

public class FindMatrix_SEA1258 {
	static int testcase, n, subMatrixCount;
	static int[][] map;
	//static Box[] boxes;
	static HashMap<Integer, Box> boxes;
	public static void main(String[] args) throws Exception {
		// SWEA 1258 행렬찾기
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = null;
		
		testcase = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= testcase; tc++) {
			
			// INIT
			n = Integer.parseInt(br.readLine());
			map = new int[n][n];
			boxes = new HashMap<>();
					//new int[n+1][2];
			for (int i = 0; i < n; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < n; j++) {
					int element = Integer.parseInt(st.nextToken());
					if (element == 0)
						map[i][j] = 0;
					else
						map[i][j] = -1;
				}
			}
			
			// SOLVE
			for (int y = 0; y < n; y++) {
				for (int x = 0; x < n; x++) {
					if (map[y][x] != -1)
						continue;
					
					if (x - 1 < 0) // WIDTH
						map[y][x] = 1;
					else
						map[y][x] = map[y][x-1] + 1;
					
					if (x + 1 >= n || map[y][x+1] == 0) { // HEIGHT
						int width = map[y][x];
						if (!boxes.containsKey(width))
							boxes.put(width, new Box(width, 1));
						else
							boxes.get(width).height++;
					}
				}
			}
			ArrayList<Box> list = new ArrayList<>();
			for (int i : boxes.keySet()) {
				list.add(boxes.get(i));
			}
			Collections.sort(list);
			
			sb.append("#").append(tc).append(" ");
			sb.append(list.size()).append(" ");
			for (Box box : list) {
				sb.append(box.height).append(" ");
				sb.append(box.width).append(" ");
			}
			sb.append("\n");
			
		}
		
		System.out.println(sb.toString());
		br.close();
	}
	
	static class Box implements Comparable<Box> {
		int width;
		int height;
		public Box(int width, int height) {
			this.width = width;
			this.height = height;
		}
		public int getSize() {
			return this.width * this.height;
		}
		
		@Override
		public int compareTo(Box o) {
			if (this.getSize() != o.getSize())
				return this.getSize() - o.getSize();
			else
				return this.height - o.height;
		}
	}

}
