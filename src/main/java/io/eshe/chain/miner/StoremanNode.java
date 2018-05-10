package io.eshe.chain.miner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents a storeman node. This node is responsible for managing all cross-blockchain locked funds.
 * <p>
 * Storeman nodes were first described in the Wanchain whitepaper.
 *
 * @author enamakel@eshe.io
 */
public class StoremanNode extends Thread {
    private final static Logger logger = LoggerFactory.getLogger(StoremanNode.class);


    @Override public void run() {
        logger.debug("initializing");
    }
}
