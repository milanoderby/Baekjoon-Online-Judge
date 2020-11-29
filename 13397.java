import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int N, M;
    private static int[] arr;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            N = Integer.parseInt(tokenizer.nextToken());
            M = Integer.parseInt(tokenizer.nextToken());

            arr = new int[N];
            tokenizer = new StringTokenizer(br.readLine());
            for(int i=0; i<N; i++){
                arr[i] = Integer.parseInt(tokenizer.nextToken());
            }
            
            int low = 0;
            int high = 10000;

            if(getNumOfSection(low) <= M){
                System.out.println(low);
                return;
            }

            int mid = (low + high) / 2;
            while(low + 1 <high) {
                if(getNumOfSection(mid - 1) <= M) {
                    high = mid;
                } else if(getNumOfSection(mid) > M) {
                    low = mid;
                } else {
                    System.out.println(mid);
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

    private static int getNumOfSection(int maxLimitOfScore){
        int sectionNum = 0;
        int minNum = 10001;
        int maxNum = 0;
        for(int i=0; i<N; i++){
            if (arr[i] < minNum) {
                minNum = arr[i];
            }

            if (arr[i] > maxNum) {
                maxNum = arr[i];
            }

            if(maxNum - minNum > maxLimitOfScore) {
                sectionNum += 1;
                minNum = arr[i];
                maxNum = arr[i];
            }
        }
        sectionNum += 1;
        return sectionNum;
    }
}
