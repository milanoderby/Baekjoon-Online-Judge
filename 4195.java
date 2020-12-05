import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    private static int T, F;
    private static int[] groupOfFriends;
    private static int[] groupSizeOfPerson;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            T = Integer.parseInt(br.readLine());
            for (int i = 0; i < T; i++) {
                Map<String, Integer> friendsMap = new HashMap<>();
                int personNum = 0;
                F = Integer.parseInt(br.readLine());
                groupOfFriends = new int[2*F + 1];
                groupSizeOfPerson = new int[2*F + 1];
                for (int j = 0; j < F; j++) {
                    StringTokenizer tokenizer = new StringTokenizer(br.readLine());
                    String a = tokenizer.nextToken();
                    String b = tokenizer.nextToken();

                    if (!friendsMap.containsKey(a)) {
                        personNum++;
                        friendsMap.put(a, personNum);
                        groupOfFriends[personNum] = personNum;
                        groupSizeOfPerson[personNum] = 1;
                    }

                    if (!friendsMap.containsKey(b)) {
                        personNum++;
                        friendsMap.put(b, personNum);
                        groupOfFriends[personNum] = personNum;
                        groupSizeOfPerson[personNum] = 1;
                    }

                    int friendNumA = friendsMap.get(a);
                    int friendNumB = friendsMap.get(b);

                    System.out.println(unionFriends(friendNumA, friendNumB));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static int unionFriends(int a, int b) {
        a = findFriends(a);
        b = findFriends(b);

        if (a == b) {
            return groupSizeOfPerson[a];
        }

        if (a < b) {
            groupOfFriends[b] = a;
            groupSizeOfPerson[a] += groupSizeOfPerson[b];
            return groupSizeOfPerson[a];
        } else {
            groupOfFriends[a] = b;
            groupSizeOfPerson[b] += groupSizeOfPerson[a];
            return groupSizeOfPerson[b];
        }
    }

    private static int findFriends(int personNum) {
        while (personNum != groupOfFriends[personNum]) {
            personNum = groupOfFriends[personNum];
        }
        return personNum;
    }
}
