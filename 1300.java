import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
	// write your code here
        Scanner scanner = new Scanner(System.in);
        long N = scanner.nextInt();
        long K = scanner.nextInt();

        if(K == 1 || K == N*N){
            System.out.println(K);
            return;
        }

        long left = 1;
        long right = N*N;

        while(left <= right){
            long mid = (left + right)/2;

            long leftCnt = 0;
            long rightCnt = 0;

            for(int i=1; i<=N; i++){
                leftCnt += Math.min((mid-1)/i, N);
                rightCnt += Math.min(mid/i, N);
            }

            if(K <= leftCnt) {
                right = mid;
            }
            else if(rightCnt < K){
                left = mid;
            }
            else{
                System.out.println(mid);
                return;
            }
        }
    }
}
