package com.twu.refactoring;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
		String header = "Rental Record for " + getName() + "\n";
		return header + body() + footer();
	}

	private String footer() {
		return "Amount owed is " + String.valueOf(getTotalAmount()) + "\n" + "You earned " + String.valueOf(getFrequentRenterPoints())
				+ " frequent renter points";
	}

	private String body() {
		return rentalList.stream()
						 .map(rental -> "\t" + rental.getMovie().getTitle() + "\t" + String.valueOf(amountFor(rental)) + "\n")
						 .collect(Collectors.joining(""));

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
