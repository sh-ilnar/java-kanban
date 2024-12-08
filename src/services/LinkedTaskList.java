package services;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LinkedTaskList {
    private Map<Integer, Node<Task>> nodeMap = new HashMap<Integer, Node<Task>>();
    private Node<Task> head = null;
    private Node<Task> tail = null;

    public void linkLast(Task task) {
        if (task == null)
            return;

        Node<Task> newNode = new Node<>(task);

        if(this.nodeMap.isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
            nodeMap.put(task.getId(), newNode);
        }
        else {
            if(nodeMap.containsKey(task.getId())) {
                this.removeNode(task.getId());
                this.tail = newNode;
                newNode.prev = this.tail;
                nodeMap.put(task.getId(), newNode);
            }
            else {
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
        for(Node<Task> node : nodeMap.values()) {
            taskList.add(node.data);
        }
        return taskList;
    }

    public void removeNode(int taskId) {

        if (!nodeMap.containsKey(taskId))
            return;

        Node<Task> currentNode = nodeMap.get(taskId);
        if (currentNode.next != null && currentNode.prev != null) {
            Node<Task> nextNode = currentNode.next;
            Node<Task> prevNode = currentNode.prev;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        else if (currentNode.next == null && currentNode.prev != null) {
            Node<Task> prevNode = currentNode.prev;
            prevNode.next = null;
            tail = prevNode;
        }
        else if (currentNode.next == null && currentNode.prev == null) {
            head = null;
            tail = null;
        }
        nodeMap.remove(taskId);
    }
}
