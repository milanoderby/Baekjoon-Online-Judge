import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int NUMBER_OF_POINT = 3;
    private static int NUMBER_OF_XY = 2;
    private static int[][] POINT;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            POINT = new int[NUMBER_OF_POINT][NUMBER_OF_XY];
            for (int i = 0; i < NUMBER_OF_POINT; i++) {
                StringTokenizer tokenizer = new StringTokenizer(br.readLine());
                POINT[i][0] = Integer.parseInt(tokenizer.nextToken());
                POINT[i][1] = Integer.parseInt(tokenizer.nextToken());
            }
            int answer = getAnswer();
            System.out.println(answer);
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static int getAnswer() {
        int S = (POINT[1][0] - POINT[0][0]) * (POINT[2][1] - POINT[0][1]) - (POINT[1][1] - POINT[0][1]) * (POINT[2][0] - POINT[0][0]);
        if (S > 0) {
            return 1;
        } else if (S < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}