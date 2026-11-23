import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// 定时检查层。单个后台线程执行 check，一轮结束后再等指定间隔。
public class Monitor {
    private final long intervalMs;
    private final Runnable check;
    private final ScheduledExecutorService worker =
            Executors.newSingleThreadScheduledExecutor(task -> {
                Thread thread = new Thread(task, "monitor");
                thread.setDaemon(true);
                return thread;
            });
    private boolean started;

    public Monitor(long intervalMs, Runnable check) {
        if (intervalMs < 1) throw new IllegalArgumentException("Interval must be positive");
        this.intervalMs = intervalMs;
        this.check = check;
    }

    public synchronized void start() {
        if (started || worker.isShutdown()) return;
        started = true;
        worker.scheduleWithFixedDelay(() -> {
            try {
                check.run();
            } catch (RuntimeException ex) {
                ex.printStackTrace(); // 本轮报错时在控制台显示，后续轮次继续。
            }
        }, 0, intervalMs, TimeUnit.MILLISECONDS);
    }

    public synchronized void stop() {
        worker.shutdownNow();
    }
}
