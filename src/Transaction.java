import java.security.*;
import java.util.ArrayList;

public class Transaction {

    public String transactionId; // also the hash of the transaction
    public PublicKey sender; // sender's public key
    public PublicKey receiver; // receiver's public key
    public float value;
    public byte[] signature;

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

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
}
