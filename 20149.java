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

            if (isLineCrossed(x1, y1, x2, y2, x3, y3, x4, y4)) {
                System.out.println(1);
                if (isOnOneStraightLine(x1, y1, x2, y2, x3, y3, x4, y4)) {
                    if(x1 == x2) {
                        long minYBetweenAandB = Math.min(y1, y2);
                        long maxYBetweenAandB = Math.max(y1, y2);

                        long minYBetweenCandD = Math.min(y3, y4);
                        long maxYBetweenCandD = Math.max(y3, y4);

                        if (maxYBetweenCandD == minYBetweenAandB) {
                            System.out.print(x1 + " ");
                            System.out.println(maxYBetweenCandD);
                            return;
                        }

                        if (maxYBetweenAandB == minYBetweenCandD) {
                            System.out.print(x1 + " ");
                            System.out.println(maxYBetweenAandB);
                            return;
                        }
                    }

                    long minXBetweenAandB = Math.min(x1, x2);
                    long maxXBetweenAandB = Math.max(x1, x2);

                    long minXBetweenCandD = Math.min(x3, x4);
                    long maxXBetweenCandD = Math.max(x3, x4);

                    if (maxXBetweenCandD == minXBetweenAandB) {
                        System.out.print(minXBetweenAandB + " ");
                        if (x1 < x2) {
                            System.out.println(y1);
                        } else {
                            System.out.println(y2);
                        }
                        return;
                    }

                    if (maxXBetweenAandB == minXBetweenCandD) {
                        System.out.print(maxXBetweenAandB + " ");
                        if (x1 > x2) {
                            System.out.println(y1);
                        } else {
                            System.out.println(y2);
                        }
                        return;
                    }
                    return;
                }
                printIntersectionPoint(x1, y1, x2, y2, x3, y3, x4, y4);
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

    private static int ccwForDouble(long x1, long y1, long x2, long y2, double x3, double y3) {
        double ccw = (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1);

        if (ccw > 0)
            return 1;

        if (ccw < 0)
            return -1;

        return 0;
    }

    private static boolean isOnOneStraightLine(long x1, long y1, long x2, long y2, long x3, long y3, long x4, long y4) {
        return (ccw(x1, y1, x2, y2, x3, y3) == 0)
            && (ccw(x1, y1, x2, y2, x4, y4) == 0)
            && (ccw(x3, y3, x4, y4, x1, y1) == 0)
            && (ccw(x3, y3, x4, y4, x2, y2) == 0);
    }

    private static boolean isLineCrossed(long x1, long y1, long x2, long y2, long x3, long y3, long x4, long y4) {
        if (isOnOneStraightLine(x1, y1, x2, y2, x3, y3, x4, y4)) {
            if(x1 == x2) {
                long minYBetweenAandB = Math.min(y1, y2);
                long maxYBetweenAandB = Math.max(y1, y2);

                long minYBetweenCandD = Math.min(y3, y4);
                long maxYBetweenCandD = Math.max(y3, y4);
                if ((maxYBetweenCandD < minYBetweenAandB) || (maxYBetweenAandB < minYBetweenCandD)){
                    return false;
                } else {
                    return true;
                }
            }

            long minXBetweenAandB = Math.min(x1, x2);
            long maxXBetweenAandB = Math.max(x1, x2);

            long minXBetweenCandD = Math.min(x3, x4);
            long maxXBetweenCandD = Math.max(x3, x4);
            if ((maxXBetweenCandD < minXBetweenAandB) || (maxXBetweenAandB < minXBetweenCandD)){
                return false;
            } else {
                return true;
            }
        }

        if ((ccw(x1, y1, x2, y2, x3, y3) * ccw(x1, y1, x2, y2, x4, y4) <= 0)
            && (ccw(x3, y3, x4, y4, x1, y1) * ccw(x3, y3, x4, y4, x2, y2) <= 0)) {
            return true;
        } else {
            return false;
        }
    }

    private static void printIntersectionPoint(long x1, long y1, long x2, long y2, long x3, long y3, long x4, long y4) {
        double padding = Math.pow(10, -9);

        double leftX = x3;
        double leftY = y3;

        double rightX = x4;
        double rightY = y4;

        double distance = 0;
        distance = getDistanceBetweenLineAndPoint(x1, y1, x2, y2, leftX, leftY);
        if(distance <= padding) {
            System.out.print(leftX + " ");
            System.out.println(leftY);
            return;
        }

        distance = getDistanceBetweenLineAndPoint(x1, y1, x2, y2, rightX, rightY);
        if(distance <= padding) {
            System.out.print(rightX + " ");
            System.out.println(rightY);
            return;
        }

        while (true) {
            double midX = (leftX + rightX) / 2;
            double midY = (leftY + rightY) / 2;

            distance = getDistanceBetweenLineAndPoint(x1, y1, x2, y2, midX, midY);
            if(distance <= padding) {
                System.out.print(midX + " ");
                System.out.println(midY);
                return;
            }

            if (ccwForDouble(x1, y1, x2, y2, midX, midY) * ccwForDouble(x1, y1, x2, y2, leftX, leftY) < 0) {
                rightX = midX;
                rightY = midY;
            } else {
                leftX = midX;
                leftY = midY;
            }
        }
    }

    private static double getDistanceBetweenLineAndPoint(long x1, long y1, long x2, long y2, double mx, double my) {
        double a = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double b = (mx - x1) * (y2 - y1) - (my - y1) * (x2 - x1);
        return Math.abs(b / a);
    }
}