package tu.sofia;

import java.util.Date;

public class Block {
    public String hash;
    public String previousHash;
    // Data will be a simple message.
    public String data;
    // Number of milliseconds since 1/1/1970.
    private long timeStamp;
    private int nonce;
    public int difficulty;

    // Block Constructor.
    public Block(String data, String previousHash, int difficulty) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.difficulty = difficulty;
        // Do this after setting the other values.
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        data
        );
    }

    public void mineBlock(int difficulty) {
        // Create a string with difficulty * "0"
        String target = new String(new char[difficulty]).replace('\0', '0');

        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }
}
