package com.bigcustard.silverbars;

import com.bigcustard.silverbars.model.Order;

public class NoOrderToCancelException extends RuntimeException {

    public NoOrderToCancelException(Order order) {

        super("Can't cancel non-existent order: " + order);
    }
}
