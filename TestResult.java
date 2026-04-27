public class TestResult {
    public final long iterations;
    public final long timeNs;

    public TestResult(long iterations, long timeNs) {
        this.iterations = iterations;
        this.timeNs = timeNs;
    }
}
