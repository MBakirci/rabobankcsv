package utils;

/**
 * Created by mehmet on 23-4-17.
 */

import domain.Bankafschrift;
import domain.BankafschriftItem;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.*;
import jxl.write.Number;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ExcelWriter {
    private final DateFormat customDateFormat = new DateFormat("dd/MM/yyyy");
    private final WritableCellFormat dateFormat = new WritableCellFormat(customDateFormat);

    private WritableCellFormat times;
    private String inputFile;
    private Bankafschrift bankafschrift;

    public ExcelWriter(Bankafschrift bankafschrift) {
        this.bankafschrift = bankafschrift;
    }

    public void setOutputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void write() throws IOException, WriteException {
        File file = new File(inputFile);
        WorkbookSettings wbSettings = new WorkbookSettings();

        wbSettings.setLocale(new Locale("en", "EN"));

        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        workbook.createSheet("afschrift " + new SimpleDateFormat("dd-MM-YYYY").format(new GregorianCalendar().getTime()), 0);
        WritableSheet excelSheet = workbook.getSheet(0);
        createLabel(excelSheet);
        createContent(excelSheet);

        workbook.write();
        workbook.close();
    }

    private void createLabel(WritableSheet sheet)
            throws WriteException {
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        times = new WritableCellFormat(times10pt);
        times.setWrap(true);
        CellView cv = new CellView();
        cv.setFormat(times);
        cv.setAutosize(true);

        //headers
        addHeader(sheet, 0, "Datum");
        addHeader(sheet, 1, "Rek. Nr.");
        addHeader(sheet, 2, "Rekeninghouder");
        addHeader(sheet, 3, "Ontvangst");
        addHeader(sheet, 4, "BTW");
        addHeader(sheet, 5, "Uitgaaf");
        addHeader(sheet, 6, "BTW");
        addHeader(sheet, 8, "Omschrijving");
    }

    private void createContent(WritableSheet sheet) throws WriteException {
        for (int i = 0; i < bankafschrift.getItems().size(); i++) {
            BankafschriftItem item = bankafschrift.getItems().get(i);
            int row = i + 1;
            //Datum
            addDate(sheet, 0, row, item.getDate());
            //Rek. Nr.
            addText(sheet, 1, row, item.getIban());
            //Rekeninghouder
            addText(sheet, 2, row, item.getNaamRekeninghouder());
            if (item.getBijafCode() == 'C') {
                // Ontvangst
                addCurrency(sheet, 3, row, item.getBedrag());
                // BTW
                addCurrency(sheet, 4, row, item.getBtwBedrag());
            } else {
                // Uitgaaf
                addCurrency(sheet, 5, row, item.getBedrag());
                // BTW
                addCurrency(sheet, 6, row, item.getBtwBedrag());
            }
            //Overige omschrijving
            addText(sheet, 8, row, item.getOmschrijving());
        }
    }

    private void addHeader(WritableSheet sheet, int column, String s)
            throws WriteException {
        Label label;
        label = new Label(column, 0, s);
        sheet.addCell(label);
    }

    private void addNumber(WritableSheet sheet, int column, int row,
                           Integer integer) throws WriteException {
        Number number;
        number = new Number(column, row, integer, times);
        sheet.addCell(number);
    }

    private void addCurrency(WritableSheet sheet, int column, int row,
                             Double d) throws WriteException {

        NumberFormat currencyFormat = new NumberFormat("€" + " ###,###.00", NumberFormat.COMPLEX_FORMAT);
        WritableCellFormat lCurrencyFormat = new WritableCellFormat(currencyFormat);
        Number currency = new Number(column, row, d, lCurrencyFormat);
        sheet.addCell(currency);
    }

    private void addText(WritableSheet sheet, int column, int row, String s)
            throws WriteException {
        Label label;
        label = new Label(column, row, s);
        sheet.addCell(label);
    }

    private void addDate(WritableSheet sheet, int column, int row, Date date) throws WriteException {
        DateTime dateTime = new DateTime(column, row, date, dateFormat);
        sheet.addCell(dateTime);
    }

}
