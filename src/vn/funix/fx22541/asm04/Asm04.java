package vn.funix.fx22541.asm04;

import vn.funix.fx22541.asm04.model.DigitalBank;

import java.util.Objects;
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
//        createCustomer();

////
//        System.out.println("Getting customer : "+ activeBank.getCustomerById("123123123123"));
//        System.out.println(activeBank.getCustomerById("123123123123"));
//        System.out.println(activeBank.isAccountExisted("123123123123", "123124"));
//        activeBank.save();
        activeBank.importCustomers();
//        activeBank.save();
        activeBank.showCustomer();
//        activeBank.list();


//        Path path = Path.of(CUSTOMER_FILE);
//        System.out.println(path.toAbsolutePath());
//        Stream<String> lines;
//        try {
//            Files.lines(path).forEach(System.out::println);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


    }

    private static void createCustomer() {
        while (true) {
            System.out.println("123".matches("\\d{3}") + "   123.matches");
            System.out.print("Enter id: ");
            String id = new Scanner(System.in).next().toLowerCase();
            String name = new Scanner(System.in).next();
            if (Objects.equals(id, "q")) {
                System.out.println("You want to quit!");
                break;
            }
            try {
//                var customer = new DigitalCustomer<>(id, name);
                activeBank.addCustomer(name, id);
                activeBank.save();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + ", re-enter or press q for quit:");
            }
        }
    }

    private static void printMenu() {
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
                Chức năng:""";
        System.out.println(textBlock);

    }
}
