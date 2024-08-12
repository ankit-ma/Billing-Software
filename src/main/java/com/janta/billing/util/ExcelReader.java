package com.janta.billing.util;

import java.util.Map;

public interface ExcelReader {
    void beforeProcessing(); 

    void processRow(Map<Integer, String> rowData); 

    void afterProcessing(); 

}
