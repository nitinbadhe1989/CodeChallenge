package au.com.mybank.transaction.anayzer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import au.com.mybank.transaction.TransactionRecordImpl;

public class TestTransactionAnalyzer {

	@Test
	public void initiateTest(){
		
		String fileName= "./src/main/resources/transaction.csv";
		TransactionRecordImpl transactionRecordImpl = new TransactionRecordImpl(fileName);
		transactionRecordImpl.setAccountID("ACC334455");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime fromTimeFrame = LocalDateTime.parse("20/10/2018 12:00:00", formatter);

		
		LocalDateTime toTimeFrame = LocalDateTime.parse("20/10/2018 19:00:00", formatter);
	
		System.out.println("Input Params for Junit test");
		
		System.out.println("AccountId: "+"ACC334455" );
		System.out.println("fromTimeFrame: "+ "20/10/2018 12:00:00" );
		System.out.println("toTimeFrame: "+ "20/10/2018 19:00:00" );
		transactionRecordImpl.setFromTimeFrame(fromTimeFrame);
		transactionRecordImpl.setToTimeFrame(toTimeFrame);
		
		
		transactionRecordImpl.init();
	}
	
}
