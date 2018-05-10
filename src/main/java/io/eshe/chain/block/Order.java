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

    Double amount;
    Double price;

    Double transactionFeeAddress;
    Double transactionFeeAmount;
}
