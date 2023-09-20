package vn.funix.fx22541.asm04;

import vn.funix.fx22541.asm04.dao.CustomerDAO;
import vn.funix.fx22541.asm04.model.Customer;
import vn.funix.fx22541.asm04.model.DigitalBank;

import javax.xml.stream.events.EntityReference;
import java.util.Scanner;

public class Asm04 {


  private static final int EXIT_COMMAND_CODE = 0;
  private static final int EXIT_ERROR_CODE = -1;
  private static final Scanner scanner = new Scanner(System.in);
  private static final DigitalBank activeBank = new DigitalBank("BANK", "123123");
  private static final String CUSTOMER_NAME = "FUNIX";
  private static final String AUTHOR = "FX22541";
  private static final String VERSION = "v4.0.0";

  public static void main(String[] args) {
    DigitalBank bank = new DigitalBank("VNB", "99999");
    int quit = -2;
    printMenu();
    while (quit != EXIT_COMMAND_CODE && quit != EXIT_ERROR_CODE) {
      int option = -2;
      try {
        option = Integer.parseInt(scanner.nextLine());
      } catch (NumberFormatException e) {
        System.out.print("Please enter valid option: ");
        continue;
      }
      switch (option) {
        case 0 -> quit = EXIT_COMMAND_CODE;
        case 1 -> CustomerDAO.list().forEach(Customer::displayInformation);
        case 2 -> CustomerDAO.importCustomers();
        case 3 -> bank.addSavingsAccount();
        case 4 -> bank.transfer();
        case 5 -> bank.withdraw(scanner);
        case 6 -> bank.showTransactions();
        default -> {
        System.out.print("Please enter valid option: ");
          continue;
        }
      }
    }
  }


  public static void printMenu() {
    String textBlock = """                
            +---------+----------------------+--------+
            | NGÂN HÀNG SỐ |\s""" + AUTHOR + """
            @""" + VERSION + """
                       |
            +---------+----------------------+--------+
            1. Xem danh sách khách hàng
            2. Nhập danh sách khách hàng
            3. Thêm tài khoản ATM
            4. Chuyển tiền
            5. Rút tiền
            6. Tra cứu lịch sử giao dịch
            0. Thoát
            +---------+----------------------+--------+
            Chức năng: """;
    System.out.println(textBlock);
  }

}
