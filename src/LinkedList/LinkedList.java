package LinkedList;

import java.io.PrintWriter;

import Proj2.Car;

public class LinkedList {

	// Very important to declear where the list in null and when it is not
	private Node front; // Dummy Header
	private Node back;
	private int size = 0;

	public LinkedList() {
		front = back = null;
	}

	public void addFirst(Object element) {
		if (isEmpty()) {
			Node node = new Node(element);
			front = back = node;
		} else {
			Node node = new Node(element, front);
			front = node;
		}
		size++;
	}

	/*
	 * Object getFirst() Returns the first element in the list.
	 */
	public Object getFirst() {
		if (isEmpty())
			return null;
		return front.getElement();
	}

	/*
	 * void addLast(Object o) Appends the given element to the end of the list.
	 */
	public void addLast(Object element) {
		Node newNode = new Node(element);
		if (size == 0) {
			front = back = newNode;
		} else {
			back.setNext(newNode);
			back = newNode;
		}
		size++;
	}

	/*
	 * Object getLast() Returns the last element in the list.
	 */
	public Object getLast() {
		if (size == 0)
			return null;
		return back.getElement();
	}

	/*
	 * Object get(int index) Returns the element at the specified position in the
	 * list.
	 */
	public Object get(int index) {
		if (isEmpty())
			return null;
		else if (index == 0)
			return getFirst();
		else if (index == size - 1)
			return getLast();
		else if (index > 0 && index < size - 1) {
			Node current = front;
			for (int i = 0; i < index; i++)
				current = current.getNext();
			return current.getElement();
		} else
			return null;
	}

	/*
	 * int size() Returns the number of elements in the list.
	 */
	public int size() {
		return this.size;
	}

	/*
	 * void add(int index, T element) Inserts the specified element at the specified
	 * position index in this list.
	 */
	public void add(int index, Object element) {
		if (index == 0)
			addFirst(element);
		else if (index > size - 1)
			addLast(element);
		else if (index > 0 && index <= size - 1) {
			Node current = front;
			for (int i = 0; i < index - 1; i++)
				current = current.getNext();

			Node newNode = new Node(element, current.getNext());
			current.setNext(newNode);
			size++;
		}
	}

	/*
	 * void add(Object o) Appends the specified element to the end of the list
	 */
	public void add(Object element) {
		addLast(element);
	}

	/*
	 * boolean removeFirst() Removes the first element from the list.
	 */
	public boolean removeFirst() {
		if (size == 0)
			return false;
		else if (size == 1)
			front = back = null;
		else
			front = front.getNext();
		size--;
		return true;
	}

	/*
	 * boolean removeLast() Removes the last element from this list.
	 */
	public boolean removeLast() {
		if (size == 0)
			return false;
		else if (size == 1) {
			front = back = null;
		} else {
			Node current = front;
			for (int i = 0; i < size - 2; i++)
				current = current.getNext();

			back = current;
			current.setNext(null);
		}
		size--;
		return true;
	}


	/*
	 * void traverse prints the list recursively from head to back
	 */

	public void traverse(Node current) {
		if (current != null) {
			System.out.println(current.getElement());
			traverse(current.getNext());
		}
	}

	/*
	 * void clear() Removes all of the elements from the list.
	 */
	public void clear() {
		if (!isEmpty()) {
			front = back = null;
			size = 0;
		}
	}

	/*
	 * return the first index for the specified element in the list
	 */
	public int find(Object o) {

		if (size == 0)
			return -1;
		else {
			Node current = front;
			for (int i = 0; i < size; i++) {
				if (current.getElement() == o)
					return i;
				current = current.getNext();
			}
			return -1;
		}
	}

	/*
	 * Removes the first occurrence of the specified element in the list
	 */
	public boolean remove(Car o) {
		if (isEmpty())
			return false;

		boolean frontStatus = ((Car) front.getElement()).getModel().equalsIgnoreCase(o.getModel())
				&& ((Car) front.getElement()).getYear() == (o.getYear())
				&& ((Car) front.getElement()).getColor().equalsIgnoreCase(o.getColor())
				&& ((Car) front.getElement()).getPrice() == (o.getPrice());
		
		boolean backStatus = ((Car) back.getElement()).getModel().equalsIgnoreCase(o.getModel())
				&& ((Car) back.getElement()).getYear() == (o.getYear())
				&& ((Car) back.getElement()).getColor().equalsIgnoreCase(o.getColor())
				&& ((Car) back.getElement()).getPrice() == (o.getPrice());

		
		if (frontStatus)
			return removeFirst();
		else if (backStatus) {
			return removeLast();
		}
		else {
			Node current = front;

			while (current.getNext() != null) {
				boolean currentStatus = ((Car) current.getNext().getElement()).getModel().equalsIgnoreCase(o.getModel())
						&& ((Car) current.getNext().getElement()).getYear() == (o.getYear())
						&& ((Car) current.getNext().getElement()).getColor().equalsIgnoreCase(o.getColor())
						&& ((Car) current.getNext().getElement()).getPrice() == (o.getPrice());

				if (currentStatus) {
					current.setNext(current.getNext().getNext());
					size--;
					return true;
				}
				current = current.getNext();
			}
		}

		return false;

	}

	/*
	 * void printAll() prints all the elements in the list
	 */
	public void printAll() {
		Node current = front;
		while (current != null) {
			System.out.println(current.getElement().toString());
			current = current.getNext();
		}
		System.out.println();

	}

	/*
	 * boolean isEmpty() checks whether the list is empty or not
	 */
	public boolean isEmpty() {
		return (size == 0);
	}

	/*
	 * Checks whether the list contains a specific element or not
	 */
	public boolean contains(Object o) {
		if (isEmpty())
			return false;
		int i = find(o);
		return (i == -1) ? false : true;
	}

	/*
	 * int lastIndexOf(Object o) fint the last index of a specific element in the
	 * list
	 */
	public int lastIndexOf(Object o) {
		if (isEmpty())
			return -1;
		Node current = front;
		int index = -1;
		for (int i = 0; i < size; i++)
			if (current.getElement() == o)
				index = i;
		return index;
	}

	// Insert sorted element in the list
	public void insertSort(Object o) {
		Node node = new Node(o);
		if (isEmpty())
			addFirst(o);
		else if (front.compareTo(node) >= 0)
			addFirst(o);
		else if (back.compareTo(node) <= 0)
			addLast(o);
		else {
			Node current = front;
			while (!(node.compareTo(current) >= 0 && node.compareTo(current.getNext()) == -1))
				current = current.getNext();

			node.setNext(current.getNext());
			current.setNext(node);
			size++;
		}
	}

	// printing to a file
	public void printToFile(PrintWriter pw, String CarBrand) {
		Node current = front;

		while (current != null) {
			pw.println(CarBrand + ", " + ((Car) current.getElement()).toString());
			current = current.getNext();
		}
	}

	// get and return the element if exists
	public Object get(Object o) {
		if (isEmpty())
			return null;

		Node current = front;

		while (current != null) {
			boolean currentStatus = ((Car) current.getElement()).getModel().equalsIgnoreCase(((Car) o).getModel())
					&& ((Car) current.getElement()).getYear() == (((Car) o).getYear())
					&& ((Car) current.getElement()).getColor().equalsIgnoreCase(((Car) o).getColor())
					&& ((Car) current.getElement()).getPrice() == (((Car) o).getPrice());

			if (currentStatus)
				return current.getElement();
			current = current.getNext();
		}

		return null;

	}

	// sort a car in the list
	public void sort(Car car) {

		remove(car);
		insertSort(car);
	}

}
