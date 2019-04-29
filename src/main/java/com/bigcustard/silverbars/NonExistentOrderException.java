package com.bigcustard.silverbars;

import com.bigcustard.silverbars.model.Order;

public class NonExistentOrderException extends RuntimeException {

    public NonExistentOrderException(Order order) {

        super("Order does not exist: " + order);
    }
}
