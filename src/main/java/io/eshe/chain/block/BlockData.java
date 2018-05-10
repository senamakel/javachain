package io.eshe.chain.block;

import io.eshe.chain.exceptions.InvalidTransaction;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class BlockData {
    private List<Transaction> transactions = new ArrayList<>();


    @Override
    public String toString() {
        // todo: convert transactions to string
        return "n";
    }


    /**
     * Helper function to validate all the transactions
     *
     * @return True iff all the transactions are valid.
     */
    public boolean validateTransactions() {
        for (BlockData.Transaction transaction : getTransactions())
            if (!transaction.validate()) return false;
        return true;
    }


    @Data
    public static class Transaction {
        Order buyOrder;
        Order sellOrder;


        public Transaction(Order buyOrder, Order sellOrder) throws InvalidTransaction {
            // check if they are buy and sell orders respectively.
            if (!buyOrder.isBuyOrder || sellOrder.isBuyOrder) throw new InvalidTransaction();

            // and check if the from and to addresses match
            if (!buyOrder.getFromAddress().equals(sellOrder.getToAddress())) throw new InvalidTransaction();
            if (!sellOrder.getFromAddress().equals(buyOrder.getToAddress())) throw new InvalidTransaction();

            this.buyOrder = buyOrder;
            this.sellOrder = sellOrder;
        }


        /**
         * This function validates a transaction by checking the balances of both parties and seeing if they are satisfied.
         * <p>
         * TODO: write unit tests for this
         *
         * @return True iff the transaction is valid.
         */
        public boolean validate() {
            Order aliceOrder = getBuyOrder();
            Order bobOrder = getSellOrder();

            String aliceAddress = aliceOrder.getFromAddress();
            String bobAddress = bobOrder.getFromAddress();

            // Check if the both order's base/asset pairs match with each other
            if (!aliceOrder.getBaseAssetId().equals(bobOrder.getBaseAssetId()) ||
                    !bobOrder.getQuoteAssetId().equals(aliceOrder.getQuoteAssetId()))
                return false;

            // Check if the bid/ask conditions are satisfied
            if (aliceOrder.getPrice() < bobOrder.getPrice()) {
                // If Alice's price is lower than that of Bob's, make sure that alice is selling (Alice will prefer
                // to get more for the price she is selling for).
                if (aliceOrder.getIsBuyOrder())
                    return false;
            } else if (aliceOrder.getPrice() > bobOrder.getPrice()) {
                // If Alice's price is higher than that of Bob's, make sure that alice is buying (Alice will prefer
                // to pay less for the price she is buying for).
                if (bobOrder.getIsBuyOrder())
                    return false;
            } // else alicePrice == bobPrice, which is all good..

            // check alice's and bob's balance
            if (aliceOrder.getIsBuyOrder()) {
                // If Alice is buying, make sure she has enough of the quote asset (that she's giving away) in her account
                // and make sure that Bob has enough of the base asset (that he's giving away) in his account.
                if (Address.getBalance(aliceAddress, aliceOrder.getQuoteAssetId()) < aliceOrder.getVolume())
                    return false;
                if (Address.getBalance(bobAddress, bobOrder.getBaseAssetId()) < bobOrder.getAmount())
                    return false;
            } else {
                // vice-versa
                if (Address.getBalance(bobAddress, bobOrder.getQuoteAssetId()) < bobOrder.getVolume())
                    return false;
                if (Address.getBalance(aliceAddress, aliceOrder.getBaseAssetId()) < aliceOrder.getAmount())
                    return false;
            }

            // Validate the signatures of the orders. This will ensure us that Alice and Bob have validate these orders
            // that the exchange has sent to the miners.
            if (!aliceOrder.verifyOrder(aliceAddress) || !bobOrder.verifyOrder(bobAddress))
                return false;

            return true;
        }
    }
}
