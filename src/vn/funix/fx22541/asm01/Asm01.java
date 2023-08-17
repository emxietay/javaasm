package vn.funix.fx22541.asm01;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Asm01 {

	private static final Scanner scanner = new Scanner(System.in);
	private static final String AUTHOR = "FX22541";
	private static final String VERSION = "v1.0.0";


	public static void main(String[] args) {
		printMenu();
		boolean quit = false;
		while (!quit) {
			try {
				int option = Integer.parseInt(scanner.nextLine());
				switch (option) {
					case 0 -> {
						System.out.println("Closing application...");
						quit = true;
					}
					case 1 -> {
						verifyPasscode();
						checkIdNumber();
						quit = true;
					}
					default -> {
						System.out.println("Lựa chọn không khả dụng. Vui lòng nhập lại. ");
						printMenu();
					}
				}
			} catch (NumberFormatException e) {
				System.out.println("Vui lòng nhập kí tự số.");
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
				| 1. Nhập CCCD                            |
				| 0. Thoát                                |
				+---------+----------------------+--------+
				Chức năng:""";

		System.out.println(textBlock + " ");
	} //Hiển thị màn hình chính


	private static boolean isNumeric(String cccd) {
		return cccd.matches("\\d+");
	} //Kiểm tra chuỗi ký tự là số

	private static void checkIdNumber() {
		boolean quit = false;
		while (!quit) {
			quit = true;
			System.out.println("Nhập CCCD: ");
			while (true) {
				String cccd = scanner.nextLine();
				if (cccd.length() == 12 && isNumeric(cccd)) {
					deployFeatures(cccd);
					break;
				} else if (cccd.equals("No")) {
					quit = true;
					break;
				} else {
					System.out.println("Số CCCD không hợp lệ. Vui lòng nhập lại hoặc 'No' để thoát.");
					quit = false;
				}
			}
		}
	}  //Kiểm tra tính hợp lệ của CCCD

	private static void checkPlaceOfBirth(String cccd) {
		String checkPlaceOfBirth = cccd.substring(0, 3);

		switch (checkPlaceOfBirth) {
			case "001" -> checkPlaceOfBirth = "Hà Nội";
			case "002" -> checkPlaceOfBirth = "Hà Giang";
			case "004" -> checkPlaceOfBirth = "Cao Bằng";
			case "006" -> checkPlaceOfBirth = "Bắc Kạn";
			case "008" -> checkPlaceOfBirth = "Tuyên Quang";

			case "010" -> checkPlaceOfBirth = "Lào Cai";
			case "011" -> checkPlaceOfBirth = "Điện Biên";
			case "012" -> checkPlaceOfBirth = "Lai Châu";
			case "014" -> checkPlaceOfBirth = "Sơn La";
			case "015" -> checkPlaceOfBirth = "Yên Bái";

			case "017" -> checkPlaceOfBirth = "Hòa Bình";
			case "019" -> checkPlaceOfBirth = "Thái Nguyên";
			case "020" -> checkPlaceOfBirth = "Lạng Sơn";
			case "022" -> checkPlaceOfBirth = "Quảng Ninh";
			case "024" -> checkPlaceOfBirth = "Bắc Giang";

			case "025" -> checkPlaceOfBirth = "Phú Thọ";
			case "026" -> checkPlaceOfBirth = "Vĩnh Phúc";
			case "027" -> checkPlaceOfBirth = "Bắc Ninh";
			case "030" -> checkPlaceOfBirth = "Hải Dương";
			case "031" -> checkPlaceOfBirth = "Hải Phòng";

			case "033" -> checkPlaceOfBirth = "Hưng Yên";
			case "034" -> checkPlaceOfBirth = "Thái Bình";
			case "035" -> checkPlaceOfBirth = "Hà Nam";
			case "036" -> checkPlaceOfBirth = "Nam Định";
			case "037" -> checkPlaceOfBirth = "Ninh Bình";

			case "038" -> checkPlaceOfBirth = "Thanh Hóa";
			case "040" -> checkPlaceOfBirth = "Nghệ An";
			case "042" -> checkPlaceOfBirth = "Hà Tĩnh";
			case "044" -> checkPlaceOfBirth = "Quảng Bình";
			case "045" -> checkPlaceOfBirth = "Quảng Trị";

			case "046" -> checkPlaceOfBirth = "Thừa Thiên Huế";
			case "048" -> checkPlaceOfBirth = "Đà Nẵng";
			case "049" -> checkPlaceOfBirth = "Quảng Nam";
			case "051" -> checkPlaceOfBirth = "Quảng Ngãi";
			case "052" -> checkPlaceOfBirth = "Bình Định";

			case "054" -> checkPlaceOfBirth = "Phú Yên";
			case "056" -> checkPlaceOfBirth = "Khánh Hòa";
			case "058" -> checkPlaceOfBirth = "Khánh Hòa";
			case "060" -> checkPlaceOfBirth = "Bình Thuận";
			case "062" -> checkPlaceOfBirth = "Kon Tum";
			case "064" -> checkPlaceOfBirth = "Gia Lai";
			case "066" -> checkPlaceOfBirth = "Đắk Lắk";
			case "067" -> checkPlaceOfBirth = "Đắk Nông";
			case "068" -> checkPlaceOfBirth = "Lâm Đồng";
			case "070" -> checkPlaceOfBirth = "Bình Phước";

			case "072" -> checkPlaceOfBirth = "Tây Ninh";
			case "074" -> checkPlaceOfBirth = "Bình Dương";
			case "075" -> checkPlaceOfBirth = "Đồng Nai";
			case "077" -> checkPlaceOfBirth = "Bà Rịa - Vũng Tàu";
			case "079" -> checkPlaceOfBirth = "Hồ Chí Minh";

			case "080" -> checkPlaceOfBirth = "Long An";
			case "082" -> checkPlaceOfBirth = "Tiền Giang";
			case "083" -> checkPlaceOfBirth = "Bến Tre";
			case "084" -> checkPlaceOfBirth = "Trà Vinh";
			case "086" -> checkPlaceOfBirth = "Vĩnh Long";

			case "087" -> checkPlaceOfBirth = "Đồng Tháp";
			case "089" -> checkPlaceOfBirth = "An Giang";
			case "091" -> checkPlaceOfBirth = "Kiên Giang";
			case "092" -> checkPlaceOfBirth = "Cần Thơ";
			case "093" -> checkPlaceOfBirth = "Hậu Giang";

			case "094" -> checkPlaceOfBirth = "Sóc Trăng";
			case "095" -> checkPlaceOfBirth = "Bạc Liêu";
			case "096" -> checkPlaceOfBirth = "Cà Mau";
		}
		System.out.println("Nơi sinh: " + checkPlaceOfBirth);
	} //Kiểm tra và hiển thị nơi sinh

	public static void checkGender(String cccd) {
		String gender = cccd.substring(3, 4);
		String yearBirth = cccd.substring(4, 6);

		switch (gender) {
			case "0" -> {
				gender = "nam";
				yearBirth = "19" + yearBirth;
			}
			case "1" -> {
				gender = "nữ";
				yearBirth = "19" + yearBirth;
			}
			case "2" -> {
				gender = "nam";
				yearBirth = "20" + yearBirth;
			}
			case "3" -> {
				gender = "nữ";
				yearBirth = "20" + yearBirth;
			}
			case "4" -> {
				gender = "nam";
				yearBirth = "21" + yearBirth;
			}
			case "5" -> {
				gender = "nữ";
				yearBirth = "21" + yearBirth;
			}
			case "6" -> {
				gender = "nam";
				yearBirth = "22" + yearBirth;
			}
			case "7" -> {
				gender = "nữ";
				yearBirth = "22" + yearBirth;
			}
			case "8" -> {
				gender = "nam";
				yearBirth = "23" + yearBirth;
			}
			case "9" -> {
				gender = "nữ";
				yearBirth = "23" + yearBirth;
			}
		}
		System.out.println("Giới tính: " + gender + " , Năm sinh: " + yearBirth);
	} // Kiểm tra và hiển thị giới tính

	private static void checkRandomNumber(String cccd) {
		String randomNumber = cccd.substring(6);
		System.out.println("Số ngẫu nhiên: " + randomNumber);
	} //Kiểm tra và hiển thị số ngẫu nhiên

	private static void deployFeatures(String cccd) {

		boolean quit = false;
		while (!quit) {
			try {

				System.out.print(
						"""
								  |1. Kiểm tra nơi sinh
								  |2. Kiểm tra năm sinh, giới tính
								  |3. Kiểm tra số ngẫu nhiên
								  |0. Thoát
								Chọn chức năng:\s"""
				);
				int s = Integer.parseInt(scanner.nextLine());
				switch (s) {
					case 0 -> quit = true;

					case 1 -> checkPlaceOfBirth(cccd);

					case 2 -> checkGender(cccd);

					case 3 -> checkRandomNumber(cccd);

					default -> System.out.println("Lựa chọn không đúng. Vui lòng chọn lại. ");

				}
			} catch (Exception e) {
				System.out.println("Vui lòng nhập kí tự số.");
			}
		}
	} //Hiển thị chức năng và chọn chức năng

	private static void verifyPasscode() {
		System.out.println("""
				1. EASY
				2. HARD
				Vui lòng chọn kiểu xác thực.""");
		while (true) {
			try {
				int option = Integer.parseInt(scanner.nextLine());
				switch (option) {
					case 1 -> {
						verifyEasy();
						return;
					}
					case 2 -> {
						verifyHard();
						return;
					}
					default -> System.out.println("Lựa chọn không khả dụng. Vui lòng thử lại. ");
				}

			} catch (NumberFormatException e) {
				System.out.println("Vui lòng nhập kí tự số.");
			}
		}
	} //Xác thực người dùng

	private static void verifyEasy() {
		int attemps = 3;
		while (attemps-- > 0) {
			String secretCode = (String.valueOf(new Random().nextInt(900) + 100)); //Khởi tạo giá trị ngẫu nhiên là số có 3 chữ số.
			System.out.println("Nhập mã xác thực: " + secretCode);
			String codeUserInput = scanner.nextLine();
			if (Objects.equals(codeUserInput, secretCode)) {
				return;
			} else {
				if (attemps == 0) {
					System.out.println("Đã quá số lần nhập mã xác thực. Vui lòng thực hiện lại.");
					verifyPasscode();
				} else
					System.out.println("Sai mã xác thực. Còn lại " + attemps + " lần nhập." + " Vui lòng nhập lại.");
			}
		}
	} //Xác thực người dùng easy

	private static void verifyHard() {
		int attemps = 3;
		while (attemps-- > 0) {
			String passcodeToCheck = randomPasscode();
			System.out.println("Vui lòng nhập passcode (" + passcodeToCheck + "):");
			String passcode = scanner.nextLine();
			if (passcodeToCheck.equals(passcode)) {
				break;
			} else {
				if (attemps == 0) {
					System.out.println("Đã quá số lần nhập mã xác thực. Vui lòng thực hiện lại.");
					verifyPasscode();
				} else
					System.out.println("Sai mã xác thực. Còn lại " + attemps + " lần nhập." + " Vui lòng nhập lại.");

			}
		}
	}  // Xác thực người dùng hard

	private static String randomPasscode() {
		String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lower = upper.toLowerCase();
		String digits = "0123456789";
		String alphabet = upper + lower + digits;

		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			int index = random.nextInt(alphabet.length());
			char c = alphabet.charAt(index);
			sb.append(c);
		}
		return sb.toString();
	} //Khởi tạo chuỗi ký tự ngẫu nhiên
}


