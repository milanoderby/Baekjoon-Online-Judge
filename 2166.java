import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int N;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            N = Integer.parseInt(br.readLine());

            Point[] points = new Point[N];
            for (int i = 0; i < N; i++) {
                StringTokenizer tokenizer = new StringTokenizer(br.readLine());
                points[i] = new Point();
                points[i].x = Integer.parseInt(tokenizer.nextToken());
                points[i].y = Integer.parseInt(tokenizer.nextToken());
            }

            double answer = 0;
            for (int i = 1; i < N - 1; i++) {
                answer += getDimension(points[i].x - points[0].x,
                                       points[i].y - points[0].y,
                                       points[i + 1].x - points[0].x,
                                       points[i + 1].y - points[0].y);
            }
            answer = Math.abs(answer);
            System.out.println(String.format("%.1f", answer / 10));
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static class Point {
        long x, y;
    }

    private static long getDimension(long x1, long y1, long x2, long y2) {
        return (x1*y2 - x2*y1) * 5;
    }
}