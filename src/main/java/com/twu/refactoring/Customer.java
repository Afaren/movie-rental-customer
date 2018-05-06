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
        return "<H1>Rentals for <EM>Dinsdale Pirhana</EM></H1><P>" +
                "Monty Python and the Holy Grail: 3.5<BR>" +
                "Ran: 2.0<BR>LA Confidential: 6.0<BR>" +
                "Star Trek 13.2: 3.0<BR>" +
                "Wallace and Gromit: 6.0<BR><P>" +
                "You owe <EM>20.5</EM><P>" +
                "On this rental you earned <EM>6</EM> frequent renter points<P>";
    }
}
