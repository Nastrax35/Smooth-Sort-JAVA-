public class ArrayTester {
    public static TestResult run(int[] data) {
        long startTime = System.nanoTime();
        SmoothSort.sort(data);
        long endTime = System.nanoTime();
        return new TestResult(SmoothSort.iterations, endTime - startTime);
    }
}
