package com.janta.billing.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.janta.billing.document.Product;
import com.janta.billing.dto.CoreRequestDto;
import com.janta.billing.service.SolrSearchService;


import jakarta.validation.Valid;


@RestController
@RequestMapping("/solr")
public class SolrSearchController {

	@Autowired
	private SolrSearchService solrSearchService;
	
	@PostMapping("/publish-core")
	public ResponseEntity<String> publishCore( @RequestBody CoreRequestDto coreRequest){
		
		try {
		String responseString = solrSearchService.publishSolrCore((String)coreRequest.getCoreName(), (Map<String, String>)coreRequest.getFields());
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseString);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/index/products")
	public ResponseEntity<String> indexProduct(){
		try {
			
			solrSearchService.indexProductsIntoSolr();
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Indexed succesfully");
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/search/products/{keyword}")
	public ResponseEntity<?> searchProduct(@PathVariable String keyword){
		try {
			
			List<Product>resultList =  solrSearchService.searchProductInSolr(keyword);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(resultList);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
