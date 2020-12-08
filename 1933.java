import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    private static int N;
    private static List<Building> buildingList;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            N = Integer.parseInt(br.readLine());

            buildingList = new ArrayList<>();
            StringTokenizer tokenizer;
            for (int i = 0; i < N; i++) {
                tokenizer = new StringTokenizer(br.readLine());
                int L = Integer.parseInt(tokenizer.nextToken());
                int H = Integer.parseInt(tokenizer.nextToken());
                int R = Integer.parseInt(tokenizer.nextToken());

                buildingList.add(new Building(L, H, R));
            }

            Collections.sort(buildingList);
            List<HeightChangePoint> answer = divideAndConquer(0, N - 1);
            for (int i = 0; i < answer.size(); i++) {
                System.out.print(answer.get(i).x + " " + answer.get(i).y + " ");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static List<HeightChangePoint> divideAndConquer(int start, int end) {
        if(start == end) {
            List<HeightChangePoint> heightChangePoints = new ArrayList<>();
            heightChangePoints.add(new HeightChangePoint(buildingList.get(start).left, buildingList.get(start).height));
            heightChangePoints.add(new HeightChangePoint(buildingList.get(start).right, 0));
            return heightChangePoints;
        }

        int mid = (start + end) / 2;
        List<HeightChangePoint> heightChangePointList1 = divideAndConquer(start, mid);
        List<HeightChangePoint> heightChangePointList2 = divideAndConquer(mid + 1, end);

        List<HeightChangePoint> result = new ArrayList<>();
        int height = 0;
        int h1 = 0, h2 = 0;
        int i, j;
        for (i = 0, j = 0; i < heightChangePointList1.size() && j < heightChangePointList2.size(); ) {
            if (heightChangePointList1.get(i).x == heightChangePointList2.get(j).x) {
                h1 = heightChangePointList1.get(i).y;
                h2 = heightChangePointList2.get(j).y;

                if (height != Math.max(h1, h2)) {
                    height = Math.max(h1, h2);
                    result.add(new HeightChangePoint(heightChangePointList1.get(i).x, height));
                }


                i++;
                j++;
            } else if (heightChangePointList1.get(i).x < heightChangePointList2.get(j).x) {
                h1 = heightChangePointList1.get(i).y;
                if (height != Math.max(h1, h2)) {
                    height = Math.max(h1, h2);
                    result.add(new HeightChangePoint(heightChangePointList1.get(i).x, height));
                }
                i++;
            } else {
                h2 = heightChangePointList2.get(j).y;
                if (height != Math.max(h1, h2)) {
                    height = Math.max(h1, h2);
                    result.add(new HeightChangePoint(heightChangePointList2.get(j).x, height));
                }
                j++;
            }
        }

        for (int k = i; k < heightChangePointList1.size(); k++) {
            result.add(heightChangePointList1.get(k));
        }

        for (int k = j; k < heightChangePointList2.size(); k++) {
            result.add(heightChangePointList2.get(k));
        }

        return result;
    }

    private static class Building  implements Comparable<Building>{
        int left, right, height;
        public Building (int left, int height, int right) {
            this.left = left;
            this.right = right;
            this.height = height;
        }

        @Override
        public int compareTo(Building o) {
            return this.left - o.left;
        }
    }

    private static class HeightChangePoint {
        int x, y;
        public HeightChangePoint (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
