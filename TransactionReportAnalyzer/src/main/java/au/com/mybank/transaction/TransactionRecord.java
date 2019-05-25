package au.com.mybank.transaction;
import java.time.LocalDateTime;

/**This POJO class defines the transaction record 
 * @author nitinbadhe
 *
 */
public class TransactionRecord {

	String transactionId;
	String fromAccountId;
	String toAccountId;
	LocalDateTime createAt;
	Float amount;
	Enum<TransactionType> transactionType;

	public LocalDateTime getCreateAt() {
		return createAt;
	}
	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}
	
	public Enum<TransactionType> getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(Enum<TransactionType> transactionType) {
		this.transactionType = transactionType;
	}
	String relatedTransaction;
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getFromAccountId() {
		return fromAccountId;
	}
	public void setFromAccountId(String fromAccountId) {
		this.fromAccountId = fromAccountId;
	}
	public String getToAccountId() {
		return toAccountId;
	}
	public void setToAccountId(String toAccountId) {
		this.toAccountId = toAccountId;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public String getRelatedTransaction() {
		return relatedTransaction;
	}
	public void setRelatedTransaction(String relatedTransaction) {
		this.relatedTransaction = relatedTransaction;
	}
}
