package au.com.mybank.main;

import au.com.mybank.transaction.TransactionRecordImpl;

/**
 * @author nitinbadhe
 *
 *This is the main class to invoke the transaction anaylzer
 *
 */
public class TransactionAnalyzer {

	public static void main(String[] args) {
		
		//Assuming file is always placed in resources folder this can be enhanced to pass filename with full path as an input
		//Argument
		
		String fileName= "./src/main/resources/transaction.csv";
		
		TransactionRecordImpl transactionRecordImpl = new TransactionRecordImpl(fileName);
		transactionRecordImpl.readUserInput();
        transactionRecordImpl.init();

	}

}
