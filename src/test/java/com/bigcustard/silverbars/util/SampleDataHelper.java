package com.bigcustard.silverbars.util;

import com.bigcustard.silverbars.model.Bid;
import com.bigcustard.silverbars.model.BuyOrSell;
import com.bigcustard.silverbars.model.Order;

import java.math.BigDecimal;

public class SampleDataHelper {

    public static Bid buildBid(BuyOrSell buyOrSell, double pricePerKg) {

        return new Bid(buyOrSell, BigDecimal.valueOf(pricePerKg));
    }

    public static Order buildOrder(String userId, BuyOrSell buyOrSell, double quantity, double pricePerKg) {

        return new Order(userId, BigDecimal.valueOf(quantity), buildBid(buyOrSell, pricePerKg));
    }
}
