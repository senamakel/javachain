package io.eshe.chain;

import io.eshe.chain.miner.StoremanNode;
import io.eshe.chain.miner.VerificationNode;
import io.eshe.chain.miner.VoucherNode;

public class Main {
    public static void main(String[] args) {
        StoremanNode storemanNode = new StoremanNode();
        storemanNode.start();

        VerificationNode verificationNode = new VerificationNode();
        verificationNode.start();

        VoucherNode voucherNode = new VoucherNode();
        voucherNode.start();
    }
}
