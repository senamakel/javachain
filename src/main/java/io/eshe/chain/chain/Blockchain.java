package io.eshe.chain.chain;

import io.eshe.chain.block.Block;
import io.eshe.chain.block.BlockUtil;

import java.util.Stack;


public class Blockchain {
    private static Stack<Block> blockchain;

    static {
        initBlockchain();
    }


    public static Block getLatestBlock() {
        return blockchain.peek();
    }


    public static void pushNewBlock(Block newBlock) {
        blockchain.push(newBlock);
    }


    private static void initBlockchain() {
        blockchain = new Stack<>();
        blockchain.push(BlockUtil.generateGenesisBlock());
    }
}
