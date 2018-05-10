package io.eshe.chain.miner;

import io.eshe.chain.block.Address;
import io.eshe.chain.chain.Blockchain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This node is responsible for keeping track of cross-chain transactions and informing the main-chain of any activity
 * that might happen.
 * <p>
 * Voucher nodes were first described in the Wanchain whitepaper.
 *
 * @author enamakel@eshe.io
 */
public class VoucherNode extends Thread {
    private final static Logger logger = LoggerFactory.getLogger(VoucherNode.class);


    @Override public void run() {
        logger.debug("initializing");
    }


    public void processNewDeposit(String assetId, String address, Double amount) {
        Address address1 = Blockchain.getAddress(address);

        if (address1 == null) address1 = new Address(address);

        Double previousBalance = address1.getBalance(assetId);
        address1.setBalance(assetId, previousBalance + amount);

        Blockchain.updateAddress(address1);
    }


    public void processNewWithdrawl(String assetId, String address, Double amount) {
        Address address1 = Blockchain.getAddress(address);

        if (address1 == null) address1 = new Address(address);

        // Todo: make sure this is +ve
        Double previousBalance = address1.getBalance(assetId);
        address1.setBalance(assetId, previousBalance - amount);

        Blockchain.updateAddress(address1);
    }
}
