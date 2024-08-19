package com.janta.billing.util;

import org.springframework.web.multipart.MultipartFile;

import com.janta.billing.exception.SystemException;

public class FileUtils {

	public static void validateExcelUploadFile(MultipartFile file) throws SystemException{
		// ANNIT.PDF 22/03/2024 , 22-06/2024
		// 2d,3d, 
		String[] fileName = file.getOriginalFilename().split("\\.");
		String filetype = fileName[fileName.length-1];
		long size = file.getSize();
		if(!filetype.equals("xlsx")) {
			throw new SystemException("Please Uplaod .xlsx file");
		}
		else if(size > 50000l) {
			throw new SystemException("File size is very big");
		}
	}
}
