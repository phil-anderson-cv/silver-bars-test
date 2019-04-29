package com.bigcustard.silverbars.model;

import java.math.BigDecimal;

public final class SummaryLine {

    private static final String TO_STRING_TEMPLATE = "%s: %.1f kg for £%.2f";

    private final Bid bid;
    private final BigDecimal totalQuantity;

    public SummaryLine(Bid bid, BigDecimal totalQuantity) {

        this.bid = bid;
        this.totalQuantity = totalQuantity;
    }

    public Bid getBid() {

        return bid;
    }

    public BigDecimal getTotalQuantity() {

        return totalQuantity;
    }

    public String toString() {

        return String.format(TO_STRING_TEMPLATE, bid.getBuyOrSell(), totalQuantity, bid.getPricePerKg());
    }
}
