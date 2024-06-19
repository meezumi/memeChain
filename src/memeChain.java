import java.util.ArrayList;
import com.google.gson.GsonBuilder;
import java.security.Security;
import java.util.Base64;
import java.util.HashMap;


public class memeChain {

    // will be using arrays for the blocks:
    public static ArrayList<Block> blockchain = new ArrayList<>();
    public static HashMap<String, TransactionOutputs> UTXOs = new HashMap<String, TransactionOutputs>();
//  list of all unspent transactions
    public static int difficulty = 5;

    public static Wallet walletA;
    public static Wallet walletB;

    public static void main(String[] args) {

//        setting up Bouncy Castle as a security provider
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

//        creating new wallets
        walletA = new Wallet();
        walletB = new Wallet();

//        testing public and private keys
        System.out.println("Private and Public keys:");
        System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
        System.out.println(StringUtil.getStringFromKey(walletA.publicKey));

//        creating a test transaction from wallet A to B
        Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
//        null here since the first ever transaction
        transaction.generateSignature(walletA.privateKey);

//        now we verify if the signature works from the public key
        System.out.println("Is signature verified: ");
        System.out.println(transaction.verifySignature());

//        will comment the rest of the part out for now in the main method

//        adding the blocks to the arraylist
//        blockchain.add(new Block("this is the first block", "0"));
//        System.out.println("trying to mine block 1...");
//        blockchain.get(0).mineBlock(difficulty);
//
//        blockchain.add(new Block("it's me hi im the second block it's me", blockchain.get(blockchain.size()-1).hash));
//        System.out.println("trying to mine block 2...");
//        blockchain.get(1).mineBlock(difficulty);
//
//        blockchain.add(new Block("ayo it's the third block here", blockchain.get(blockchain.size()-1).hash));
//        System.out.println("trying to mine block 3...");
//        blockchain.get(2).mineBlock(difficulty);
//
//        System.out.println("\nBlockchain is valid : " + isChainValid());
//
//        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
//        System.out.println("\n The BlockChain: ");
//        System.out.println(blockchainJson);

//        the comment ends here

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

