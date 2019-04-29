package com.bigcustard.silverbars;

import com.bigcustard.silverbars.model.Bid;
import com.bigcustard.silverbars.model.Order;
import com.bigcustard.silverbars.model.SummaryLine;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static java.util.stream.Collectors.toList;

// Not thread-safe
public class LiveOrderBoard {

    private final List<Order> orders = new ArrayList<>();
    private final TreeMap<Bid, Integer> summary = new TreeMap<>();

    public void register(Order order) {

        orders.add(order);
        addToSummary(order);
    }

    public void cancel(Order order) {

        if (orders.remove(order)) { // Only remove from summary if there was an order present
            removeFromSummary(order);
        }
    }

    public List<SummaryLine> getSummary() {

        return summary.entrySet().stream()
                .map((entry) -> new SummaryLine(entry.getKey(), entry.getValue()))
                .collect(toList());
    }

    private void addToSummary(Order order) {

        Bid bid = order.getBid();
        int quantity = getSummaryQuantity(bid);
        summary.put(bid, quantity + order.getQuantityInGrams());
    }

    private void removeFromSummary(Order order) {

        Bid bid = order.getBid();
        int quantity = getSummaryQuantity(bid);
        int newQuantity = quantity - order.getQuantityInGrams();
        if(newQuantity != 0) {
            summary.put(bid, newQuantity);
        } else {
            summary.remove(bid);
        }
    }

    private int getSummaryQuantity(Bid bid) {

        Integer quantity = summary.get(bid);
        return quantity != null ? quantity : 0;
    }
}
