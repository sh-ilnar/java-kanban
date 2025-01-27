package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {

    private Epic epic;

    public Subtask(String name, String description, Duration duration, Epic epic) {
        super(name, description, duration);
        this.epic = epic;
    }

    public Subtask(String name, String description, Duration duration, LocalDateTime startTime, Epic epic) {
        super(name, description, duration, startTime);
        this.epic = epic;
    }

    public Subtask(Integer id, String name, String description, Status status, Duration duration, Epic epic) {
        super(id, name, description, status, duration);
        this.epic = epic;
    }

    public Subtask(Integer id, String name, String description, Status status, Duration duration, LocalDateTime startTime, Epic epic) {
        super(id, name, description, status, duration, startTime);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", duration=" + duration.toMinutes() +
                ", startTime=" + startTime.format(formatter) +
                ", endTime=" + getEndTime().format(formatter) +
                ", epic=" + epic +
                "} ";
    }

    public String toCsvRow() {
        return id + "," +
                Type.SUBTASK + "," +
                name + "," +
                status + "," +
                description + "," +
                duration.toMinutes() + "," +
                startTime.format(formatter) + "," +
                epic.getId() +
                System.lineSeparator();
    }
}
