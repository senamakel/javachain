package io.eshe.chain.miner;

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
        for (BlockData.OrderPair pair : data.getOrderpairs()) {
            String aliceAddress = pair.getA().getFromAddress();
            String bobAddress = pair.getB().getFromAddress();


        }
    }


    private void validateOrderPaid(BlockData.OrderPair pair) {
        Order aliceOrder = pair.getA();
        Order bobOrder = pair.getB();

        String aliceAddress = aliceOrder.getFromAddress();
        String bobAddress = bobOrder.getFromAddress();

        // First validate the signatures of the orders
        aliceOrder.getSignature();
    }
}

