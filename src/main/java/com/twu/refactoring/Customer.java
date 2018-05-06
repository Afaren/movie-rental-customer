package com.twu.refactoring;

import java.util.ArrayList;
import java.util.Iterator;
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
        String result = "Rental Record for " + getName() + "\n";
        result += getBody();
        // add footer lines
        result += "Amount owed is " + String.valueOf(getTotalAmount()) + "\n";
        result += "You earned " + String.valueOf(getFrequentRenterPoints()) + " frequent renter points";
        return result;
    }

    private String getBody() {
        return rentalList.stream()
                         .map(rental -> "\t" + rental.getMovie().getTitle() + "\t" + String.valueOf(rental.getCharge()) + "\n")
                         .collect(Collectors.joining(""));
    }

    private int getFrequentRenterPoints() {
        return rentalList.stream()
                         .mapToInt(Rental::getFrequentRenterPoint)
                         .sum();

    }

    private double getTotalAmount() {
        return rentalList.stream()
                         .mapToDouble(Rental::getCharge)
                         .sum();
    }
}
