package Proj2;

/* 
 * The Car class represents a car object with properties such as model, year,
 * color, and price.
 */
public class Car implements Comparable<Car> {
	private String model; // The model of the car
	private int year; // The year of the car
	private String color; // The color of the car
	private int price; // The price of the car

	/*
	 * Constructs a Car object with the specified model, year, color, and price.
	 */
	public Car(String model, int year, String color, int price) {
		setModel(model);
		setYear(year);
		setColor(color);
		setPrice(price);
	}

	/*
	 * Returns the model of the car.
	 */
	public String getModel() {
		return model;
	}

	/*
	 * Sets the model of the car.
	 */
	public void setModel(String model) {
		if (model != null)
			this.model = model;
	}

	/*
	 * Returns the year of the car.
	 */
	public int getYear() {
		return year;
	}

	/*
	 * Sets the year of the car.
	 */
	public void setYear(int year) {
		if (year >= 1900)
			this.year = year;
	}

	/*
	 * Returns the color of the car.
	 */
	public String getColor() {
		return color;
	}

	/*
	 * Sets the color of the car.
	 */
	public void setColor(String color) {
		if (color != null)
			this.color = color;
	}

	/*
	 * Returns the price of the car.
	 */
	public int getPrice() {
		return price;
	}

	/*
	 * Sets the price of the car.
	 */
	public void setPrice(int price) {
		if (price > 0)
			this.price = price;
	}

	@Override
	public String toString() {
		String priceNotation;
		if (price % 1000000 == 0)
			priceNotation = (price/1000000)+"M";
		else if (price % 1000 == 0)
			priceNotation = (price/1000)+"K";
		else
			priceNotation = price+"";
		return model + ", " + year + ", " + color + ", " + priceNotation;
	}

	@Override
	public int compareTo(Car o) {
		if (this.year > o.year)
			return 1;
		else if (this.year < o.year)
			return -1;
		return 0;
	}
}
