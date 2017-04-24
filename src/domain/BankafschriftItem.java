package domain;

import java.util.Date;

/**
 * Created by mehmet on 23-4-17.
 */
public class BankafschriftItem {
    private final double btwPercentage = 21d;

    private int id;
    private Date date;
    private char bijafCode;
    private double bedrag;
    private double btwBedrag;
    private String naamRekeninghouder;
    private String iban;
    private String omschrijving;

    public BankafschriftItem(int id, Date date, char bijafCode, double bedrag, String naamRekeninghouder, String iban, String omschrijving) {
        this.id = id;
        this.date = date;
        this.bijafCode = bijafCode;
        this.bedrag = bedrag;
        this.naamRekeninghouder = naamRekeninghouder;
        this.iban = iban;
        this.omschrijving = omschrijving;
        this.btwBedrag = bedrag * (btwPercentage / 100);
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public char getBijafCode() {
        return bijafCode;
    }

    public double getBedrag() {
        return bedrag;
    }

    public double getBtwBedrag() {
        return btwBedrag;
    }

    public String getNaamRekeninghouder() {
        return naamRekeninghouder;
    }

    public String getIban() {
        return iban;
    }

    public String getOmschrijving() {
        return omschrijving;
    }
}
