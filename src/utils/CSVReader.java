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
                String[] item = line.split(splitBy);
                for (int i = 0; i < item.length; i++) {
                    item[i] = item[i].replaceAll("^\"|\"$", "");
                }
                BankafschriftItem bankafschriftItem = new BankafschriftItem(id, new SimpleDateFormat("yyyyMMdd").parse(item[2]), item[3].charAt(0), Double.parseDouble(item[4]), item[6], item[5], itemDescription(item));
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
        for (int i = 10; i < 19; i++) {
            itemDescription.add(item[i]);
        }
        itemDescription.removeAll(Arrays.asList("", null));
        return String.join(" ", itemDescription);
    }


}
