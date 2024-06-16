import java.util.ArrayList;
import com.google.gson.GsonBuilder;


public class memeChain {

    // will be using arrays for the blocks:
    public static ArrayList<Block> blockchain = new ArrayList<>();
    public static int difficulty = 5;

    public static void main(String[] args) {

//        adding the blocks to the arraylist
        blockchain.add(new Block("this is the first block", "0"));
        System.out.println("trying to mine block 1...");
        blockchain.get(0).mineBlock(difficulty);

        blockchain.add(new Block("its me hi im the second block its me", blockchain.get(blockchain.size()-1).hash));
        System.out.println("trying to mine block 2...");
        blockchain.get(1).mineBlock(difficulty);

        blockchain.add(new Block("ayo its the third block here", blockchain.get(blockchain.size()-1).hash));
        System.out.println("trying to mine block 3...");
        blockchain.get(2).mineBlock(difficulty);

        System.out.println("\nBlockchain is valid : " + isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\n The BlockChain: ");
        System.out.println(blockchainJson);

//        the output will now look a lot like a blockChain
//        now to check the integrity of the blockchain, we create a boolean method isChainValid()

    }

    private static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

//        looping the entire blockchain, i.e. arraylist
        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);

            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                System.out.println("current hashes are not equal");
                return false;
            }

//            now the same for previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                System.out.println("previous hashes are not equal");
                return false;
            }

//            if the hash is solved
            if(!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("this block hasn't been mined");
                return false;
            }
        }
        return true;
    }
}

