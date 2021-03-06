package javafx;

import domain.Bankafschrift;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import jxl.write.WriteException;
import utils.CSVReader;
import utils.ExcelWriter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class MainController {
  public Button btnOpen;
  public TextField txtFilePath;
  public Button btnSave;
  private CSVReader csvReader = new CSVReader("", ",");
  private Bankafschrift afschrift;

  private static String downloadsDir = System.getProperty("user.home") + File.separator + "Downloads";

  public void OnOpen(ActionEvent actionEvent) throws ParseException {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(downloadsDir));
    fileChooser.setTitle("Open bankafschrift");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
        new FileChooser.ExtensionFilter("Text Files", "*.txt"));
    File selectedFile = fileChooser.showOpenDialog(btnOpen.getScene().getWindow());
    if (selectedFile != null) {
      this.txtFilePath.setText(selectedFile.getAbsolutePath());
      this.txtFilePath.end();
      this.afschrift = csvReader.read(txtFilePath.getText());
      System.out.print(this.afschrift.toString());
      this.btnSave.setDisable(false);
    }
  }

  public void OnSave(ActionEvent actionEvent) throws IOException, WriteException {
    ExcelWriter writer = new ExcelWriter(afschrift);
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(downloadsDir));
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xls)", "*.xls");
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showSaveDialog(btnSave.getScene().getWindow());

    if (file != null) {
      String fileName = file.getAbsolutePath();
      if (!fileName.endsWith(".xls")) {
        fileName = fileName + ".xls";
      }
      writer.setOutputFile(fileName);
      writer.write();
      Desktop.getDesktop().open(new File(fileName));
    }

  }
}
