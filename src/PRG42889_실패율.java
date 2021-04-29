import java.util.Arrays;

class PRG42889_실패율 {
	static class StageInfo {
		int index;
		int stay;
		int pass;

		public StageInfo(int index) {
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
		StageInfo[] stageInfos = new StageInfo[N+2];
		Arrays.setAll(stageInfos, StageInfo::new);

		for (int stoppedStage : stages) {
			stageInfos[stoppedStage].stay++;
		}

		for (int i = N + 1; i > 0; i--) {
			stageInfos[i-1].pass = stageInfos[i].pass + stageInfos[i].stay;
		}

		stageInfos = Arrays.copyOfRange(stageInfos, 1, N + 1); // 양 끝 자리 자르기

		return Arrays.stream(stageInfos)
			.sorted((p1, p2) -> {
				int compareResult = Double.compare(p2.getFailRate(), p1.getFailRate());
				return compareResult != 0 ? compareResult : Integer.compare(p1.index, p2.index);
			})
			.mapToInt(stageInfo -> stageInfo.index)
			.toArray();
	}
}
