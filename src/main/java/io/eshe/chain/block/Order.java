package io.eshe.chain.block;

import lombok.Data;

/**
 * @author enamakel@eshe.io
 */
@Data
public class Order {
    String signature;

    String fromAddress;
    String toAddress;

    String baseAssetId;
    String quoteAssetId;

    // This basically says that this order would like to trade
    // 'amount' quoteAsset at 'price' worth of baseAsset
    Double amount;
    Double price;

    Boolean isBuyOrder;

    // The exchange should ideally set this. This will instruct the miners to take this much of the base asset as
    // transaction fees.
    Double transactionFeeAddress;
    Double transactionFeeAmount;


    public Double getVolume() {
        return amount * price;
    }


    public boolean verifyOrder(String publicKey) {
        // Todo: verify the order using the given public key
        return true;
    }
}
