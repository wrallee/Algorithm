import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class BOJ2261_ClosestSet {
	static int N, size;
	static Point[] list;
	public static void main(String[] args) throws Exception {
		// System.setIn(new FileInputStream("TC.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		list = new Point[N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			list[i] = new Point(x, y);
		}
		Arrays.sort(list, (o1, o2) -> o1.x - o2.x); // Sort Asc

		System.out.println(closestSet(0, N - 1));
		// System.out.println(System.currentTimeMillis() - stt);

		br.close();
	}

	static int closestSet(int stt, int end) {
		if (end == stt)
			return 987654321;

		int mid = (stt + end) / 2;
		int left = closestSet(stt, mid);
		int right = closestSet(mid + 1, end);
		int min = Math.min(left, right);

		ArrayList<Point> l = new ArrayList<>();

		for (int i = stt; i <= end; i++) {
			int dx = list[i].x - list[mid].x;
			if (dx * dx < min)
				l.add(list[i]);
		}

		Collections.sort(l);

		for (int i = 0; i < l.size() - 1; i++) {
			Point prev = l.get(i);
			for (int j = i + 1; j < l.size(); j++) {
				Point next = l.get(j);
				int dy = prev.y - next.y;
				if (dy * dy > min)
					break;

				min = Math.min(min, getDistance(prev, next));
			}
		}

		return min;
	}

	static int getDistance(Point p1, Point p2) {
		return (int) (Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
	}

	static class Point implements Comparable<Point> {
		int x, y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Point o) {
			return this.y - o.y;
		}
	}
}