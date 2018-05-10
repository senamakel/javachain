package io.eshe.chain.block;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class BlockData {
    private String name;
    private int age;

    private List<OrderPair> orderpairs = new ArrayList<>();


    public BlockData(String name, int age) {
        this.name = name;
        this.age = age;
    }


    @Override
    public String toString() {
        return name + "n" + age;
    }


    @Data
    public static class OrderPair {
        Order A;
        Order B;
    }
}
