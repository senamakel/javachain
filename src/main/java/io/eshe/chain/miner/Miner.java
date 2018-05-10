package io.eshe.chain.miner;

import io.eshe.chain.block.Address;
import io.eshe.chain.block.Block;
import io.eshe.chain.block.BlockData;
import io.eshe.chain.block.Order;
import io.eshe.chain.chain.Blockchain;

import java.security.DigestException;
import java.security.NoSuchAlgorithmException;

public class Miner {
    public static Block generateNewBlock(BlockData data) throws NoSuchAlgorithmException, DigestException {
        Block previousBlock = Blockchain.getLatestBlock();

        int index = previousBlock.getIndex() + 1;
        long timestamp = System.currentTimeMillis();
        String previousHash = previousBlock.getHash();
        String hash = PoW.generateHash(index, previousHash, timestamp, data);

        return new Block(index, previousHash, timestamp, hash, data);
    }


    public static boolean isNewBlockValid(Block newBlock) throws NoSuchAlgorithmException, DigestException {
        Block previousBlock = Blockchain.getLatestBlock();

        return previousBlock.getIndex() == newBlock.getIndex() - 1 &&
                previousBlock.getHash().equals(newBlock.getPreviousHash()) &&
                PoW.validate(newBlock.getHash());
    }


    private void validateNewOrders(BlockData data) {
        for (BlockData.OrderPair pair : data.getOrderpairs()) validateOrderPair(pair);
    }

    // Alice wants to buy 5 BTC for 10  $ ->            0.5 BTC/$ vol:
    // Bob   wants to buy   15$ for 6 BTC -> 2 $/BTC -> 0.4 BTC/$

    // Alice wants to buy   5 BTC for 10$ -> price
    // Bob   wants to sell  6 BTC for 15$ -> price
    // Alice -> 0$ 5BTC ----- Bob -> 5$ 1BTC


    // Bob has more volumed
    // Alice wants to buy  5 BTC for 10$/BTC vol: 50$
    // Bob   wants to sell 6 BTC for 10$/BTC vol: 60$
    // ---->


    /**
     * This function validates an OrderPair by checking the balances of both parties and seeing if they are satisfied.
     * <p>
     * TODO: write unit tests for this
     *
     * @param pair
     * @return True iff the order pair is valid.
     */
    private boolean validateOrderPair(BlockData.OrderPair pair) {
        Order aliceOrder = pair.getA();
        Order bobOrder = pair.getB();

        String aliceAddress = aliceOrder.getFromAddress();
        String bobAddress = bobOrder.getFromAddress();

        // Check if the both order's base/asset pairs match with each other
        if (!aliceOrder.getBaseAssetId().equals(bobOrder.getBaseAssetId()) ||
                !bobOrder.getQuoteAssetId().equals(aliceOrder.getQuoteAssetId()))
            return false;

        // Check if one is a buy order and the other is a sell order
        if (aliceOrder.getIsBuyOrder() == bobOrder.getIsBuyOrder()) return false;

        // Check if the bid/ask conditions are satisfied
        if (aliceOrder.getPrice() < bobOrder.getPrice()) {
            // If Alice's price is lower than that of Bob's, make sure that alice is selling (Alice will prefer
            // to get more for the price she is selling for).
            if (aliceOrder.getIsBuyOrder()) return false;
        } else if (aliceOrder.getPrice() > bobOrder.getPrice()) {
            // If Alice's price is higher than that of Bob's, make sure that alice is buying (Alice will prefer
            // to pay less for the price she is buying for).
            if (bobOrder.getIsBuyOrder()) return false;
        } else {
            // else alicePrice == bobPrice, which is all good..
        }

        // check alice's and bob's balance
        if (aliceOrder.getIsBuyOrder()) {
            if (Address.getBalance(aliceAddress, aliceOrder.getQuoteAssetId()) < aliceOrder.getVolume()) return false;
            if (Address.getBalance(bobAddress, bobOrder.getBaseAssetId()) > 0) return false;
        } else {
            if (Address.getBalance(bobAddress, bobOrder.getQuoteAssetId()) < bobOrder.getVolume()) return false;
            if (Address.getBalance(aliceAddress, aliceOrder.getBaseAssetId()) > 0) return false;
        }

        // Validate the signatures of the orders
        if (!aliceOrder.verifyOrder(aliceAddress) || !bobOrder.verifyOrder(bobAddress))
            return false;

        return true;
    }
}

