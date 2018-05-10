package io.eshe.chain.miner;

import io.eshe.chain.block.Address;
import io.eshe.chain.block.Block;
import io.eshe.chain.block.BlockData;
import io.eshe.chain.block.Order;
import io.eshe.chain.chain.Blockchain;

import java.security.DigestException;
import java.security.NoSuchAlgorithmException;

public class Miner {
    /**
     * Given the blockdata, generate a new block
     *
     * @param data The block data to include
     * @return The newly generated block
     */
    public static Block generateNewBlock(BlockData data) throws NoSuchAlgorithmException, DigestException {
        Block previousBlock = Blockchain.getLatestBlock();

        int index = previousBlock.getIndex() + 1;
        long timestamp = System.currentTimeMillis();
        String previousHash = previousBlock.getHash();
        String hash = PoW.generateHash(index, previousHash, timestamp, data);

        return new Block(index, previousHash, timestamp, hash, data);
    }


    /**
     * Check if a given block is valid
     *
     * @param newBlock The block to check
     * @return True iff the block is valid
     */
    public static boolean isNewBlockValid(Block newBlock) throws NoSuchAlgorithmException, DigestException {
        Block previousBlock = Blockchain.getLatestBlock();

        return previousBlock.getIndex() == newBlock.getIndex() - 1 &&
                previousBlock.getHash().equals(newBlock.getPreviousHash()) &&
                validateTransactions(newBlock.getData()) &&
                PoW.validate(newBlock.getHash());
    }


    /**
     * Helper function to validate all the transactions
     *
     * @param data The block data
     * @return True iff all the transaction is valid.
     */
    private static boolean validateTransactions(BlockData data) {
        for (BlockData.Transaction transaction : data.getTransactions())
            if (!validateTransaction(transaction)) return false;
        return true;
    }


    /**
     * This function validates a transaction by checking the balances of both parties and seeing if they are satisfied.
     * <p>
     * TODO: write unit tests for this
     *
     * @param transaction The transaction to validate
     * @return True iff the transaction is valid.
     */
    private static boolean validateTransaction(BlockData.Transaction transaction) {
        Order aliceOrder = transaction.getA();
        Order bobOrder = transaction.getB();

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
        } // else alicePrice == bobPrice, which is all good..

        // check alice's and bob's balance
        if (aliceOrder.getIsBuyOrder()) {
            // If Alice is buying, make sure she has enough of the quote asset (that she's giving away) in her account
            // and make sure that Bob has enough of the base asset (that he's giving away) in his account.
            if (Address.getBalance(aliceAddress, aliceOrder.getQuoteAssetId()) < aliceOrder.getVolume()) return false;
            if (Address.getBalance(bobAddress, bobOrder.getBaseAssetId()) < bobOrder.getAmount()) return false;
        } else {
            // vice-versa
            if (Address.getBalance(bobAddress, bobOrder.getQuoteAssetId()) < bobOrder.getVolume()) return false;
            if (Address.getBalance(aliceAddress, aliceOrder.getBaseAssetId()) < aliceOrder.getAmount()) return false;
        }

        // Validate the signatures of the orders. This will ensure us that Alice and Bob have validate these orders
        // that the exchange has sent to the miners.
        if (!aliceOrder.verifyOrder(aliceAddress) || !bobOrder.verifyOrder(bobAddress))
            return false;

        return true;
    }
}

