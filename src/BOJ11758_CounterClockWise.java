import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ11758_CounterClockWise {
	static Line[] lines;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		lines = new Line[2];
		String[] in = br.readLine().split(" ");
		Line std = new Line(Integer.parseInt(in[0])
							, Integer.parseInt(in[1]));
		for (int i = 0; i < 2; i++) {
			in = br.readLine().split(" ");
			lines[i] = new Line(Integer.parseInt(in[0])
								, Integer.parseInt(in[1]));
			lines[i].sub(std);
		}
		
		System.out.println(ccw(lines[0], lines[1]));
		
		br.close();
	}
	
	static int ccw(Line l1, Line l2) {
		int res = l1.x*l2.y - l2.x*l1.y;
		if (res > 0)
			return 1;
		else if (res < 0)
			return -1;
		else
			return 0;
	}
	
	static class Line {
		int x, y;
		public Line(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public void sub(Line p) {
			this.x -= p.x;
			this.y -= p.y;
		}
	}

}
