package com.bigcustard.silverbars.model;

public class SummaryLine {

    private static final String TO_STRING_TEMPLATE = "%s: %.1f kg for £%.2f";

    private final Bid bid;
    private final int totalQuantityInGrams;

    public SummaryLine(Bid bid, int totalQuantityInGrams) {

        this.bid = bid;
        this.totalQuantityInGrams = totalQuantityInGrams;
    }

    public Bid getBid() {

        return bid;
    }

    public int getTotalQuantityInGrams() {

        return totalQuantityInGrams;
    }

    public String toString() {

        return String.format(TO_STRING_TEMPLATE, bid.getBuyOrSell(), totalQuantityInGrams / 1000f, bid.getPencePerKg() / 100f);
    }
}
