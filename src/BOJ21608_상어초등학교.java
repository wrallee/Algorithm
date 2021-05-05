import static java.lang.Integer.*;

import java.io.*;
import java.util.*;

public class BOJ21608_상어초등학교 {

	private static int N;
	private static Student[] order;
	private static Student[][] map;
	private static int[] dy = {-1, 1, 0, 0};
	private static int[] dx = { 0, 0, 1,-1};

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = parseInt(br.readLine());
		map = new Student[N][N];
		order = new Student[N * N];

		String[] in;
		for (int i = 0; i < N * N; i++) {
			in = br.readLine().split(" ");
			Set<Integer> like = new HashSet<>();
			like.add(parseInt(in[1]));
			like.add(parseInt(in[2]));
			like.add(parseInt(in[3]));
			like.add(parseInt(in[4]));
			order[i] = new Student(parseInt(in[0]));
			order[i].like = like;
		}

		System.out.println(getBestSeat());

		br.close();
	}

	private static int getBestSeat() {
		for (Student student : order) {
			student.point = getFirstClass(student);
			map[student.point.y][student.point.x] = student;
		}

		int satisfied = 0;
		for (Student student : order) {
			satisfied += getSatisfactory(student);
		}

		return satisfied;
	}

	private static Point getFirstClass(Student student) {
		ArrayList<Point> list = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == null) {
					list.add(getPoint(student, i, j));
				}
			}
		}
		Collections.sort(list);
		return list.get(0);
	}

	private static Point getPoint(Student student, int i, int j) {
		Point point = new Point(i, j);
		for (int k = 0; k < 4; k++) {
			int nY = i + dy[k];
			int nX = j + dx[k];
			if (nY < 0 || nX < 0 || nY >= N || nX >= N)
				continue;
			if (map[nY][nX] == null) {
				point.blank++;
				continue;
			}
			if (student.like.contains(map[nY][nX].seq)) {
				point.like++;
				continue;
			}
		}
		return point;
	}

	private static int getSatisfactory(Student student) {
		int like = getPoint(student, student.point.y, student.point.x).like;
		// System.out.println(like + " ||| " + student);
		return like == 0 ? 0 : (int)Math.pow(10, like - 1);
	}

	static class Student {
		int seq;
		Point point;
		Set<Integer> like;
		public Student(int seq) {
			this.seq = seq;
		}

		@Override
		public String toString() {
			return "Student{" +
				"seq=" + seq +
				", point=" + point +
				", like=" + like +
				'}';
		}
	}

	static class Point implements Comparable<Point> {
		int y;
		int x;
		int like;
		int blank;

		public Point(int y, int x) {
			this.y = y;
			this.x = x;
		}

		@Override
		public String toString() {
			return "Point{" +
				"y=" + y +
				", x=" + x +
				", like=" + like +
				", blank=" + blank +
				'}';
		}

		@Override
		public int compareTo(Point point) {
			if (this.like == point.like) {
				if (this.blank == point.blank) {
					if (this.y == point.y) {
						return Integer.compare(this.x, point.x);
					}
					return Integer.compare(this.y, point.y);
				}
				return (-1) * Integer.compare(this.blank, point.blank);
			}
			return (-1) * Integer.compare(this.like, point.like);
		}
	}
}
