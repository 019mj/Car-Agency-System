package Proj2;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Order {
	private String name; // The name of the customer placing the order.
	private String mobile; // The mobile number of the customer.
	private String brand; // The brand of the car being ordered.
	private Car car; // The details of the car being ordered.
	private GregorianCalendar orderDate = new GregorianCalendar(); // The date the order was placed.

	public Order(String name, String mobile, String brand, Car car, int day, int month, int year) {
		setName(name);
		setMobile(mobile);
		setBrand(brand);
		setCar(car);
		setOrderDate(day, month, year);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name != null)
			this.name = name; // Sets the name of the customer.
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		if (mobile != null)
			this.mobile = mobile; // Sets the mobile number of the customer.
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		if (brand != null)
			this.brand = brand; // Sets the brand of the car being ordered.
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		if (car != null)
			this.car = car; // Sets the details of the car being ordered.
	}

	public GregorianCalendar getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(int day, int month, int year) {
		orderDate.set(Calendar.YEAR, year); // Sets the year of the order date.
		orderDate.set(Calendar.MONTH, month - 1); // Sets the month of the order date.
		orderDate.set(Calendar.DATE, day); // Sets the day of the order date.
	}

	@Override
	public String toString() {
		return name + ", " + mobile + ", " + brand + ", " + car.toString() + ", " + orderDate.get(Calendar.DATE) + "/"
				+ (orderDate.get(Calendar.MONTH) + 1) + "/" + orderDate.get(Calendar.YEAR); // Returns a string representation of the Order object.
	}

}
