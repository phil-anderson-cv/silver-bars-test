package com.bigcustard.model;

import org.junit.Test;

import static com.bigcustard.model.OrderType.BUY;
import static com.bigcustard.model.OrderType.SELL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BidTest {

    private static final Bid SAMPLE_BID = new Bid(BUY, 12345);
    private static final Bid EQUIVALENT_BID = new Bid(BUY, 12345);
    private static final Bid DIFFERENT_TYPE_BID = new Bid(SELL, 12345);
    private static final Bid DIFFERENT_PRICE_BID = new Bid(BUY, 98765);

    @Test
    public void equals_returnsTrueIfOtherBidIsEquivalent() {

        assertThat(SAMPLE_BID).isEqualTo(SAMPLE_BID);
        assertThat(SAMPLE_BID).isEqualTo(EQUIVALENT_BID);
    }

    @Test
    public void equals_returnsFalseIfOtherBidIsDifferent() {

        assertThat(SAMPLE_BID).isNotEqualTo(null);
        assertThat(SAMPLE_BID).isNotEqualTo("Not a bid");
        assertThat(SAMPLE_BID).isNotEqualTo(DIFFERENT_TYPE_BID);
        assertThat(SAMPLE_BID).isNotEqualTo(DIFFERENT_PRICE_BID);
    }

    @Test
    public void hashCode_isSameForEquivalentBids() {

        assertThat(SAMPLE_BID.hashCode()).isEqualTo(EQUIVALENT_BID.hashCode());
    }

    @Test
    public void hashCode_isDifferentIfBidsAreDifferent() {

        assertThat(SAMPLE_BID.hashCode()).isNotEqualTo(DIFFERENT_TYPE_BID.hashCode());
        assertThat(SAMPLE_BID.hashCode()).isNotEqualTo(DIFFERENT_PRICE_BID.hashCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_throwsIfNullOrderType() {

        new Bid(null, 12345);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_throwsIfInvalidPrice() {

        new Bid(BUY, -12345);
    }
}
