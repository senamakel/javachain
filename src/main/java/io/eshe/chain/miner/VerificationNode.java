package io.eshe.chain.miner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    }
}
