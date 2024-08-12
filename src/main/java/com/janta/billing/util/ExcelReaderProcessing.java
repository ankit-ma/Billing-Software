package com.janta.billing.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;

import com.janta.billing.exception.SystemException;




public abstract class ExcelReaderProcessing implements ExcelReader {

	private InputStream file;
	private Map<Integer, String> headerMap;
	
	ExcelReaderProcessing(InputStream file,Map<Integer, String> headerMap) {
		this.file = file;
		this.headerMap = headerMap;
	}

	public void readExcel() throws SystemException {
		try (InputStream fis = this.file; Workbook workbook = new XSSFWorkbook(fis)) {

			Sheet sheet = workbook.getSheetAt(0); // Reading the first sheet
			beforeProcessing();

			int rowNumber =0;
			for (Row row : sheet) {
				Map<Integer, String> rowData = new HashMap<>();
				
				for (Cell cell : row) {
					switch (cell.getCellType()) {
					case STRING:
						rowData.put(cell.getColumnIndex(), cell.getStringCellValue());
						break;
					case NUMERIC:
						rowData.put(cell.getColumnIndex(), String.valueOf(cell.getNumericCellValue()));
						break;
					case BOOLEAN:
						rowData.put(cell.getColumnIndex(), String.valueOf(cell.getBooleanCellValue()));
						break;
					default:
						rowData.put(cell.getColumnIndex(), "");
					}
				}
				if(row.getRowNum() ==0) {
					
					if(!validateHeader(rowData)) {
						throw new SystemException("Headers are not matching, Kindly download templete");
					}
				}
				else {
					processRow(rowData);
				}
				
			}

			afterProcessing();
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException(e.getMessage());
		}
	}

	public Map<Integer, String> getHeadMap(){
		return this.headerMap;
	}
	public boolean validateHeader(Map<Integer, String> header) {
		if(header.size()!=headerMap.size()) return false;
		for(Entry<Integer, String> entry:headerMap.entrySet()) {
			Integer key = entry.getKey();
			String value = entry.getValue();
			if(!header.get(key).equals(value)) {
				return false;
			}
		}
		return true;
	}
}
