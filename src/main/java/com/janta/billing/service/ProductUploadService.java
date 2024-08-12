package com.janta.billing.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.janta.billing.entity.ProductUploadRecord;
import com.janta.billing.exception.SystemException;

public interface ProductUploadService {

	public String processUploadedProductExcel(MultipartFile file) throws SystemException;

	public Page<ProductUploadRecord> fetchUploadhistory(Integer size, Integer pageNumber);
}
