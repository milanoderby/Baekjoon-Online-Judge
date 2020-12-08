import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    private static int N, M;
    private static Set<String> notHeardPeople, result;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer tokenizer = new StringTokenizer(br.readLine());
            N = Integer.parseInt(tokenizer.nextToken());
            M = Integer.parseInt(tokenizer.nextToken());

            notHeardPeople = new HashSet<>();
            result = new HashSet<>();

            String name;
            for (int i = 0; i < N; i++) {
                name = br.readLine();
                notHeardPeople.add(name);
            }

            for (int i = 0; i < M; i++) {
                name = br.readLine();
                if (notHeardPeople.contains(name)) {
                    result.add(name);
                }
            }

            List<String> people = new ArrayList<>(result);
            Collections.sort(people);
            System.out.println(people.size());
            for (String person : people) {
                System.out.println(person);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
