package io.eshe.chain.block;

import io.eshe.chain.encrypt.SHA256;

import java.security.NoSuchAlgorithmException;


public class BlockUtil {
    public static Block generateGenesisBlock() {
        int index = 0;
        long timestamp = System.currentTimeMillis();
        String previousHash = "0";
        String hash = "0";

        BlockData data = new BlockData("burokuru-shuriken", 42);

        return new Block(index, previousHash, timestamp, hash, data);
    }


    public static String wrapBlockContent(int index, String previousHash, long timestamp, BlockData data)
            throws NoSuchAlgorithmException {

        String content = index + previousHash + timestamp + data.toString();
        return SHA256.toSha256(content);
    }
}
