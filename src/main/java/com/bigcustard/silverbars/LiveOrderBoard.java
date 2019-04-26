package com.bigcustard.silverbars;

import com.bigcustard.silverbars.model.Bid;
import com.bigcustard.silverbars.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class LiveOrderBoard {

    private final List<Order> orders = new ArrayList<>();
    private final TreeMap<Bid, Integer> summary = new TreeMap<>();

    public void register(Order order) {

    }

    public void cancel(Order order) {

    }
}
