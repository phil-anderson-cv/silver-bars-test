package com.bigcustard.model;

import org.junit.Test;

import static com.bigcustard.model.OrderType.BUY;
import static com.bigcustard.model.OrderType.SELL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BidTest {

    private static final Bid buyBid = new Bid(BUY, 12345);
    private static final Bid sellBid = new Bid(SELL, 12345);
    private static final Bid equivalentBuyBid = new Bid(BUY, 12345);
    private static final Bid highBuyBid = new Bid(BUY, 98765);

    @Test
    public void equals_returnsTrueIfOtherBidIsEquivalent() {

        assertThat(buyBid).isEqualTo(buyBid);
        assertThat(buyBid).isEqualTo(equivalentBuyBid);
    }

    @Test
    public void equals_returnsFalseIfOtherBidIsDifferent() {

        assertThat(buyBid).isNotEqualTo(null);
        assertThat(buyBid).isNotEqualTo("Not a bid");
        assertThat(buyBid).isNotEqualTo(sellBid);
        assertThat(buyBid).isNotEqualTo(highBuyBid);
    }

    @Test
    public void hashCode_isAffectedByOrderType() {

        assertThat(buyBid.hashCode()).isNotEqualTo(sellBid.hashCode());
    }

    @Test
    public void hashCode_isAffectedByPrice() {

        Bid lowBid = new Bid(BUY, 12345);
        Bid highBid = new Bid(BUY, 98765);
        assertThat(lowBid.hashCode()).isNotEqualTo(highBid.hashCode());
    }
}
