package io.eshe.chain;

import io.eshe.chain.block.BlockData;
import io.eshe.chain.block.Order;
import io.eshe.chain.miner.StoremanNode;
import io.eshe.chain.miner.VerificationNode;
import io.eshe.chain.miner.VoucherNode;

import java.math.BigInteger;

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

        // Test Shamir's key sharing algorithim
//        final Scheme scheme = Scheme.of(2, 2);
//        final byte[] secret = hexStringToByteArray("f4240"); //"hello there".getBytes(StandardCharsets.UTF_8);
//        final Map<Integer, byte[]> parts = scheme.split(secret);
//        final byte[] recovered = scheme.join(parts);
//        System.out.println(new String(recovered, StandardCharsets.UTF_8));
//
//        System.out.println(DatatypeConverter.printHexBinary(secret));
//        System.out.println(DatatypeConverter.printHexBinary(parts.get(1)));
//        System.out.println(DatatypeConverter.printHexBinary(parts.get(2)));
//        try {
//            start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


    public static byte[] hexStringToByteArray(String s) {
        return new BigInteger(s, 16).toByteArray();
    }


    private static void start() throws Exception {
        Thread.sleep(2000);

        // Fund both alice and bob with some bitcoins and usdt. This should ideally be blockchain transactions monitored
        // by the voucher node.
        voucherNode.processNewDeposit("bitcoin", "alice", 10d); // 10 bitcoins to alice
        voucherNode.processNewDeposit("usdt", "bob", 1000d);  // 1000$ to bob

        // Create a buy and sell order
        Order buyOrder = new Order("alice-sig", "bob", "alice", "bitcoin", "usdt", 1d, 100d, true, "exchange1", 0.001);
        Order sellOrder = new Order("bob-sig", "alice", "bob", "bitcoin", "usdt", 1d, 100d, false, "exchange1", 0.001);

        // Create a transaction
        BlockData.Transaction tx = new BlockData.Transaction(buyOrder, sellOrder);

        // Send it to the blockchain
        verificationNode.queueTransaction(tx);

        // Add a breakpoint here to view the state fo the ledger
        Thread.sleep(2000);

//        voucherNode.processNewWithdrawl("bitcoin", "steven", 100d);
    }
}
