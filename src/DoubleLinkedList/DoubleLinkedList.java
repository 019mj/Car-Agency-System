package DoubleLinkedList;

import java.io.File;
import java.io.PrintWriter;

import Proj2.Car;
import Proj2.CarBrand;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DoubleLinkedList {
	private Node front;
	private Node back;
	private int size = 0;

	public DoubleLinkedList() {
	}

	// Add a new node with the given car brand and car object at the beginning of
	// the list.
	public void addFirst(String carBrand, Car car) {
		CarBrand cBrand = new CarBrand(carBrand);

		if (car != null)
			cBrand.getCars().insertSort(car);

		Node node = new Node(cBrand);
		if (isEmpty())
			front = back = node;
		else {
			node.setNext(front);
			front.setPrev(node);
			front = node;
		}
		size++;
	}

	// Add a new node with the given CarBrand object at the beginning of the list.
	public void addFirst(CarBrand cBrand) {

		Node node = new Node(cBrand);
		if (isEmpty())
			front = back = node;
		else {
			node.setNext(front);
			front.setPrev(node);
			front = node;
		}
		size++;
	}

	/*
	 * Returns the first element in the list.
	 */
	public Object getFirst() {
		if (isEmpty())
			return null;
		return front.getElement();
	}

	/*
	 * Append the given car brand and car object to the end of the list.
	 */
	public void addLast(String carBrand, Car car) {
		CarBrand cBrand = new CarBrand(carBrand);

		if (car != null)
			cBrand.getCars().insertSort(car);

		Node node = new Node(cBrand);
		if (isEmpty())
			front = back = node;
		else {
			back.setNext(node);
			node.setPrev(back);
			back = node;
		}
		size++;
	}

	/*
	 * Append the given CarBrand object to the end of the list.
	 */
	public void addLast(CarBrand cBrand) {

		Node node = new Node(cBrand);
		if (isEmpty())
			front = back = node;
		else {
			back.setNext(node);
			node.setPrev(back);
			back = node;
		}
		size++;
	}

	/*
	 * Returns the last element in the list.
	 */
	public Object getLast() {
		if (isEmpty())
			return null;
		return back.getElement();
	}

	// Check if the list is empty.
	public boolean isEmpty() {
		return (size == 0);
	}

	// Returns the element at the specified position in the list.
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
		}
		return null;
	}

	/*
	 * Returns the number of elements in the list.
	 */
	public int size() {
		return this.size;
	}

	/*
	 * Removes the first element from the list.
	 */
	public boolean removeFirst() {
		if (isEmpty())
			return false;
		else if (size == 1)
			front = back = null;
		else {
			front.getNext().setPrev(null);
			front = front.getNext();
		}
		size--;
		return true;
	}

	/*
	 * Removes the last element from this list.
	 */
	public boolean removeLast() {
		if (isEmpty())
			return false;
		else if (size == 1)
			front = back = null;
		else {
			back.getPrev().setNext(null);
			back = back.getPrev();
		}
		size--;
		return true;
	}

	// Remove the element at the specified index from the list.
	public boolean remove(int index) {
		if (size == 0)
			return false;
		else if (index == 0)
			return removeFirst();
		else if (index == size - 1)
			return removeLast();
		else if (index > 0 && index < size - 1) {
			Node current = front;
			for (int i = 0; i < index - 1; i++)
				current = current.getNext();
			current.getNext().getNext().setPrev(current);
			current.setNext(current.getNext().getNext());
			size--;
			return true;
		}

		return false;
	}

	// Print all the elements in the list.
	public void printAll() {
		Node current = front;
		while (current != null) {
			System.out.println(((CarBrand) current.getElement()).getBrand());
			((CarBrand) current.getElement()).getCars().printAll();
			current = current.getNext();
		}
		System.out.println();
	}

	// Clear the list, removing all elements.
	public void clear() {
		if (!isEmpty()) {
			front = back = null;
			size = 0;
		}
	}

	/*
	 * Checks if the list contains the specified element.
	 */
	boolean contains(Object o) {
		if (isEmpty())
			return false;
		Object object = find(o);
		return (object == null) ? false : true;
	}

	/*
	 * Returns the last index of the specified element in the list.
	 */
	public int lastIndexOf(Object o) {
		if (isEmpty())
			return -1;
		Node current = back;
		for (int i = size - 1; i >= 0; i++) {
			if (current.getElement() == o)
				return i;
			current = current.getPrev();

			if (current == null)
				break;
		}

		return -1;
	}

	/*
	 * Find the element that is in the list.
	 */
	public Object find(Object element) {
		if (isEmpty())
			return null;
		Node current = front;
		while (current != null) {
			if (((CarBrand) current.getElement()).getBrand().equalsIgnoreCase((String) element))
				return current;
			current = current.getNext();
		}
		return null;
	}

	// Insert a new car brand and car object into the sorted list.
	public void insertSort(String carBrand, Car car) {

		if (isEmpty())
			addFirst(carBrand, car);
		else if (((CarBrand) (front.getElement())).getBrand().compareTo(carBrand) == 0) {
			if (car != null)
				((CarBrand) front.getElement()).getCars().insertSort(car);
		} else if (((CarBrand) (front.getElement())).getBrand().compareTo(carBrand) > 0)
			addFirst(carBrand, car);
		else if (((CarBrand) (back.getElement())).getBrand().compareTo(carBrand) == 0) {
			if (car != null)
				((CarBrand) back.getElement()).getCars().insertSort(car);
		} else if (((CarBrand) (back.getElement())).getBrand().compareTo(carBrand) < 0)
			addLast(carBrand, car);
		else {

			CarBrand cBrand = new CarBrand(carBrand);

			if (car != null)
				cBrand.getCars().insertSort(car);

			Node node = new Node(cBrand);
			Node current = front;
			while (current.getNext() != null) {
				if (current.compareTo(node) == 0) {
					((CarBrand) current.getElement()).getCars().insertSort(car);
					break;
				} else if (current.getNext().compareTo(node) > 0) {
					node.setNext(current.getNext());
					current.getNext().setPrev(node);
					current.setNext(node);
					node.setPrev(current);
					size++;
					break;
				}
				current = current.getNext();
			}
		}

	}

	// Print the elements of the list to a file.
	public void printToFile(File file) {
		try {
			PrintWriter pw = new PrintWriter(file);

			pw.println("Brand, Model, Year, Color, Price");

			Node current = front;

			while (current != null) {
				String brand = ((CarBrand) current.getElement()).getBrand();
				((CarBrand) current.getElement()).getCars().printToFile(pw, brand);
				current = current.getNext();
			}

			pw.close();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No file has been chosen");
			alert.showAndWait();
		}

	}

	/*
	 * Checks if the list contains a car brand with the specified name.
	 */
	public boolean contains(String brand) {
		if (isEmpty())
			return false;

		Node current = front;
		while (current != null) {
			if (((CarBrand) current.getElement()).getBrand().equalsIgnoreCase(brand))
				return true;
			current = current.getNext();
		}
		return false;
	}

	/*
	 * Remove the first occurrence of the specified car brand from the list.
	 */
	public boolean remove(String brand) {
		if (size == 0)
			return false;
		else if (((CarBrand) front.getElement()).getBrand().equalsIgnoreCase(brand))
			return removeFirst();
		else if (((CarBrand) back.getElement()).getBrand().equalsIgnoreCase(brand)) {
			System.out.println("hi");
			return removeLast();
		} else {
			Node current = front;

			while (current.getNext() != null) {
				String carBrand = ((CarBrand) current.getNext().getElement()).getBrand();
				if (carBrand.equalsIgnoreCase(brand)) {
					brand = carBrand;
					current.getNext().getNext().setPrev(current);
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
	 * Get the element with the specified brand name.
	 */
	public Object get(String brand) {
		if (isEmpty())
			return null;

		Node current = front;
		while (current != null) {
			if (((CarBrand) current.getElement()).getBrand().equalsIgnoreCase(brand))
				return current.getElement();
			current = current.getNext();
		}
		return null;
	}

	/*
	 * Sort the list based on the specified car brand.
	 */
	public void sort(CarBrand carBrand) {

		remove(carBrand.getBrand());

		if (isEmpty())
			addFirst(carBrand);
		else if (((CarBrand) (front.getElement())).compareTo(carBrand) > 0)
			addFirst(carBrand);
		else if (((CarBrand) (back.getElement())).compareTo(carBrand) < 0)
			addLast(carBrand);
		else {
			Node node = new Node(carBrand);
			Node current = front;
			while (current.getNext() != null) {
				if (current.compareTo(node) == 0) {
					current.setElement(carBrand);
					break;
				} else if (current.getNext().compareTo(node) > 0) {
					node.setNext(current.getNext());
					current.getNext().setPrev(node);
					current.setNext(node);
					node.setPrev(current);
					size++;
					break;
				}
				current = current.getNext();
			}
		}

	}

	// get the specific node that has a specific car brand
	public Node findNode(Object carBrand) {
		if (isEmpty())
			return null;
		else {
			Node current = front;

			while (current != null) {
				if ((((CarBrand) current.getElement()).getBrand()).equalsIgnoreCase((String) carBrand))
					return current;
				current = current.getNext();

			}
		}
		return null;
	}

}
