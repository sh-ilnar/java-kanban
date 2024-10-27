import java.util.ArrayList;
import java.util.UUID;

public class Epic extends Task {

    private final ArrayList<Subtask> subtasks;

    public Epic(int id, String name, String description) {
        super(id, name, description);
        subtasks = new ArrayList<Subtask>();
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public void deleteSubtask(Subtask subtask) {
        subtasks.removeIf(sub -> sub.equals(subtask));
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void updateStatus() {
        if (subtasks == null) {
            status = Status.NEW;
            return;
        }

        int newCount = 0;
        int doneCount = 0;
        for (Subtask subtask : subtasks) {
            if(subtask.status == Status.IN_PROGRESS) {
                status = Status.IN_PROGRESS;
                return;
            }
            else if (subtask.status == Status.NEW) newCount++;
            else doneCount++;
        }
        if (newCount == subtasks.size())
            status = Status.NEW;
        else if (doneCount == subtasks.size())
            status = Status.DONE;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
