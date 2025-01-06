package model;

public class Subtask extends Task {

    private Epic epic;

    public Subtask(String name, String description, Epic epic) {
        super(name, description);
        this.epic = epic;
    }

    public Subtask(Integer id, String name, String description, Status status, Epic epic) {
        super(id, name, description, status);
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
                ", epic=" + epic +
                "} ";
    }

    public String toCsvRow() {
        return id + "," +
                Type.SUBTASK + "," +
                name + "," +
                status + "," +
                description + "," +
                epic.getId() +
                System.lineSeparator();
    }
}
