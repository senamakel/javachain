package io.eshe.chain.miner;

import io.eshe.chain.block.Block;
import io.eshe.chain.block.BlockData;
import io.eshe.chain.chain.Blockchain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.DigestException;
import java.security.NoSuchAlgorithmException;

/**
 * This node is used to verify on-chain transactions. All order-matching verification, distribution of transaction
 * fees and maintaing of account balances happen at this node.
 * <p>
 * Verification nodes were first described in the Wanchain whitepaper.
 *
 * @author enamakel@eshe.io
 */
public class VerificationNode extends Thread {
    private final static Logger logger = LoggerFactory.getLogger(VerificationNode.class);


    @Override public void run() {
        logger.debug("initializing");

        for (int i = 0; i < 100; i++) {
            try {
                BlockData data = new BlockData();
                Block block = generateNewBlock(data);

                if (isNewBlockValid(block)) Blockchain.pushNewBlock(block);

                System.out.println(block.getHash());

            } catch (NoSuchAlgorithmException | DigestException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Given the blockdata, generate a new block
     *
     * @param data The block data to include
     * @return The newly generated block
     */
    public Block generateNewBlock(BlockData data) throws NoSuchAlgorithmException, DigestException {
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
                newBlock.getData().validateTransactions() &&
                PoW.validate(newBlock.getHash());
    }
}
