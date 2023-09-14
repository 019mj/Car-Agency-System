package Proj2;

import LinkedList.LinkedList;

/* 
 * The CarBrand class represents a car brand object with a brand name and a list of cars within it.
 */

public class CarBrand implements Comparable<CarBrand>{
	private String brand; // Represents the brand name of the car brand.
	private LinkedList cars = new LinkedList(); // Represents a list of cars belonging to the car brand.

	/*
	 * Constructs a CarBrand object with the specified brand name.
	 */
	public CarBrand(String brand) {
		setBrand(brand); // Initializes the car brand object with the provided brand name.
	}

	/*
	 * Returns the brand name of the car brand.
	 */
	public String getBrand() {
		return brand;
	}

	/*
	 * Sets the brand name of the car brand.
	 */
	public void setBrand(String brand) {
		if (brand != null)
			this.brand = brand; // Sets the brand name of the car brand, if a non-null value is provided.
	}

	/*
	 * Returns the list of cars belonging to the car brand.
	 */
	public LinkedList getCars() {
		return cars;
	}
	
	@Override
	public int compareTo(CarBrand o) {
		if (this.brand.compareToIgnoreCase(o.brand) > 0)
			return 1;
		else if (this.brand.compareToIgnoreCase(o.brand) < 0)
			return -1;
		return 0;
	}
}
