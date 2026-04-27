import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DataManager {
    public static void generateFiles(int count, int step) throws IOException {
        File dir = new File("data");
        if (!dir.exists()) dir.mkdir();
        Random random = new Random();
        for (int i = 1; i <= count; i++) {
            int size = i * step;
            try (PrintWriter pw = new PrintWriter(new FileWriter("data/set_" + size + ".txt"))) {
                for (int j = 0; j < size; j++) pw.println(random.nextInt(1000000));
            }
        }
    }

    public static int[] readArray(int size) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data/set_" + size + ".txt"))) {
            return br.lines().mapToInt(Integer::parseInt).toArray();
        }
    }

    public static List<Integer> readList(int size) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data/set_" + size + ".txt"))) {
            return br.lines().map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
        }
    }
}
