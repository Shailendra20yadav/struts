

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MergeExcelFiles {
	
	public static String outputFileName="MergedExcelFiles.xlsx";
	public static String sourceFilePath= "files/";
	
	public static List<FileInputStream> readAllExcelFiles() throws Exception{
		List<FileInputStream> list = new ArrayList<FileInputStream>();
		File dir = new File(sourceFilePath);
	    if (dir.isDirectory()) {
	        File[] xlsFiles = dir.listFiles(new FilenameFilter() {

	            @Override
	            public boolean accept(File folder, String name) {
	                return name.toLowerCase().endsWith(".xls") || name.toLowerCase().endsWith(".xlsx") ;
	            }
	        });
	        if (xlsFiles ==null){
	        	System.out.println("No xls file exists");
	        	throw new Exception("No xls file exists");
	        
	        }else{

	        	for (File file : xlsFiles) {
					list.add(new FileInputStream(file));
				}
	        }
	    }
		return list;
	}

	public static void main(String[] args) throws Exception {
		
		File outputFile = new File(sourceFilePath+outputFileName);
		if (outputFile.exists()){
			outputFile.delete();
		}
		mergeExcelFiles(outputFile, readAllExcelFiles());
		System.out.println("Files merged successfully");

	}
	  public static void mergeExcelFiles(File file, List<FileInputStream> list) throws IOException {
		    XSSFWorkbook book = new XSSFWorkbook();
		    XSSFSheet sheet = book.createSheet(file.getName());
		    int count =0;
		    for (FileInputStream fin : list) {
		      XSSFWorkbook b = new XSSFWorkbook(fin);
		      for (int i = 0; i < b.getNumberOfSheets(); i++) {
		        copySheets(book, sheet, b.getSheetAt(i),count+i);
		      }
		      count++;
		    }
		    
		    try {
		      writeFile(book, file);
		    } catch (Exception e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
		  }
		  
		  protected static void writeFile(XSSFWorkbook book, File file) throws Exception {
		    FileOutputStream out = new FileOutputStream(file);
		    book.write(out);
		    out.close();
		  }
		  
		  private static void copySheets(XSSFWorkbook newWorkbook, XSSFSheet newSheet, XSSFSheet sheet, int sheetNo){     
		    copySheets(newWorkbook, newSheet, sheet, true,sheetNo);
		  }     

		  private static void copySheets(XSSFWorkbook newWorkbook, XSSFSheet newSheet, XSSFSheet sheet, boolean copyStyle,int sheetNo){     
		    int newRownumber = newSheet.getLastRowNum()==0 ?newSheet.getLastRowNum():newSheet.getLastRowNum() + 1;
		    int maxColumnNum = 0;     
		    Map<Integer, XSSFCellStyle> styleMap = (copyStyle) ? new HashMap<Integer, XSSFCellStyle>() : null; 
		    
		    int getroNnumber =0;
		    int createRowNumber = 0;
		    for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) { 
		    	if(sheetNo >0 && i==0) {
		    		getroNnumber++;
		    		 continue;
		    	}
		      XSSFRow srcRow = sheet.getRow(getroNnumber);     
		      XSSFRow destRow = newSheet.createRow(createRowNumber + newRownumber);     
		      if (srcRow != null ) { 
		        copyRow(newWorkbook, sheet, newSheet, srcRow, destRow, styleMap);     
		        if (srcRow.getLastCellNum() > maxColumnNum) {     
		            maxColumnNum = srcRow.getLastCellNum();     
		        }     
		      }   
		      getroNnumber++;
		      createRowNumber++;
		    }     
		    for (int i = 0; i <= maxColumnNum; i++) {     
		      newSheet.setColumnWidth(i, sheet.getColumnWidth(i));     
		    }     
		  }     
		  
		  public static void copyRow(XSSFWorkbook newWorkbook, XSSFSheet srcSheet, XSSFSheet destSheet, XSSFRow srcRow, XSSFRow destRow, Map<Integer, XSSFCellStyle> styleMap) {     
		    destRow.setHeight(srcRow.getHeight());
		    for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum(); j++) {     
		      XSSFCell oldCell = srcRow.getCell(j);
		      XSSFCell newCell = destRow.getCell(j);
		      if (oldCell != null) {     
		        if (newCell == null) {     
		          newCell = destRow.createCell(j);     
		        }     
		        copyCell(newWorkbook, oldCell, newCell, styleMap);
		      }     
		    }                
		  }
		  
		  public static void copyCell(XSSFWorkbook newWorkbook, XSSFCell oldCell, XSSFCell newCell, Map<Integer, XSSFCellStyle> styleMap) {      
		    if(styleMap != null) {     
		      int stHashCode = oldCell.getCellStyle().hashCode();     
		      XSSFCellStyle newCellStyle = styleMap.get(stHashCode);     
		      if(newCellStyle == null){     
		        newCellStyle = newWorkbook.createCellStyle();     
		        newCellStyle.cloneStyleFrom(oldCell.getCellStyle());     
		        styleMap.put(stHashCode, newCellStyle);     
		      }     
		      newCell.setCellStyle(newCellStyle);   
		    }     
		    switch(oldCell.getCellType()) {     
		      case XSSFCell.CELL_TYPE_STRING:     
		        newCell.setCellValue(oldCell.getRichStringCellValue());     
		        break;     
		      case XSSFCell.CELL_TYPE_NUMERIC:     
		        newCell.setCellValue(oldCell.getNumericCellValue());     
		        break;     
		      case XSSFCell.CELL_TYPE_BLANK:     
		        newCell.setCellType(XSSFCell.CELL_TYPE_BLANK);     
		        break;     
		      case XSSFCell.CELL_TYPE_BOOLEAN:     
		        newCell.setCellValue(oldCell.getBooleanCellValue());     
		        break;     
		      case XSSFCell.CELL_TYPE_ERROR:     
		        newCell.setCellErrorValue(oldCell.getErrorCellValue());     
		        break;     
		      case XSSFCell.CELL_TYPE_FORMULA:     
		        newCell.setCellFormula(oldCell.getCellFormula());     
		        break;     
		      default:     
		        break;     
		    }
		  }
	
	

}
