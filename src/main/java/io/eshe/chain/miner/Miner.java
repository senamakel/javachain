package io.eshe.chain.miner;

import io.eshe.chain.block.Block;
import io.eshe.chain.block.BlockData;
import io.eshe.chain.chain.Blockchain;

import java.security.DigestException;
import java.security.NoSuchAlgorithmException;

public class Miner {
    public static Block generateNewBlock(BlockData data) throws NoSuchAlgorithmException, DigestException {
        Block previousBlock = Blockchain.getLatestBlock();

        int index = previousBlock.getIndex() + 1;
        long timestamp = System.currentTimeMillis();
        String previous_hash = previousBlock.getHash();
        String hash = PoW.generateHash(index, previous_hash, timestamp, data);

        return new Block(index, previous_hash, timestamp, hash, data);
    }


    public static boolean isNewBlockValid(Block newBlock) throws NoSuchAlgorithmException, DigestException {
        Block previousBlock = Blockchain.getLatestBlock();

        return previousBlock.getIndex() == newBlock.getIndex() - 1 &&
                previousBlock.getHash().equals(newBlock.getPreviousHash()) &&
                PoW.validate(newBlock.getHash());
    }
}
