package io.eshe.chain.block;

import lombok.Data;


@Data
public class Block {
    private int index;
    private long timestamp;
    private String previousHash;
    private String hash;
    private BlockData data;


    public Block(int index, String previousHash, long timestamp, String hash, BlockData data) {
        this.index = index;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.hash = hash;
        this.data = data;
    }
}
