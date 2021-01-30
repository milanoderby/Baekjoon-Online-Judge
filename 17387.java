import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            long x1, y1, x2, y2, x3, y3, x4, y4;

            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            x1 = Long.parseLong(tokenizer.nextToken());
            y1 = Long.parseLong(tokenizer.nextToken());
            x2 = Long.parseLong(tokenizer.nextToken());
            y2 = Long.parseLong(tokenizer.nextToken());

            tokenizer = new StringTokenizer(br.readLine());
            x3 = Long.parseLong(tokenizer.nextToken());
            y3 = Long.parseLong(tokenizer.nextToken());
            x4 = Long.parseLong(tokenizer.nextToken());
            y4 = Long.parseLong(tokenizer.nextToken());

            if ((ccw(x1, y1, x2, y2, x3, y3) == 0)
                && (ccw(x1, y1, x2, y2, x4, y4) == 0)
                && (ccw(x3, y3, x4, y4, x1, y1) == 0)
                && (ccw(x3, y3, x4, y4, x2, y2) == 0)) {

                if(x1 == x2) {
                    long minYBetweenAandB = Math.min(y1, y2);
                    long maxYBetweenAandB = Math.max(y1, y2);

                    long minYBetweenCandD = Math.min(y3, y4);
                    long maxYBetweenCandD = Math.max(y3, y4);
                    if ((maxYBetweenCandD < minYBetweenAandB) || (maxYBetweenAandB < minYBetweenCandD)){
                        System.out.println(0);
                    } else {
                        System.out.println(1);
                    }
                    return;
                }

                long minXBetweenAandB = Math.min(x1, x2);
                long maxXBetweenAandB = Math.max(x1, x2);

                long minXBetweenCandD = Math.min(x3, x4);
                long maxXBetweenCandD = Math.max(x3, x4);
                if ((maxXBetweenCandD < minXBetweenAandB) || (maxXBetweenAandB < minXBetweenCandD)){
                    System.out.println(0);
                } else {
                    System.out.println(1);
                }
                return;
            }

            if ((ccw(x1, y1, x2, y2, x3, y3) * ccw(x1, y1, x2, y2, x4, y4) <= 0)
                && (ccw(x3, y3, x4, y4, x1, y1) * ccw(x3, y3, x4, y4, x2, y2) <= 0)) {
                System.out.println(1);
            } else {
                System.out.println(0);
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static int ccw(long x1, long y1, long x2, long y2, long x3, long y3) {
        long ccw = (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1);

        if (ccw > 0)
            return 1;

        if (ccw < 0)
            return -1;

        return 0;
    }
}