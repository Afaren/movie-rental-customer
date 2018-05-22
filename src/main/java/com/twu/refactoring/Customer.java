package com.twu.refactoring;

import java.util.ArrayList;
import java.util.Iterator;

public class Customer {

	private String name;
	private ArrayList<Rental> rentalList = new ArrayList<Rental>();

	public Customer(String name) {
		this.name = name;
	}

	public void addRental(Rental arg) {
		rentalList.add(arg);
	}

	public String getName() {
		return name;
	}

	public String statement() {
		int frequentRenterPoints = 0;
		Iterator<Rental> rentals = rentalList.iterator();
		String result = "Rental Record for " + getName() + "\n";
		while (rentals.hasNext()) {
			Rental each = rentals.next();
			// show figures for this rental
			result += "\t" + each.getMovie().getTitle() + "\t"
					+ String.valueOf(amountFor(each)) + "\n";
		}
		// add footer lines
		result += "Amount owed is " + String.valueOf(getTotalAmount()) + "\n";
		result += "You earned " + String.valueOf(getFrequentRenterPoints())
				+ " frequent renter points";
		return result;
	}

	private int getFrequentRenterPoints() {
		return rentalList.stream()
						 .mapToInt(rental -> {
							 // add frequent renter points
							 int frequentRenterPoints = 1;
							 // add bonus for a two day new release rental
							 if ((rental.getMovie().getPriceCode() == Movie.NEW_RELEASE)
									 && rental.getDaysRented() > 1)
								 frequentRenterPoints++;
							 return frequentRenterPoints;
						 })
						 .sum();
	}

	private double getTotalAmount() {
		return rentalList.stream()
						 .mapToDouble(this::amountFor)
						 .sum();
	}

	private double amountFor(Rental each) {
		// determine amounts for each line
		double thisAmount = 0;
		switch (each.getMovie().getPriceCode()) {
        case Movie.REGULAR:
            thisAmount += 2;
            if (each.getDaysRented() > 2)
                thisAmount += (each.getDaysRented() - 2) * 1.5;
            break;
        case Movie.NEW_RELEASE:
            thisAmount += each.getDaysRented() * 3;
            break;
        case Movie.CHILDRENS:
            thisAmount += 1.5;
            if (each.getDaysRented() > 3)
                thisAmount += (each.getDaysRented() - 3) * 1.5;
            break;

        }
		return thisAmount;
	}

}
