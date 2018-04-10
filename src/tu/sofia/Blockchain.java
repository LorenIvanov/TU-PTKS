package tu.sofia;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Blockchain {
    public static List<Block> blockchain = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        blockchain.add(new Block("Hi I am the first block", "0", 0));

        PersonThread georgi = new PersonThread("Georgi", 60, 3, 4);
        PersonThread petar = new PersonThread("Ivan", 70, 2, 4);

        BadPersonThread badPerson = new BadPersonThread("Bad person", 5);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.execute(georgi);
        executor.execute(petar);
        TimeUnit.SECONDS.sleep(1);
        executor.execute(badPerson);

        executor.shutdown();

        while (!executor.isTerminated()) {
        }

        System.out.println("\nBlockchain is Valid: " + isChainValid());

//        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
//        System.out.println("\nThe block chain: ");
//        System.out.println(blockchainJson);
        for (Block block : blockchain) {
            System.out.println(block.data);
        }
    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget;

        // Loop through blockchain to check hashes:
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);

            hashTarget = new String(new char[currentBlock.difficulty]).replace('\0', '0');
            // Compare registered hash and calculated hash:
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }
            // Compare previous hash and registered previous hash
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            // Check if hash is solved
            if (!currentBlock.hash.substring(0, currentBlock.difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }
}
