import java.security.PublicKey;

public class TransactionOutputs {
    public String id;
    public PublicKey receiver; // also known as the new owner of these coins.
    public float value; // the amount of coins they own
    public String parentTransactionId; // the id of the transaction this output was created in

    //Constructor
    public TransactionOutputs(PublicKey receiver, float value, String parentTransactionId) {
        this.receiver = receiver;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = StringUtil.applySha256(StringUtil.getStringFromKey(receiver)+Float.toString(value)+parentTransactionId);
    }

    // check if coin belongs to you
    public boolean isMine(PublicKey publicKey) {
        return (publicKey == receiver);
    }

//    Transaction outputs will show the final amount sent to each party from the transaction. These, when referenced as inputs in new transactions, act as proof that you have coins to send
}
