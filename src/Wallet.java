import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
    public PrivateKey privateKey;
    public PublicKey publicKey;

//    only the UTXOs owned by this wallet
    public HashMap<String, TransactionOutputs> UTXOs = new HashMap<String, TransactionOutputs>();

    public Wallet() {
        generateKeyPair();
    }

    public void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");

//            now we initialise and generate a keyPair
            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public float getBalance() {
        float total = 0;

        for (Map.Entry<String, TransactionOutputs> item: memeChain.UTXOs.entrySet()) {
            TransactionOutputs UTXO = item.getValue();
            if (UTXO.isMine(publicKey)) { // if output belongs to us,
                UTXOs.put(UTXO.id, UTXO); // add to list of unspent transactions.
                total += UTXO.value;
            }
        }
        return total;
    }

    public Transaction sendFunds(PublicKey _receiver, float value ) {
        if(getBalance() < value) { // gather balance and check funds
            System.out.println("#Not Enough funds to send transaction. Transaction discarded.");
            return null;
        }

        ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();

        float total = 0;
        for (Map.Entry < String, TransactionOutputs> item: UTXOs.entrySet()){
            TransactionOutputs UTXO = item.getValue();
            total += UTXO.value;
            inputs.add(new TransactionInput(UTXO.id));
            if(total > value) break;
        }

        Transaction newTransaction = new Transaction(publicKey, _receiver, value, inputs);
        newTransaction.generateSignature(privateKey);

        for(TransactionInput input: inputs){
            UTXOs.remove(input.transactionOutputId);
        }
        return newTransaction;
    }
}
