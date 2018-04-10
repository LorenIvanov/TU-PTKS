package tu.sofia;

import java.util.List;

public class BadPersonThread extends Thread {
    private String name;
    private int corruptedBlock;
    private List<Block> corruptedChain;

    BadPersonThread(String name, int corruptedBlock) {
        this.name = name;
        this.corruptedBlock = corruptedBlock;
    }

    @Override
    public void run() {
        corruptedChain = Blockchain.blockchain;
        int blockchainSize = Blockchain.blockchain.size();

        corruptedChain.get(corruptedBlock).data = name + " did a bad thing :(";
        for (int i = corruptedBlock; i < blockchainSize; i++) {
            Block block = new Block(corruptedChain.get(i).data, corruptedChain.get(i - 1).hash, 0);
            corruptedChain.set(i, block);
        }
        if (blockchainSize == Blockchain.blockchain.size()) {
            Blockchain.blockchain = corruptedChain;
        }
    }
}
