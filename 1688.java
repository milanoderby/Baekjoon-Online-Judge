import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int N = Integer.parseInt(br.readLine());

            Point[] defensePoints = new Point[N];
            for (int i = 0; i < N; i++) {
                StringTokenizer tokenizer = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(tokenizer.nextToken());
                int y = Integer.parseInt(tokenizer.nextToken());
                defensePoints[i] = new Point(x, y);
            }

            for (int i = 0; i < 3; i++) {
                StringTokenizer tokenizer = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(tokenizer.nextToken());
                int y = Integer.parseInt(tokenizer.nextToken());

                Point friendPoint = new Point(x, y);
                Point externalPoint = new Point(x + 1000000000, y + 1);

                int cnt = 0;
                for (int j = 1; j <= N; j++) {
                    if (isLineCrossed(friendPoint, externalPoint, defensePoints[j - 1], defensePoints[j % N])) {
                        cnt++;
                    }

                    if (friendPoint.isOnTheLine(defensePoints[j - 1], defensePoints[j % N])) {
                        cnt = 1;
                        break;
                    }
                }

                System.out.println(cnt % 2);
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static class Point {
        long x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean isOnTheLine(Point a, Point b) {
            if (isOnTheStraight(a, b, this)) {
                if (a.x == b.x) {
                    return Math.min(a.y, b.y) <= this.y && this.y <= Math.max(a.y, b.y);
                }
                return Math.min(a.x, b.x) <= this.x && this.x <= Math.max(a.x, b.x);
            }
            return false;
        }
    }

    private static int ccw(Point a, Point b, Point c) {
        long ccw = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);

        if (ccw > 0)
            return 1;

        if (ccw < 0)
            return -1;

        return 0;
    }

    private static boolean isOnTheStraight(Point a, Point b, Point c) {
        return (ccw(a, b, c) == 0);
    }

    private static boolean isLineCrossed(Point a, Point b, Point c, Point d) {
        if ((ccw(a, b, c) * ccw(a, b, d) <= 0)
            && (ccw(c, d, a) * ccw(c, d, b) <= 0)) {
            return true;
        } else {
            return false;
        }
    }
}