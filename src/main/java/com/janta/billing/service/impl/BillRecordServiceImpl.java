package com.janta.billing.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import com.janta.billing.configuration.EmailService;
import com.janta.billing.entity.*;
import com.janta.billing.repository.*;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.janta.billing.dto.BillGenerateDto;
import com.janta.billing.exception.SystemException;
import com.janta.billing.service.BillRecordService;
import com.janta.billing.service.PdfService;
import com.janta.billing.util.Constants;
import com.nimbusds.jose.shaded.gson.Gson;



@Service
public class BillRecordServiceImpl implements BillRecordService {

	private static Logger LOGGER = LoggerFactory.getLogger(BillRecordServiceImpl.class);

	@Autowired
	private DueRecordRepository dueRecordRepository;

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private CustomerDetailsRepository customerDetailsRepository;

	@Autowired 
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private PdfService pdfService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private EmailTemplateRepository emailTemplateRepository;
	@Override
	@Transactional
	public ResponseEntity<Resource> generateBillForCustomer(BillGenerateDto billGenerateDto, Long employeeId) throws IOException {
		LOGGER.error(billGenerateDto.toString());
		
		CustomerDetails customerDetails = customerDetailsRepository
				.findByPhoneNumber(billGenerateDto.getCustomerDetails().getPhoneNumber()).orElseGet(null);
		if (customerDetails == null) {
			throw new SystemException("Customer Not found");
		}
		// update in due record
		Double dueAmount = new Double(0l);
		if (billGenerateDto.getPaymentMode().equals("Due")) {
			DueRecord dueRecord = DueRecord.builder()
					.customer(customerDetails)
					.dueAmount(billGenerateDto.getTotalPrice())
					.lastDueAmount(billGenerateDto.getCustomerDetails().getDue())
					.lastUpdatedBy(employeeId)
					.loggedBy(employeeId).build();
			if(customerDetails.getDueRecord()!=null) {
				dueRecord.setId(customerDetails.getDueRecord().getId());
				dueRecordRepository.save(dueRecord);
			}else {
				dueRecordRepository.save(dueRecord);
			}
			
			dueAmount = billGenerateDto.getTotalPrice();
		}

		// add in billrecord
		// String billId = UUID.randomUUID().toString();
		Gson gson = new Gson();
		String productDetail = gson.toJson(billGenerateDto.getProducts());
		AtomicInteger quantity = new AtomicInteger(0);
		List<Inventory> inventoryList = new ArrayList<>();
		
		billGenerateDto.getProducts().forEach(product -> {
		    quantity.addAndGet(product.getQuantity());
		    Inventory inventory = inventoryRepository.findById(product.getProductId()).orElseThrow();
		    inventory.setQuantity(inventory.getQuantity()-product.getQuantity());
		    inventoryRepository.save(inventory);
		    
		});
		int totalQuantity = quantity.get();
		BillRecord billRecord = BillRecord.builder().customerDetails(customerDetails)
				.billAmount(billGenerateDto.getTotalPrice()).dueAmount(dueAmount)
				.totalAmount(billGenerateDto.getCustomerDetails().getTotalAmount())
				.productName(productDetail)
				.quantity(totalQuantity)
				.billAmount(billGenerateDto.getCustomerDetails().getCurrentBillAmount()).loggedBy(employeeId)
				.lastUpdatedBy(employeeId).build();
		billRepository.save(billRecord);

		// make bill
		String htmlContent = createHtmlBill(billGenerateDto,billRecord.getId(),totalQuantity);
		ByteArrayResource resource =  pdfService.createPdfFromHtml(htmlContent);
		// save to localstorage

		Path uploadPath = Paths.get(Constants.BILL_PATH);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		//String fileName = UUID.randomUUID().toString()+".xlsx";
		String fileName =  billRecord.getId()+".pdf";
		Path filePath = uploadPath.resolve(fileName);

		Files.copy(resource.getInputStream(), filePath);
		// send bill over mail
		Map<String,Object> model = new HashMap<>();
		model.put("name",customerDetails.getCustomerName());
		model.put("billAmount",billGenerateDto.getTotalPrice());
		model.put("totalAmount",billGenerateDto.getCustomerDetails().getTotalAmount());
		CompletableFuture.runAsync(()->{
			sendBillOnEmail(Constants.BILL_PATH+fileName,customerDetails.getEmail(),"bill",model);
		});

		// send to response entity
		HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName);
		
        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .contentLength(resource.contentLength())          
                .body(resource);
	}

	private String createHtmlBill(BillGenerateDto billData, String billId, int totalQuantity) {
		Context context = new Context();
		context.setVariable("customerName", billData.getCustomerDetails().getCustomerName());
		context.setVariable("phoneNumber", billData.getCustomerDetails().getPhoneNumber());
		context.setVariable("address", billData.getCustomerDetails().getAddress());
		context.setVariable("due", billData.getCustomerDetails().getDue());
		context.setVariable("totalPrice", billData.getCustomerDetails().getCurrentBillAmount());
		context.setVariable("quantity", totalQuantity);
		context.setVariable("products", billData.getProducts());
		context.setVariable("paymentMode", billData.getPaymentMode());
		context.setVariable("bilId", billId);
		
		return templateEngine.process("bill-template", context);
	}

	private String sendBillOnEmail(String filePath, String to, String templateName, Map<String,Object> model){
		Optional<EmailTemplates> emailMetaData = emailTemplateRepository.findByTemplateName(templateName);

		// Prepare email template
		emailMetaData.ifPresentOrElse((e)->{
			String htmlBody = createDueHtmlEmailBody(e.getDynamicKeys(),model,e.getTemplateName());
			try {
				emailService.sendMimeEmailWithAttachment(to,"Janta store bill",htmlBody,filePath);
			} catch (MessagingException ex) {
				throw new RuntimeException(ex);
			}

		}, ()->{
			throw new SystemException("There is no email template");
		});

		// send mail using service
		return "successfully mail sent";
	}

	public String createDueHtmlEmailBody(String keys,Map<String,Object> content,String templateName) {
		Context context = new Context();
		String[] keyArray = keys.split(",");
		for(String key : keyArray) {
			context.setVariable(key,content.get(key));
		}

		return templateEngine.process(templateName, context);
	}
}
