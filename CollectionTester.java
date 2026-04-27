import java.util.List;

public class CollectionTester {
    public static TestResult run(List<Integer> data) {
        long startTime = System.nanoTime();
        SmoothSort.sort(data);
        long endTime = System.nanoTime();
        return new TestResult(SmoothSort.iterations, endTime - startTime);
    }
}
