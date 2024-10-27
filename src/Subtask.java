import java.util.UUID;

public class Subtask extends Task {

    private final Epic epic;

    public Subtask(int id, String name, String description, Epic epic) {
        super(id, name, description);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    public final void update(Subtask subtask) {
        this.name = subtask.name;
        this.description = subtask.description;
        this.status = subtask.status;

        epic.updateStatus();
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", epic=" + epic +
                "} ";
    }
}
