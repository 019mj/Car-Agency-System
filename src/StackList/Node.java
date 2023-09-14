package StackList;


// Stores one single element of a Linked List
public class Node implements Comparable<Node>{
	
	private Object element;
	private Node next;

	
	public Object getElement() {
		return element;
	}

	public void setElement(Object element) {
		this.element = element;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}
	
	public Node(Object element , Node pointer) {
		this.element = element;
		this.next = pointer;
	}
	
	public Node(Object element) {
		this(element, null);
	}

	@Override
	public int compareTo(Node o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
