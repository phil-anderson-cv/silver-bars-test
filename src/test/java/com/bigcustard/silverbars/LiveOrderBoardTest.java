package com.bigcustard.silverbars;

import com.bigcustard.silverbars.model.BuyOrSell;
import com.bigcustard.silverbars.model.Order;
import com.bigcustard.silverbars.model.SummaryLine;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.bigcustard.silverbars.model.BuyOrSell.BUY;
import static com.bigcustard.silverbars.model.BuyOrSell.SELL;
import static com.bigcustard.silverbars.util.SampleDataHelper.buildOrder;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LiveOrderBoardTest {

    private OrderBoardTestHelper helper;

    @Before
    public void SetUp() {

        helper = new OrderBoardTestHelper();
    }

    @Test
    public void getSummary_worksForProvidedExample() {

        helper.registerOrder("testUser", SELL, 3.5, 306)
              .registerOrder("testUser", SELL, 1.2, 310)
              .registerOrder("testUser", SELL, 1.5, 307)
              .registerOrder("testUser", SELL, 2.0, 306)

              .validateSummary("SELL: 5.5 kg for £306.00",
                               "SELL: 1.5 kg for £307.00",
                               "SELL: 1.2 kg for £310.00");
    }

    @Test
    public void register_registersOrders() {

        helper.registerOrder("testUser", BUY, 1.0, 100)
              .registerOrder("otherUser", SELL, 1.0, 100)

              .validateSummary("BUY: 1.0 kg for £100.00",
                               "SELL: 1.0 kg for £100.00");
    }

    @Test
    public void cancel_cancelsOrders() {

        helper.registerOrder("testUser", BUY, 1.0, 100)
              .registerOrder("testUser", BUY, 1.0, 100)
              .validateSummary("BUY: 2.0 kg for £100.00")

              .cancelOrder("testUser", BUY, 1.0, 100)
              .validateSummary("BUY: 1.0 kg for £100.00")

              .cancelOrder("testUser", BUY, 1.0, 100)
              .validateSummary(); // Should be empty
    }

    @Test
    public void cancel_cancellingNonExistentOrdersThrowsAnException() {

        helper.registerOrder("testUser", BUY, 1.0, 100)

              .cancelOrder("otherUser", BUY, 1.0, 100)
              .cancelOrder("testUser", BUY, 9.9, 100)
              .cancelOrder("testUser", SELL, 1.0, 100)
              .cancelOrder("testUser", BUY, 1.0, 999)

              .validateExceptionCount(4);
    }

    @Test
    public void getSummary_groupsEquivalentBidsTogether() {

        helper.registerOrder("testUser", BUY, 1.0, 100)
              .registerOrder("testUser", SELL, 1.0, 100)
              .registerOrder("testUser", BUY, 2.0, 100)
              .registerOrder("testUser", SELL, 2.0, 100)

              .validateSummary("BUY: 3.0 kg for £100.00",
                               "SELL: 3.0 kg for £100.00");
    }

    @Test
    public void getSummary_ignoresUserWhenGrouping() {

        helper.registerOrder("firstUser", BUY, 1.0, 100)
              .registerOrder("secondUser", BUY, 2.0, 100)
              .registerOrder("thirdUser", BUY, 3.0, 100)

              .validateSummary("BUY: 6.0 kg for £100.00");
    }

    @Test
    public void getSummary_hasBuysBeforeSells() {

        helper.registerOrder("testUser", SELL, 1.0, 100)
              .registerOrder("testUser", BUY, 1.0, 100)
              .registerOrder("otherUser", SELL, 1.0, 100)
              .registerOrder("otherUser", BUY, 1.0, 100)

              .validateSummary("BUY: 2.0 kg for £100.00",
                               "SELL: 2.0 kg for £100.00");
    }

    @Test
    public void getSummary_hasAscendingPriceOrderForSells() {

        helper.registerOrder("testUser", SELL, 1.0, 444)
              .registerOrder("testUser", SELL, 2.0, 333)
              .registerOrder("testUser", SELL, 3.0, 222)
              .registerOrder("testUser", SELL, 4.0, 111)

              .validateSummary("SELL: 4.0 kg for £111.00",
                               "SELL: 3.0 kg for £222.00",
                               "SELL: 2.0 kg for £333.00",
                               "SELL: 1.0 kg for £444.00");
    }

    @Test
    public void getSummary_hasDescendingPriceOrderForBuys() {

        helper.registerOrder("testUser", BUY, 4.0, 111)
              .registerOrder("testUser", BUY, 3.0, 222)
              .registerOrder("testUser", BUY, 2.0, 333)
              .registerOrder("testUser", BUY, 1.0, 444)

              .validateSummary("BUY: 1.0 kg for £444.00",
                               "BUY: 2.0 kg for £333.00",
                               "BUY: 3.0 kg for £222.00",
                               "BUY: 4.0 kg for £111.00");
    }

    @Test
    public void getSummary_groupsAndSortsMixedOrders(){

        helper.registerOrder("testUser", BUY, 1.3, 100)
              .registerOrder("testUser", SELL, 2.4, 100)
              .registerOrder("testUser", BUY, 2.1, 100)
              .registerOrder("testUser", BUY, 1.9, 200)
              .registerOrder("otherUser", SELL, 3.0, 200)
              .registerOrder("otherUser", SELL, 1.8, 200)
              .registerOrder("otherUser", BUY, 1.0, 300)
              .registerOrder("otherUser", SELL, 3.0, 400)

              .validateSummary("BUY: 1.0 kg for £300.00",
                               "BUY: 1.9 kg for £200.00",
                               "BUY: 3.4 kg for £100.00",
                               "SELL: 2.4 kg for £100.00",
                               "SELL: 4.8 kg for £200.00",
                               "SELL: 3.0 kg for £400.00");
  }

    private class OrderBoardTestHelper {

        private final LiveOrderBoard orderBoard;
        private int exceptionCount;

        private OrderBoardTestHelper() {

            this.orderBoard = new LiveOrderBoard();
        }

        private OrderBoardTestHelper registerOrder(String userId, BuyOrSell buyOrSell, double quantity, double pricePerKg) {

            Order order = buildOrder(userId, buyOrSell, quantity, pricePerKg);
            orderBoard.register(order);
            return this;
        }

        private OrderBoardTestHelper cancelOrder(String userId, BuyOrSell buyOrSell, double quantity, double pricePerKg) {

            Order order = buildOrder(userId, buyOrSell, quantity, pricePerKg);
            try {
                orderBoard.cancel(order);
            } catch(NoOrderToCancelException e) {
                exceptionCount++;
            }
            return this;
        }

        private OrderBoardTestHelper validateSummary(String... expectedSummaryLines) {

            List<SummaryLine> summary = orderBoard.getSummary();
            assertThat(summary.size()).isEqualTo(expectedSummaryLines.length);

            int i = 0;
            for(SummaryLine summaryLine : summary) {
                assertThat(summaryLine.toString()).isEqualTo(expectedSummaryLines[i++]);
            }
            return this;
        }

        private OrderBoardTestHelper validateExceptionCount(int expectedExceptionCount) {

            assertThat(exceptionCount).isEqualTo(expectedExceptionCount);
            exceptionCount = 0;
            return this;
        }
    }
}
