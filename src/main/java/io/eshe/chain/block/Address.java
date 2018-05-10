package io.eshe.chain.block;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;


/**
 * @author enamakel@eshe.io
 */
@Data
public class Address {
    String publicKey;

    // { asset_id: balance }
    Map<String, Double> balances = new HashMap<>();


    public Double getBalance(String assetId) {
        return 0d;
    }


    public static Double getBalance(String address, String assetId) {
        return 0d;
    }
}
