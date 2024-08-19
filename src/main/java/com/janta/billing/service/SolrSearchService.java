package com.janta.billing.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

import com.janta.billing.document.Product;


public interface SolrSearchService {

	public String publishSolrCore(String coreName, Map<String, String> fields) throws SolrServerException, IOException;
	public void indexProductsIntoSolr() throws InvocationTargetException;
	public List<Product> searchProductInSolr(String keyword); 
}
