package vn.funix.fx22541.asm04.dao;

import vn.funix.fx22541.asm04.model.Account;
import vn.funix.fx22541.asm04.service.BinaryFileService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static vn.funix.fx22541.asm04.dao.AccountDAO.Finder.*;

public class AccountDAO<T extends Account> {


  private static final String ACCOUNT_FILE_PATH = "src/vn/funix/fx22541/asm04/store/accounts.dat";


  public static <T extends Account> void update(T editAccount) {

    List<T> accounts = list();
//    result = false;
    boolean hasExist1 = hasExistedAccount(editAccount);
    System.out.println("hasExist = " + hasExist1);
    List<T> updatedAccounts;
    if (!hasExist1) {
      updatedAccounts = new ArrayList<>(accounts);
      updatedAccounts.add(editAccount);

    } else {
      updatedAccounts = new ArrayList<>();
      for (T account : accounts) {
        if (account.getAccountNumber().equals(editAccount.getAccountNumber())) {
          updatedAccounts.add(editAccount);
        } else {
          updatedAccounts.add(account);
        }
      }
    }
    save(updatedAccounts);
  }

  public static <T extends Account> boolean hasExisted(T editAccount) {
    ExecutorService executor
            = Executors.newFixedThreadPool(Finder.thread_max);
    for (int i = 0; i < Finder.thread_max / 4; i++) {
      ThreadSearch(thread_max, editAccount.getAccountNumber());
      executor.execute(new Runnable() {
        public void run() {
          Finder.key = editAccount.getAccountNumber();
          ThreadSearch(Finder.current_thread++, editAccount.getAccountNumber());
        }
      });
    }
    executor.shutdown();
    while (!executor.isTerminated()) {
      // Wait for all threads to complete
    }
    return result;
  }


  public static <T extends Account> ArrayList<T> list() {
    List<T> objects = BinaryFileService.readFile(ACCOUNT_FILE_PATH);
    return new ArrayList<>(objects);
  }

  public static <T extends Account> void save(List<T> accounts) {
    BinaryFileService.writeFile(ACCOUNT_FILE_PATH, accounts);
  }

  public static <T extends Account> T getAccountById(String accountNumber) {
    return (T) list().stream().filter(e -> e.getAccountNumber().equals(accountNumber)).findFirst().orElse(null);
  }

  public static boolean hasAnyAccount(String customerID) {
    return list().stream().anyMatch(e -> e.getCustomerId().equals(customerID));
  }


  public static class Finder<T extends Account> implements Runnable {

    static boolean result = false;
    static ArrayList<Account> accounts = AccountDAO.list();
    static int thread_max;
    static int current_thread = 0;
    static String key;

    static {
      int size = AccountDAO.list().size();
      thread_max = size <= 1 ? 3 :
              size <= 10 ? size - 2 : 51;
    }

    static <T extends Account> void ThreadSearch(int num, String key) {
      Finder.key = key;
      for (int i = num * (accounts.size() / thread_max);
           i < ((num + 1) * (accounts.size() / thread_max)); i++) {
        if (accounts.get(i).getAccountNumber().equals(key))
          result = true;
      }
    }

    public static boolean getDefault() {
      return false;
    }

    @Override
    public void run() {
      ThreadSearch(current_thread++, key);
    }
  }

  public static <T extends Account> boolean hasExistedAccount(T account) {
    return AccountDAO.list().stream().anyMatch(e -> e.getAccountNumber().equals(account.getAccountNumber()));
  }


}


