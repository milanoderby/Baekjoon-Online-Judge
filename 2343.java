import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

public class Main {
    private static int N, M;
    private static int[] lessonLength;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            N = Integer.parseInt(tokenizer.nextToken());
            M = Integer.parseInt(tokenizer.nextToken());

            lessonLength = new int[N];
            tokenizer = new StringTokenizer(br.readLine());
            for(int i=0; i<N; i++){
                lessonLength[i] = Integer.parseInt(tokenizer.nextToken());
            }

            int maxLengthOfLesson = IntStream.of(lessonLength)
                                             .max()
                                             .getAsInt();

            int low = maxLengthOfLesson;
            int high = 1000000001;

            if(getRequiredBlurayNum(low) <= M){
                System.out.println(low);
                return;
            }

            int mid = (low + high) / 2;
            while(low + 1 < high) {
                if(getRequiredBlurayNum(mid) > M) {
                    low = mid;
                } else if(getRequiredBlurayNum(mid - 1) <= M) {
                    high = mid;
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

    private static int getRequiredBlurayNum(int blurayCapacity){
        int currentBluraySize = 0;
        int blurayNum = 0;
        for(int i=0; i<N; i++){
            if(currentBluraySize + lessonLength[i] > blurayCapacity) {
                blurayNum += 1;
                currentBluraySize = lessonLength[i];
            } else{
                currentBluraySize += lessonLength[i];
            }
        }
        if(currentBluraySize > 0) {
            blurayNum += 1;
        }
        return blurayNum;
    }
}
