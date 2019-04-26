package com.bigcustard.silverbars.model;

import org.junit.Test;

import static com.bigcustard.silverbars.model.OrderType.BUY;
import static com.bigcustard.silverbars.model.OrderType.SELL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrderTest {

    private static final Order SAMPLE_ORDER = new Order("userId", 1000, BUY, 12345);
    private static final Order EQUIVALENT_ORDER = new Order("userId", 1000, BUY, 12345);
    private static final Order DIFFERENT_USER_ORDER = new Order("otherId", 1000, BUY, 12345);
    private static final Order DIFFERENT_QUANTITY_ORDER = new Order("userId", 9999, BUY, 12345);
    private static final Order DIFFERENT_BID_ORDER = new Order("userId", 1000, SELL, 12345);

    @Test
    public void equals_returnsTrueIfOtherOrderEquivalent() {

        assertThat(SAMPLE_ORDER).isEqualTo(SAMPLE_ORDER);
        assertThat(SAMPLE_ORDER).isEqualTo(EQUIVALENT_ORDER);
    }

    @Test
    public void equals_returnsFalseIfOtherOrderDifferent() {

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
    public void hashCode_canBeAffectedByDataChanges() {

        assertThat(SAMPLE_ORDER.hashCode()).isNotEqualTo(DIFFERENT_USER_ORDER.hashCode());
        assertThat(SAMPLE_ORDER.hashCode()).isNotEqualTo(DIFFERENT_QUANTITY_ORDER.hashCode());
        assertThat(SAMPLE_ORDER.hashCode()).isNotEqualTo(DIFFERENT_BID_ORDER.hashCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_throwsIfBidIsNull() {

        new Order("userId", 1000, null);
    }
}
