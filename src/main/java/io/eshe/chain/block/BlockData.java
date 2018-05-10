package io.eshe.chain.block;

import lombok.Data;


@Data
public class BlockData {
    private String name;
    private int age;


    public BlockData(String name, int age) {
        this.name = name;
        this.age = age;
    }


    @Override
    public String toString() {
        return name + "n" + age;
    }
}
