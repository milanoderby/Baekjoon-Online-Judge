import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    private static int N, M;
    private static int[][] cityMap;
    private static Set<Integer> cityToTravel;
    private static int[] countryOfCity;
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            N = Integer.parseInt(br.readLine());
            M = Integer.parseInt(br.readLine());

            cityMap = new int[N+1][N+1];
            countryOfCity = new int[N+1];
            cityToTravel = new HashSet<>();
            StringTokenizer tokenizer;
            for (int i = 1; i <= N; i++) {
                tokenizer = new StringTokenizer(br.readLine());
                for (int j = 1; j <= N; j++) {
                    cityMap[i][j] = Integer.parseInt(tokenizer.nextToken());
                }
            }

            tokenizer = new StringTokenizer(br.readLine());
            for (int i = 1; i <= M; i++) {
                cityToTravel.add(Integer.parseInt(tokenizer.nextToken()));
            }

            for (int i = 1; i <= N; i++) {
                countryOfCity[i] = i;
            }

            for (int i = 1; i <= N; i++) {
                for (int j = 1; j < i; j++) {
                    if (cityMap[i][j] == 1) {
                        if (getCountryOfCity(i) != getCountryOfCity(j)) {
                            countryOfCity[getCountryOfCity(i)] = getCountryOfCity(j);
                        }
                    }
                }
            }

            Set<Integer> countries = new HashSet<>();
            for (Integer city : cityToTravel) {
                countries.add(getCountryOfCity(city));
            }

            if (countries.size() > 1) {
                System.out.println("NO");
            } else {
                System.out.println("YES");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static int getCountryOfCity(int city) {
        while(city != countryOfCity[city]) {
            city = countryOfCity[city];
        }
        return city;
    }
}
