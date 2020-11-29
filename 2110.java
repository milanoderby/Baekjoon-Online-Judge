import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    private static int[] housePosition;
    private static int N;
    private static int C;

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            N = Integer.parseInt(tokenizer.nextToken());
            C = Integer.parseInt(tokenizer.nextToken());

            housePosition = new int[N];
            for(int i=0; i<N; i++){
                housePosition[i] = Integer.parseInt(br.readLine());
            }

            Arrays.sort(housePosition);

            if(C == 2){
                System.out.println(housePosition[N-1] - housePosition[0]);
                return;
            }

            int left = 1;
            int right = 1000000000;
            while(left < right){
                int mid = (left + right)/2;
                if(getRouterNum(mid) < C){
                    right = mid;
                }
                else if(getRouterNum(mid+1) >= C){
                    left = mid;
                }
                else{
                    System.out.println(mid);
                    return;
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static int getRouterNum(int K){
        int installedRouterNum = 1;
        int distance = 0;
        for(int i=0; i<N-1; i++){
            distance += housePosition[i+1] - housePosition[i];
            if (distance >= K){
                installedRouterNum++;
                distance = 0;
            }
        }
        return installedRouterNum;
    }
}
