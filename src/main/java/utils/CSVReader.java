package utils;

import domain.Bankafschrift;
import domain.BankafschriftItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mehmet on 23-4-17.
 */
public class CSVReader {
  private String line;
  private String splitBy;

  public CSVReader(String line, String splitBy) {
    this.line = line;
    this.splitBy = splitBy;
  }

  public Bankafschrift read(String csvFilePath) throws ParseException {
    Bankafschrift bankafschrift = new Bankafschrift();
    try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
      int id = 1;
      br.readLine(); //Headers
      while ((line = br.readLine()) != null) {
        Pattern pat = Pattern.compile("([\"][^\"]*(?<!\\\\)[\"])");
        Matcher mat = pat.matcher(line);
        String[] items = new String[26];
        int index = 0;
        while (mat.find()) {
          items[index] = mat.group();
          index++;
        }
        for (int i = 0; i < items.length; i++) {
          items[i] = items[i].replaceAll("^\"|\"$", "");
        }
        Date dt = new SimpleDateFormat("yyyy-MM-dd").parse(items[4]);
        Double amount = Double.valueOf(items[6].replace('"',' ').replace(',','.'));
        char code = 'C'; // Credit (Bij)
        if (amount < 0) {
          amount *= -1;
          code = 'D'; // Debit (Af)
        }
        String accountHolder = items[9];
        String iban = items[8];

        BankafschriftItem bankafschriftItem = new BankafschriftItem(id, dt, code, amount, accountHolder, iban, itemDescription(items));
        bankafschrift.addItem(bankafschriftItem);
        id++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return bankafschrift;
  }

  private String itemDescription(String[] item) {
    ArrayList<String> itemDescription = new ArrayList<>();
    for (int i = 19; i < 22; i++) {
      itemDescription.add(item[i]);
    }
    itemDescription.removeAll(Arrays.asList("", null));
    return String.join(" ", itemDescription);
  }


}
