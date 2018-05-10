package io.eshe.chain.block;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class BlockData {
    private List<Transaction> transactions = new ArrayList<>();


    @Override
    public String toString() {
        // todo: convert orderPairs to string
        return "n";
    }


    @Data
    public static class Transaction {
        Order A;
        Order B;
    }
}
