package io.eshe.chain.chain;

import io.eshe.chain.block.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class Blockchain {
    private static Stack<Block> blockchain;
    private static Map<String, Map<String, Double>> addresses = new HashMap<>();


    static {
        initBlockchain();
    }


    public static Block getLatestBlock() {
        return blockchain.peek();
    }


    public static Address getAddress(String address) {
        Map<String, Double> balances = addresses.get(address);

        Address address1 = new Address(address);
        if (balances != null) address1.setBalances(balances);
        return address1;
    }


    public static void updateAddress(Address address) {
        addresses.put(address.getAddress(), address.getBalances());
    }


    public static void pushNewBlock(Block newBlock) {
        blockchain.push(newBlock);

        // Whenever a new block is pushed, we read it and update all account balances
        // todo: throw a validation error if any
        for (BlockData.Transaction tx : newBlock.getData().getTransactions()) {
            Order buyOrder = tx.getBuyOrder();
            Order sellOrder = tx.getSellOrder();

            String baseAssetId = buyOrder.getBaseAssetId();
            String quoteAssetId = buyOrder.getQuoteAssetId();

            Address alice = getAddress(buyOrder.getFromAddress());
            Address bob = getAddress(buyOrder.getToAddress());

            // TODO: this does not take care of partial orders
            alice.setBalance(baseAssetId, alice.getBalance(baseAssetId) + sellOrder.getAmount() - buyOrder.getTransactionFeeAmount());
            alice.setBalance(quoteAssetId, alice.getBalance(quoteAssetId) - buyOrder.getVolume());

            bob.setBalance(baseAssetId, bob.getBalance(baseAssetId) - sellOrder.getAmount() - sellOrder.getTransactionFeeAmount());
            bob.setBalance(quoteAssetId, bob.getBalance(quoteAssetId) + buyOrder.getVolume());

            // Process the transaction fee for the exchange
            Address tx1address = getAddress(buyOrder.getTransactionFeeAddress());
            Address tx2address = getAddress(sellOrder.getTransactionFeeAddress());
            tx1address.increaseBalance(buyOrder.getBaseAssetId(), buyOrder.getTransactionFeeAmount());
            tx2address.increaseBalance(sellOrder.getBaseAssetId(), sellOrder.getTransactionFeeAmount());

            updateAddress(alice);
            updateAddress(bob);
            updateAddress(tx1address);
            updateAddress(tx2address);
        }

        return;
    }


    private static void initBlockchain() {
        blockchain = new Stack<>();
        blockchain.push(BlockUtil.generateGenesisBlock());
    }
}
