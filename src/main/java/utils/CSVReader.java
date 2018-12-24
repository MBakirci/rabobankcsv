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
      while ((line = br.readLine()) != null) {
        Pattern pat = Pattern.compile("([\"][^\"]*(?<!\\\\)[\"])");
        Matcher mat = pat.matcher(line);
        mat.find();
        Double amount = Double.valueOf(mat.group(1).replace('"',' ').replace(',','.'));
        String[] item = line.split(splitBy);
        for (int i = 0; i < item.length; i++) {
          item[i] = item[i].replaceAll("^\"|\"$", "");
        }
        Date dt = new SimpleDateFormat("MM/dd/yyyy").parse(item[4]);
        char code = 'C'; // Credit (Bij)
        if (amount < 0) {
          amount *= -1;
          code = 'D'; // Debit (Af)
        }
        String accountHolder = item[11];
        String iban = item[10];
        itemDescription(item);

        BankafschriftItem bankafschriftItem = new BankafschriftItem(id, dt, code, amount, accountHolder, iban, itemDescription(item));
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
    for (int i = 20; i < 23; i++) {
      itemDescription.add(item[i]);
    }
    itemDescription.removeAll(Arrays.asList("", null));
    return String.join(" ", itemDescription);
  }


}
