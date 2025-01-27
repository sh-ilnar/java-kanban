package services;

import interfaces.HistoryManager;
import interfaces.TaskManager;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LinkedTaskListTest {

    @Test
    void doubleAddTask_getTasks_returnOneTask() {

        Task task1 = new Task(1, "Первая задача", "Описание 1", Status.NEW, Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        LinkedTaskList testList = new LinkedTaskList();
        testList.linkLast(task1);
        testList.linkLast(task1);

        ArrayList<Task> tasks = testList.getTasks();

        Assertions.assertEquals(1, tasks.size(), "Не корректная обработка повторного добавления");
    }

    @Test
    void addTwoTask_getTasks_returnTwoTask() {

        Task task1 = new Task(1, "Первая задача", "Описание 1", Status.NEW, Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        Task task2 = new Task(2, "Вторая задача", "Описание 2", Status.NEW, Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("02.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        LinkedTaskList testList = new LinkedTaskList();
        testList.linkLast(task1);
        testList.linkLast(task2);

        ArrayList<Task> tasks = testList.getTasks();

        Assertions.assertEquals(2, tasks.size(), "Не корректно добавляются задачи");
    }

    @Test
    void newTask_getTasks_returnTask() {

        Task task1 = new Task(1, "Первая задача", "Описание 1", Status.NEW, Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        LinkedTaskList testList = new LinkedTaskList();
        testList.linkLast(task1);

        ArrayList<Task> tasks = testList.getTasks();
        Task assertTask = tasks.getFirst();

        Assertions.assertEquals(task1, assertTask, "Задачи не совпадают");
    }

    @Test
    void addTwoTask_removeFirstTask_returnSecondTask() {

        Task task1 = new Task(1, "Первая задача", "Описание 1", Status.NEW, Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        Task task2 = new Task(2, "Вторая задача", "Описание 2", Status.NEW, Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        LinkedTaskList testList = new LinkedTaskList();
        testList.linkLast(task1);
        testList.linkLast(task2);
        testList.removeNode(1);

        ArrayList<Task> tasks = testList.getTasks();

        Task assertTask = tasks.getFirst();

        Assertions.assertEquals(task2, assertTask, "Не корректно удаляются узлы");
    }
}