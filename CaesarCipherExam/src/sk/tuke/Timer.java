package sk.tuke;

public class Timer {
    private long startTime;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public long finish() {
        return System.currentTimeMillis() - startTime;
    }
}
