package com.tw.splitter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExpenseSplitterApp {

    private static final Logger logger = Logger.getLogger(ExpenseSplitterApp.class.getName());
    private static final String TRANSACTION_FILE_PATH = "/transactions.txt";


    public static List<String> readTransactionsFromFile(String filePath) throws FileNotFoundException {
        try {
            InputStream inputStreamReader = ExpenseSplitterApp.class.getResourceAsStream(filePath);
            if (inputStreamReader == null) {
                logger.severe("No such file found: " + filePath);
                throw new FileNotFoundException(filePath + " File not found!");
            } else {
                ArrayList<String> transactionStatements = new ArrayList<>();
                Scanner scanner = new Scanner(inputStreamReader);
                while (scanner.hasNext()) {
                    transactionStatements.add(scanner.nextLine());
                }
                logger.info("Successfully read " + transactionStatements.size() + " transaction(s) from file.");
                return transactionStatements;
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error while reading transactions: " + ex.getMessage(), ex);
            throw ex;
        }
    }

    public static void main(String[] args) {
        try {
            List<String> transactionStatements = readTransactionsFromFile(TRANSACTION_FILE_PATH);
            ExpenseSettlementService transactionService = new TransactionProcessor();
            transactionService.setTransactionList(transactionStatements);
            transactionService.calculateOwedAmounts();
            transactionService.optimizeOwedAmounts();
            transactionService.printBalances();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Unexpected error in main: " + ex.getMessage(), ex);
        }

    }
}
