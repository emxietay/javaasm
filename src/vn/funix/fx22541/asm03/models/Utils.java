package vn.funix.fx22541.asm03.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
  public static String getDivider() {
    return ("+---------+-----------------+--------+");
  }

  public static String getDateTime() {
    LocalDateTime localDateTime = LocalDateTime.now();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    return (localDateTime.format(dateTimeFormatter));
  }

  public static String formatBalance(double amount) {
    return String.format("%,.0f", amount);
  }
}
