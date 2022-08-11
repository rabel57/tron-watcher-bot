package network.tron.job;

import network.tron.TronWatcherBot;

public interface Task {
    void execute(TronWatcherBot bot);
}