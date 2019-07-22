import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Turret_BOJ1002 {
	static int T, d, r1, r2;
	static int x1, y1, x2, y2;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for (int t = 0; t < T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			x1 = Integer.parseInt(st.nextToken());
			y1 = Integer.parseInt(st.nextToken());
			r1 = Integer.parseInt(st.nextToken());
			x2 = Integer.parseInt(st.nextToken());
			y2 = Integer.parseInt(st.nextToken());
			r2 = Integer.parseInt(st.nextToken());
			
			sb.append(solve()).append("\n");
		}
		System.out.print(sb.toString());
		
		br.close();
	}
	
	static int solve() {
		d = (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2);
		int result = 0;
		int add = (r1+r2)*(r1+r2);
		int sub = (r1-r2)*(r1-r2);
		if (x1 == x2 && y1 == y2 && r1 == r2)
			result = -1;
		else if (add == d || sub == d)
			result = 1;
		else if (add < d || d < sub)
			result = 0;
		else
			result = 2;
		
		return result;
	}

}
