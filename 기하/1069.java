import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws  IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        int X = Integer.parseInt(tokenizer.nextToken());
        int Y = Integer.parseInt(tokenizer.nextToken());
        int D = Integer.parseInt(tokenizer.nextToken());
        int T = Integer.parseInt(tokenizer.nextToken());

        double distance = Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2));
        double distanceAfterJump = distance;
        double minDistance = distance;

        for (int i = 1; distanceAfterJump >= 0 ; i++) {
            distanceAfterJump = distance - i * D;
            double restDistance = Math.abs(distanceAfterJump);
            if (restDistance <= D) {
                minDistance = Math.min(minDistance, (i + 1) * T);
            }
            minDistance = Math.min(minDistance, i * T + restDistance);
        }

        bw.append(minDistance + "");
        bw.flush();
    }
}
