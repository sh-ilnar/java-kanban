package services;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LinkedTaskList {
    private Map<Integer, Node<Task>> nodeMap = new HashMap<Integer, Node<Task>>();
    private Node<Task> head = null;
    private Node<Task> tail = null;

    public void linkLast(Task task) {
        if (task == null)
            return;

        Node<Task> newNode = new Node<>(task);

        if (this.nodeMap.isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
            nodeMap.put(task.getId(), newNode);
        } else {
            if (nodeMap.containsKey(task.getId())) {
                this.removeNode(task.getId());
                this.tail = newNode;
                newNode.prev = this.tail;
                nodeMap.put(task.getId(), newNode);
            } else {
                Node<Task> prevNode = this.tail;
                prevNode.next = newNode;
                newNode.prev = prevNode;
                this.tail = newNode;
                newNode.prev = this.tail;
                nodeMap.put(task.getId(), newNode);
            }
        }
    }

    public ArrayList<Task> getTasks() {

        ArrayList<Task> taskList = new ArrayList<>();
        for (Node<Task> node : nodeMap.values()) {
            taskList.add(node.data);
        }
        return taskList;
    }

    public void removeNode(int taskId) {

        if (!nodeMap.containsKey(taskId)) {
            return;
        }

        Node<Task> currentNode = nodeMap.get(taskId);

        int condition = 0;

        if (currentNode.prev == null) {
            condition |= 1;
        }
        if (currentNode.next == null) {
            condition |= 2;
        }

        switch (condition) {
            case 0: // prev != null && next != null
                Node<Task> nextNode = currentNode.next;
                Node<Task> prevNode = currentNode.prev;
                prevNode.next = nextNode;
                nextNode.prev = prevNode;
                break;

            case 1: // prev == null && next != null
                Node<Task> nextNodeOnly = currentNode.next;
                nextNodeOnly.prev = null;
                head = nextNodeOnly;
                break;

            case 2: // prev != null && next == null
                Node<Task> prevNodeOnly = currentNode.prev;
                prevNodeOnly.next = null;
                tail = prevNodeOnly;
                break;

            case 3: // prev == null && next == null
                head = null;
                tail = null;
                break;
        }

        nodeMap.remove(taskId);
    }
}
