public class PRG1845_Phonekemon {
    public static void main(String[] args) {
        PRG1845_Phonekemon s = new PRG1845_Phonekemon();
        int[] nums = {3,1,2,3};

        System.out.println(s.solution(nums));
    }
    public int solution(int[] nums) {
        boolean[] exists = new boolean[200001];

        int cnt = 0;
        for (int i = 0; i < nums.length; i++) {
            if (exists[nums[i]])
                continue;
            cnt++;
            exists[nums[i]] = true;
        }

        return Math.min(cnt, nums.length/2);
    }
}
