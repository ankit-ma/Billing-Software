package com.janta.billing.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.janta.billing.entity.Inventory;
import com.janta.billing.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.janta.billing.entity.ProductUploadRecord;
import com.janta.billing.service.ProductUploadService;
import com.janta.billing.util.FileUtils;



@RestController
@RequestMapping("/admin/product")
public class ProductBulkController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final String UPLOAD_DIR = "src/main/resources/static/upload-product/";
	
	@Autowired
	private ProductUploadService productUploadService;

	@Autowired
	private InventoryService inventoryService;

	@GetMapping("/download-template")
	public ResponseEntity<Resource> downloadProductTemplete(){
		try {
            // Load the Excel file from the classpath
            Resource resource = new ClassPathResource("static/product_template.xlsx");

            // Set the content disposition to attachment so it prompts a download
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=product_template.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
	}
	
	@PostMapping("/upload-product-list")
	public ResponseEntity<String> uploadProductTemplate(@RequestParam("file") MultipartFile file )
	{
		try {
			if (file.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload");
			}

			FileUtils.validateExcelUploadFile(file);
			
			Path uploadPath = Paths.get(UPLOAD_DIR);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			//String fileName = UUID.randomUUID().toString()+".xlsx";
			String fileName =  productUploadService.processUploadedProductExcel(file);
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(file.getInputStream(), filePath);

			return ResponseEntity.ok("File uploaded successfully: " + fileName);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Could not upload the file: " + e.getMessage());
		}
	}
	
	@GetMapping("/upload-history/{size}/{pageNumber}")
	public ResponseEntity<Page<ProductUploadRecord>> fecthUploadhistory(@PathVariable Integer size,@PathVariable Integer pageNumber){
		
		try {
			Page<ProductUploadRecord> historyList = productUploadService.fetchUploadhistory(size,pageNumber);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(historyList);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Page.empty());
		}
	}

	@GetMapping("/inventry-info/{size}/{pageNumber}")
	public ResponseEntity<Page<Inventory>> fetchInventryDetails(@PathVariable Integer size,@PathVariable Integer pageNumber){
		try {
			Page<Inventory> inventoryList = inventoryService.fetchInventryDetails(size,pageNumber);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(inventoryList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Page.empty());
		}
	}
}
