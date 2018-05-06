package com.twu.refactoring;

public class Movie {
	public static final int  CHILDRENS = 2;
	public static final int  REGULAR = 0;
	public static final int  NEW_RELEASE = 1;

	private String title;
	private Price price;

	public Movie(String title, int priceCode) {
		this.title = title;
		setPriceCode(priceCode);
	}

	public int getPriceCode() {
		return price.getPriceCode();
	}

	public void setPriceCode(int arg) {
        switch (arg) {
            case REGULAR:
                price = new RegularPrice();
                break;
            case NEW_RELEASE:
                price = new NewReleasePrice();
                break;
            case CHILDRENS:
                price = new ChildrensPrice();
                break;
            default:
                throw new IllegalArgumentException("Incorrect price code");
        }
    }

	public String getTitle () {
		return title;
	}

	double getCharge(int daysRented) {
        return price.getCharge(daysRented);
    }

	int getFrequentRenterPoints(int daysRented) {
        // add bonus for a two day new release rental
        if ((getPriceCode() == NEW_RELEASE) && daysRented > 1)
            return 2;
        return 1;
    }
}

