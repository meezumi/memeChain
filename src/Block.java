import java.util.Date;

public class Block {

    // Firsts lets create class Block that make up the blockchain

    public String hash; // stores the digital signature
    public String previousHash; // previous block's hash

    private String data; // this will have out personal message.
    private long timeStamp; // will save the time of the message/transaction when it was placed.

    // creating constructor for the class

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    //  Next we will need a way to generate a digital fingerprint, so we create a handy helper method in a new StringUtil 'utility' class.

    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
                previousHash + Long.toString(timeStamp) + data
        );
        return calculatedhash;
    }
}

