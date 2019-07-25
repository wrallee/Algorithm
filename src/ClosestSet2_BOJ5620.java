import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class ClosestSet2_BOJ5620 {
	static int N;
	static ArrayList<Point> list;
	static TreeSet<Point> candidates;
	public static void main(String[] args) throws Exception {
		//System.setIn(new  FileInputStream("TC.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		list = new ArrayList<>();
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			list.add(new Point(x, y));
		}
		Collections.sort(list, (o1, o2) -> o1.x - o2.x); // Sort Asc
		candidates = new TreeSet<>((o1, o2) -> (o1.y != o2.y ? o1.y - o2.y : o1.x - o2.x));
		candidates.add(list.get(0));
		candidates.add(list.get(1));
		
		int ans = getDistance(list.get(0), list.get(1));
		int start = 0;
		for (int i = 2; i < N; i++) {
			Point now = list.get(i);
			
			// x축 기준으로 d값 세팅
			while (start < i) {
				Point pivot = list.get(start);
				int dx = pivot.x - now.x;
				if (dx*dx > ans) {
					candidates.remove(pivot);
					start++;
				} else {
					break;
				}
			}
			int d = (int) Math.ceil(Math.sqrt(ans));
			
			Point lowerPoint = new Point(now.y - d, now.y + d);
			SortedSet<Point> ss = candidates.headSet(lowerPoint);
			for (Point point : ss) {
				int dist = getDistance(now, point);
				if (ans > dist)
					ans = dist;
			}
			candidates.add(now);
		}
		
		System.out.println(ans);
		
		br.close();
	}
	
	static int getDistance(Point p1, Point p2) {
		return (int)(Math.pow(p1.x-p2.x, 2) + Math.pow(p1.y-p2.y, 2));
	}
	
	static class Point implements Comparable<Point> {
		int x, y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		@Override
		public int compareTo(Point o) {
			// 1: 기준(lowerPoint)이 더 큼
			//   -> 기준보다 작은 값 확인
			//   -> Sort Asc + headSet
			return x < o.y && o.y < y ? 1 : -1;
		}
	}
}