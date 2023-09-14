package LinkedList;

import Proj2.Car;

public class Node implements Comparable<Node> {
    
    private Object element; // The element stored in the node.
    private Node next; // Reference to the next node.

    // Getter for the element stored in the node.
    public Object getElement() {
        return element;
    }

    // Setter for the element stored in the node.
    public void setElement(Object element) {
        this.element = element;
    }

    // Getter for the next node.
    public Node getNext() {
        return next;
    }

    // Setter for the next node.
    public void setNext(Node next) {
        this.next = next;
    }

    // Constructs a Node object with the specified element and next node reference.
    public Node(Object element, Node pointer) {
        this.element = element;
        this.next = pointer;
    }

    // Constructs a Node object with the specified element and null next reference.
    public Node(Object element) {
        this(element, null);
    }

    @Override
    public int compareTo(Node o) {
        if (((Car) element).getYear() > ((Car) o.element).getYear()) {
            return 1;
        } else if (((Car) element).getYear() < ((Car) o.element).getYear()) {
            return -1;
        }
        return 0;
    }
}
