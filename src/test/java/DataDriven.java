import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class DataDriven {

    public ArrayList<String> getData(String testCaseName) throws IOException {
        FileInputStream fis = new FileInputStream("C:\\Users\\mdsha\\OneDrive\\Desktop\\Demodata.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        int no_of_sheets = workbook.getNumberOfSheets();
        ArrayList<String> testData = new ArrayList<String>();
        for (int i = 0; i < no_of_sheets; i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase("Profile")) {
                XSSFSheet sheet = workbook.getSheetAt(i); //Sheet is collection of rows

                Iterator<Row> rows = sheet.iterator();
                Row firstrow = rows.next(); //Row is collection of cell

                Iterator<Cell> cell = firstrow.cellIterator();
                int k=0;
                int column = 0;
                while (cell.hasNext()){
                    Cell value = cell.next();
                    if(value.getStringCellValue().equalsIgnoreCase("Testcases")){
                        column = k ;
                    }
                    k++;
                }
                System.out.println(column);

                while (rows.hasNext()){
                    Row value = rows.next();
                    if(value.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName)){
                        Iterator<Cell> deptValue = value.cellIterator();
                        while (deptValue.hasNext()){
                            testData.add(deptValue.next().getStringCellValue());
                        }
                    }
                }

            }

        }
        return testData;
    }

}
