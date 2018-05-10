package io.eshe.chain;

import io.eshe.chain.block.BlockData;
import io.eshe.chain.block.Order;
import io.eshe.chain.miner.StoremanNode;
import io.eshe.chain.miner.VerificationNode;
import io.eshe.chain.miner.VoucherNode;

public class Main {
    static StoremanNode storemanNode;
    static VerificationNode verificationNode;
    static VoucherNode voucherNode;


    public static void main(String[] args) {
        storemanNode = new StoremanNode();
        verificationNode = new VerificationNode();
        voucherNode = new VoucherNode();

        storemanNode.start();
        verificationNode.start();
        voucherNode.start();

        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void start() throws Exception {
        Thread.sleep(2000);

        // Fund both alice and bob with some bitcoins and usdt
        voucherNode.processNewDeposit("bitcoin", "alice", 10d); // 10 bitcoins to alice
        voucherNode.processNewDeposit("usdt", "bob", 1000d);  // 1000$ to bob

        // Create a buy and sell order
        Order buyOrder = new Order("alice-sig", "bob", "alice", "bitcoin", "usdt", 1d, 100d, true, "exchange1", 0.001);
        Order sellOrder = new Order("bob-sig", "alice", "bob", "bitcoin", "usdt", 1d, 100d, false, "exchange1", 0.001);

        BlockData.Transaction tx = new BlockData.Transaction(buyOrder, sellOrder);
        verificationNode.queueTransaction(tx);

        Thread.sleep(2000);

//        voucherNode.processNewWithdrawl("bitcoin", "steven", 100d);
    }
}
