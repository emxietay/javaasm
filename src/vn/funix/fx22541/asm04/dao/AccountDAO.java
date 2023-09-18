package vn.funix.fx22541.asm04.dao;

import vn.funix.fx22541.asm04.model.Account;
import vn.funix.fx22541.asm04.service.BinaryFileService;

import java.util.ArrayList;
import java.util.List;

public class AccountDAO <T extends Account> {
    private static final String ACCOUNT_FILE_PATH = "src/vn/funix/fx22541/asm04/store/accounts.dat";


    public static <T extends Account> void update(Account editAccount) {
        List<Account> accounts = list();
        boolean hasExist = accounts.stream().anyMatch(
                account -> account.getAccountNumber().equals(editAccount.getAccountNumber()
                ));

        List<Account> updatedAccounts;
        if (!hasExist) {
            updatedAccounts = new ArrayList<>(accounts);
            updatedAccounts.add(editAccount);

        } else {
            updatedAccounts = new ArrayList<>();
            for (Account account : accounts) {
                if (account.getAccountNumber().equals(editAccount.getAccountNumber())) {
                    updatedAccounts.add(editAccount);
                } else {
                    updatedAccounts.add(account);
                }
            }
        }
        save(updatedAccounts);
    }


//    @Override
//    public static <T extends Account> void save(List<? super T> list) {
//
//    }

//    @Override
//    public void read() {
//
//    }

    public static List<Account> list() {
        List<Account> objects = BinaryFileService.readFile(ACCOUNT_FILE_PATH);
        return new ArrayList<>(objects);
    }

    public static void save(List<Account> accounts) {
        BinaryFileService.writeFile(ACCOUNT_FILE_PATH, accounts);
    }

}
