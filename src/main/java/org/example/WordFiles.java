package org.example;

import org.apache.poi.xwpf.usermodel.*;
import org.example.enums.UserLang;
import org.example.enums.UserStatus;
import org.example.model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static org.example.DataBase.users;

public class WordFiles {
    public static void resiver() {
        try {
            XWPFDocument document = new XWPFDocument();
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            run.setBold(true);
            run.setText("Userlar ro'yhati !!!");

            XWPFTable table = document.createTable();
            XWPFTableRow row = table.getRow(0);
            XWPFTableCell cell = row.getCell(0);
            cell.setWidthType(TableWidthType.AUTO);
            cell.setText("UserId");
            row.createCell().setText("name");

            for (Map.Entry<Long, User> u : DataBase.users.entrySet()) {
                row = table.createRow();
                row.getCell(0).setText(String.valueOf(u.getValue().getUserId()));
                row.getCell(1).setText(u.getValue().getName());
            }

            FileOutputStream out = new FileOutputStream(new File("src/main/resources/word1.docx"));
            document.write(out);
            out.close();
            System.out.println("succesfully word create");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
