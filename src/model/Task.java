package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task implements Comparable<Task> {

    protected int id;
    protected String name;
    protected String description;
    protected Status status;
    protected Duration duration;
    protected LocalDateTime startTime;

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    public static final LocalDateTime minDate = LocalDateTime.of(2000, 1, 1, 0, 0, 0, 0);

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.duration = Duration.ZERO;
        this.startTime = minDate;
    }

    public Task(String name, String description, Duration duration) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.duration = duration;
        this.startTime = minDate;
    }

    public Task(String name, String description, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.status = Status.NEW;
        this.startTime = startTime;
    }

    public Task(int id, String name, String description, Status status, Duration duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = minDate;
    }

    public Task(int id, String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    public LocalDateTime getEndTime() {
        if (startTime == LocalDateTime.MAX) {
            return LocalDateTime.MAX;
        }
        return startTime.plus(duration);
    }

    public static Task copyOf(Task task) {
        Task copy = new Task(task.name, task.description, task.duration);
        copy.id = task.id;
        copy.status = task.status;
        copy.duration = task.duration;
        return copy;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", duration=" + duration.toMinutes() +
                ", startTime=" + startTime.format(formatter) +
                ", endTime=" + getEndTime() +
                "}";
    }

    public String toCsvRow() {
        return id + "," +
                Type.TASK + "," +
                name + "," +
                status + "," +
                description + "," +
                duration.toMinutes() + "," +
                startTime.format(formatter) +
                System.lineSeparator();
    }

    @Override
    public int compareTo(Task o) {
        int startTimeComparator = startTime.compareTo(o.startTime);
        int idComparator = id - o.id;

        return (startTimeComparator != 0 ? startTimeComparator : idComparator);
    }
}
