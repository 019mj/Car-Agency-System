package DoubleLinkedList;

import Proj2.CarBrand;

public class Node {
    
    private Object element; // The element stored in the node.
    private Node next; // Reference to the next node.
    private Node prev; // Reference to the previous node.
    
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
    
    // Getter for the previous node.
    public Node getPrev() {
        return prev;
    }

    // Setter for the previous node.
    public void setPrev(Node prev) {
        this.prev = prev;
    }

    // Constructs a Node object with the specified element, next, and prev references.
    public Node(Object element, Node next, Node prev) {
        this.element = element;
        this.next = next;
        this.prev = prev;
    }
    
    // Constructs a Node object with the specified element and null next and prev references.
    public Node(Object element) {
        this(element, null, null);
    }

    // Compares this node with another node based on the car brand.
    public int compareTo(Node o) {
        return (((CarBrand)element).getBrand().compareTo(((CarBrand)o.getElement()).getBrand()));
    }
}
