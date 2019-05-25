package au.com.mybank.transaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**This class is used to process and calculate relative account balance
 * @author nitinbadhe
 *
 */
public class TransactionRecordImpl extends TransactionRecord {

	
	String fileName;
	String accountID;
	LocalDateTime fromTimeFrame, ToTimeFrame;
	public TransactionRecordImpl() {

	}

	public TransactionRecordImpl(String fileName) {
		this.fileName = fileName;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public LocalDateTime getFromTimeFrame() {
		return fromTimeFrame;
	}

	public void setFromTimeFrame(LocalDateTime fromTimeFrame) {
		this.fromTimeFrame = fromTimeFrame;
	}

	public LocalDateTime getToTimeFrame() {
		return ToTimeFrame;
	}

	public void setToTimeFrame(LocalDateTime toTimeFrame) {
		ToTimeFrame = toTimeFrame;
	}

	public void init() {
		
		
		try {

			
			//Process the file
			List<TransactionRecord> listOfRecords = processInputFile(fileName);

			List<TransactionRecord> listOfRecordsWithPayment, listOfRelativeAccountBalance;

			
			//Defining filter criterias
			Predicate<TransactionRecord> fromAccountId = e -> e.getFromAccountId().equalsIgnoreCase(accountID);

			Predicate<TransactionRecord> toAccountId = e -> e.getToAccountId().equalsIgnoreCase(accountID);
			Predicate<TransactionRecord> isPayment = e -> e.getTransactionType().toString()
					.equalsIgnoreCase(TransactionType.PAYMENT.name());

			Predicate<TransactionRecord> startDateTime = e -> e.getCreateAt().isAfter(fromTimeFrame);
			Predicate<TransactionRecord> startDateTimeEquals = e -> e.getCreateAt().isEqual(fromTimeFrame);
			Predicate<TransactionRecord> endDateTime = e -> e.getCreateAt().isBefore(ToTimeFrame);
			Predicate<TransactionRecord> endDateTimeEquals = e -> e.getCreateAt().isEqual(ToTimeFrame);
			// Get Reversals
			Predicate<TransactionRecord> isReversal = e -> e.getTransactionType().toString()
					.equalsIgnoreCase(TransactionType.REVERSAL.name());

			
			//Fetching records based on the filter
			listOfRecordsWithPayment = listOfRecords.stream().filter(fromAccountId.or(toAccountId)).filter(isPayment)
					.filter((startDateTime.or(startDateTimeEquals)).and(endDateTime.or(endDateTimeEquals))).collect(Collectors.toList());
			
			//Fetch reversals
			List<String> listOfReversalTransactionIds = listOfRecords.stream().filter(fromAccountId.or(toAccountId))
					.filter(isReversal).map(e -> e.getRelatedTransaction()).collect(Collectors.toList());

			//Define removal filter
			Predicate<TransactionRecord> removeReversal = e -> listOfReversalTransactionIds
					.contains(e.getTransactionId());

			//Get Final list for balance
			listOfRelativeAccountBalance = listOfRecordsWithPayment.stream().filter(removeReversal.negate())
					.collect(Collectors.toList());

			Float balance = 0.0F;
			Float amount;
			for (TransactionRecord transactionRecord : listOfRelativeAccountBalance) {

				if (transactionRecord.getFromAccountId().equalsIgnoreCase(accountID)) {
					amount = -transactionRecord.getAmount();
					balance += amount;
				} else {
					amount = transactionRecord.getAmount();
					balance += amount;
				}

			}
			System.out.println("Relative balance for the period is:" + balance);
			System.out.println("Number of transactions included is:" + listOfRelativeAccountBalance.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**This Method process the input csv file
	 * @param inputFilePath
	 * @return
	 */
	private List<TransactionRecord> processInputFile(String inputFilePath) {

		List<TransactionRecord> inputList = new ArrayList<TransactionRecord>();

		try {

			File inputF = new File(inputFilePath);

			InputStream inputFS = new FileInputStream(inputF);

			BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

			// skip the header of the csv

			inputList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());

			br.close();

		} catch (IOException e) {

		}

		return inputList;

	}

	private Function<String, TransactionRecord> mapToItem = (line) -> {

		String[] p = line.split(",");// a CSV has comma separated lines

		TransactionRecord item = new TransactionRecord();

		item.setTransactionId(p[0]);// <-- this is the first column in the csv file
		item.setFromAccountId(p[1]);
		item.setToAccountId(p[2]);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		try {
			item.setCreateAt(LocalDateTime.parse(p[3], formatter));
		} catch (Exception e) {
			e.printStackTrace();
		}
		item.setAmount(Float.parseFloat(p[4]));
		item.setTransactionType(TransactionType.valueOf(p[5]));
		if (p.length > 6)
			item.setRelatedTransaction(p[6]);
		return item;

	};
	
	/**This method reads user input
	 * 
	 */
	public void readUserInput(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		// Read user input
		Scanner in = new Scanner(System.in);
		System.out.println("Enter Account Id");
		accountID = in.nextLine();
		System.out.println("Enter the start timeframe in format (dd/mm/yyyy HH:MM:SS)");
		fromTimeFrame = LocalDateTime.parse(in.nextLine(), formatter);

		System.out.println("Enter the end timeframe in format (dd/mm/yyyy HH:MM:SS)");
		ToTimeFrame = LocalDateTime.parse(in.nextLine(), formatter);
		in.close();
	}
}
