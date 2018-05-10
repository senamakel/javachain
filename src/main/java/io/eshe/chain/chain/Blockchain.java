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


    public static void pushNewBlock(Block new_block) {
        blockchain.push(new_block);
    }


    private static void initBlockchain() {
        blockchain = new Stack<Block>();
        blockchain.push(BlockUtil.generateGenesisBlock());
    }
}
