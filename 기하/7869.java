import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws  IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        double x1 = Double.parseDouble(tokenizer.nextToken());
        double y1 = Double.parseDouble(tokenizer.nextToken());
        double r1 = Double.parseDouble(tokenizer.nextToken());
        double x2 = Double.parseDouble(tokenizer.nextToken());
        double y2 = Double.parseDouble(tokenizer.nextToken());
        double r2 = Double.parseDouble(tokenizer.nextToken());

        double distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        double answer = 0, dimension = 0;
        if (distance <= Math.abs(r1 - r2)) {
            double min = Math.min(r1, r2);
            dimension = Math.PI * Math.pow(min, 2);
        } else if (distance >= r1 + r2) {
            dimension = 0;
        } else {
            double theta1 = Math.acos( (Math.pow(r1, 2) + Math.pow(distance, 2) - Math.pow(r2, 2)) / (2 * r1 * distance));
            double theta2 = Math.acos( (Math.pow(r2, 2) + Math.pow(distance, 2) - Math.pow(r1, 2)) / (2 * r2 * distance));
            dimension = ((2 * theta1 - Math.sin(2 * theta1)) * Math.pow(r1, 2) + (2 * theta2 - Math.sin(2 * theta2)) * Math.pow(r2, 2))/ 2;
        }

        answer = Math.round(dimension * 1000) / 1000.0;
        bw.append(String.format("%.3f", answer) + "");
        bw.flush();
    }
}
