package com.ciandt.techgallery.utils;

import au.com.bytecode.opencsv.CSVWriter;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.profile.UserProfile;
import com.ciandt.techgallery.persistence.model.profile.UserProfileItem;
import com.googlecode.objectify.Ref;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;


/**
 * Created by jneves on 11/03/16.
 */
public class ExportUtils {

  private static final int SHEET_CELL_SIZE = 5;
  private static final String SHEET_NAME = "Dados Gerais";
  private static final String[] SHEET_HEADERS =
          new String[]{"Login", "Nome", "Tecnologia", "Total de Indicações", "Auto-Avaliação"};
  public static final Charset SHEET_CHARSET = StandardCharsets.UTF_8;


  public static byte[] createXlsUsersProfile(List<UserProfile> usersProfile) throws IOException {

    UserProfile.sortUsersProfileByOwnerName(usersProfile);

    Workbook workbook = createUsersProfileWorkbook(usersProfile);

    ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
    workbook.write(outByteStream);
    return outByteStream.toByteArray();
  }

  public static byte[] createCsvUsersProfile(List<UserProfile> usersProfile) throws IOException {

    UserProfile.sortUsersProfileByOwnerName(usersProfile);

    Workbook workbook = createUsersProfileWorkbook(usersProfile);
    return createCsv(workbook).toString().getBytes(SHEET_CHARSET);
  }

  private static Workbook createUsersProfileWorkbook(List<UserProfile> usersProfile) {

    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet();
    workbook.setSheetName(workbook.getSheetIndex(sheet), SHEET_NAME);

    int rownum = 0;
    Row headerRow = sheet.createRow(rownum++);

    for (int rn = 0; rn < SHEET_HEADERS.length; rn++) {
      headerRow.createCell(rn).setCellValue(SHEET_HEADERS[rn]);
    }
    makeRowBold(workbook, headerRow);

    for (UserProfile userProfile : usersProfile) {
      Ref<TechGalleryUser> techGalleryUserRef = userProfile.getOwner();
      TechGalleryUser techGalleryUser = techGalleryUserRef.get();

      if (techGalleryUser != null) {

        for (UserProfileItem tec : userProfile.getAllItems()) {
          if (tec.getSkillLevel() > 0 || tec.getEndorsementQuantity() > 0) {
            Row row = sheet.createRow(rownum++);
            int cellnum = 0;

            row.createCell(cellnum++).setCellValue(createLoginByEmail(techGalleryUser.getEmail()));
            row.createCell(cellnum++).setCellValue(techGalleryUser.getName());
            row.createCell(cellnum++).setCellValue(tec.getTechnologyName());
            row.createCell(cellnum++).setCellValue(tec.getEndorsementQuantity());
            row.createCell(cellnum++).setCellValue(tec.getSkillLevel());
          }
        }
      }
    }
    return workbook;
  }

  private static StringWriter createCsv(Workbook workBook) throws IOException {

    Sheet sheet = workBook.getSheetAt(0);
    Iterator<Row> rowIterator = sheet.iterator();
    StringWriter stringWriter = new StringWriter();
    CSVWriter csvOutput = new CSVWriter(stringWriter);

    while (rowIterator.hasNext()) {
      Row row = rowIterator.next();
      int i = 0;
      String[] cellValues = new String[SHEET_CELL_SIZE];
      Iterator<Cell> cellIterator = row.cellIterator();
      while (cellIterator.hasNext()) {
        Cell cell = cellIterator.next();
        switch (cell.getCellType()) {
          case Cell.CELL_TYPE_STRING:
            cellValues[i] = cell.getStringCellValue();
            break;
          case Cell.CELL_TYPE_NUMERIC:
            cellValues[i] = String.valueOf(cell.getNumericCellValue());
            break;
          case Cell.CELL_TYPE_BLANK:
            cellValues[i] = "";
            break;
        }
        i = i + 1;
      }
      csvOutput.writeNext(cellValues);
    }
    csvOutput.close();
    return stringWriter;
  }

  private static String createLoginByEmail(String email) {
    if (email != null && email.contains("@")) {
      return (email.split("@")[0]);
    }
    return "";
  }

  private static void makeRowBold(HSSFWorkbook wb, Row row) {
    CellStyle style = wb.createCellStyle();
    Font font = wb.createFont();
    font.setBold(true);
    style.setFont(font);

    for (int i = 0; i < row.getLastCellNum(); i++) {
      row.getCell(i).setCellStyle(style);
    }
  }

}
