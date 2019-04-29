package com.bigcustard.silverbars.model;

import org.junit.Test;

import java.math.BigDecimal;

import static com.bigcustard.silverbars.model.BuyOrSell.BUY;
import static com.bigcustard.silverbars.model.BuyOrSell.SELL;
import static com.bigcustard.silverbars.util.SampleDataHelper.buildBid;
import static org.assertj.core.api.Assertions.assertThat;

public class BidTest {

    private static final Bid SAMPLE_BUY = buildBid(BUY, 12345);
    private static final Bid EQUIVALENT_BUY = buildBid(BUY, 12345);
    private static final Bid SAMPLE_SELL = buildBid(SELL, 12345);
    private static final Bid HIGH_PRICE_BUY = buildBid(BUY, 98765);
    private static final Bid HIGH_PRICE_SELL = buildBid(SELL, 98765);

    @Test
    public void compareTo_isEqualIfBidsAreTheSame() {

        assertThat(SAMPLE_BUY).isEqualByComparingTo(SAMPLE_BUY);
        assertThat(SAMPLE_BUY).isEqualByComparingTo(EQUIVALENT_BUY);
    }

    @Test
    public void compareTo_putsBuysBeforeSells() {

        assertThat(SAMPLE_BUY).isLessThan(SAMPLE_SELL);
        assertThat(SAMPLE_SELL).isGreaterThan(SAMPLE_BUY);
    }

    @Test
    public void compareTo_ordersByAscendingPriceForSells() {

        assertThat(SAMPLE_SELL).isLessThan(HIGH_PRICE_SELL);
        assertThat(HIGH_PRICE_SELL).isGreaterThan(SAMPLE_SELL);
    }

    @Test
    public void compareTo_ordersByDescendingPriceForBuys() {

        assertThat(HIGH_PRICE_BUY).isLessThan(SAMPLE_BUY);
        assertThat(SAMPLE_BUY).isGreaterThan(HIGH_PRICE_BUY);
    }

    @Test
    public void equals_returnsTrueIfOtherBidIsEquivalent() {

        assertThat(SAMPLE_BUY).isEqualTo(SAMPLE_BUY);
        assertThat(SAMPLE_BUY).isEqualTo(EQUIVALENT_BUY);
    }

    @Test
    public void equals_returnsFalseIfOtherBidIsDifferent() {

        assertThat(SAMPLE_BUY).isNotEqualTo(null);
        assertThat(SAMPLE_BUY).isNotEqualTo("Not a bid");
        assertThat(SAMPLE_BUY).isNotEqualTo(SAMPLE_SELL);
        assertThat(SAMPLE_BUY).isNotEqualTo(HIGH_PRICE_BUY);
    }

    @Test
    public void hashCode_isSameForEquivalentBids() {

        assertThat(SAMPLE_BUY.hashCode()).isEqualTo(EQUIVALENT_BUY.hashCode());
    }

    @Test
    public void hashCode_isDifferentForDifferentBids() {

        assertThat(SAMPLE_BUY.hashCode()).isNotEqualTo(SAMPLE_SELL.hashCode());
        assertThat(SAMPLE_BUY.hashCode()).isNotEqualTo(HIGH_PRICE_BUY.hashCode());
    }

    @Test(expected = NullPointerException.class)
    public void constructor_throwsIfNullBuyOrSell() {

        new Bid(null, BigDecimal.valueOf(12345));
    }

    @Test(expected = NullPointerException.class)
    public void constructor_throwsIfNullPrice() {

        new Bid(BUY, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_throwsIfInvalidPrice() {

        new Bid(BUY, BigDecimal.valueOf(-12345));
    }
}
