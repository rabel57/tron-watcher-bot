package network.tron.job;

import network.tron.TronWatcherBot;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InfiniteTaskExecutor {

    private final List<Task> tasks;

    public InfiniteTaskExecutor(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Async("threadPoolTaskExecutor")
    public void start(TronWatcherBot bot) {
        //noinspection InfiniteLoopStatement
        while (true) {
            tasks.forEach(task -> task.execute(bot));
        }
    }
}