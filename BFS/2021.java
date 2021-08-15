import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {
    private static int N, L;
    public static void main(String[] args) throws  IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        L = Integer.parseInt(tokenizer.nextToken());

        String[] subway = new String[L];
        for (int i = 0; i < L; i++) {
            subway[i] = br.readLine();
        }

        tokenizer = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(tokenizer.nextToken());
        int end = Integer.parseInt(tokenizer.nextToken());

        bw.append(solution(subway, start, end) + "");
        bw.flush();
    }

    private static int solution(String[] subway, int start, int end) {
        if (start == end) {
            return 0;
        }
        
        List<Integer>[] stationToLines = new List[N + 1];
        for (int i = 1; i < stationToLines.length; i++) {
            stationToLines[i] = new ArrayList<>();
        }

        List<Integer>[] lineToStations = new List[L + 1];
        for (int i = 1; i < lineToStations.length; i++) {
            lineToStations[i] = new ArrayList<>();
        }

        for (int line = 1; line <= L; line++) {
            StringTokenizer tokenizer = new StringTokenizer(subway[line - 1]);
            while (tokenizer.hasMoreTokens()) {
                int station = Integer.parseInt(tokenizer.nextToken());
                if (station == -1) {
                    break;
                }
                stationToLines[station].add(line);
                lineToStations[line].add(station);
            }
        }

        boolean[] isVisitedLine = new boolean[L + 1];
        boolean[] isVisitedStation = new boolean[N + 1];
        isVisitedStation[start] = true;
        Queue<Node> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(new Station(start, true, -1));

        while (!nodeQueue.isEmpty()) {
            Node cur = nodeQueue.poll();

            if (cur.isStation) {
                for (Integer nextLine : stationToLines[cur.index]) {
                    if (!isVisitedLine[nextLine]) {
                        isVisitedLine[nextLine] = true;
                        nodeQueue.offer(new Line(nextLine, false, cur.countOfTransfer + 1));
                    }
                }
            } else {
                for (Integer nextStation : lineToStations[cur.index]) {
                    if (!isVisitedStation[nextStation]) {
                        isVisitedStation[nextStation] = true;
                        if (nextStation == end) {
                            return cur.countOfTransfer;
                        }
                        nodeQueue.offer(new Station(nextStation, true, cur.countOfTransfer));
                    }
                }
            }
        }
        return -1;
    }

    private static abstract class Node {
        private int index;
        private boolean isStation;
        private int countOfTransfer;

        public Node(int index, boolean isStation, int countOfTransfer) {
            this.index = index;
            this.isStation = isStation;
            this.countOfTransfer = countOfTransfer;
        }
    }

    private static class Station extends Node {
        public Station(int station, boolean isStation, int countOfTransfer) {
            super(station, isStation, countOfTransfer);
        }
    }

    private static class Line extends Node {
        public Line(int line, boolean isStation, int countOfTransfer) {
            super(line, isStation, countOfTransfer);
        }
    }
}
