package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mehmet on 23-4-17.
 */
public class Bankafschrift {
    private List<BankafschriftItem> items = new ArrayList<>();

    public Bankafschrift() {
    }

    public List<BankafschriftItem> getItems() {
        return items;
    }

    public void setItems(List<BankafschriftItem> items) {
        this.items = items;
    }

    public void addItem(BankafschriftItem item) {
        items.add(item);
    }

    @Override
    public String toString() {
        String afschrift = new String();
        for (BankafschriftItem item : items) {
            afschrift += "item " + item.getId() + " [datum= " + item.getDate() + ", bij/af=" + item.getBijafCode() +
                    ", bedrag=" + item.getBedrag() + ", Naar_Naam=" + item.getNaamRekeninghouder() +
                    ", TegenRekening=" + item.getIban() + ", Omschrijving=" + item.getOmschrijving() + "] \n\r";

        }
        return afschrift;
    }
}
