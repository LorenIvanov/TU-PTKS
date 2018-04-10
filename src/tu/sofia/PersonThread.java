package tu.sofia;

import java.util.concurrent.ThreadLocalRandom;

public class PersonThread extends Thread {

    private String name;
    private int blocksToMine;
    private int blocksMined = 1;
    private int difficultyFrom;
    private int difficultyTo;

    PersonThread(String name, int blocksToMine, int difficultyFrom, int difficultyTo) {
        this.name = name;
        this.blocksToMine = blocksToMine;
        this.difficultyFrom = difficultyFrom;
        this.difficultyTo = difficultyTo;
    }

    @Override
    public void run() {
        for (int i = 0; i < blocksToMine; i++) {
            if (Blockchain.isChainValid()) {
                Block block = block();
                Blockchain.blockchain.add(block);
                blocksMined++;
            } else {
                i--;
            }
        }
    }

    private Block block() {
        String data = name + " " + String.valueOf(blocksMined);
        int difficulty = ThreadLocalRandom.current().nextInt(difficultyFrom, difficultyTo);
        Block block = new Block(data, String.valueOf(Blockchain.blockchain.get(Blockchain.blockchain.size() - 1).hash), difficulty);
        block.mineBlock(difficulty);
        if (Blockchain.blockchain.get(Blockchain.blockchain.size() - 1).hash == block.previousHash) {
            return block;
        } else {
            return block();
        }
    }
}
