package com.janta.billing.service.impl;

import java.io.ByteArrayOutputStream;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.janta.billing.service.PdfService;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

@Service
public class PdfServiceImpl implements PdfService {

	@Override
	public ByteArrayResource createPdfFromHtml(String htmlContent) {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try {
	        PdfRendererBuilder builder = new PdfRendererBuilder();
	        builder.withHtmlContent(htmlContent, null);
	        builder.toStream(outputStream);
	        builder.run();
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to create PDF from HTML", e);
	    }

	    return new ByteArrayResource(outputStream.toByteArray());
	}


}
