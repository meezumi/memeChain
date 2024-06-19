

public class TransactionInput {
    public String transactionOutputId; // reference to TransactionOutputs -> transactionId
    public TransactionOutputs UTXO; // contains the Unspent transaction output

    public TransactionInput(String transactionOutputId) {
        this.transactionOutputId = transactionOutputId;
    }

//    This class will be used to reference TransactionOutputs that have not yet been spent. The transactionOutputId will be used to find the relevant TransactionOutput, allowing miners to check your ownership.
}
