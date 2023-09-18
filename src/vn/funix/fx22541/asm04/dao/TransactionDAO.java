package vn.funix.fx22541.asm04.dao;

import vn.funix.fx22541.asm04.model.Transaction;
import vn.funix.fx22541.asm04.service.BinaryFileService;

import java.util.List;

public class TransactionDAO {

    public static final String TRANSACTIONS_FILE_NAME = "src/vn/funix/fx22541/asm04/store/accounts.dat";

    public static void save(List<Transaction> transactions) {
        BinaryFileService.writeFile(TRANSACTIONS_FILE_NAME, transactions);
    }
}
