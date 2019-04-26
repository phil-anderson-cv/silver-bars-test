package com.bigcustard.silverbars.model;

import java.util.function.IntBinaryOperator;

public enum OrderType {

    BUY((price1, price2) -> 0 - Integer.compare(price1, price2)),  // Buys sort by descending price
    SELL(Integer::compare);                                        // Sells sort by ascending price

    private final IntBinaryOperator priceComparator;

    OrderType(IntBinaryOperator priceComparator) {

        this.priceComparator = priceComparator;
    }

    public int comparePrices(int price1, int price2) {

        return priceComparator.applyAsInt(price1, price2);
    }
}
