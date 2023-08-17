package vn.funix.fx22541.asm02;

import vn.funix.fx22541.asm02.models.Bank;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Asm02 {
	private static final Bank bank = new Bank();
	private static final Scanner scanner = new Scanner(System.in);
	private static final String AUTHOR = "FX22541";
	private static final String VERSION = "v2.0.0";

	public static void main(String[] args) {
		boolean quit = false;
		while (!quit) {
			try {
				printMenu();
				int option = scanner.nextInt();
				scanner.nextLine();
				if (option > 5 || option < 0) {
					throw new Exception("Not valid option");
				} else {
					switch (option) {
						case 1 -> addCustomer();
						case 2 -> addAccount();
						case 3 -> bank.listCustomers();
						case 4 -> bank.searchCustomerById();
						case 5 -> bank.searchCustomerByName();
						case 0 -> quit = true;
					}
				}
			} catch (InputMismatchException e) {
				System.out.println("Vui lòng nhập đúng chức năng từ 0-5.");
			} catch (Exception e) {
				System.out.println(e.getMessage());
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
                | 1. Thêm khách hàng                      |
                | 2. Thêm tài khoản cho khách hàng        |
                | 3. Hiển thị danh sách khách hàng        |
                | 4. Tìm theo CCCD                        |
                | 5. Tìm theo tên khách hàng              |
                | 0. Thoát                                |
                +---------+----------------------+--------+
                Chức năng:""";

		System.out.print(textBlock + " ");
	} //Hiển thị màn hình chính

	public static void addCustomer() {
		while (true) {
			try {
				System.out.println("Nhập tên của khách hàng cần thêm: ");
				String inputName = scanner.nextLine();
				if (inputName == null || inputName.isBlank() || !inputName.matches("[a-zA-Z\\s]+")) {
					throw new Exception("Vui lòng nhập lại tên khách hàng.");
				}
				while (true) {
					try {
						System.out.println("Nhập CCCD của khách hàng cần thêm: ");
						String inputId = scanner.nextLine();
						if (!bank.validateCustomerId(inputId)) {
							throw new Exception("Vui lòng nhập đúng định dạng CCCD gồm 12 chữ số.");
						}
						if (bank.isCustomerExisted(inputId)) {
							throw new Exception("Khách hàng đã tồn tại, vui lòng nhập lại. ");
						}
						bank.addCustomer(inputName, inputId);
						System.out.println("Khách hàng " + inputId + " đã được thêm.");
//                        throw new Exception("Vui long chon chuc nang tiep theo");
						break;
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void addAccount()  {
		while (true) {
			try {
				System.out.print("Nhập ID: ");
				String customerId = scanner.nextLine();
				if (!customerId.matches("\\d{12}")) {
					throw new Exception("Vui lòng nhập đúng định dạng CCCD.");
				}
				if (bank.isCustomerExisted(customerId))  {
					System.out.println("Khách hàng: " + bank.getCustomerById(customerId).getName());
					while (true) {
						try {

							System.out.println("Nhập số tài khoản cần tạo thêm: ");
							String accountNum = scanner.nextLine();
							if (!accountNum.matches("\\d{6}")) {
								throw new Exception("Vui lòng nhập số có 6 chữ số.");
							}
							while (true) {
								try {
									System.out.println("Nhập số dư ban đầu");
									double initialAmount = scanner.nextDouble();
									if (initialAmount < 50000) {
										throw new Exception("Vui lòng nhập số dư tối thiểu 50.000đ.");
									}
									bank.getCustomerById(customerId).addAccount(accountNum, initialAmount);
									break;
								} catch (Exception e) {
									System.out.println(e.getMessage());
								}
							}

							break;
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
				} else {
					System.out.println("Khách hàng không tồn tại trên hệ thống. Vui lòng thử lại.");
				}
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
