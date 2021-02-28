import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PolygonArea_BOJ2166 {
	static int N;
	static Line[] lines;
	static double area;
	static final int MAX = 100000;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// INIT
		N = Integer.parseInt(br.readLine());
		lines = new Line[N];
		StringTokenizer st;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			long x = Long.parseLong(st.nextToken());
			long y = Long.parseLong(st.nextToken());
			lines[i] = new Line(y, x);
		}
		
		for (int i = 1; i < N; i++) {
			lines[i].y -= lines[0].y;
			lines[i].x -= lines[0].x;
		}
		
		area = 0.0;
		for (int i = 1; i < N-1; i++) {
			area += ccw(lines[i], lines[i+1]) / 2.0;
			
		}
		System.out.printf("%.1f\n", Math.abs(area));
		
		br.close();
	}
	
	static long ccw(Line line1, Line line2) {
		return line1.x * line2.y - line2.x * line1.y;
	}
	
	static class Line {
		long y, x;
		public Line(long y, long x) {
			super();
			this.y = y;
			this.x = x;
		}
		@Override
		public String toString() {
			return x + " " + y;
		}
	}
}
