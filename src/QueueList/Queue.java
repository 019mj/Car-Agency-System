package QueueList;

import java.io.PrintWriter;

import Proj2.Order;

public class Queue {
	private Node front, back;

	/*
	 * no-argument constructor to create an empty queue
	 */
	public Queue() {
		front = back = null;
	}

	/*
	 * EnQueue := to add to the last of the Queue list It takes a constant time O(1)
	 */
	public void enQueue(Object element) {
		Node node = new Node(element);
		if (isEmpty())
			front = node;
		else
			back.setNext(node);

		back = node;
	}

	/*
	 * DeQueue := to remove and return the first element in the Queue list
	 */
	public Object deQueue() {
		Object element = getFront();

		if (!isEmpty())
			front = front.getNext();

		if (front == null)
			back = null;

		return element;
	}

	/*
	 * GetFront := to get the first element in the queue list
	 */
	public Object getFront() {
		if (isEmpty())
			return null;
		return front.getElement();
	}

	/*
	 * isEmpty := to check whether the queue list is empty or not
	 */
	public boolean isEmpty() {
		return front == null && back == null;
	}

	/*
	 * Clear := to free the list form all its elements
	 */
	public void clear() {
		front = back = null;
	}

	/*
	 * PrintToFile := printing all the queue data to an external file
	 */
	public void printToFile(PrintWriter pw) {
		Queue tempQueue = new Queue();
		while (!isEmpty()) {
			Object order = deQueue();
			tempQueue.enQueue(order);

			pw.println(((Order) order).toString() + ", InProcess");
		}

		while (!tempQueue.isEmpty())
			enQueue(tempQueue.deQueue());
	}

}
