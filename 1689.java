import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int N = Integer.parseInt(br.readLine());

            List<Point> pointList = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                StringTokenizer tokenizer = new StringTokenizer(br.readLine());
                int startPoint = Integer.parseInt(tokenizer.nextToken());
                int endPoint = Integer.parseInt(tokenizer.nextToken());

                pointList.add(new Point(startPoint, 0));
                pointList.add(new Point(endPoint, 1));
            }

            int answer = 0;
            int numOfLine = 0;
            pointComparator comp = new pointComparator();
            Collections.sort(pointList, comp);
            for (int i = 0; i < pointList.size(); i++) {
                Point cur = pointList.get(i);
                if (cur.isEndPoint == 0) {
                    numOfLine++;
                    if (answer < numOfLine) {
                        answer = numOfLine;
                    }
                } else {
                    numOfLine--;
                }
            }

            System.out.println(answer);
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static class Point {
        int x;
        int isEndPoint;

        public Point(int x, int isEndPoint) {
            this.x = x;
            this.isEndPoint = isEndPoint;
        }
    }

    private static class pointComparator implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            if (o1.x == o2.x) {
                return o2.isEndPoint - o1.isEndPoint;
            }
            return o1.x - o2.x;
        }
    }
}