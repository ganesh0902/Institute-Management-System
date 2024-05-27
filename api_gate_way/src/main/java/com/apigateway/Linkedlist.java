package com.apigateway;

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class LinkedList {
    Node head;

    // Adds a new node at the end of the list
    public void add(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    // Searches for an element in the list
    public boolean search(int target) {
        Node current = head;
        while (current != null) {
            if (current.data == target) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Prints the elements of the list
    public void printList() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }
}

public class Linkedlist {
    public static void main(String[] args) {
        LinkedList list = new LinkedList();

        // Adding elements to the list
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);

        // Printing the list
        System.out.print("Linked List: ");
        list.printList();

        // Searching for an element
        int searchElement = 30;
        boolean isFound = list.search(searchElement);
        System.out.println("Element " + searchElement + (isFound ? " is found" : " is not found") + " in the list.");

        // Searching for an element not in the list
        searchElement = 60;
        isFound = list.search(searchElement);
        System.out.println("Element " + searchElement + (isFound ? " is found" : " is not found") + " in the list.");
    }
}