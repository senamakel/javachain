package io.eshe.chain.block;

import io.eshe.chain.chain.Blockchain;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;


/**
 * @author enamakel@eshe.io
 */
@Data
public class Address {
    String address;
    Map<String, Double> balances = new HashMap<>();


    public Address(String address) {
        this.address = address;
    }


    public void setBalance(String assetId, Double balance) {
        balances.put(assetId, balance);
    }


    public void increaseBalance(String assetId, Double balance) {
        balances.put(assetId, getBalance(assetId) + balance);
    }

    public void decreaseBalance(String assetId, Double balance) {
        balances.put(assetId, getBalance(assetId) - balance);
    }


    public Double getBalance(String assetId) {
        if (!balances.containsKey(assetId)) return 0d;
        return balances.get(assetId);
    }


    public static Double getBalance(String address, String assetId) {
        Address address1 = Blockchain.getAddress(address);
        return address1.getBalance(assetId);
    }
}
