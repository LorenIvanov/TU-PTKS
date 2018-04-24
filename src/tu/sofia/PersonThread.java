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
            Block block = block();

            if (Blockchain.isChainValid()) {
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

        String previousHash = String.valueOf(Blockchain.blockchain.get(Blockchain.blockchain.size() - 1).hash);

        Block block = new Block(data, previousHash, difficulty);
        block.mineBlock(difficulty);

        if (Blockchain.blockchain.get(Blockchain.blockchain.size() - 1).hash == block.previousHash) {
            return block;
        } else {
            return block();
        }
    }
}
