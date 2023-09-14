package StackList;

import java.io.PrintWriter;

import Proj2.Order;

public class Stack {
	private int size; // Tracks the size of the stack
	private Node front; // Points to the top/front node of the stack

	public Stack() {
		size = 0; // Initialize the size to 0
		front = null; // Initialize the front to null, indicating an empty stack
	}

	public void push(Object element) {
		// Create a new node and make it the new front of the stack
		Node node = new Node(element, front);
		front = node;
		size++; // Increase the size of the stack
	}

	public boolean isEmpty() {
		return (size == 0); // Returns true if the stack is empty, false otherwise
	}

	public Object pop() {
		if (isEmpty())
			return null; // If the stack is empty, return null

		Node top = front; // Store the top/front node
		front = front.getNext(); // Move the front pointer to the next node
		size--; // Decrease the size of the stack
		return top.getElement(); // Return the element of the top node
	}

	public Object peek() {
		if (isEmpty())
			return null; // If the stack is empty, return null
		return front.getElement(); // Return the element of the top node without removing it
	}

	public int size() {
		return size; // Return the size of the stack
	}

	public void printToFile(PrintWriter pw) {
		Stack tempStack = new Stack(); // Create a temporary stack
		while (peek() != null) {
			Object top = pop(); // Remove and get the top element from the stack
			tempStack.push(top); // Push the element to the temporary stack

			// Print the element and indicate it is finished
			pw.println(((Order) top).toString() + ", Finished");
		}

		while (tempStack.peek() != null)
			push(tempStack.pop()); // Restore the original stack by pushing elements from the temporary stack
	}

	public void clear() {
		front = null; // Clear the stack by setting the front to null
		size = 0; // Reset the size to 0
	}
}
