package timer;

public class Timer {
    private long elapsed, totalTime;

    public void start() {
        elapsed = System.nanoTime();
        totalTime = 0;
    }

    public long stop() {
        elapsed = System.nanoTime() - elapsed;

        totalTime += elapsed;
        return totalTime;
    }
//
//    public long pause() {
//        elapsed = System.nanoTime() - elapsed;
//
//        totalTime += elapsed;
//        return totalTime;
//    }
//
//    public void resume() {
//        elapsed = System.nanoTime();
//    }
}
