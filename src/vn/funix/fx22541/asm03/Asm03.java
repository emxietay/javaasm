package vn.funix.fx22541.asm03;

import vn.funix.fx22541.asm02.models.Customer;
import vn.funix.fx22541.asm03.models.DigitalBank;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Asm03 {

	private static final int EXIT_COMMAND_CODE = 0;
	private static final int EXIT_ERROR_CODE = -1;
	private static final Scanner scanner = new Scanner(System.in);
	private static final DigitalBank activeBank = new DigitalBank();
	private static final String CUSTOMER_ID = "001215000001";
	private static final String CUSTOMER_NAME = "FUNIX";
	private static final String AUTHOR = "FX22541";
	private static final String VERSION = "v3.0.0";

	public static void main(String[] args) {
		activeBank.addCustomer(CUSTOMER_ID, CUSTOMER_NAME);
		boolean quit = false;
		while (!quit) {
			try {
				printMenu();
				int option = scanner.nextInt();
				scanner.nextLine();
				if (option > 5 || option < 0) {
					throw new Exception("Vui lòng nhập đúng chức năng từ 0-5.");
				} else {
					switch (option) {
						case 1 -> showCustomer();
						case 2 -> addATMAccount();
						case 3 -> addLoanAccount();
						case 4 -> withdraw();
						case 5 -> showTransactions();
						case 0 -> quit = true;
					}
				}
			} catch (InputMismatchException e) {
				System.out.println("Vui lòng nhập kí tự số");
				scanner.nextLine();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private static void showCustomer() {
		Customer customer = activeBank.getCustomerById(CUSTOMER_ID);
		if (customer != null) {
			customer.displayInformation();
		}
	}

	private static void withdraw() {
		System.out.println("Nhap so tai khoan can rut: ");
		String newAccount = scanner.nextLine();
		System.out.print("Nhap so tien can rut: ");
		double initialAmount = scanner.nextDouble();
		activeBank.withdraw(CUSTOMER_ID, newAccount, initialAmount);
	}

	private static void addATMAccount()  {
		System.out.println("Nhập số tài khoản gồm 6 chữ số: ");
		String newAccount = scanner.nextLine();
		System.out.print("Nhập số dư ban đầu: ");
		double initialAmount = scanner.nextDouble();
		activeBank.addSavingsAccount(CUSTOMER_ID, newAccount, initialAmount);
	}

	private static void addLoanAccount() {
		System.out.print("Nhập số tài khoản gồm 6 chữ số: ");
		String newAccount = scanner.nextLine();
		System.out.print("Nhập số dư ban đầu: ");
		double initialAmount = scanner.nextDouble();
		activeBank.addLoanAccount(CUSTOMER_ID, newAccount, initialAmount);
	}

	private static void showTransactions() {
		activeBank.showTransactions(CUSTOMER_ID);
	}

	private static void printMenu() {
		String textBlock = """                
						+---------+----------------------+--------+
						| NGÂN HÀNG SỐ |\s""" + AUTHOR + """
						@""" + VERSION + """
						           |
						+---------+----------------------+--------+
						1. Thông tin khách hàng
						2. Thêm tài khoản ATM
						3. Thêm tài khoản tín dụng
						4. Rút tiền
						5. Lịch sử giao dịch
						0. Thoát
						+---------+----------------------+--------+
						Chức năng:""";

		System.out.print(textBlock + " ");
	} //Hiển thị màn hình chính
}
