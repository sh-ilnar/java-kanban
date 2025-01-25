package services;

import interfaces.HistoryManager;
import interfaces.TaskManager;

import java.nio.file.Path;

public class Managers {

    public static TaskManager getDefault(HistoryManager historyManager) {
        return new InMemoryTaskManager(historyManager);
    }

    public static TaskManager getFromBackedFile(HistoryManager historyManager, Path path) {
        return FileBackedTaskManager.loadFromFile(historyManager, path.toFile());
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
