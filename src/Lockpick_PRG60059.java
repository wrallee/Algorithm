class Lockpick_PRG60059 {
	int N, M;
	int[][] key, lock;
    public boolean solution(int[][] key, int[][] lock) {
    	// programmers.co.kr/learn/courses/30/lessons/60059
    	// ÀÚ¹°¼è¿Í ¿­¼è
        M = key.length;
        N = lock.length;
        this.key = key;
        this.lock = lock;
        for (int r = 0; r < 4; r++) {
	        for (int dy = -(M-1); dy <= N-1; dy++) {
				for (int dx = -(M-1); dx <= N-1; dx++) {
					if (sol(dy, dx))
						return true;
				}
			}
	        rotateKey();
		}
        return false;
    }
    
    boolean sol(int dy, int dx) {
    	for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				int state = lock[i][j];
				if (!outOfBound(i-dy, j-dx, M))
					state += key[i-dy][j-dx];
				if (state != 1)
					return false;
			}
		}
    	return true;
    }
    
    boolean outOfBound(int i, int j, int size) {
    	if (i < 0 || j < 0 || i >= size || j >= size)
    		return true;
    	return false;
    }
    
    void rotateKey() {
    	int[][] tmp = new int[M][M];
    	for (int i = 0; i < M; i++) {
			for (int j = 0; j < M; j++) {
				tmp[i][j] = key[j][M-i-1];
			}
		}
    	key = tmp;
    }
    
    public static void main(String[] args) {
    	Lockpick_PRG60059 s = new Lockpick_PRG60059();
    	int[][] key = {{0,0,0},{1,0,0},{0,1,1}};
    	int[][] lock = {{1,1,1},{1,1,0},{1,0,1}};
    	System.out.println(s.solution(key, lock));
	}
}