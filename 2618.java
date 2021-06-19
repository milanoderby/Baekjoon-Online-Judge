import java.io.*;
import java.util.*;

public class Main {
    private static int N;
    private static List<Position> positionOfIncidents;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        int W = Integer.parseInt(br.readLine());

        positionOfIncidents = new ArrayList<>();
        // 사건의 번호를 1번부터 시작하기 위해 허위 값을 넣어줍니다.
        positionOfIncidents.add(new Position(0, 0));

        for (int i = 0; i < W; i++) {
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(tokenizer.nextToken());
            int x = Integer.parseInt(tokenizer.nextToken());
            positionOfIncidents.add(new Position(y, x));
        }

        // DP[i][j] : 경찰차1이 i번 사건을, 경찰차2가 j번 사건을 해결했을 때, 이동한 거리의 최솟값
        int[][] DP = new int[W + 1][W + 1];
        for (int i = 0; i <= W; i++) {
            for (int j = 0; j <= W; j++) {
                DP[i][j] = Integer.MAX_VALUE;
            }
        }

        Incident[][] pastIncident = new Incident[W + 1][W + 1];
        pastIncident[1][0] = new Incident(0, 0);
        pastIncident[0][1] = new Incident(0, 0);
        DP[1][0] = getDistanceBetweenIncident(0, 1);
        DP[0][1] = getDistanceBetweenIncident(1, 0);

        // 경찰이 최근에 해결한 사건의 번호를 저장하는 큐
        Queue<Incident> incidentQueue = new LinkedList<>();
        incidentQueue.add(new Incident(0, 1));
        incidentQueue.add(new Incident(1, 0));

        while (!incidentQueue.isEmpty()) {
            Incident incident = incidentQueue.poll();

            int nextIncident = Math.max(incident.police1SolvedLately, incident.police2SolvedLately) + 1;
            if (nextIncident > W) {
                continue;
            }

            int minDistance = DP[incident.police1SolvedLately][incident.police2SolvedLately];
            int distanceIfPolice1SolveNextIncident = getDistanceBetweenIncident(incident.police1SolvedLately, nextIncident);
            if (DP[nextIncident][incident.police2SolvedLately] > minDistance + distanceIfPolice1SolveNextIncident) {
                DP[nextIncident][incident.police2SolvedLately] = minDistance + distanceIfPolice1SolveNextIncident;
                pastIncident[nextIncident][incident.police2SolvedLately] = new Incident(incident.police1SolvedLately, incident.police2SolvedLately);

                incidentQueue.add(new Incident(nextIncident, incident.police2SolvedLately));
            }

            int distanceIfPolice2Solve = getDistanceBetweenIncident(nextIncident, incident.police2SolvedLately);
            if (DP[incident.police1SolvedLately][nextIncident] > minDistance + distanceIfPolice2Solve) {
                DP[incident.police1SolvedLately][nextIncident] = minDistance + distanceIfPolice2Solve;
                pastIncident[incident.police1SolvedLately][nextIncident] = new Incident(incident.police1SolvedLately, incident.police2SolvedLately);

                incidentQueue.add(new Incident(incident.police1SolvedLately, nextIncident));
            }
        }

        int answer = Integer.MAX_VALUE;
        int lastIncidentOfPolice1 = 0, lastIncidentOfPolice2 = 0;
        for (int i = 0; i <= W; i++) {
            if (answer > DP[W][i]) {
                answer = DP[W][i];

                lastIncidentOfPolice1 = W;
                lastIncidentOfPolice2 = i;
            }

            if (answer > DP[i][W]) {
                answer = DP[i][W];

                lastIncidentOfPolice1 = i;
                lastIncidentOfPolice2 = W;
            }
        }
        bw.append(answer + "\n");

        List<Integer> incidentOfPoliceWhenMinimumDistance = new ArrayList<>();
        while (Objects.nonNull(pastIncident[lastIncidentOfPolice1][lastIncidentOfPolice2])) {
            if (lastIncidentOfPolice1 > lastIncidentOfPolice2) {
                incidentOfPoliceWhenMinimumDistance.add(1);
            } else {
                incidentOfPoliceWhenMinimumDistance.add(2);
            }
            Incident incident = pastIncident[lastIncidentOfPolice1][lastIncidentOfPolice2];
            lastIncidentOfPolice1 = incident.police1SolvedLately;
            lastIncidentOfPolice2 = incident.police2SolvedLately;
        }
        Collections.reverse(incidentOfPoliceWhenMinimumDistance);
        for (Integer numOfIncident : incidentOfPoliceWhenMinimumDistance) {
            bw.append(numOfIncident + "\n");
        }

        bw.flush();
    }

    private static class Position {
        private int y;
        private int x;

        public Position(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    private static class Incident {
        private int police1SolvedLately;
        private int police2SolvedLately;

        public Incident(int police1SolvedLately, int police2SolvedLately) {
            this.police1SolvedLately = police1SolvedLately;
            this.police2SolvedLately = police2SolvedLately;
        }
    }

    private static int getDistanceBetweenIncident(int lastIncidentOfPolice1, int lastIncidentOfPolice2) {
        Position police1, police2;
        if (lastIncidentOfPolice1 == 0) {
            police1 = new Position(1, 1);
        } else {
            police1 = positionOfIncidents.get(lastIncidentOfPolice1);
        }

        if (lastIncidentOfPolice2 == 0) {
            police2 = new Position(N, N);
        } else {
            police2 = positionOfIncidents.get(lastIncidentOfPolice2);
        }

        return Math.abs(police2.y - police1.y) + Math.abs(police2.x - police1.x);
    }
}
