package com.bigcustard.silverbars;

import com.bigcustard.silverbars.model.Bid;
import com.bigcustard.silverbars.model.Order;
import com.bigcustard.silverbars.model.SummaryLine;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.multiset.HashMultiSet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.stream.Collectors.toList;

// Not thread-safe
public class LiveOrderBoard {

    // Like a normal set, but keeps a count of occurrences for each item added.
    private final MultiSet<Order> orders = new HashMultiSet<>();
    private final Map<Bid, BigDecimal> summary = new TreeMap<>();

    public void register(Order order) {

        orders.add(order);
        addToSummary(order);
    }

    public void cancel(Order order) {

        if (!orders.remove(order)) {
            throw new NoOrderToCancelException(order);
        }
        removeFromSummary(order);
    }

    public List<SummaryLine> getSummary() {

        return summary.entrySet().stream()
                .map((entry) -> new SummaryLine(entry.getKey(), entry.getValue()))
                .collect(toList());
    }

    private void addToSummary(Order order) {

        Bid bid = order.getBid();
        BigDecimal currentQuantity = getSummaryQuantity(bid);
        summary.put(bid, currentQuantity.add(order.getQuantity()));
    }

    private void removeFromSummary(Order order) {

        Bid bid = order.getBid();
        BigDecimal currentQuantity = getSummaryQuantity(bid);
        BigDecimal newQuantity = currentQuantity.subtract(order.getQuantity());
        if(newQuantity.compareTo(BigDecimal.ZERO) != 0) {
            summary.put(bid, newQuantity);
        } else {
            summary.remove(bid);
        }
    }

    private BigDecimal getSummaryQuantity(Bid bid) {

        BigDecimal quantity = summary.get(bid);
        return quantity != null ? quantity : BigDecimal.ZERO;
    }
}
