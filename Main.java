import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        int count = 100; int step = 100;
        DataManager.generateFiles(count, step);

        System.out.println("Size;Arr_Iter;Arr_Time;Coll_Iter;Coll_Time");

        for (int i = 1; i <= count; i++) {
            int size = i * step;

            // Тест массива
            int[] arr = DataManager.readArray(size);
            long s1 = System.nanoTime();
            SmoothSort.sort(arr);
            long t1 = System.nanoTime() - s1;
            long i1 = SmoothSort.iterations;

            // Тест чистого ArrayList (через .get/.set)
            List<Integer> list = DataManager.readList(size);
            long s2 = System.nanoTime();
            SmoothSort.sort(list);
            long t2 = System.nanoTime() - s2;
            long i2 = SmoothSort.iterations;

            System.out.println(size + ";" + i1 + ";" + t1 + ";" + i2 + ";" + t2);
        }
    }
}
