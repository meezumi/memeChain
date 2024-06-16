import java.util.ArrayList;
import com.google.gson.GsonBuilder;


public class memeChain {
    // here we will create blocks and print hashes to check.

    public static void main(String[] args) {



//        first block has previousHash 0 since it's the first block
        Block genesisBlock = new Block("this is the first block meow", "0");
        System.out.println("hash for block 1: " + genesisBlock.hash);

        Block secondBlock = new Block("its me, hi im the second block its me", genesisBlock.hash);
        System.out.println("hash for block 2: " + secondBlock.hash);

        Block thirdBlock = new Block("ayo im the third block fam", secondBlock.hash);
        System.out.println("hash for block 3: " + thirdBlock.hash);

    }
}
