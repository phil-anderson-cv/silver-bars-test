package com.bigcustard.silverbars.model;

import org.junit.Test;

import java.math.BigDecimal;

import static com.bigcustard.silverbars.model.BuyOrSell.BUY;
import static com.bigcustard.silverbars.model.BuyOrSell.SELL;
import static com.bigcustard.silverbars.util.SampleDataHelper.buildBid;
import static com.bigcustard.silverbars.util.SampleDataHelper.buildOrder;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrderTest {


    private static final Order SAMPLE_ORDER = buildOrder("userId", BUY, 1000, 12345);
    private static final Order EQUIVALENT_ORDER = buildOrder("userId", BUY, 1000, 12345);
    private static final Order DIFFERENT_USER_ORDER = buildOrder("otherId", BUY, 1000, 12345);
    private static final Order DIFFERENT_QUANTITY_ORDER = buildOrder("userId", BUY, 9999, 12345);
    private static final Order DIFFERENT_BID_ORDER = buildOrder("userId", SELL, 1000, 12345);

    @Test
    public void equals_returnsTrueIfOtherOrderIsEquivalent() {

        assertThat(SAMPLE_ORDER).isEqualTo(SAMPLE_ORDER);
        assertThat(SAMPLE_ORDER).isEqualTo(EQUIVALENT_ORDER);
    }

    @Test
    public void equals_returnsFalseIfOtherOrderIsDifferent() {

        assertThat(SAMPLE_ORDER).isNotEqualTo(null);
        assertThat(SAMPLE_ORDER).isNotEqualTo("Not an order");
        assertThat(SAMPLE_ORDER).isNotEqualTo(DIFFERENT_USER_ORDER);
        assertThat(SAMPLE_ORDER).isNotEqualTo(DIFFERENT_QUANTITY_ORDER);
        assertThat(SAMPLE_ORDER).isNotEqualTo(DIFFERENT_BID_ORDER);
    }

    @Test
    public void hashCode_isSameForEquivalentOrders() {

        assertThat(SAMPLE_ORDER.hashCode()).isEqualTo(EQUIVALENT_ORDER.hashCode());
    }

    @Test
    public void hashCode_isDifferentForDifferentOrders() {

        assertThat(SAMPLE_ORDER.hashCode()).isNotEqualTo(DIFFERENT_USER_ORDER.hashCode());
        assertThat(SAMPLE_ORDER.hashCode()).isNotEqualTo(DIFFERENT_QUANTITY_ORDER.hashCode());
        assertThat(SAMPLE_ORDER.hashCode()).isNotEqualTo(DIFFERENT_BID_ORDER.hashCode());
    }

    @Test(expected = NullPointerException.class)
    public void constructor_throwsIfUserIdIsNull() {

        new Order(null, BigDecimal.valueOf(1000), buildBid(BUY, 100));
    }

    @Test(expected = NullPointerException.class)
    public void constructor_throwsIfBidIsNull() {

        new Order("userId", BigDecimal.valueOf(1000), null);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_throwsIfQuantityIsNull() {

        new Order("userId", null, buildBid(BUY, 100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_throwsIfInvalidQuantity() {

        new Order("userId", BigDecimal.valueOf(-12345), buildBid(BUY, 12345));
    }
}
