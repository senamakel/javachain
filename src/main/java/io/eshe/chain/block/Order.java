package io.eshe.chain.block;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author enamakel@eshe.io
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    String transactionFeeAddress;
    Double transactionFeeAmount;


    public Double getVolume() {
        return amount * price;
    }


    public boolean verifyOrder(String publicKey) {
        // Todo: verify the order using the given public key
        return true;
    }
}
