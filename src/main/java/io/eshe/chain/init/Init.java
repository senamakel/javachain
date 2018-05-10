package io.eshe.chain.init;

import java.security.DigestException;
import java.security.NoSuchAlgorithmException;

import io.eshe.chain.chain.Blockchain;
import io.eshe.chain.miner.Miner;
import io.eshe.chain.block.Block;
import io.eshe.chain.block.BlockData;

public class Init {

	public static void main(String[] args) throws NoSuchAlgorithmException, DigestException {
		for (int i = 0; i < 100; i++) {
			BlockData data = new BlockData("Andre", i);
			Block block = Miner.generateNewBlock(data);
			if (Miner.isNewBlockValid(block))
				Blockchain.pushNewBlock(block);

			System.out.println(block.getHash());
		}
	}
}
