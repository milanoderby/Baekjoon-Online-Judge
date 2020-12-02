import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    private static int N, M;
    private static int[] operationTime;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            N = Integer.parseInt(tokenizer.nextToken());
            M = Integer.parseInt(tokenizer.nextToken());

            operationTime = new int[M];
            tokenizer = new StringTokenizer(br.readLine());
            for (int i=0; i<M; i++) {
                operationTime[i] = Integer.parseInt(tokenizer.nextToken());
            }

            long low = 0;
            long high = 60000000001L;

            if(N - M <= 0){
                System.out.println(N);
                return;
            }

            long mid = (low + high) / 2;
            while(low + 1 <high) {
                if(getNumOfChildWhoBoarded(mid + 1) < N - M) {
                    low = mid;
                } else if (getNumOfChildWhoBoarded(mid) >= N - M) {
                    high = mid;
                } else {
                    System.out.println(getAnswer(mid, N - M - getNumOfChildWhoBoarded(mid)));
                    return;
                }
                mid = (low + high) / 2;
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static long getNumOfChildWhoBoarded(long time){
        long numOfChildWhoHaveBoarded = 0;
        for (int i = 0; i < M; i++) {
            numOfChildWhoHaveBoarded += time/operationTime[i];
        }
        return numOfChildWhoHaveBoarded;
    }

    private static int getAnswer(long time, long numOfChildWhoDontBoarded) {
        List<Rides> ridesList = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            ridesList.add(new Rides(i, (int) (operationTime[i] - time % operationTime[i])));
        }
        Collections.sort(ridesList);

        return ridesList.get((int) numOfChildWhoDontBoarded - 1).num + 1;
    }

    private static class Rides implements Comparable<Rides>{
        int num;
        int restOperationTime;
        public Rides(int num, int restOperationTime){
            this.num = num;
            this.restOperationTime = restOperationTime;
        }

        @Override
        public int compareTo(Rides o) {
            if(restOperationTime == o.restOperationTime){
                return num - o.num;
            }
            return restOperationTime - o.restOperationTime;
        }
    }
}
