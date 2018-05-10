package io.eshe.chain.block;


import lombok.Data;

@Data
public class Block {
    private int index;
    private long timestamp;
    private String previous_hash;
    private String hash;
    private BlockData data;


    public Block(int index, String previous_hash, long timestamp, String hash, BlockData data) {
        this.index = index;
        this.timestamp = timestamp;
        this.previous_hash = previous_hash;
        this.hash = hash;
        this.data = data;
    }
}
