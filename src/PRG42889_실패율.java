import java.util.Arrays;

class PRG42889_실패율 {
	static class Pair {
		int index;
		int stay;
		int pass;
		public Pair(int index) {
			this.index = index;
		}
		public double getFailRate() {
			if (stay == 0 && pass == 0) {
				return 0.0;
			}
			return (double)stay / pass;
		}
	}
	public int[] solution(int N, int[] stages) {
		Pair[] pairs = new Pair[N+1];
		Arrays.setAll(pairs, Pair::new);

		for (int stopPoint : stages) {
			pairs[stopPoint - 1].stay += 1;
		}

		for (int i = N; i > 0; i--) {
			pairs[i-1].pass = pairs[i].pass + pairs[i].stay;
		}

		pairs = Arrays.copyOfRange(pairs, 0, N);

		return Arrays.stream(pairs)
			.sorted((p1, p2) -> {
				int compareResult = Double.compare(p2.getFailRate(), p1.getFailRate());
				return compareResult != 0 ? compareResult : Integer.compare(p1.index, p2.index);
			})
			.mapToInt(pair -> pair.index + 1)
			.toArray();
	}
}