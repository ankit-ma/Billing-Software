package com.janta.billing.service;

import org.springframework.core.io.ByteArrayResource;

public interface PdfService {

	public ByteArrayResource createPdfFromHtml(String htmlContent);
}
