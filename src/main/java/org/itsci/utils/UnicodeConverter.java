package org.itsci.utils;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class UnicodeConverter {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Unicode Converter");
    System.out.println("1. Convert Unicode escapes to readable text");
    System.out.println("2. Convert readable text to Unicode escapes");
    System.out.print("Choose option (1 or 2): ");

    String choice = scanner.nextLine();

    try {
      switch (choice) {
        case "1":
          convertUnicodeToReadable();
          break;
        case "2":
          convertReadableToUnicode();
          break;
        default:
          System.out.println("Invalid choice. Running both conversions...");
          convertUnicodeToReadable();
          convertReadableToUnicode();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    scanner.close();
  }

  /**
   * Convert Unicode escapes to readable text
   */
  public static void convertUnicodeToReadable() throws IOException {
    System.out.println("Converting Unicode escapes to readable text...");

    Properties props = new Properties();
    try (FileInputStream fis = new FileInputStream("src/main/resources/messages.properties")) {
      props.load(fis);
    }

    try (OutputStreamWriter writer = new OutputStreamWriter(
        new FileOutputStream("src/main/resources/messages_readable.properties"), "UTF-8")) {

      // Write header comment
      writer.write("# Readable version of messages.properties\n");
      writer.write("# Edit this file and then use option 2 to convert back\n\n");

      for (String key : props.stringPropertyNames()) {
        writer.write(key + "=" + props.getProperty(key) + "\n");
      }
    }

    System.out.println("✓ Converted to messages_readable.properties");
  }

  /**
   * Convert readable text to Unicode escapes
   */
  public static void convertReadableToUnicode() throws IOException {
    System.out.println("Converting readable text to Unicode escapes...");

    File readableFile = new File("src/main/resources/messages_readable.properties");
    if (!readableFile.exists()) {
      System.out.println("❌ messages_readable.properties not found!");
      System.out.println("   Please run option 1 first or create the file manually.");
      return;
    }

    Properties props = new Properties();
    try (InputStreamReader reader = new InputStreamReader(
        new FileInputStream(readableFile), "UTF-8")) {
      props.load(reader);
    }

    // Backup the original file
    File originalFile = new File("src/main/resources/messages.properties");
    File backupFile = new File("src/main/resources/messages.properties.backup");
    if (originalFile.exists()) {
      copyFile(originalFile, backupFile);
      System.out.println("✓ Created backup: messages.properties.backup");
    }

    // Write with Unicode escapes
    try (FileOutputStream fos = new FileOutputStream(originalFile)) {
      props.store(fos, "Generated properties file with Unicode escapes");
    }

    System.out.println("✓ Converted readable text to Unicode escapes in messages.properties");
  }

  /**
   * Copy file utility method
   */
  private static void copyFile(File source, File dest) throws IOException {
    try (FileInputStream fis = new FileInputStream(source);
        FileOutputStream fos = new FileOutputStream(dest)) {

      byte[] buffer = new byte[1024];
      int bytesRead;
      while ((bytesRead = fis.read(buffer)) != -1) {
        fos.write(buffer, 0, bytesRead);
      }
    }
  }

  /**
   * Manual conversion method for specific strings
   */
  public static String toUnicodeEscape(String input) {
    StringBuilder output = new StringBuilder();
    for (char c : input.toCharArray()) {
      if (c > 127) {
        output.append(String.format("\\u%04X", (int) c));
      } else {
        output.append(c);
      }
    }
    return output.toString();
  }

  /**
   * Manual conversion method from Unicode escapes
   */
  public static String fromUnicodeEscape(String input) {
    StringBuilder output = new StringBuilder();
    int i = 0;
    while (i < input.length()) {
      if (i < input.length() - 5 && input.charAt(i) == '\\' && input.charAt(i + 1) == 'u') {
        try {
          String hexCode = input.substring(i + 2, i + 6);
          int charCode = Integer.parseInt(hexCode, 16);
          output.append((char) charCode);
          i += 6;
        } catch (NumberFormatException e) {
          output.append(input.charAt(i));
          i++;
        }
      } else {
        output.append(input.charAt(i));
        i++;
      }
    }
    return output.toString();
  }
}
