import java.security.*;
import java.util.ArrayList;

public class Transaction {

    public String transactionId; // also the hash of the transaction
    public PublicKey sender; // sender's public key
    public PublicKey receiver; // receiver's public key
    public float value;
    public byte[] signature;

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutputs> outputs = new ArrayList<TransactionOutputs>();

    private static int sequence = 0; // count of number of transactions made

//    constructor
    public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
        this.sender = from;
        this.receiver = to;
        this.value = value;
        this.inputs = inputs;
    }

    private String calculateHash() {
        sequence++;
        return StringUtil.applySha256(
                StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(receiver) + Float.toString(value) + sequence
        );
    }

//    to sign all the data we don't want to be tampered with
    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(receiver) + Float.toString(value);
        signature = StringUtil.applyECDSASig(privateKey,data);
    }

//    to verify if the data we signed has been tampered with or not
    public boolean verifySignature() {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(receiver) + Float.toString(value);
        return StringUtil.verifyECDSASig(sender, data, signature);
    }

//    returns true if new transactions can be created
    public boolean processTransaction() {

        if(verifySignature() == false) {
            System.out.println("#Transaction signature failed to verify");
            return false;
        }

        // gathering transaction inputs (the unspent ones)
        for (TransactionInput i : inputs) {
            i.UTXO = memeChain.UTXOs.get(i.transactionOutputId);
        }

//        to check if the transaction is valid
        if(getInputsValue() < memeChain.minimumTransaction) {
            System.out.println("#Transaction Inputs too small: " + getInputsValue());
            return false;
        }

//        now to generate transaction outputs
        float leftOver = getInputsValue() - value; // gets the value of inputs and then the leftover change

        transactionId = calculateHash();

        outputs.add(new TransactionOutputs(this.receiver, value, transactionId));
        outputs.add(new TransactionOutputs(this.sender, leftOver, transactionId));

//        add outputs to unspent list
        for (TransactionOutputs o : outputs) {
            memeChain.UTXOs.put(o.id, o);
        }

//        removing transactions inputs from the UTXO lists as they have now been spent
        for(TransactionInput i : inputs) {
            if(i.UTXO == null) continue;
            memeChain.UTXOs.remove(i.UTXO.id);
        }
        return true;

    }

//    next, to return sum of inputs(UTXOs) value
    public float getInputsValue() {
        float total = 0;
        for(TransactionInput i : inputs) {
            if(i.UTXO == null) continue;
            total += i.UTXO.value;
        }
        return total;
    }

//    similarly, to return the sum of outputs
    public float getOutputsValue() {
        float total = 0;
        for(TransactionOutputs o : outputs) {
            total += o.value;
        }
        return total;
    }
}
