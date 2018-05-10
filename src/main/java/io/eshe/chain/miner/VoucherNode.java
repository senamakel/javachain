package io.eshe.chain.miner;

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
}
