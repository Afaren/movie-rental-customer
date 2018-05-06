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
        return head() + body() + foot();
    }

    private String head() {
        return "Rental Record for " + getName() + "\n";
    }

    private String body() {
        return rentalList.stream()
                         .map(rental -> "\t" + rental.getMovie().getTitle() + "\t" + String.valueOf(rental.getCharge()) + "\n")
                         .collect(Collectors.joining(""));
    }

    private String foot() {
        return "Amount owed is " + String.valueOf(getTotalAmount()) + "\n" +
        "You earned " + String.valueOf(getFrequentRenterPoints()) + " frequent renter points";
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

    public String htmlStatement() {
        return htmlHead() + htmlBody() + htmlFoot();
    }

    private String htmlFoot() {
        return "<P>You owe <EM>" + getTotalAmount() + "</EM><P>" +
                "On this rental you earned <EM>" + getFrequentRenterPoints() + "</EM> frequent renter points<P>";
    }

    private String htmlHead() {
        return "<H1>Rentals for <EM>" + getName() + "</EM></H1><P>";
    }

    private String htmlBody() {
        return rentalList.stream()
                         .map(rental -> rental.getMovie() .getTitle() + ": " + String.valueOf(rental.getCharge()) + "<BR>")
                         .collect(Collectors.joining(""));
    }
}
