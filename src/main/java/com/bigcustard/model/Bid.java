package com.bigcustard.model;

public class Bid {

    private final OrderType buyOrSell;
    private final int pricePerKg; // Prices held in pence to avoid rounding issues.

    public Bid(OrderType buyOrSell, int pricePerKg) {

        this.buyOrSell = buyOrSell;
        this.pricePerKg = pricePerKg;
    }

    public OrderType getBuyOrSell() {

        return buyOrSell;
    }

    public double getPricePerKg() {

        return pricePerKg;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Bid)) return false;

        Bid other = (Bid) o;
        return this.buyOrSell == other.buyOrSell && this.pricePerKg == other.pricePerKg;
    }

    @Override
    public int hashCode() {

        return pricePerKg * OrderType.values().length + buyOrSell.ordinal();
    }
}
