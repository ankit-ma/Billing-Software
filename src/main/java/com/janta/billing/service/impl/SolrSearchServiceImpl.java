package com.janta.billing.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CoreAdminParams;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.janta.billing.document.Product;
import com.janta.billing.exception.SystemException;
import com.janta.billing.repository.InventoryRepository;
import com.janta.billing.service.SolrSearchService;


@Service
public class SolrSearchServiceImpl implements SolrSearchService {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Value("${solr.core}")
	private String core;
	
	@Autowired
	private SolrClient solrClient;
	@Autowired
	private InventoryRepository inventoryRepository;
	

	@Override
	public void indexProductsIntoSolr() throws InvocationTargetException{
		try {
			List<SolrInputDocument> products = new LinkedList<>();
			inventoryRepository.findAll().forEach(inventory -> {
				SolrInputDocument document = new SolrInputDocument();
				document.addField("productName",inventory.getProductName()+" - "+inventory.getBrandName());
				document.addField("brandName",inventory.getBrandName());
				document.addField("mrp",inventory.getMrp());
				document.addField("cgst",inventory.getCgst());
				document.addField("sgst",inventory.getSgst());
				document.addField("discount",inventory.getDiscount());
				document.addField("quantity",inventory.getQuantity());
				document.addField("thresholdQuantity",inventory.getThresholdQuantity());
				document.addField("additionalInfo",inventory.getAdditionalInfo());
				document.addField("productId",inventory.getId());
				products.add(document);
			
			});
			solrClient.deleteByQuery(core, "*:*");
			solrClient.add(core, products);
			solrClient.commit(core);
		}
		catch(Exception e) {
			throw new SystemException(e.getMessage());
		}

	}

	@Override
	public List<Product> searchProductInSolr(String keyword) {

		try {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("productName:" + keyword );

        QueryResponse response = solrClient.query(core, solrQuery);

        List<Product> products = new ArrayList<>();
        List<SolrDocument> docs = response.getResults();
        if(!docs.isEmpty()) {
        	for(SolrDocument doc:docs) {
        		Product product = Product.builder()
        				.productId(((List<Object>)doc.get("productId")).get(0))
        				.productName(((List<Object>)doc.get("productName")).get(0))
        				.brandName(((List<Object>)doc.get("brandName")).get(0))
        				.additionalInfo(((List<Object>)doc.get("additionalInfo")).get(0))
        				.cgst(((List<Object>)doc.get("cgst")).get(0))
        				.sgst(((List<Object>)doc.get("sgst")).get(0))
        				.mrp(((List<Object>)doc.get("mrp")).get(0))
        				.discount(((List<Object>)doc.get("discount")).get(0))
        				.quantity(1)
        				.availableQuantity(((List<Object>)doc.get("quantity")).get(0))
        				.build();
        		products.add(product);
        	}
        }
        return products;
		}
		catch(Exception e) {
			throw new SystemException(e.getMessage());
		}
	}

	@Override
	public String publishSolrCore(String coreName, Map<String, String> fields) throws SolrServerException, IOException {

		  if (doesCoreExist(coreName)) {
	            // If it exists, delete the core
	            deleteCore(coreName);
	        }

	        // Create the core with the specified fields
	        createCore(coreName, fields);

	        return "Core '" + coreName + "' published successfully!";
	}
	 private boolean doesCoreExist(String coreName) throws SolrServerException, IOException {
	        CoreAdminRequest request = new CoreAdminRequest();
	        request.setAction(CoreAdminParams.CoreAdminAction.STATUS);

	        CoreAdminResponse cores = request.process(solrClient);

	        return cores.getCoreStatus(coreName) != null;
	    }

	    private void deleteCore(String coreName) throws SolrServerException, IOException {
	        CoreAdminRequest.Unload unloadRequest = new CoreAdminRequest.Unload(true);
	        unloadRequest.setCoreName(coreName);
	        unloadRequest.setDeleteInstanceDir(true);
	        solrClient.request(unloadRequest);
	    }

	    private void createCore(String coreName, Map<String, String> fields) throws SolrServerException, IOException {
	        // Create the core
	        CoreAdminRequest.Create createRequest = new CoreAdminRequest.Create();
	        createRequest.setCoreName(coreName);
	        createRequest.setInstanceDir(coreName);
	    //    createRequest.setConfigSet("/opt/solr/server/solr/configsets/_default");
	        solrClient.request(createRequest);

	        // Define fields for the core
	        for (Map.Entry<String, String> field : fields.entrySet()) {
	            Map<String, Object> fieldAttributes = new HashMap<>();
	            fieldAttributes.put("name", field.getKey());
	            fieldAttributes.put("type", field.getValue());
	            fieldAttributes.put("stored", true);
	            fieldAttributes.put("indexed", true);

	            SchemaRequest.AddField addFieldRequest = new SchemaRequest.AddField(fieldAttributes);
	            SchemaResponse.UpdateResponse response = addFieldRequest.process(solrClient, coreName);

	            if (response.getStatus() != 0) {
	                throw new RuntimeException("Error adding field: " + field.getKey());
	            }
	        }
	    }
}
