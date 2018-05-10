package io.eshe.chain;

import io.eshe.chain.miner.StoremanNode;
import io.eshe.chain.miner.VerificationNode;
import io.eshe.chain.miner.VoucherNode;

import java.security.DigestException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, DigestException {
//        for (int i = 0; i < 100; i++) {
//            BlockData data = new BlockData();
//            Block block = Miner.generateNewBlock(data);
//
//            if (Miner.isNewBlockValid(block))
//                Blockchain.pushNewBlock(block);
//
//            System.out.println(block.getHash());
//        }

        StoremanNode storemanNode = new StoremanNode();
        storemanNode.run();

        VerificationNode verificationNode = new VerificationNode();
        verificationNode.run();

        VoucherNode voucherNode = new VoucherNode();
        voucherNode.run();
    }
}
